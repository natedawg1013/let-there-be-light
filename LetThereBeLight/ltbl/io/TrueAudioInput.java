package ltbl.io;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;

import ltbl.iface.AudioInput;
import ltbl.util.RingBuffer;

/**
 * Class which takes audio from various input sources on the computer
 * and buffers it for the later use of FourierAnalysis. It also
 * has static methods to get available sound devices.
 * @author Nathan Bernard
 *
 */
public class TrueAudioInput extends Thread implements AudioInput{
	volatile public RingBuffer rb;
	volatile TargetDataLine line = null;
	Boolean running;
	Thread input = null;
	volatile AudioFormat af;
	Mixer mixer;
	
	/**
	 * allows for getting the data in the buffer
	 */
	public RingBuffer getBuffer(){
		return rb;
	}

	/**
	 * Constructor for TrueAudioInput
	 * @param mi mixer info object identifying the java mixer (line) to use
	 * @param sampleRate audio sample rate in samples per second
	 * @param bufLen length of buffer to allocate
	 * @throws LineUnavailableException
	 */
	public TrueAudioInput(Mixer.Info mi, int sampleRate, int bufLen) throws LineUnavailableException{
		af = new AudioFormat(sampleRate, 8, 1, true, false);
		rb = new RingBuffer(bufLen);
		mixer = AudioSystem.getMixer(mi);
		mixer.open();
		line = (TargetDataLine) AudioSystem.getLine(mixer.getTargetLineInfo()[0]);
		line = AudioSystem.getTargetDataLine(af, mi);
	}
	/**
	 * opens the audio line and starts buffering in its own thread
	 */
	public void begin() throws LineUnavailableException{
		line.open();
		line.start();
		running = Boolean.valueOf(true);
		input = new Thread(this,"input");
		input.start();
	}
	/**
	 * main thread function. Gets audio data from data line and buffers.
	 */
	public void run(){
		while(running){
			byte[] buf2 = new byte[rb.getSize()/20];
			line.read(buf2, 0, buf2.length);
			int len = line.read(buf2, 0, buf2.length);
			rb.write(buf2, len);
		}
		synchronized(this){
			notify();
		}
	}
	
	/**
	 * stops processing
	 */
	public void end(){
		running=false;
		synchronized(input){
			try {
				input.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		line.stop();
		line.close();
		mixer.close();
	}
	/**
	 * gets length of buffer
	 */
	public int bufLen(){
		return rb.length();
	}
	
	/**
	 * gets data in buffer
	 */
	public byte[] getBuf(){
		return rb.peek(rb.length());
	}
	
	/**
	 * lists available mixers (Audio lines)
	 * @return list of Mixer.Info objects describing the available mixers
	 */
	public static List<Mixer.Info> getAvailableMixers(){
		ArrayList<Mixer.Info> mixers = new ArrayList<Mixer.Info>();
		for(Mixer.Info mixerInfo : AudioSystem.getMixerInfo()){
			Mixer mixer = AudioSystem.getMixer(mixerInfo); // default mixer
			try { mixer.open(); }
			catch (LineUnavailableException e) { continue; }
			for(Line.Info info : mixer.getTargetLineInfo()) {
				if(TargetDataLine.class.isAssignableFrom(info.getLineClass())) {
					TargetDataLine.Info info2 = (TargetDataLine.Info) info;
					AudioFormat[] formats = info2.getFormats();
					for(AudioFormat format : formats) {
						if(format.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED) &&
							format.getFrameSize()==1 && format.getSampleSizeInBits()==8 &&
							format.getChannels()==1){
							mixers.add(mixerInfo);
							break;
						}
					}
				}
			}
			mixer.close();
		}
		return mixers;
	}
}


package ltbl.io;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;

import ltbl.util.RingBuffer;

public class TrueAudioInput extends Thread implements AudioInput{
	volatile public RingBuffer rb;
	volatile TargetDataLine line = null;
	
	Boolean running;
	Thread input = null;
	volatile AudioFormat af;
	
	public RingBuffer getBuffer(){
		return rb;
	}

	public TrueAudioInput(Mixer.Info mi, int sampleRate, int bufLen) throws LineUnavailableException{
		af = new AudioFormat(sampleRate, 8, 1, true, false);
		rb = new RingBuffer(bufLen);
		Mixer mixer = AudioSystem.getMixer(mi);
		mixer.open();
		line = (TargetDataLine) AudioSystem.getLine(mixer.getTargetLineInfo()[0]);
		line = AudioSystem.getTargetDataLine(af, mi);
	}
	
	public void begin() throws LineUnavailableException{
		line.open();
		line.start();
		running = Boolean.valueOf(true);
		input = new Thread(this,"input");
		input.start();
	}
	
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
	
	public void end() throws IOException{
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
	}

	public int bufLen(){
		return rb.length();
	}
	
	public byte[] getBuf(){
		return rb.peek(rb.length());
	}
	
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
	
	/*public static List<TargetDataLine.Info> getSupportedTargets(Mixer.Info mixerInfo){
		ArrayList<TargetDataLine.Info> lines = new ArrayList<TargetDataLine.Info>();
		Mixer mixer = AudioSystem.getMixer(mixerInfo);
		Line.Info info = mixer.getTargetLineInfo()
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
	}*/
}


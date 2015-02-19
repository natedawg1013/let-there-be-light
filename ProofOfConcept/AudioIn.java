package ProofOfConcept;
import java.io.*;

import javax.sound.sampled.*;
public class AudioIn implements Runnable{
	volatile public RingBuffer buf;
	volatile TargetDataLine line = null;
	Boolean running;
	Thread input = null;
	volatile AudioFormat af;

	public AudioIn(int mixerNum, int bufLen) throws LineUnavailableException{
		buf = new RingBuffer(bufLen);
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		Mixer mixer = AudioSystem.getMixer(mixerInfo[mixerNum]);
		Line.Info[] targetLineInfo = mixer.getTargetLineInfo();
		line = (TargetDataLine) mixer.getLine(targetLineInfo[0]);
		if (line == null)
			throw new UnsupportedOperationException("No recording device found");
	}
	
	public static String[] audioList() throws LineUnavailableException{
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		String[] out = new String[mixerInfo.length];
		for(int i=0;i<mixerInfo.length;i++){
			Mixer mixer = AudioSystem.getMixer(mixerInfo[i]);
			Line.Info[] targetLineInfo = mixer.getTargetLineInfo();
			out[i]=mixerInfo[i].getName()+'['+targetLineInfo.length+']';
		}
		return out;
	}

	public void begin() throws LineUnavailableException{
		af = new AudioFormat(48000, 8, 1, true, false);
		line.open(af);
		line.start();
		running = Boolean.valueOf(true);
		input = new Thread(this,"input");
		input.start();
	}
	
	public void run(){
		while(running){
			int ssize=(int)af.getSampleRate();
			int fsize=af.getFrameSize();
			byte[] buf2 = new byte[ssize*fsize];
			line.read(buf2, 0, buf2.length);
			int len = line.read(buf2, 0, buf2.length);
			buf.write(buf2, len);
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
		return buf.length();
	}
	
	public byte[] getBuf(){
		return buf.peek(buf.length());
	}
	
	public static void main(String args[]){
		String[] io = null;
		try {
			io=audioList();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<io.length;i++){
			System.out.println(i + " - " + io[i]);
		}
	}

}
package ltbl.io;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;

import ltbl.util.RingBuffer;

public class AudioInput extends Thread{
	volatile public RingBuffer rb;
	volatile Line port = null;
	volatile TargetDataLine line = null;
	
	Boolean running;
	Thread input = null;
	volatile AudioFormat af;
	
	public RingBuffer getBuffer(){
		return rb;
	}

	public AudioInput(Mixer.Info mi, Line.Info li, int bufLen) throws LineUnavailableException{
		af = new AudioFormat(48000, 8, 1, true, false);
		rb = new RingBuffer(bufLen);
		Mixer mixer = AudioSystem.getMixer(mi);
		port = mixer.getLine(li);
		line = AudioSystem.getTargetDataLine(af);
		mixer.open();
		for(Line.Info l : getSources(mi))
			AudioSystem.getLine(l).close();
		port.open();
		if (line == null)
			throw new UnsupportedOperationException("No recording device found");
		
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
	
	public static List<Mixer.Info> getSoundCards(){
		//a Java mixer is equivalent to a sound device
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		List<Mixer.Info> cards = new ArrayList<Mixer.Info>();
		for (Mixer.Info mixerInfo : mixers){
			if(mixerInfo.getName().contains("Port")){
				cards.add(mixerInfo);
			}
		}
		return cards;
	}
	
	public static List<Line.Info> getSources(Mixer.Info card){
		Mixer m = AudioSystem.getMixer(card);
		Line.Info[] lines = m.getSourceLineInfo();
		ArrayList<Line.Info> sources = new ArrayList<Line.Info>();
		for (Line.Info li : lines){
			sources.add(li);
		}
		return sources;
	}
	
	public static List<Line.Info> getTargets(Mixer.Info card){
		Mixer m = AudioSystem.getMixer(card);
		Line.Info[] lines = m.getTargetLineInfo();
		ArrayList<Line.Info> targets = new ArrayList<Line.Info>();
		for (Line.Info li : lines){
			targets.add(li);
		}
		return targets;
	}
}


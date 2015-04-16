package ltbl.io;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;

import ltbl.util.RingBuffer;

public class AudioInput{
	private RingBuffer rb;
	
	public AudioInput(String name, int samplerate, int bufferLength){
		rb = new RingBuffer(bufferLength);
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
		ArrayList<Line.Info> sources = new ArrayList<Line.Info>();
		for (Line.Info li : lines){
			sources.add(li);
		}
		return sources;
	}
}


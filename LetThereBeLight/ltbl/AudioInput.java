package ltbl;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;

public class AudioInput{
	private RingBuffer rb;

	public static void getAvailableLines(){
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		List<Line.Info> availableLines = new ArrayList<Line.Info>();
		for (Mixer.Info mixerInfo : mixers){
			System.out.println("Found Mixer: " + mixerInfo);
			Mixer m = AudioSystem.getMixer(mixerInfo);
			Line.Info[] lines = m.getTargetLineInfo();
				for (Line.Info li : lines){
					System.out.println("Found target line: " + li);
					try {
						m.open();
						availableLines.add(li);    
						m.close();
					} catch (LineUnavailableException e){
						System.out.println("Line unavailable.");
				}
			}  
		}
		for(Line.Info info : availableLines){
			System.out.println("Available line: " + info);
		}
	}
	
	public AudioInput(String name, int samplerate, int bufferLength){
		rb = new RingBuffer(bufferLength);
	}
	
	public static List<Mixer.Info> getSoundCards(){
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

	public static void main(String args[]){
		Mixer.Info port = getSoundCards().get(0);
		for(Line.Info li : getSources(port)){
			System.out.println(li.toString().substring(0, li.toString().length()-12));
			try {
				AudioSystem.getLine(li).open();
				Control[] controls = AudioSystem.getLine(li).getControls();
				AudioSystem.getLine(li).close();
				for(Control c : controls){
					System.out.println(" - " + c.getType());
				}
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}


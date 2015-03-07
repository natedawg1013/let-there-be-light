import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;

public class AudioInput{

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

	public static void main(String args[]){
		getAvailableLines();
	}
}

package ltbl.io;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;

import ltbl.util.RingBuffer;

public class DummyAudioInput implements AudioInput{
	public RingBuffer getBuffer(){
		return new RingBuffer(0);
	}

	public DummyAudioInput(){		
	}
	public void begin(){
	}
	
	public void run(){
	}
	
	public void end() throws IOException{
	}

	public int bufLen(){
		return 0;
	}
	
	public byte[] getBuf(){
		return new byte[0];
	}
	
	public static List<Mixer.Info> getSoundCards(){
		return new ArrayList<Mixer.Info>();
	}
	
	public static List<Line.Info> getSources(Mixer.Info card){
		return new ArrayList<Line.Info>();
	}
	
	public static List<Line.Info> getTargets(Mixer.Info card){
		return new ArrayList<Line.Info>();
	}
}


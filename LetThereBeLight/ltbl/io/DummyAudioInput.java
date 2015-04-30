package ltbl.io;
import java.io.IOException;

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
}


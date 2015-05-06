package ltbl.io;

import ltbl.iface.AudioInput;
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
	
	public void end(){
	}

	public int bufLen(){
		return 0;
	}
	
	public byte[] getBuf(){
		return new byte[0];
	}
}


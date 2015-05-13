package ltbl.io;

import ltbl.iface.AudioInput;
import ltbl.util.RingBuffer;

/**
 * Dummy audio input to prevent errors when no audio input
 * has been chosen yet
 * @author Nathan Bernard
 *
 */
public class DummyAudioInput implements AudioInput{
	/**
	 * returns new ring buffer of length 0
	 */
	public RingBuffer getBuffer(){
		return new RingBuffer(0);
	}
	/**
	 * constructor
	 */
	public DummyAudioInput(){		
	}
	/**
	 * does nothing, required by interface
	 */
	public void begin(){
	}
	/**
	 * does nothing, required by interface
	 */
	public void run(){
	}
	/**
	 * does nothing, required by interface
	 */
	public void end(){
	}
	/**
	 * returns length of  0
	 */
	public int bufLen(){
		return 0;
	}
	/**
	 * returns empty array
	 * @return empty array
	 */
	public byte[] getBuf(){
		return new byte[0];
	}
}


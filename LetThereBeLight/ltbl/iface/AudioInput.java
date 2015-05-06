package ltbl.iface;
import javax.sound.sampled.*;

import ltbl.util.RingBuffer;

public interface AudioInput {
	public RingBuffer getBuffer();
	
	public void begin() throws LineUnavailableException;
	
	public void run();
	
	public void end();

	public int bufLen();
	
	public byte[] getBuf();

}


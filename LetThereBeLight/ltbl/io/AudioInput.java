package ltbl.io;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import ltbl.util.RingBuffer;

public interface AudioInput{
	public RingBuffer getBuffer();

	public void begin() throws LineUnavailableException;
	
	public void run();
	
	public void end() throws IOException;

	public int bufLen();
	
	public byte[] getBuf();
}


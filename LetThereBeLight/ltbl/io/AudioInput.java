package ltbl.io;
import java.io.IOException;
import javax.sound.sampled.*;

import ltbl.util.RingBuffer;

public interface AudioInput {
	public RingBuffer<float> getBuffer();
	
	public void begin() throws LineUnavailableException;
	
	public void run();
	
	public void end() throws IOException;

	public int bufLen();
	
	public float[] getBuf();

}


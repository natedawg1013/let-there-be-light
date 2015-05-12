package ltbl.iface;
import javax.sound.sampled.*;

import ltbl.util.RingBuffer;

// The class AudioInput provides audio input data to FourierAnalysis
// Sequential cohesion. The output from AudioInput because the processed input for FourierAnalysis
// Provides list of audio sources to InputSettings
// Sequential cohesion. AudioInput first checks all inputs for further processing by InputSettings
// Instantiated by InputSettings
// Temporal cohesion. AudioInput is explicitly when InputSettings is opened
// AudioInput collaborates with the following classes
// InputSettings - Control coupling. InputSettings manipulates the behavior of Input
// Runner - Control coupling. Runner controls the flow of classes called from MainMenu
// FourierAnalysis - Data Coupling. AudioInput is passed on to FourierAnalysis for processing
// RingBuffer - Data coupling. AudioInput is an object that becomes a methhod of carrying an input signal to be read by RingBuffer
// PeriodicEffect - Data coupling. The input for PeriodicEffect is the processed data from AudioInput

public interface AudioInput {
	public RingBuffer getBuffer();
	
	public void begin() throws LineUnavailableException;
	
	public void run();
	
	public void end();

	public int bufLen();
	
	public byte[] getBuf();

}


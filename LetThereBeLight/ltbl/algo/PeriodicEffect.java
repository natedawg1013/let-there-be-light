package ltbl.algo;

import java.util.ArrayList;

import ltbl.control.Runner;
import ltbl.iface.Output;
import ltbl.iface.OutputSource;
/**
 * Settings for a periodic effect,
 * manages LFO
 * @author Nathan Brnard
 *
 */
// The PeriodicEffect class Uses time and audio “beats” to create an output signal for the user
// The PeriodicEffect class makes use of the "Visitor" design pattern
// Sequential cohesion. PeriodicEffect creates a signal, and that data is then passed on to another class by Runner as needed
// PeriodicEffect collaborates with the following classes
// MainMenu - Control coupling. Runner controls the flow of classes called from MainMenu
// Runner - Control coupling. Runner controls the flow of classes called from MainMenu
// AudioInput - Data coupling. The input for PeriodicEffect is the processed data from AudioInput
// PeriodicEffectWindow - Common coupling. PeriodicEffectWindow only contains the elements of PeriodicEffect
// PeriodicEffectPanel - Control coupling. PeriodicEffectPanel is the settings that manipulate the behavior of PeriodicEffect


public class PeriodicEffect implements OutputSource{
	private LFO oscillator;
	private ArrayList<Integer> channels;
	private boolean running;
	
	public PeriodicEffect(Runner r){
		oscillator=null;
		channels = new ArrayList<Integer>();
		running=false;
	}
	/**
	 * Sets whether or not effect is enabled
	 * @param enabled state of effect
	 */
	public void setEnabled(boolean enabled){
		running=enabled;
	}
	/**
	 * Creates a new LFO
	 * @param type Type of LFO (sine, square, triangle, sawtooth, pulse)
	 * @param period Period of waveform in seconds
	 */
	public void setLFO(String type, double period, double dutyCycle){
		switch(type){
		case "sine":
			oscillator=new LFO.Sine(period);
			break;
		case "square":
			oscillator=new LFO.Square(period);
			break;
		case "triangle":
			oscillator=new LFO.Triangle(period);
			break;
		case "sawtooth":
			oscillator=new LFO.Sawtooth(period);
			break;
		case "pulse":
			oscillator=new LFO.Pulse(period, dutyCycle);
			break;
		default:
			break;
		}
		oscillator.start();
		oscillator.pause(false);
		
	}
	
	/**
	 * Adds ch to list of DMX channels to be controlled by this effect
	 * @param ch channel number to add
	 */
	public void addChannel(int ch){
		channels.add(ch);
	}
	/**
	 * Updates DMX channels under this effect's control
	 */
	@Override
	public void getOutputs(Output o) {
		if(running && oscillator != null){
			for(int i : channels){
				o.setChannel(i, oscillator.getValue());
			}
		}
	}
}
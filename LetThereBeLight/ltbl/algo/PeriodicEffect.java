package ltbl.algo;

import java.util.ArrayList;

import ltbl.control.Runner;
/**
 * Settings for a periodic effect,
 * manages LFO
 * @author Nathan Brnard
 *
 */
public class PeriodicEffect{
	private LFO oscillator;
	private ArrayList<Integer> channels;
	private boolean running;
	private Runner runner;
	
	public PeriodicEffect(Runner r){
		oscillator=null;
		channels = new ArrayList<Integer>();
		running=false;
		runner=r;
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
	 * Updates DMC channels under this effect's control
	 */
	public void update(){
		if(running && oscillator != null && runner.getOutput() != null){
			for(int i : channels){
				runner.getOutput().setChannel(i, oscillator.getValue());
			}
		}
	}
}
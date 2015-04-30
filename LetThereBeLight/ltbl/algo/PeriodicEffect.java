package ltbl.algo;

import java.util.ArrayList;

import ltbl.control.Runner;

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
	
	public void setEnabled(boolean enabled){
		running=enabled;
	}
	
	public void setLFO(String type, double period){
		switch(type){
		case "sine":
			oscillator=new Sine(period);
			break;
		case "square":
			oscillator=new Square(period);
			break;
		case "triangle":
			oscillator=new Triangle(period);
			break;
		case "sawtooth":
			oscillator=new Sawtooth(period);
			break;
		default:
			break;
		}
		oscillator.start();
		oscillator.pause(false);
		
	}
	public void setLFO(String type, double period, double dutyCycle){
		if(type.equals("pulse")){
			oscillator=new Pulse(period, dutyCycle);
			oscillator.start();
			oscillator.pause(false);
		}
	}
	
	public void addChannel(int ch){
		channels.add(ch);
	}
	
	public void update(){
		System.out.println("update() called");
		if(running && oscillator != null && runner.getOutput() != null){
			for(int i : channels){
				runner.getOutput().setChannel(i, oscillator.getValue());
			}
		}
	}
}
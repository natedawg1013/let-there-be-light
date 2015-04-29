package ltbl.algo;

public abstract class LFO extends Thread{
	protected double period;
	private double counter;
	private volatile boolean running;
	protected double value;
	
	public abstract void calcValue();
	
	public void run(){
		while(true){
			if(running){
				counter+=0.05;
				if(counter > period) counter=0.0;
				calcValue();
			}
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public double getValue(){
		return value;
	}
	
	public void pause(boolean paused){
		running=!paused;
	}
	
	public boolean isPaused(){
		return !running;
	}
	
	public void setPeriod(double period){
		this.period = period;
	}
	
	public double getPeriod(){
		return period;
	}
	
	public class Sine extends LFO{
		public Sine(double period){
			this.period=period;
		}
		@Override
		public void calcValue() {
			value=0.5+0.5*Math.sin(counter/period*2*Math.PI);
		}
	}
	public class Square extends LFO{
		public Square(double period){
			this.period=period;
		}
		@Override
		public void calcValue() {
			if(counter>period/2) value=0.0;
			else value=0.0;
		}
	}
	public class Triangle extends LFO{
		public Triangle(double period){
			this.period=period;
		}
		@Override
		public void calcValue() {
			value=Math.abs(counter/period-0.5);
		}
	}
	public class Sawtooth extends LFO{
		public Sawtooth(double period){
			this.period=period;
		}
		@Override
		public void calcValue() {
			value=counter/period;
		}
	}
	public class Pulse extends LFO{
		private double dutyCycle;
		public Pulse(double period, double dutyCycle){
			this.period=period;
			this.dutyCycle=dutyCycle;
		}
		@Override
		public void calcValue() {
			if(counter/period>dutyCycle) value=1.0;
			else this.value=0.0;
		}
	}
	
}
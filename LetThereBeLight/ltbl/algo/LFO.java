package ltbl.algo;

/** 
 * Low frequency oscillator for lighting effects
 * @author Nathan Bernard
 *
 */
public abstract class LFO extends Thread{
	protected double period;
	protected volatile double counter;
	private volatile boolean running;
	protected volatile double value;

	public abstract void calcValue();
	/**
	 * Runs in the background as its own thread
	 */
	public void run(){
		while(true){
			if(running){
				counter+=0.05;
				if(counter > period) counter-=period;
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
	/**
	 * Returns current value of calculation
	 * @return 0.0...1.0 indicating value of function at current time
	 */
	public float getValue(){
		return (float) value;
	}
	/**
	 * Pauses and un-pauses effect
	 * @param paused pause/run state
	 */
	public void pause(boolean paused){
		running=!paused;
	}
	/**
	 * Returns whether effect is paused or not
	 * @return true if effect paused
	 */
	public boolean isPaused(){
		return !running;
	}
	/**
	 * Sine wave generator
	 * @author Nathan Bernard
	 *
	 */
	public static class Sine extends LFO{
		public Sine(double period){
			this.period=period;
		}
		@Override
		public void calcValue() {
			value=0.5+0.5*Math.sin(counter/period*2*Math.PI);
		}
	}

	/**
	 * Square wave generator
	 * @author Nathan Bernard
	 *
	 */
	public static class Square extends LFO{
		public Square(double period){
			this.period=period;
		}
		@Override
		public void calcValue() {
			if(counter>period/2) value=0.0;
			else value=1.0;
		}
	}
	/**
	 * Triangle wave generator
	 * @author Nathan Brnard
	 *
	 */
	public static class Triangle extends LFO{
		public Triangle(double period){
			this.period=period;
		}
		@Override
		public void calcValue() {
			value=Math.abs(counter/period-0.5)*2;
		}
	}
	/**
	 * Sawtooth wave generator
	 * @author Nathan Brnard
	 *
	 */
	public static class Sawtooth extends LFO{
		public Sawtooth(double period){
			this.period=period;
		}
		@Override
		public void calcValue() {
			value=counter/period;
		}
	}
	/**
	 * Pulse wave generator
	 * @author Nathan Brnard
	 *
	 */
	public static class Pulse extends LFO{
		private double dutyCycle;
		public Pulse(double period, double dutyCycle){
			this.period=period;
			this.dutyCycle=dutyCycle;
		}
		@Override
		public void calcValue() {
			if((counter/period)>dutyCycle) value=1.0;
			else this.value=0.0;
		}
	}
}
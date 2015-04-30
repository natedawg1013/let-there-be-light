package ltbl.algo;

public abstract class LFO extends Thread{
	protected double period;
	protected volatile double counter;
	private volatile boolean running;
	protected volatile double value;

	public abstract void calcValue();

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

	public float getValue(){
		return (float) value;
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
}

class Sine extends LFO{
	public Sine(double period){
		this.period=period;
	}
	@Override
	public void calcValue() {
		value=0.5+0.5*Math.sin(counter/period*2*Math.PI);
	}
}
class Square extends LFO{
	public Square(double period){
		this.period=period;
	}
	@Override
	public void calcValue() {
		if(counter>period/2) value=0.0;
		else value=1.0;
	}
}
class Triangle extends LFO{
	public Triangle(double period){
		this.period=period;
	}
	@Override
	public void calcValue() {
		value=Math.abs(counter/period-0.5)*2;
	}
}
class Sawtooth extends LFO{
	public Sawtooth(double period){
		this.period=period;
	}
	@Override
	public void calcValue() {
		value=counter/period;
	}
}
class Pulse extends LFO{
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

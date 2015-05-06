package ltbl.algo;

import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;

import ltbl.algo.Complex;
import ltbl.algo.FFT;
import ltbl.iface.AudioInput;
import ltbl.iface.FourierUpdateListener;

public class FourierAnalysis extends Thread{
	private AudioInput in;
	int bufLen;
	private float[] dataPoints;
	private ArrayList<FourierUpdateListener> listeners;
	private Boolean paused;

	public FourierAnalysis(AudioInput i, int len){
		in=i;
		bufLen=len;
		listeners = new ArrayList<FourierUpdateListener>();
		paused=true;
	}

	public void setInput(AudioInput i){
		in.end();
		in=i;
		try {
			in.begin();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pause(boolean state){
		paused = Boolean.valueOf(state);
	}

	public float[] getData(){
		return dataPoints;
	}

	public void begin(){
		try {
			in.begin();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public boolean ready(){
		return in.bufLen()==bufLen;
	}

	public void process(){
		if(in.getBuffer().getSize()==0) return;
		if(in.getBuffer().length() != in.getBuffer().getSize())
			return;
		byte[] array = in.getBuf();
		Complex[] x = new Complex[bufLen];
		for(int i=0;i<bufLen;i++){
			x[i]=new Complex(array[i]/127f,0);
		}
		Complex[] y = FFT.fft(x);
		float[] f = new float[bufLen];
		for(int i=0;i<bufLen/2;i++){
			f[i]=(float) (y[i].abs()+y[bufLen-1-i].abs())/2/bufLen*50;
		}
		dataPoints=LinearLog.logFromLinear(f, 100);
	}

	void end(){
		in.end();
	}

	public void addUpdateListener(FourierUpdateListener l){
		listeners.add(l);
	}

	@Override
	public void run() {
		this.begin();
		while(true){
			if(!paused){
				process();
				for(FourierUpdateListener l : listeners){
					l.update(this);
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
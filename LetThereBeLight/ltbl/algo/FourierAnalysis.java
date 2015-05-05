package ltbl.algo;

import javax.sound.sampled.LineUnavailableException;

import ltbl.algo.Complex;
import ltbl.algo.FFT;
import ltbl.io.AudioInput;

public class FourierAnalysis {
    private AudioInput in;
	int bufLen;
	private float[] dataPoints;
	
	public FourierAnalysis(AudioInput i, int len){
		in=i;
		bufLen=len;
	}
    
    public void setInput(AudioInput i){
    	in=i;
    }
    
    public AudioInput getInput(){
    	return in;
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
	
	public float[] process(){
		if(in.getBuffer().getSize()==0) return new float[0];
		if(in.getBuffer().length() != in.getBuffer().getSize())
			return new float[bufLen];
		byte[] array = in.getBuf();
		Complex[] x = new Complex[bufLen];
		for(int i=0;i<bufLen;i++){
			x[i]=new Complex(array[i]/127f,0);
		}
		Complex[] y = FFT.fft(x);
		float[] f = new float[bufLen];
		for(int i=0;i<bufLen/2;i++){
			f[i]=(float) (y[i].abs()+y[bufLen-1-i].abs())/2;
		}
		dataPoints=f;
		return f;
	}
	
	void end(){
		in.end();
	}
}
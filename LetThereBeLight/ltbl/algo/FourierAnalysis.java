package ltbl.algo;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import ltbl.algo.Complex;
import ltbl.algo.FFT;
import ltbl.io.AudioInput;
import ltbl.io.TrueAudioInput;

public class FourierAnalysis {
    private AudioInput in;
	int bufLen;
	
	public FourierAnalysis(AudioInput i, int len){
		in=i;
		bufLen=len;
	}
    
    public void setInput(TrueAudioInput i){
    	in=i;
    }
    
    public AudioInput getInput(){
    	return in;
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
		if(in.getBuffer().length() != in.getBuffer().getSize())
			return new float[in.getBuffer().getSize()];
		byte[] array = in.getBuf();
		Complex[] x = new Complex[in.getBuffer().getSize()];
		for(int i=0;i<in.getBuffer().getSize();i++){
			x[i]=new Complex(array[i],0);
		}
		Complex[] y = FFT.fft(x);
		float[] f = new float[in.getBuffer().getSize()];
		for(int i=0;i<in.getBuffer().getSize()/2;i++){
			f[i]=(float) y[i].abs();
		}
		return f;
	}
	
	void end(){
		try {
			in.end();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
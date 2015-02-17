package ProofOfConcept;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

public class FourrierAnalysis{
	AudioIn source;
	int bufLen;
	
	public FourrierAnalysis(int input,int len){
		bufLen=len;
		try {
			source = new AudioIn(input, len);
		} catch (LineUnavailableException e2) {
			return;
		}
	}
	
	public void begin(){
		try {
			source.begin();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	 
	public boolean ready(){
		return source.bufLen()==bufLen;
	}
	
	public float process(){
		byte[] array = source.getBuf();
		Complex[] x = new Complex[bufLen];
		for(int i=0;i<bufLen;i++){
			x[i]=new Complex(array[i],0);
		}
		Complex[] y = FFT.fft(x);
		double[] f = new double[bufLen];
		for(int i=0;i<bufLen/2;i++){
			f[i]=y[i].abs();
		}
		double[] g = new double[bufLen/2];
		for(int i=0;i<bufLen/2;i++){
			g[i]=f[i]+f[i+1]+f[i+2];
		}
		double highest=0;
		int index=0;
		for(int i=0;i<bufLen/2;i++){
			if(g[i]>highest){
				highest=g[i];
				index=i;
			}
		}
		return (float)index/(bufLen/2);
	}
	
	void end(){
		try {
			source.end();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
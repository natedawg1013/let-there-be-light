package ltbl.algo;

public class LinearLog{
	public static float[] logFromLinear(float[] lin, int len){
		float[] log = new float[len];
		for(int i=0;i<len;i++){
			float total = 0.0f;
			int prev, next, sample;
			sample = logToLin(i, len, lin.length, 44100);
			if(i!=0)
				prev = logToLin(i-1, len, lin.length, 44100);
			else
				prev = sample;
			if(i!=len-1)
				next = logToLin(i+1, len, lin.length, 44100);
			else
				next=sample;
			for(int j=prev; j<next;j++){
				total+=lin[j];
			}
			total/=(float)(next-prev);
			log[i]=total;
		}
		return log;
	}
	
	public static float[] loglog(float[] samples){
		float[] result = new float[samples.length];
		for(int i=0;i<samples.length;i++){
			result[i]=(float) (Math.log(samples[i]+1)/Math.log(1.4)) * 3;
		}
		return result;
	}
	
	public static int logToLin(int index, int max, int samples, int sampleRate){
		double top = Math.log(5000)/Math.log(2);
		double bottom = Math.log(20)/Math.log(2);
		double power = (((double)index/max)*(top-bottom) + bottom);
		double freq = Math.pow(2,  power);
		if(freq>20000) freq=20000;
		int i = (int) (freq/20000*(samples-1)*(40000.0/sampleRate));
		return i;
	}	
}



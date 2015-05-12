package ltbl.algo;

/**
 * Class for resampling an array with a linear domain to one
 * with a logarithmic domain.
 * @author Nathan Bernard
 *
 */
public class LinearLog{
	/**
	 * Takes a linear domain input array and returns a log domain array.
	 * @param lin linear array to resample
	 * @param len length of output array
	 * @return new array with logarithmic domain of length len
	 */
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
			total/=( 1+( (len-i)*3.0f/len) );
			log[i]=total;
		}
		return log;
	}
	
	/**
	 * Scales each element of the given array logarithmically
	 * @param samples input array
	 * @return new array where each element is log10(n)*10 where n is the original element
	 */
	public static float[] loglog(float[] samples){
		float[] result = new float[samples.length];
		for(int i=0;i<samples.length;i++){
			result[i]=(float) (Math.log(samples[i]))*10;
		}
		return result;
	}
	
	/**
	 * Helper method to help with scaling within the audible range
	 * @param index along with max, defines position within hearing range
	 * @param max max number of samples to be collected
	 * @param samples number of samples in input array
	 * @param sampleRate sample rate used when generating input array
	 * @return int representing index in original array which maps to "index" in the output array
	 */
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



package ltbl.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ltbl.control.Runner;

public class FFTBox {
    
    // relative to the outer boundaries of the space
    private double[] relativeDimensions;
    private Runner runner;
    private Map<Integer, Float> outs;
    private FourierAnalysis fourier;
    private boolean ready;
    
    public FFTBox ( Runner r ) {
        relativeDimensions = new double[4];
        runner = r;
        fourier = r.getFourier();
        outs = new TreeMap<Integer, Float>();
        ready = false;
    }
    
    public void setDimensions ( int[] dim, int xOut, int yOut ) {
        relativeDimensions[0] = ( (double) dim[0] ) / xOut;
        relativeDimensions[1] = ( (double) dim[1] ) / yOut;
        relativeDimensions[2] = ( (double) dim[2] ) / xOut;
        relativeDimensions[3] = ( (double) dim[3] ) / yOut;
        ready = true;
    }
    
    public int[] getDimensions ( int xDim, int yDim ) {
        int[] newDim = new int[4];
    	if (ready) {
            newDim[0] = (int) (xDim*relativeDimensions[0]);
            newDim[1] = (int) (yDim*relativeDimensions[1]);
            newDim[2] = (int) (xDim*relativeDimensions[2]);
            newDim[3] = (int) (yDim*relativeDimensions[3]);
    	}
    	else {
    		for ( int i = 0; i < newDim.length; ++i ) {
    			newDim[i] = 0;
    		}
    	}
        return newDim;
    }
    
    public void setOutputs ( Map<Integer, Float> channels ) {
    	outs = channels;
    }
    
    public void update(){
    	if (ready) {
	    	boolean flag=false;
	    	//System.out.println(fourier.getData());
	    	float [] points = fourier.getData();
	    	if ( points != null ) {
		    	int frqMin = points.length * (int)relativeDimensions[0];
		    	int frqMax = points.length * (int)relativeDimensions[2];
		    	for ( int i=frqMin; i<=frqMax && !flag; ++i ) {
		    		// TODO: use the correct scaling here
		    		float relAmp = (3/4 - points[i])/8;
		    		if ( relativeDimensions[1] < relAmp && relativeDimensions[3] > relAmp ) {
		    			flag = true;
		    		}
		    	}
	    		if(flag) {
	    			for(int i : outs.keySet()){
		    			runner.getOutput().setChannel(i, outs.get(i) );
		    		}
		    	}
	    	}
    	}
    }
}
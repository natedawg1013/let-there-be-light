package ltbl.algo;

import java.util.ArrayList;
import java.util.List;

import ltbl.control.Runner;

public class FFTBox {
    
    // relative to the outer boundaries of the space
    private double[] relativeDimensions;
    private int frqMin, frqMax;
    private float ampMin, ampMax;
    private Runner runner;
    private List<Integer> outs;
    private FourierAnalysis fourier;
    
    public FFTBox ( Runner r ) {
        relativeDimensions = new double[4];
        runner = r;
        outs = new ArrayList<Integer>();
    }
    
    public void setDimensions ( int[] dim, int xOut, int yOut ) {
        relativeDimensions[0] = ( (double) dim[0] ) / xOut;
        relativeDimensions[1] = ( (double) dim[1] ) / yOut;
        relativeDimensions[2] = ( (double) dim[2] ) / xOut;
        relativeDimensions[3] = ( (double) dim[3] ) / yOut;
    }
    
    public int[] getDimensions ( int xDim, int yDim ) {
        int[] newDim = new int[4];
        newDim[0] = (int) (xDim*relativeDimensions[0]);
        newDim[1] = (int) (yDim*relativeDimensions[1]);
        newDim[2] = (int) (xDim*relativeDimensions[2]);
        newDim[3] = (int) (yDim*relativeDimensions[3]);
        return newDim;
    }
    
    public void update(){
    	boolean flag=false;
    	for(int i=frqMin; i<=frqMax;i++){
    		if(fourier.getData()[i] < ampMax &&
    		   fourier.getData()[i] > ampMin ){
    			flag=true;
    			break;
    		}
    	}
    	for(int i : outs){
    		if(flag)
    			runner.getOutput().setChannel(i, 1.0f);
    		else
    			runner.getOutput().setChannel(i, 0.0f);
    	}
    }
}
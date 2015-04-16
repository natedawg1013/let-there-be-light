package ltbl;

public class FFTBox {
    
    // relative to the outer boundaries of the space
    private double[] relativeDimensions;
    
    public FFTBox () {
        relativeDimensions = new double[4];
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
}
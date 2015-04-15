package ltbl;
import javax.swing.JPanel;


public class FFTBoxOverlay extends JPanel {
    // store location/dimensions of box as fractions of the graph's dimensions
    private int [] dimensions; // x0, y0, x1, y1
    private int xOuter, yOuter;
    private FFTBox box;
    
    public FFTBoxOverlay () {
        // make box
        box = new FFTBox();
        // initially we want it to be not showed until we set its size via the MousePanel
    }
    
    // Sets the size of the box based on the graph's dimensions and box's current relative dimensions
    public void updateRelative () {
        // FFTBox stores its bounding dimensions as doubles from 0-1
        // where 0 and 1 are the outermost dimensions on the graph
        dimensions = box.getDimensions( xOuter, yOuter );
    }
    
    // sets the size of the graph's dimensions
    public void setOuter ( int x, int y ) {
        xOuter = x;
        yOuter = y;
    }
    
    public void setdimensions ( int x0, int y0, int x1, int y1 ) {
        dimensions = new int[4];
        dimensions[0] = x0;
        dimensions[1] = y0;
        dimensions[2] = x1;
        dimensions[3] = x2;
    }
}





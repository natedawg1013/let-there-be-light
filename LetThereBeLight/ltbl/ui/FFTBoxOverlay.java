package ltbl.ui;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import java.awt.Rectangle;

import ltbl.algo.FFTBox;
import ltbl.control.Runner;


public class FFTBoxOverlay extends JPanel {
	private static final long serialVersionUID = -2758325029512626906L;
	// store location/dimensions of box as fractions of the graph's dimensions
    private int [] dimensions; // x0, y0, x1, y1
    private int xOuter, yOuter;
    private FFTBox box;
    private Runner runner;
    private static int borderRadius = 8;
    private FFTBoxSettings settings;
    private JFrame settingsFrame;
    
    public FFTBoxOverlay ( Runner run ) {
    	runner = run;
        // make box
        box = new FFTBox(run);
        runner.addBox(box);
        // initially we want it to be not showed until we set its size via the MousePanel
        Random rand = new Random();
        int r = rand.nextInt(0x100);
        int g = rand.nextInt(0x100);
        int b = rand.nextInt(0x100);
        this.setBackground( new Color( r, g, b, 0x80 ) );
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        this.setOpaque(false);
        settings = new FFTBoxSettings( runner, box );
        settingsFrame = new JFrame("Effects Settings");
        settingsFrame.add(settings);
    	settingsFrame.setMinimumSize(new Dimension(500,300));
    	settingsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    	
    }
    
    public void paintComponent( Graphics g ) {
    	g.setColor(getBackground());
    	Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        super.paintComponent(g);
    }
    
    // Sets the size of the box based on the graph's dimensions and box's current relative dimensions
    public void updateRelative () {
        // FFTBox stores its bounding dimensions as doubles from 0-1
        // where 0 and 1 are the outermost dimensions on the graph
        dimensions = box.getDimensions( xOuter, yOuter );
    }
    
    public void toggleSelected () {
    	Color c = getBackground();
    	int a = c.getAlpha();
    	a ^= 0xc0;
    	this.setBackground( new Color( c.getRed(), c.getGreen(), c.getBlue(), a ) );
    	//this.paintImmediately(this.getBounds());
    }
    
    // sets the size of the graph's dimensions
    public void setOuter ( int x, int y ) {
        xOuter = x;
        yOuter = y;
    }
    
    public void setDimensions ( int x0, int y0, int x1, int y1 ) {
        dimensions = new int[4];
        dimensions[0] = x0;
        dimensions[1] = y0;
        dimensions[2] = x1;
        dimensions[3] = y1;
        // assuming setOuter has been called, this should work
        box.setDimensions( dimensions, xOuter, yOuter );
        this.setSize( Math.abs(x0-x1), Math.abs(y0-y1) );
        this.setLocation( Math.min(x0,x1), Math.min(y0,y1) );
        this.setVisible(true);
        this.paintImmediately( this.getBounds(null) );
    }
    
    public int[] getDimensions () {
        return dimensions;
    }
    
    public void getSettings () {
    	settingsFrame.pack();
    	settingsFrame.setVisible(true);
    }
    
    public boolean isInside ( int x, int y ) {
    	return dimensions[0] <= x && x <= dimensions[2] && dimensions[1] <= y && y <= dimensions[3];
    }
    
    public int onEdge ( int x, int y ) {
    	if ( Math.abs(x-dimensions[0]) < borderRadius ) {
    		return 0;
    	}
    	else if ( Math.abs(y-dimensions[1]) < borderRadius ) {
    		return 1;
    	}
    	else if ( Math.abs(x-dimensions[2]) < borderRadius ) {
    		return 2;
    	}
    	else if ( Math.abs(y-dimensions[3]) < borderRadius ) {
    		return 3;
    	}
    	else {
    		return -1;
    	}
    }
    /*
    @Override
    public String toString() {
    	return "Box: " + dimensions[0]+" "+dimensions[1]+" "+dimensions[2]+" "+dimensions[3];
    }
    */
}





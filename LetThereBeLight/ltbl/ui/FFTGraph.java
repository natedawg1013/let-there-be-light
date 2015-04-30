package ltbl.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import ltbl.control.Runner;


public class FFTGraph extends JPanel {
    
    /**
	 * 
	 */
	private Runner r;
    private LineGraph lg;

    public FFTGraph ( Runner run ) {
    	super();
        r = run;
        //lg = new LineGraph( r.getFourier() );
        //this.add(lg);
        this.setOpaque(true);
        this.setBackground( new Color( 0x10, 0x18, 0x20 ) );
        this.setVisible(true);
        //lg.setVisible(true);
    }
    
    public LineGraph getGraph() {
    	return lg;
    }
    
}
package ltbl.ui;
import java.awt.Color;

import javax.swing.BoxLayout;
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
    	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        r = run;
        lg = new LineGraph( Color.WHITE );
        this.add(lg);
        this.setOpaque(true);
        this.setBackground( new Color( 0x10, 0x18, 0x20 ) );
        this.setVisible(true);
        lg.setVisible(true);
    }
    
    public LineGraph getGraph() {
    	return lg;
    }
    
    public void update(float[] points){
    	lg.update(points);
    }
    
}
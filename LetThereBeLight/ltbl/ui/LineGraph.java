package ltbl.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ltbl.algo.FourierAnalysis;
import ltbl.iface.FourierUpdateListener;
/**
 * Line graph used in Frequency Based Effects
 * @author Nathan Bernard
 *
 */
public class LineGraph extends JPanel implements FourierUpdateListener{
	private static final long serialVersionUID = 4570416097915326118L;
    private float[] points;
    private Color color;
    /**
     * Constructor for line graph
     * @param c color of line
     */
    public LineGraph(Color c){
    	super();
    	points = new float[0];
    	color=c;
    	this.setOpaque(false);
    	this.setBackground(new Color(0,0,0, 0));
    }
    /**
     * draw line graph
     */
    @Override
    public void paint(Graphics g){
    	super.paint(g);
    	//cast g to Graphics2D
    	Graphics2D graphics = (Graphics2D) g;
    	int x = this.getWidth();		//get panel size
    	int y = this.getHeight();
    	double width = ((double)(x)/points.length);	//horizontal scaling
    	graphics.setColor(color);
    	for(int i=1;i<points.length-1;i++){
    		int h1 = (int) ((1-points[i])*y);
    		int h2 = (int) ((1-points[i+1])*y);
    		graphics.drawLine((int)(i*width), h1, (int)((i+1)*width), h2);
    	}
    }
    /**
     * updates this graph's data
     */
	@Override
	public void update(FourierAnalysis a) {
		this.points=a.getData();
		
	}
}
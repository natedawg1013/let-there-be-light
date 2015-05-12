package ltbl.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ltbl.algo.FourierAnalysis;
import ltbl.iface.FourierUpdateListener;

// The BarGraph class communicates with FourierAnalysis to display basic histogram of current audio input
// BarGraph makes use of the "Observer" design pattern
// Sequential cohesion. The input of BarGraph is the output of FourierAnalysis
// BarGraph collaborates with the following classes
// MainMenu - Data coupling. The MainMenu window holds the contents and data contained in BarGraph
// FourierAnalysis - Content Coupling. BarGraph will display a graph that depends on the data from FourierAnalysis


public class BarGraph extends JPanel implements FourierUpdateListener{
	private static final long serialVersionUID = 4570416097915326118L;
    private float[] buckets;
    
    private final int BARS = 8;
    private final float SPACING = 0.1f;
    
    public BarGraph(FourierAnalysis a){
    	super();
    	buckets = new float[BARS];
    	for(int i=0;i<buckets.length;i++) buckets[i] = 0;
    }
    
    public BarGraph(){
    	super();
    	buckets = new float[BARS];
    	for(int i=0;i<buckets.length;i++) buckets[i] = 0;
    }
    
    @Override
    public void paint(Graphics g){
    	super.paint(g);
    	//cast g to Graphics2D
    	Graphics2D graphics = (Graphics2D) g;
    	int x = this.getWidth();		//get panel size
    	int y = this.getHeight();
    	int barWidth = (int) ((x*(1-SPACING))/BARS);	//horizontal scaling
    	graphics.setColor(new Color(127, 127, 127));	//set shape color to gray
    	for(int i=0;i<BARS;i++){
    		int start = (int) ((1-buckets[i]) * y);
    		int height = (int) (buckets[i]*y);
    		graphics.fillRect((int) (barWidth*(1+SPACING)*i), start, barWidth, height);
    	}
    	
    }

	@Override
	public void update(FourierAnalysis a) {
		float[] in = a.getData();
		if(in==null) return;
		for(int i=0;i<BARS;i++){
    		buckets[i]=0.0f;
    	}
    	for(int i=0;i<in.length;i++){
    		buckets[i/((in.length/BARS)+1)]+=in[i];
    	}
    	for(int i=0;i<BARS;i++){
    		buckets[i]/=(in.length/BARS);
    	}
    	this.repaint();
	}    
}
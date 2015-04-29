package ltbl.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ltbl.algo.FourierAnalysis;

public class BarGraph extends JPanel{
	private static final long serialVersionUID = 4570416097915326118L;
    private float[] buckets;
    
    private final int BARS = 8;
    private final float SPACING = 0.1f;
    private final int MAX_HEIGHT = 1000;
    
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
    	double scaling = (double) y/MAX_HEIGHT;			//vertical scaling
    	graphics.setColor(new Color(127, 127, 127));	//set shape color to gray
    	for(int i=0;i<BARS;i++){
    		int start =  y - (int) (buckets[i]*scaling);
    		int height = (int) (buckets[i]*scaling);
    		graphics.fillRect((int) (barWidth*(1+SPACING)*i), start, barWidth, height);
    	}
    	
    }
    
    //later will be replaced with method to get data from FourierAnalysis
    /*public void update(){
    	buckets=getRandomBuckets();
    	this.repaint();
    }*/
    
    public void update(float[] in){
    	for(int i=0;i<BARS;i++){
    		buckets[i]=0.0f;
    	}
    	for(int i=0;i<in.length;i++){
    		buckets[i/((in.length/BARS)+1)]+=in[i];
    	}
    	this.repaint();
    }    
}
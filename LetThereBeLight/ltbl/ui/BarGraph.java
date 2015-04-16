package ltbl.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ltbl.algo.FourierAnalysis;

public class BarGraph extends JPanel{
	private static final long serialVersionUID = 4570416097915326118L;
	private FourierAnalysis analysis;
    private int[] buckets;
    
    private final int BARS = 8;
    private final float SPACING = 0.1f;
    private final int MAX_HEIGHT = 10000;
    
    public BarGraph(FourierAnalysis a){
    	super();
    	analysis=a;
    	buckets = getRandomBuckets();
    }
    
    public BarGraph(){
    	super();
    	analysis=null;
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
    public void update(){
    	buckets=getRandomBuckets();
    	this.repaint();
    }
    
    //for now, gets random bar heights
    private int[] getRandomBuckets(){
    	int[] buckets = new int[BARS];
    	for(int i=0;i<BARS;i++){
    		buckets[i]=(int)(Math.random()*10000);
    		
    	}
    	return buckets;
    }
    
}
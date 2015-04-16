package ltbl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

public class BarGraph extends JPanel{
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
    	Graphics2D graphics = (Graphics2D) g;
    	int x = this.getWidth();
    	int y = this.getHeight();
    	Dimension dim = this.getSize();
    	int barWidth = (int) ((x*(1-SPACING))/BARS);
    	double scaling = (double) y/MAX_HEIGHT;
    	for(int i=0;i<BARS;i++){
    		int start =  y - (int) (buckets[i]*scaling);
    		int height = (int) (buckets[i]*scaling);
    		graphics.setColor(new Color(127, 127, 127));
    		graphics.fillRect((int) (barWidth*(1+SPACING)*i), start, barWidth, height);
    	}
    	
    }
    
    void update(){
    	buckets=getRandomBuckets();
    	this.repaint();
    }
    
    private int[] getBuckets(){
    	int[] buckets = new int[BARS];
    	for(int i=0;i<BARS;i++){
    		buckets[i]=i*1000 + 100;
    	}
    	return buckets;
    }
    private int[] getRandomBuckets(){
    	int[] buckets = new int[BARS];
    	for(int i=0;i<BARS;i++){
    		buckets[i]=(int)(Math.random()*10000);
    		
    	}
    	return buckets;
    }
    
}
package ltbl;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BarGraph extends JPanel{
    private FourierAnalysis analysis;
    
    private final int BARS = 8;
    private final float SPACING = 0.1f;
    private final int MAX_HEIGHT = 10000;
    
    public BarGraph(FourierAnalysis a){
    	super();
    	analysis=a;
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
    	System.out.println("" + x + " - " + y);
    	System.out.println(dim);
    	int[] buckets = getBuckets();
    	for(int i : buckets){
    		System.out.println(i);
    	}
    	int barWidth = (int) ((x*(1-SPACING))/BARS);
    	double scaling = (double) y/MAX_HEIGHT;
    	for(int i=0;i<BARS;i++){
    		int start =  y - (int) (buckets[i]*scaling);
    		int height = (int) (buckets[i]*scaling);
    		graphics.fillRect((int) (barWidth*(1+SPACING)*i), start, barWidth, height);
    	}
    	
    }
    
    private int[] getBuckets(){
    	int[] buckets = new int[BARS];
    	for(int i=0;i<BARS;i++){
    		buckets[i]=i*1000 + 100;
    	}
    	return buckets;
    }
}
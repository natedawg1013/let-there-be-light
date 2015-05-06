package ltbl.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ltbl.algo.FourierAnalysis;

public class LineGraph extends JPanel{
	private static final long serialVersionUID = 4570416097915326118L;
    private float[] points;
    private Color color;
    
    public LineGraph(FourierAnalysis a){
    	super();
    	points = new float[0];
    	this.setOpaque(false);
    	this.setBackground(new Color(0,0,0));
    }
    
    public LineGraph(Color c){
    	super();
    	points = new float[0];
    	color=c;
    	this.setOpaque(false);
    	this.setBackground(new Color(0,0,0, 0));
    }
    
    @Override
    public void paint(Graphics g){
    	super.paint(g);
    	//cast g to Graphics2D
    	Graphics2D graphics = (Graphics2D) g;
    	int x = this.getWidth();		//get panel size
    	int y = this.getHeight();
    	double width = ((double)(x)/points.length);	//horizontal scaling
    	graphics.setColor(color);
    	for(int i=0;i<points.length-1;i++){
    		//int h1 = (int) ((1-points[i])*y);
    		//int h2 =  (int) ((1-points[i+1])*y);
    		int h1 = (int) ((3/4-points[i]/40)*y)*5;
    		int h2 = (int) ((3/4-points[i+1]/40)*y)*5;
    		graphics.drawLine((int)(i*width), h1, (int)((i+1)*width), h2);
    	}
    	
    }
    
    //later will be replaced with method to get data from FourierAnalysis
    public void update(){
    	//points=getRandomPoints();
    	this.repaint();
    }
    
    public void update(float[] points){
    	this.points=points;
    	//this.repaint();
    }
}
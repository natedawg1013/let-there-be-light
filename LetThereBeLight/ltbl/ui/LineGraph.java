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
    
    private final int MAX_HEIGHT = 100;
    
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
    	double scaling = (double) y/MAX_HEIGHT;			//vertical scaling
    	double width = ((double)(x)/points.length);	//horizontal scaling
    	graphics.setColor(color);
    	for(int i=0;i<points.length-1;i++){
    		int h1 =  y - (int) (points[i]*scaling);
    		int h2 =  y - (int) (points[i+1]*scaling);
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
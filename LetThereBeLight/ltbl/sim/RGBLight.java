package ltbl.sim;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class RGBLight extends SimLight{
	private static final long serialVersionUID = 1514788004299401675L;
	private int[] channel;
	private JLabel l;
	
	public RGBLight(int c0, int c1, int c2, int n){
		channel = new int[3];
		channel[0]=c0;
		channel[1]=c1;
		channel[2]=c2;
		l = new JLabel(String.valueOf(n));
		this.add(l);
		setSize(new Dimension(100,100));
		setVisible(true);
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
	}
	
	public void update(float[] data){
		float r, g, b;
		r=data[channel[0]]/256.0f;
		g=data[channel[1]]/256.0f;
		b=data[channel[2]]/256.0f;
		setBackground(new Color(r,g,b));
	}
}
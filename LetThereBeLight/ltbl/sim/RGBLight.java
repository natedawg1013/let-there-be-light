package ltbl.sim;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
/**
 * JPanel which changes its color based off inputs
 * Part of simulator
 * @author Nathan Bernard
 *
 */
public class RGBLight extends SimLight{
	private static final long serialVersionUID = 1514788004299401675L;
	private int[] channel;
	private JLabel l;
	/**
	 * constructor, takes 3 channels (R, G, B) and in identity number
	 * @param c0 red channel
	 * @param c1 green channel
	 * @param c2 blue channel
	 * @param n ID
	 */
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
	
	/**
	 * updates color based on the values currently held by Output
	 * @param data array of values to compare with
	 */
	public void update(float[] data){
		float r, g, b;
		r=data[channel[0]];
		g=data[channel[1]];
		b=data[channel[2]];
		setBackground(new Color(r,g,b));
	}
}
package ltbl.sim;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import ltbl.util.ColorHelper;
/**
 * JPanel which has a fixed color and changes brightness based on input
 * @author Nathan Bernard
 *
 */
public class ColoredLight extends SimLight{
	private static final long serialVersionUID = 1514788004299401675L;
	private int channel;
	private int hue;
	private JLabel l;
	/**
	 * Constructor for colored light
	 * @param channel channel to use as value source
	 * @param hue color of light
	 * @param n ID
	 */
	public ColoredLight(int channel, int hue, int n){
		this.channel=channel;
		this.hue=hue;
		l = new JLabel(String.valueOf(n));
		this.add(l);
		setSize(new Dimension(100,100));
		setVisible(true);
		this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
	}
	/**
	 * updates color based off data value at index 'channel' 
	 * @param data values to use to set brightness value
	 */
	public void update(float[] data){
		float r, g, b;
		float color;
		float value = data[channel];
		if(hue==-1) color=-1;
		else color=hue/360.0f*6;
		float[] rgb = ColorHelper.HSVtoRGB(color,0.75f,value);
		r=rgb[0];
		g=rgb[1];
		b=rgb[2];
		setBackground(new Color(r,g,b));
	}
}
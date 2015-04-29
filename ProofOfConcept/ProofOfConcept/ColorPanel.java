package ProofOfConcept;
import java.awt.Color;

import javax.swing.JPanel;

public class ColorPanel extends JPanel{
	private static final long serialVersionUID = 1470301065333731098L;

	public void SetHSV(float h, float s, float v){
		float r, g, b;
		float[] rgb = ColorHelper.HSVtoRGB(h,s,v);
		r=rgb[0];
		g=rgb[1];
		b=rgb[2];
		setBackground(new Color(r,g,b));
	}
}
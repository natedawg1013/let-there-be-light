package ltbl.sim;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
/**
 * Panel which holds simulated lights and the Light Generator panel
 * @author Nathan Bernard
 *
 */
public class SimPanel extends JPanel{
	private static final long serialVersionUID = -5510970015886111248L;

	private ArrayList<SimLight> lights;
	private LightGeneratorPanel gen;
	private JPanel display;
	/**
	 * Constructor for simPanel
	 */
	public SimPanel(){
		super();
		this.setLayout(new BorderLayout());
		lights = new ArrayList<SimLight>();
		gen = new LightGeneratorPanel(this);
		display = new JPanel(new GridLayout(8, 8));
		display.setPreferredSize(new Dimension(600, 300));
		this.add(display, BorderLayout.WEST);
		this.add(gen, BorderLayout.EAST);
	}
	/**
	 * Adds a light to the panel and updates
	 * @param l light to add
	 * 
	 */
	public void addLight(SimLight l){
		synchronized(lights){
			lights.add(l);
			display.add(l);
		}
		display.revalidate();
		repaint();
	}
	/**
	 * updates the color of each light
	 * @param values values to pass to the lights
	 */
	public void update(float[] values) {
		synchronized(lights){
			for(SimLight l : lights){
				l.update(values);
			}
		}
	}


}
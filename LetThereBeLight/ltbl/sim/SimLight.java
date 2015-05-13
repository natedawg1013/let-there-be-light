package ltbl.sim;

import javax.swing.JPanel;
/**
 * Interface supported by all simulated lights
 * @author Nathan Bernard
 *
 */
public abstract class SimLight extends JPanel{
	private static final long serialVersionUID = 7308540758393011875L;
	/**
	 * update the light's color based on the corresponding value(s) in data
	 * @param data
	 */
	abstract void update(float[] data);
}
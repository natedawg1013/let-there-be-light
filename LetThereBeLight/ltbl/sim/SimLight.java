package ltbl.sim;

import javax.swing.JPanel;

public abstract class SimLight extends JPanel{
	private static final long serialVersionUID = 7308540758393011875L;

	abstract void update(float[] data);
}
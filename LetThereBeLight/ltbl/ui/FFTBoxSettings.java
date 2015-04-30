package ltbl.ui;
import javax.swing.JPanel;

import ltbl.algo.FFTBox;
import ltbl.control.Runner;


public class FFTBoxSettings extends JPanel {
    
	private Runner runner;
	private FFTBox box;
	
	public FFTBoxSettings ( Runner r, FFTBox b ) {
		runner = r;
		box = b;
	}
	
}
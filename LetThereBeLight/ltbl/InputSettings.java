package ltbl;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;


public class InputSettings extends JPanel implements ActionListener {
	JComboBox<String> input;
	JComboBox<Integer> sampleRate;
	JButton   set;
	Runner runner;
	
    public InputSettings(Runner r){
    	super();
    	runner = r;
    	this.setLayout(new GridBagLayout());
    	input = new JComboBox<String>();
    	sampleRate = new JComboBox<Integer>();
    	set = new JButton("Set");
    	List<String> in = AudioInput.getSources();
    	for(String s : in) input.addItem(s);
    	List<Integer> sam = AudioInput.getSampleRates(input.getSelectedItem());
    	for(int i : sam) sampleRate.addItem(i);
    	setPositions();
    	input.addActionListener(this);
    	set.addActionListener(this);
    }
    
    private void setPositions(){
    	GridBagConstraints c = new GridBagConstraints();
    	c.gridx=c.gridy=0;
    	this.add(input, c);
    	c.gridx=1;
    	this.add(sampleRate, c);
    	c.gridy=1;
    	this.add(set, c);
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==input){
			sampleRate.removeAllItems();
			List<Integer> sam = AudioInput.getSampleRates(input.getSelectedItem());
	    	for(int i : sam) sampleRate.addItem(i);
		}
		else if(ae.getSource()==set){
			String name = (String) input.getSelectedItem();
			Integer sample = (Integer) sampleRate.getSelectedItem();
			runner.updateFourrier(new AudioInput(name, sample));
		}
	}
}
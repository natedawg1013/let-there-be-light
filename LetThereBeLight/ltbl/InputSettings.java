package ltbl;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
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
    	List<Mixer.Info> cards = AudioInput.getSoundCards();
    	List<Line.Info> in = AudioInput.getSources(cards.get(0));
    	for(Line.Info s : in) input.addItem(s.toString());
    	int[] rates = {22050, 44100, 48000};
    	for(int i : rates) sampleRate.addItem(i);
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
//		if(ae.getSource()==input){
//			sampleRate.removeAllItems();
//			List<Integer> sam = AudioInput.getSampleRates((String) input.getSelectedItem());
//	    	for(int i : sam) sampleRate.addItem(i);
//		}
		if(ae.getSource()==set) {
			String name = (String) input.getSelectedItem();
			Integer sample = (Integer) sampleRate.getSelectedItem();
			runner.updateFourier(new AudioInput(name, sample, 8192));
			runner.showMainMenu(True);
			///TODO: Add dropdown for buffer length
		}
	}
}
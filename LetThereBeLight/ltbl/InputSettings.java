package ltbl;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
	JComboBox<String> cards;
	JComboBox<String> input;
	JComboBox<Integer> sampleRate;
	JButton   set;
	Runner runner;
	
    public InputSettings(Runner r){
    	super();
    	runner = r;
    	this.setLayout(new GridLayout(2,3));
    	cards = new JComboBox<String>();
    	input = new JComboBox<String>();
    	sampleRate = new JComboBox<Integer>();
    	set = new JButton("Set");
    	List<Mixer.Info> cardList = AudioInput.getSoundCards();
    	for(Mixer.Info m : cardList) cards.addItem(m.getName());
    	List<Line.Info> in = AudioInput.getSources(cardList.get(0));
    	for(Line.Info s : in) input.addItem(s.toString());
    	int[] rates = {22050, 44100, 48000};
    	for(int i : rates) sampleRate.addItem(i);
    	setPositions();
    	input.addActionListener(this);
    	set.addActionListener(this);
    	cards.addActionListener(this);
    }
    
    private void setPositions(){
    	this.add(cards);
    	this.add(input);
    	this.add(sampleRate);
    	this.add(new JPanel());
    	this.add(new JPanel());
    	this.add(set);
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
//		if(ae.getSource()==input){
//			sampleRate.removeAllItems();
//			List<Integer> sam = AudioInput.getSampleRates((String) input.getSelectedItem());
//	    	for(int i : sam) sampleRate.addItem(i);
//		}
		if(ae.getSource()==cards){
			System.out.println(cards.getSelectedIndex());
			List<Mixer.Info> cardList = AudioInput.getSoundCards();
			List<Line.Info> in = AudioInput.getSources(cardList.get(cards.getSelectedIndex()));
			input.removeAllItems();
	    	for(Line.Info s : in){
	    		input.addItem(s.toString());
	    		System.out.println(s.toString());
	    	}
	    	if(in.size() == 0){
	    		input.setEnabled(false);
	    		set.setEnabled(false);
	    	}
	    	else{
	    		input.setEnabled(true);
	    		set.setEnabled(true);
	    		input.setSelectedIndex(0);
	    	}
	    	
		}
		if(ae.getSource()==set){
			String name = (String) input.getSelectedItem();
			Integer sample = (Integer) sampleRate.getSelectedItem();
			runner.updateFourier(new AudioInput(name, sample, 8192));
			///TODO: Add dropdown for buffer length
		}
	}
}
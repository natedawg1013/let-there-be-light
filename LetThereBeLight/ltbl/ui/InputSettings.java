package ltbl.ui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import ltbl.control.Runner;
import ltbl.io.AudioInput;


public class InputSettings extends JPanel implements ActionListener {
	private static final long serialVersionUID = -997194328110445776L;
	JComboBox<String> cards;
	JComboBox<String> input;
	JComboBox<Integer> sampleRate;
	JButton   set;
	Runner runner;
	
    public InputSettings(Runner r){
    	super();
    	runner = r;		//need runner for passing new AudioIn to other components
    	this.setLayout(new GridLayout(2,3));
    	cards = new JComboBox<String>();
    	input = new JComboBox<String>();
    	sampleRate = new JComboBox<Integer>();
    	set = new JButton("Set");
    	//get lists of cards, lines, and sample rates
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
    	//set positions as per GridLayout
    	this.add(cards);
    	this.add(input);
    	this.add(sampleRate);
    	this.add(new JPanel());
    	this.add(new JPanel());
    	this.add(set);
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
		//if card selection changed
		if(ae.getSource()==cards){
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
		//if set button pressed
		if(ae.getSource()==set){
			String name = (String) input.getSelectedItem();
			Integer sample = (Integer) sampleRate.getSelectedItem();
			runner.updateFourier(new AudioInput(name, sample, 8192));
		}
	}
}
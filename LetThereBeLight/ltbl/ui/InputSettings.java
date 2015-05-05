package ltbl.ui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import ltbl.control.Runner;
import ltbl.io.TrueAudioInput;


public class InputSettings extends JPanel implements ActionListener {
	private static final long serialVersionUID = -997194328110445776L;
	JComboBox<String> mixers;
	JComboBox<Integer> sampleRate;
	JButton   set;
	Runner runner;
	
    public InputSettings(Runner r){
    	super();
    	runner = r;		//need runner for passing new AudioIn to other components
    	this.setLayout(new GridLayout(2,2));
    	mixers = new JComboBox<String>();
    	sampleRate = new JComboBox<Integer>();
    	set = new JButton("Set");
    	//get lists of cards, lines, and sample rates
    	List<Mixer.Info> cardList = TrueAudioInput.getAvailableMixers();
    	for(Mixer.Info m : cardList) mixers.addItem(m.getName());
    	int[] rates = {22050, 44100, 48000};
    	for(int i : rates) sampleRate.addItem(i);
    	sampleRate.setSelectedIndex(1);
    	setPositions();
    	set.addActionListener(this);
    	mixers.addActionListener(this);
    }
    
    private void setPositions(){
    	//set positions as per GridLayout
    	this.add(mixers);
    	this.add(sampleRate);
    	this.add(new JPanel());
    	this.add(set);
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
		//if set button pressed
		if(ae.getSource()==set){
			Mixer.Info mixer = TrueAudioInput.getAvailableMixers().get(mixers.getSelectedIndex());
			try {
				runner.getFourier().getInput().end();
				runner.updateFourier(new TrueAudioInput(mixer,  (Integer) sampleRate.getSelectedItem(), 8192));
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
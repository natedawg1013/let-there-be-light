package ltbl.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PeriodicEffectPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 3596912008204436686L;

	JLabel temp;
	JLabel onOff;
	JCheckBox on;
	JLabel sync;
	JCheckBox beatmatch;
	JLabel text;
	JTextField one;
	JLabel colon;
	JTextField two;
	public PeriodicEffectPanel(){
		super(new GridBagLayout());
		onOff = new JLabel("Enabled:");
		on = new JCheckBox();
		sync = new JLabel("Beatmatch:");
		beatmatch = new JCheckBox();
		text = new JLabel("BPM:");
		one = new JTextField(5);
		colon = new JLabel(":");
		two = new JTextField(5);	
		
		colon.setVisible(false);
		two.setVisible(false);
		beatmatch.addActionListener(this);

		placeComponents();
	}
	
	private void placeComponents(){
		GridBagConstraints c = new GridBagConstraints();
			c.weightx=0.5; c.weighty=0.5;
			c.anchor=GridBagConstraints.NORTH;
			c.gridx=0; c.gridy=0;
		this.add(onOff, c);
			c.gridx=1;
		this.add(on, c);
			c.gridx=2;
		this.add(sync);
			c.anchor=GridBagConstraints.NORTH;
			c.gridx=3;
		this.add(beatmatch, c);
			c.gridx=0; c.gridy=1;
		this.add(text, c);
			c.gridy=2;
		this.add(one, c);
			c.gridx=1;
		this.add(colon, c);
			c.gridx=2;
		this.add(two, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==beatmatch){
			boolean bm = beatmatch.isSelected();
			if(bm)
				text.setText("Ratio:");
			else
				text.setText("BPM");
			
			colon.setVisible(bm);
			two.setVisible(bm);
		}
		
	}
}
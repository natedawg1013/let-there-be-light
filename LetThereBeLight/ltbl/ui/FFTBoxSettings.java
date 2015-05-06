package ltbl.ui;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JButton;

import ltbl.algo.FFTBox;
import ltbl.control.Runner;

public class FFTBoxSettings extends JPanel implements ActionListener {
    
    private Runner runner;
    
    private FFTBox box;
    JButton set;
    JComboBox<String> outputs;
    JSpinner channel1, channel2, channel3;
    JSpinner level1, level2, level3;
    
    public FFTBoxSettings(Runner r, FFTBox b) {
        super( new GridBagLayout() );
        runner = r;
        box = b;
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        c.gridx = 4;
        c.gridy = 1;
        set = new JButton("Set");
        this.add(set, c);
        set.addActionListener(this);
        
        
        c.gridx = 0;
        c.gridy = 0;
        this.add( new JLabel("Channel Options"), c );
        c.gridx = 1;
        this.add( new JLabel("Channel 1"), c );
        c.gridx = 2;
        this.add( new JLabel("Channel 2"), c );
        c.gridx = 3;
        this.add( new JLabel("Channel 3"), c );

        c.gridx = 0;
        c.gridy = 1;
        outputs = new JComboBox<String>();
        outputs.setEditable(true);
        outputs.addItem("One Channel (Mono)");
        outputs.addItem("Three Channels (RGB)");
        outputs.addActionListener(this);
        this.add(outputs, c);
        
        c.gridx = 1;
        channel1 = new JSpinner();
        channel1.setEnabled(false);
        this.add( channel1, c );
        c.gridx = 2;
        channel2 = new JSpinner();
        channel2.setEnabled(false);
        this.add( channel2, c );
        c.gridx = 3;
        channel3 = new JSpinner();
        channel3.setEnabled(false);
        this.add( channel3, c );
        
        SpinnerNumberModel levelModel = new SpinnerNumberModel(1.0, 0.0, 1.0, 0.1);
        c.gridx = 1;
        c.gridy = 2;
        level1 = new JSpinner(levelModel);
        level1.setEnabled(false);
        this.add( level1, c );
        c.gridx = 2;
        level2 = new JSpinner(levelModel);
        level2.setEnabled(false);
        this.add( level2, c );
        c.gridx = 3;
        level3 = new JSpinner(levelModel);
        level3.setEnabled(false);
        this.add( level3, c );
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if ( ae.getSource() == outputs ) {
        	if ( outputs.getSelectedIndex() == 0 ) {
        		// One channel
        		channel1.setEnabled(true);
        		channel2.setEnabled(false);
        		channel3.setEnabled(false);
        		level1.setEnabled(true);
        		level2.setEnabled(false);
        		level3.setEnabled(false);
        	}
        	else if ( outputs.getSelectedIndex() == 1 ) {
        		// Three channels
        		channel1.setEnabled(true);
        		channel2.setEnabled(true);
        		channel3.setEnabled(true);
        		level1.setEnabled(true);
        		level2.setEnabled(true);
        		level3.setEnabled(true);
        	}
        }
        else if ( ae.getSource() == set ) {
        	TreeMap<Integer, Float> channels = new TreeMap<Integer, Float>();
        	Integer ch;
        	Float lv;
        	if ( outputs.getSelectedIndex() == 0 ) {
        		// One channel
        		ch = (Integer) channel1.getValue();
        		lv = ((SpinnerNumberModel) level1.getModel()).getNumber().floatValue();
        		channels.put(ch, lv);
        	}
        	else if ( outputs.getSelectedIndex() == 1 ) {
        		// Three channels
        		ch = (Integer) channel1.getValue();
        		lv = ((SpinnerNumberModel) level1.getModel()).getNumber().floatValue();
        		channels.put(ch, lv);
        		ch = (Integer) channel2.getValue();
        		lv = ((SpinnerNumberModel) level2.getModel()).getNumber().floatValue();
        		channels.put(ch, lv);
        		ch = (Integer) channel3.getValue();
        		lv = ((SpinnerNumberModel) level2.getModel()).getNumber().floatValue();
        		channels.put(ch, lv);
        	}
        	box.setOutputs(channels);
        }
    }
    
    
}
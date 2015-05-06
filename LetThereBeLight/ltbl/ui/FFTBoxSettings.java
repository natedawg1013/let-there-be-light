package ltbl.ui;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import ltbl.algo.FFTBox;
import ltbl.control.Runner;
import ltbl.util.Pair;

public class FFTBoxSettings extends JPanel implements ActionListener {
	private static final long serialVersionUID = -6430031583947372067L;

	private FFTBox box;
    JButton set;
    JComboBox<String> outputs;
    JLabel lChan, lVal, lC1, lC2, lC3, lAh, lAv, lFreq, lAmp;
    JCheckBox autoHueX, autoValX, autoHueY, autoValY;
    JSpinner channel1, channel2, channel3;
    JSpinner level1, level2, level3;
    
    public FFTBoxSettings(Runner r, FFTBox b) {
        super( new GridBagLayout() );
        box = b;
        set = new JButton("Set");
        outputs = new JComboBox<String>();
        lChan = new JLabel("Channel");
        lVal = new JLabel("Output Value");
        lC1 = new JLabel("Channel 1");
        lC2 = new JLabel("Channel 2");
        lC3 = new JLabel("Channel 3");
        lAh = new JLabel("Auto Hue");
        lAv = new JLabel("Auto Brightness");
        lFreq = new JLabel("Frequency Based");
        lAmp = new JLabel("Amplitude Based");
        channel1 = new JSpinner(new SpinnerNumberModel(0, 0, 511, 1));
        channel2 = new JSpinner(new SpinnerNumberModel(0, 0, 511, 1));
        channel3 = new JSpinner(new SpinnerNumberModel(0, 0, 511, 1));
        level1 = new JSpinner(new SpinnerNumberModel(127, 0, 255, 1));
        level2 = new JSpinner(new SpinnerNumberModel(127, 0, 255, 1));
        level3 = new JSpinner(new SpinnerNumberModel(127, 0, 255, 1));
        autoHueX=new JCheckBox();
        autoValX=new JCheckBox();
        autoHueY=new JCheckBox();
        autoValY=new JCheckBox();
        
        outputs.setEditable(false);
        outputs.addItem("One Channel (Mono)");
        outputs.addItem("Three Channels (RGB)");
        
        outputs.addActionListener(this);
        set.addActionListener(this);
        autoHueX.addActionListener(this);
        autoHueY.addActionListener(this);
        autoValX.addActionListener(this);
        autoValY.addActionListener(this);
        
        updateEnabled();
        
        GridBagConstraints c = new GridBagConstraints();
        	c.weightx=0.5; c.weighty=0.5;
        	c.anchor=GridBagConstraints.CENTER;
        	c.fill = GridBagConstraints.HORIZONTAL;
        	c.gridx = 1; c.gridy = 0; c.gridwidth=2;
        this.add(outputs, c);
        	c.gridx=1; c.gridy=1;
        	c.gridwidth=1;
        this.add(lFreq, c);
        	c.gridx=2;
        this.add(lAmp, c);
        	c.gridx=0;c.gridy=2;
        this.add(lAh, c);
        	c.gridx=1;
        this.add(autoHueX, c);
        	c.gridx=2;
        this.add(autoHueY, c);
    		c.gridx=0;c.gridy=3;
	    this.add(lAv, c);
	    	c.gridx=1;
	    this.add(autoValX, c);
	    	c.gridx=2;
	    this.add(autoValY, c);
	        c.gridx=1; c.gridy=4;
        this.add( lC1, c );
        	c.gridx = 2;
        this.add( lC2, c );
        	c.gridx = 3;
        this.add( lC3, c );
        	c.gridx = 0; c.gridy = 5;
        this.add( lChan, c );
        	c.gridx = 1;
        this.add( channel1, c );
        	c.gridx = 2;
        this.add( channel2, c );
        	c.gridx = 3;
        this.add( channel3, c );
        	c.gridx = 0; c.gridy = 6;
        this.add( lVal, c);
        	c.gridx=1;
        this.add( level1, c );
        	c.gridx = 2;
        this.add( level2, c );
        	c.gridx = 3;
        this.add( level3, c );
        	c.gridx = 3; c.gridy = 7;
        this.add(set, c);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    	updateEnabled();
        if ( ae.getSource() == set ) {
        	ArrayList<Pair<Integer, Integer>> channels = new ArrayList<Pair<Integer, Integer>>();
        	Pair<Integer, Integer> channel = new Pair<Integer, Integer>();
        	boolean ahx, ahy, avx, avy, rgb;
        	channel.first = (Integer) channel1.getValue();
    		channel.second = ((SpinnerNumberModel) level1.getModel()).getNumber().intValue();
    		channels.add(channel);
    		channel.first = (Integer) channel2.getValue();
    		channel.second = ((SpinnerNumberModel) level2.getModel()).getNumber().intValue();
    		channels.add(channel);
    		channel.first = (Integer) channel3.getValue();
    		channel.second = ((SpinnerNumberModel) level2.getModel()).getNumber().intValue();
    		channels.add(channel);
    		rgb=(outputs.getSelectedIndex()==1);
    		ahx=autoHueX.isSelected();
    		ahy=autoHueY.isSelected();
    		avx=autoValX.isSelected();
    		avy=autoValY.isSelected();
        	box.set(channels, ahx, ahy, avx, avy, rgb);
        }
    }
    
    private void updateEnabled(){
    	boolean ahx, ahy, avx, avy;
    	boolean c1, c2, c3;
    	boolean c1v, c2v, c3v;
    	ahx=ahy=avx=avy=c1=c2=c3=c1v=c2v=c3v=true;
    	if(outputs.getSelectedIndex()==0){
    		c2=c3=c2v=c3v=ahx=ahy=false;
    		if (autoValX.isSelected() || autoValY.isSelected() ){
    			c1v=false;
    		}
    	}
    	if(autoHueX.isSelected()) ahy=c2v=c3v=false;
    	if(autoHueY.isSelected()) ahx=c2v=c3v=false;
    	if(autoValX.isSelected()) avy=false;
    	if(autoValY.isSelected()) avx=false;
    	if( (autoHueX.isSelected() || autoHueY.isSelected() ) &&
    	    (autoValX.isSelected() || autoValY.isSelected() )    ){
    		c1v=false;
    	}
    	autoHueX.setEnabled(ahx);
    	autoHueY.setEnabled(ahy);
    	autoValX.setEnabled(avx);
    	autoValY.setEnabled(avy);
    	channel1.setEnabled(c1);
    	channel2.setEnabled(c2);
    	channel3.setEnabled(c3);
    	level1.setEnabled(c1v);
    	level2.setEnabled(c2v);
    	level3.setEnabled(c3v);
    }
    
}
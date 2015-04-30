package ltbl.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import ltbl.algo.PeriodicEffect;
import ltbl.control.Runner;

public class PeriodicEffectPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 3596912008204436686L;
	JLabel lType;
	JComboBox<String> type;
	JLabel onOff;
	JCheckBox on;
	JLabel lPeriod;
	JLabel seconds;
	JLabel lPW;
	OutList outs;
	PeriodicEffect pe;
	JSpinner period;
	JSpinner pulsewidth;
	JButton update;
	
	public PeriodicEffectPanel(Runner r){
		super(new GridBagLayout());
		lType = new JLabel("Type:");
		type = new JComboBox<String>();
		onOff = new JLabel("Enabled:");
		on = new JCheckBox();
		lPeriod = new JLabel("Period:");
		seconds = new JLabel("seconds");
		lPW = new JLabel("Pulsewidth %");
		update = new JButton("Update");
		outs = new OutList(this);
		pe=new PeriodicEffect(r);
		this.setPreferredSize(this.getSize());
		SpinnerModel periodModel = new SpinnerNumberModel(1, 0.1, 120.0, 0.1);
		period=new JSpinner(periodModel);
		SpinnerModel pulsewidthModel = new SpinnerNumberModel(50, 0.0, 100.0, .1);
		pulsewidth=new JSpinner(pulsewidthModel);
		type.addItem("sine"); type.addItem("square");
		type.addItem("triangle"); type.addItem("sawtooth");
		type.addItem("pulse");
		type.addActionListener(this);
		placeComponents();
		update.addActionListener(this);
		r.addPeriodicEffect(pe);
		pe.setEnabled(true);
		pulsewidth.setEnabled(false);
		
	}
	
	private void placeComponents(){
		GridBagConstraints c = new GridBagConstraints();
			c.weightx=0.5; c.weighty=0.5;
			c.anchor=GridBagConstraints.NORTH;
			c.gridx=0; c.gridy=0;
		this.add(lType, c);
			c.gridx=1;
		this.add(type, c);
			c.gridy=1; c.gridx=0;
			c.gridwidth=1;
		this.add(onOff, c);
			c.gridx=1;
		this.add(on, c);
			c.gridx=0; c.gridy=2;
		this.add(lPeriod, c);
			c.gridx=1;
		this.add(period, c);
			c.gridy=3; c.gridx=0;
		this.add(lPW, c);
			c.gridx=1;
		this.add(pulsewidth, c);
		//this.add(seconds);
			c.gridx=2; c.gridy=0;
			c.gridheight=3;
		this.add(outs, c);
			c.gridx=2; c.gridy=4;
			c.gridheight=1;
		this.add(update, c);
			
	}
	
	public void addOutput(int o){
		pe.addChannel(o);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==update){
			float p = ((SpinnerNumberModel) period.getModel()).getNumber().floatValue();
			if(((String)type.getSelectedItem()).equals("pulse")){
				float d = ((SpinnerNumberModel) pulsewidth.getModel()).getNumber().floatValue();
				pe.setLFO((String) type.getSelectedItem(), p, d/100);
			}
			else{
				pe.setLFO((String) type.getSelectedItem(), p);
			}
			pe.setEnabled(true);
		}
		if(e.getSource()==type){
			if(((String)type.getSelectedItem()).equals("pulse")){
				pulsewidth.setEnabled(true);
			}
			else
				pulsewidth.setEnabled(false);
		}
	}
	
	class OutList extends JPanel implements ActionListener{
		private static final long serialVersionUID = -2169636483548098286L;
		private JLabel lOut;
		private ArrayList<JLabel> outs;
		PeriodicEffectPanel master;
		JSpinner channel0;
		JButton add;
		
		public OutList(PeriodicEffectPanel master){
			super();
			this.setLayout(new GridBagLayout());
			this.master=master;
			lOut = new JLabel("Outputs:");
			outs = new ArrayList<JLabel>();
			SpinnerModel channel0Model = new SpinnerNumberModel(0, 0, 255, 1);
			channel0=new JSpinner(channel0Model);
			channel0.setEditor(new JSpinner.NumberEditor(channel0, "#"));
			add = new JButton("Add");
			add.addActionListener(this);
			
			GridBagConstraints c = new GridBagConstraints();
				c.weightx=0.5; c.weighty=0.5;
				c.anchor=GridBagConstraints.NORTH;
				c.gridx=0; c.gridy=0;
			this.add(lOut, c);
				c.gridy=16;
			this.add(channel0, c);
				c.gridy=17;
			this.add(add, c);
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource()==add){
				int n = ((SpinnerNumberModel) channel0.getModel()).getNumber().intValue();
				JLabel out = new JLabel(String.valueOf(n));
				GridBagConstraints c = new GridBagConstraints();
					c.weightx=0.5; c.weighty=0.5;
					c.anchor=GridBagConstraints.NORTH;
					c.gridx=0; c.gridy=outs.size()+1;
				this.add(out, c);
				outs.add(out);
				master.addOutput(n);
				this.revalidate();
			}
		}
	}
}
package ltbl.ui;

import gnu.io.CommPortIdentifier;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.util.Enumeration;
//import javax.comm.CommPortIdentifier;
import java.util.ArrayList;

import ltbl.control.Runner;
import ltbl.io.Output;
import ltbl.io.SerialComm;
import ltbl.io.SimOut;

public class OutputSettings extends JPanel implements ActionListener {
	private static final long serialVersionUID = -3103307847981620153L;
	
	Runner runner;
	JButton set;
	JComboBox<String> output_box;
	JComboBox<String> port_box;
	JComboBox<Integer> baud_box;
	List<String> serialList = new ArrayList<String>();
	List<CommPortIdentifier> ports;
	JLabel output_label;
	JLabel port;
	JLabel baudRate;
	
	public OutputSettings(Runner r) {
		super(new GridBagLayout());
		runner = r;
		set = new JButton("Set");
		output_label = new JLabel("Output");
		output_box = new JComboBox<String>();
		port = new JLabel("Port");
		port_box = new JComboBox<String>();
		baudRate = new JLabel("baud");
		baud_box = new JComboBox<Integer>();
		
		set.addActionListener(this);
		output_box.addItem("Serial");
		output_box.addItem("Simulator");
		output_box.addActionListener(this);
		port_box.setEditable(false);
		port_box.addActionListener(this);
		baud_box.setEditable(false);
		for(int i : SerialComm.BAUD_RATES) baud_box.addItem(i);
		baud_box.addActionListener(this);
		ports=ltbl.io.SerialComm.listPorts();
		port_box.removeAllItems();
		for(CommPortIdentifier id : ports){
			port_box.addItem(id.getName());
		}
		
		GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0; c.gridy = 0;
		this.add(output_label, c);
			c.gridx=1;
		this.add(output_box, c);
			c.gridx=2;
		this.add(port, c);
			c.gridx=3;
		this.add(port_box, c);
			c.gridx=4;
		this.add(baudRate, c);
			c.gridx=5;
		this.add(baud_box, c);
			c.gridy=1;
		this.add(set, c);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==set) {
			Output out = null;
			if( ( (String) output_box.getSelectedItem() ).equals("Serial") ){
				//out = new DMXOut( (String) port_box.getSelectedItem(),
				//				  (String) dmxver_box.getSelectedItem() );
			}
			else {
				out = new SimOut( (String) baud_box.getSelectedItem() );
				port_box.removeAllItems();
				port_box.addItem("Not Applicable");
			}
			runner.setOutput(out);
		}
		if(ae.getSource()==output_box){
			if(((String)output_box.getSelectedItem()).equals("Serial")){
				port_box.setEnabled(true);
				ports=ltbl.io.SerialComm.listPorts();
				port_box.removeAllItems();
				for(CommPortIdentifier id : ports){
					port_box.addItem(id.getName());
				}
				baud_box.removeAllItems();
				baud_box.setEditable(true);
				for(int i : SerialComm.BAUD_RATES) baud_box.addItem(i);
			}
			else{
				port_box.removeAllItems();
				port_box.setEnabled(false);
				baud_box.removeAllItems();
				baud_box.setEnabled(false);
			}
		}

	}
		
		///TODO: 
		//need function to get available serial ports
		//on action event from output box, if serial, rebuild port box from the getSerialPorts method
		//if simulator, rebuild port box with just "Not Applicable" element
}
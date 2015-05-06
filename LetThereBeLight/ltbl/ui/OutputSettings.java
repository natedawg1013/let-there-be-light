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

import ltbl.control.Runner;
import ltbl.iface.Output;
import ltbl.io.DMXOut;
import ltbl.io.SerialComm;
import ltbl.io.SimOut;

public class OutputSettings extends JPanel implements ActionListener {
	private static final long serialVersionUID = -3103307847981620153L;
	
	Runner runner;
	JButton set;
	JComboBox<String> output_box;
	JComboBox<String> port_box;
	JComboBox<Integer> baudBox;
	List<CommPortIdentifier> portList;
	JLabel output_label;
	JLabel port;
	JLabel baudRate;
	
	
	public OutputSettings(Runner r) {
		super(new GridBagLayout());
		runner = r;
		output_box=new JComboBox<String>();
		output_label = new JLabel("Output");
		port = new JLabel("Port");
		port_box = new JComboBox<String>();
		baudRate = new JLabel("Baud");
		baudBox = new JComboBox<Integer>();
		set = new JButton("Set");

		output_box.addItem("Serial");
		output_box.addItem("Simulator");
		output_box.addActionListener(this);
		port_box.setEditable(false);
		baudBox.setEditable(false);
		baudBox.addActionListener(this);
		set.addActionListener(this);
		for(int i : SerialComm.BAUD_RATES) baudBox.addItem(i);
		portList=SerialComm.listPorts();
		for(CommPortIdentifier id : portList)
			port_box.addItem(id.getName());
		
		GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
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
		this.add(baudBox, c);
			c.gridy=1;
		this.add(set, c);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==set) {
			Output out = null;
			if( ( (String) output_box.getSelectedItem() ).equals("Serial") ){
				out = new DMXOut( portList.get(port_box.getSelectedIndex()),
								  (Integer) baudBox.getSelectedItem() );
			}
			else {
				out = new SimOut();
			}
			runner.setOutput(out);
		}
		if(ae.getSource()==output_box){
			if(output_box.getSelectedIndex()==0){
				port_box.setEnabled(true);
				baudBox.setEnabled(true);
				port_box.removeAllItems();
				portList=SerialComm.listPorts();
				for(CommPortIdentifier id : portList){
					port_box.addItem(id.getName());
				}
			}
			else{
				port_box.setEnabled(false);
				baudBox.setEnabled(false);
			}
		}

	}
		
		///TODO: 
		//need function to get available serial ports
		//on action event from output box, if serial, rebuild port box from the getSerialPorts method
		//if simulator, rebuild port box with just "Not Applicable" element
}
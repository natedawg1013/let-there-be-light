package ltbl.ui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;

//import javax.comm.CommPortIdentifier;
import java.util.ArrayList;

import ltbl.control.Runner;
import ltbl.io.Output;
import ltbl.io.SimOut;

public class OutputSettings extends JPanel implements ActionListener {
	private static final long serialVersionUID = -3103307847981620153L;
	
	Runner runner;
    JButton set;
    JComboBox<String> output_box;
    JComboBox<String> port_box;
    JComboBox<String> dmxver_box;
    List<String> serialList = new ArrayList<String>();
    
    
    private void getSerialPorts() {
        
       /* Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        
        while(portList.hasMoreElements()) {
            CommPortIdentifier i = (CommPortIdentifier) portList.nextElement();
            if(i.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                serialList.add(i.getName());
            }
        }*/
    }
    
    public OutputSettings(Runner r) {
        
        runner = r;
        set = new JButton("Set");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 1;
        this.add(set, c);
        set.addActionListener(this);
        
        JLabel output_label = new JLabel("Output");
        this.add(output_label);
        output_box = new JComboBox<String>();
        output_box.setEditable(false);
        output_box.addItem("Serial");
        output_box.addItem("Simulator");
        output_box.addActionListener(this);
        this.add(output_box);
        
        JLabel port = new JLabel("Port");
        this.add(port);
        port_box = new JComboBox<String>();
        port_box.setEditable(false);
        port_box.addActionListener(this);
        this.add(port_box);
        
        JLabel DMXver = new JLabel("DMX Ver.");
        this.add(DMXver);
        dmxver_box = new JComboBox<String>();
        dmxver_box.setEditable(false);
        dmxver_box.addItem("DMX 512");
        dmxver_box.addActionListener(this);
        this.add(dmxver_box);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==set) {
            Output out = null;
            if( ( (String) output_box.getSelectedItem() ).equals("Serial") ){
                //out = new DMXOut( (String) port_box.getSelectedItem(),
                //                  (String) dmxver_box.getSelectedItem() );
                port_box.removeAllItems();
                this.getSerialPorts();
                for(int i = 0; i < serialList.size(); i++)
                    port_box.addItem(serialList.get(i));
            }
            else {
                out = new SimOut( (String) dmxver_box.getSelectedItem() );
                port_box.removeAllItems();
                port_box.addItem("Not Applicable");
            }
            runner.setOutput(out);
        }
        if(ae.getSource()==output_box){
        	if(output_box.getSelectedIndex()==0)
        		port_box.setEnabled(true);
        	else
        		port_box.setEnabled(false);
        }

    }
        
        ///TODO: 
        //need function to get available serial ports
        //on action event from output box, if serial, rebuild port box from the getSerialPorts method
        //if simulator, rebuild port box with just "Not Applicable" element
}
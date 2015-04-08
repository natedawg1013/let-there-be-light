package ltbl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;


public class OutputSettings extends JPanel {
    
    Runner runner;
    JButton Add;
    
    public OutputSettings(Runner r) {
        runner = r;
        Add = new JButton("Add");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 1;
        this.add(Add, c);
        
        JLabel output_label = new JLabel("Output");
        this.add(output_label);
        private JComboBox output_box;
        output_box = new JComboBox();
        output_box.setEditable(true);
        output_box.addItem("Serial");
        output_box.addItem("Simulator");
        output_box.addActionListener(this);
        this.add(output_box);
        
        JLabel output_label = new JLabel("Port");
        this.add(port);
        private JComboBox port_box;
        port_box = new JComboBox();
        port_box.setEditable(true);
        port_box.addItem("COM1");
        port_box.addActionListener(this);
        this.add(port);
        
        JLabel DMXver = new JLabel("DMX Ver.");
        this.add(DMXver);
        private JComboBox dmxver_box;
        dmxver_box = new JComboBox();
        dmxver_box.setEditable(true);
        dmxver_box.addItem("DMX 512");
        dmxver_box.addActionListener(this);
        this.add(dmxver);
        
        
        public void actionPerformed(ActionEvent ae) {
            
        }
        
        
    }
}
package ltbl.ui;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import ltbl.algo.FFTBox;
import ltbl.control.Runner;

public class FFTBoxSettings extends JPanel implements ActionListener {
    
    private Runner runner;
    
    private FFTBox box;
    JButton add;
    JComboBox<String> outputs;
    JComboBox<Integer> start_amplitude;
    JComboBox<Integer> end_amplitude;
    JComboBox<Integer> start_frequency;
    JComboBox<Integer> end_frequency;
    
    public FFTBoxSettings(Runner r, FFTBox b) {
        super( new GridBagLayout() );
        runner = r;
        box = b;
        add = new JButton("Add");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 1;
        this.add(add, c);
        
        JLabel output_label = new JLabel("Outputs");
        this.add(output_label);
        start_amplitude = new JComboBox<Integer>();
        end_amplitude = new JComboBox<Integer>();
        start_frequency = new JComboBox<Integer>();
        end_frequency = new JComboBox<Integer>();
        outputs = new JComboBox<String>();
        
        outputs.setEditable(true);
        start_amplitude.setEditable(true);
        end_amplitude.setEditable(true);
        start_frequency.setEditable(true);
        end_frequency.setEditable(true);
        
        
        // adding channels 1 & 2 for now 
        // adding temporary amplitude and freq. ranges as well
        // this will have to be fixed later today
        outputs.addItem("Channel 1");
        outputs.addItem("Channel 2");
        outputs.addActionListener(this);
        this.add(outputs);
        
        int amps[] = {0, 100, 200};
        int freqs[] = {22050, 44100, 48000};
        
        for(int i : freqs) {
         start_frequency.addItem(i);
         end_frequency.addItem(i);
        }
        
        for(int j : amps) {
            start_amplitude.addItem(j);
            end_amplitude.addItem(j);
        }
        
        start_amplitude.addActionListener(this);
        end_amplitude.addActionListener(this);
        start_frequency.addActionListener(this);
        end_amplitude.addActionListener(this);
        
        this.add(start_amplitude);
        this.add(end_amplitude);
        this.add(start_frequency);
        this.add(end_frequency);
        
        
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        /*
        if(ae.getSource()==add) {
            
           box.setDimensions((Integer)start_frequency.getSelectedItem(),
             (Integer)start_amplitude.getSelectedItem(), (Integer)end_frequency.getSelectedItem(),
             (Integer)end_amplitude.getSelectedItem());
            
        }
        */
        
    }
    
    
}
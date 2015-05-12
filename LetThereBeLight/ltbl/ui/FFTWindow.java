package ltbl.ui;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JLayeredPane;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ltbl.algo.FourierAnalysis;
import ltbl.control.Runner;
import ltbl.iface.FourierUpdateListener;

// The FFTWindow class holds all FFT related classes in a UI window
// Functional Cohesion. All parts of FFT contribute to the task of the FFT Module
// FFTWindow collaborates with the following classes
// FourierAnalysis - DataCoupling. FFTWindow holds and displays the contents of FFTBox
// FFTBox - DataCoupling. FFTWindow holds and displays the contents of FFTBox

public class FFTWindow extends JPanel implements ActionListener, FourierUpdateListener{
	private static final long serialVersionUID = 6071964663827731183L;
	private Runner runner;
    private JLayeredPane graphPane;
    private FFTGraph graph;
    private FFTMousePanel mousePanel;
    private JButton addBox, deleteBox;

    public FFTWindow( Runner r ) {
        super( new GridBagLayout() );
        runner = r;
        graph = new FFTGraph(runner);
        graphPane = new JLayeredPane();
        mousePanel = new FFTMousePanel( runner, graphPane );
        addBox = new JButton("Add");
        addBox.addActionListener(this);
        deleteBox = new JButton("Delete");
        deleteBox.addActionListener(this);
        setPositions();
        //this.setVisible(true);
    }
	
    private void setPositions(){
    	graphPane.add( graph, new Integer(0) );
    	graphPane.add( mousePanel, new Integer(1) );
    	
    	GridBagConstraints c = new GridBagConstraints();
    	Dimension d = new Dimension(1000, 700);
    	c.gridx = c.gridy = 0;
    	c.weightx = 1.0;
    	c.weighty = .98;
    	c.gridwidth = 2;
		graphPane.setPreferredSize(d);
		graph.setPreferredSize(d);
		mousePanel.setPreferredSize(d);
        this.add( graphPane, c );
    	this.add( mousePanel, c );
    	this.add( graph, c );
    	c.gridwidth = 1;
        c.weightx = .50;
        c.weighty = .1;
    	c.gridy = 1;
    	this.add( addBox, c );
    	c.gridx = 1;
    	this.add( deleteBox, c );
    	mousePanel.setSize();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()==addBox) {
            mousePanel.setState( FFTMousePanel.EState.ADD );
        }
        else if (ae.getSource()==deleteBox) {
        	int level = JLayeredPane.getLayer(mousePanel);
        	if ( level-- > 0 ) {
        		FFTBoxOverlay nix = (FFTBoxOverlay) graphPane.getComponentsInLayer(level)[0];
        		graphPane.remove( nix );
        		graphPane.setLayer(mousePanel, level);
        	}
        }
    }

	public void updateGraph(float[] points) {
		graph.update(points);
		graphPane.repaint();
	}

	@Override
	public void update(FourierAnalysis a) {
		graph.update(a);
		graphPane.repaint();
	}

}
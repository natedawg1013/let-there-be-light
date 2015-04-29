package ltbl.ui;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import ltbl.control.Runner;

//one bar graph window that shows real-time display of the audio db vs frequency
//six buttons
//-sound based effects
//-time based effects
//-input options
//-output options
//-start
//-stop

//button locations and grid constraints
//https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html

//see "User Scenarios" for main menu UI info
//https://docs.google.com/document/d/1ZsLmtc-dvg6UlhFYZyuu-Z8Y8vvB8OXcBTOWN7V5kgU/

public class MainMenu extends JPanel implements ActionListener{
	private static final long serialVersionUID = -2997035831207668668L;
	Runner runner;
	JButton input_settings;
	JButton sound_based_effects;
	JButton input_options;
	JButton output_options;
	JButton time_based_effects;
	JButton start;
	JButton stop;
	BarGraph graph;
	ArrayList<ActionListener> actionListeners;

	//BAR GRAPH BOX PENDING

	public MainMenu(Runner r) {
		super(new GridBagLayout());
		this.setPreferredSize(new Dimension(700, 400));
		runner = r;
		sound_based_effects = new JButton("Sound Based Effects");
		time_based_effects = new JButton("Time Based Effects");
		input_options = new JButton("Input Options");
		output_options = new JButton("Output Options");
		start = new JButton("Start");
		stop = new JButton("Stop");
		graph = new BarGraph();
		actionListeners = new ArrayList<ActionListener>();
		
		setPositions();
		
		addActionListeners();
	}
	private void setPositions(){
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
			c.weightx = 0.5;		
			c.gridx = 0; c.gridy = 0;
		this.add(sound_based_effects, c);
			c.gridx = 3; c.gridy = 0;
		this.add(time_based_effects, c);
			c.gridx = 0; c.gridy = 1;
		this.add(input_options, c);
			c.gridx = 3; c.gridy = 1;
		this.add(output_options, c);
			c.weightx = 0.75;
			c.gridx = 1; c.gridy = 1;
		this.add(start, c);
			c.gridx = 2; c.gridy = 1;
		this.add(stop, c);
			c.gridx = 1; c.gridy = 0;
			c.gridheight = 1; c.gridwidth = 2;
			c.weighty=0.5;
			c.fill = GridBagConstraints.BOTH;
		this.add(graph, c);

	}

	private void addActionListeners(){
		sound_based_effects.addActionListener(this);
		time_based_effects.addActionListener(this);
		start.addActionListener(this);
		stop.addActionListener(this);
		input_options.addActionListener(this);
		output_options.addActionListener(this);
	}

	public void addActionListener(ActionListener al){
		actionListeners.add(al);
		
	}

	//BUTTON ACTIONS AND EVENTS
	@Override
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==sound_based_effects){
			runner.showFFTWindow(true);
		}

		else if(ae.getSource()==time_based_effects) {
			runner.showPeriodicEffects(true);
		}

		else if(ae.getSource()==input_options) {
			runner.showInputSettings(true);
		}

		else if(ae.getSource()==output_options) {
			runner.showOutputSettings(true);
		}

		else if(ae.getSource()==start) {
			ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "start");
			for(ActionListener al : actionListeners){
				al.actionPerformed(event);
			}
		}

		else if(ae.getSource()==stop) {
			ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "stop");
			for(ActionListener al : actionListeners){
				al.actionPerformed(event);
			}
		}
	}
	
	/*public LineGraph getLineGraph(){
		return graph;
	}*/
	
	public BarGraph getBarGraph(){
		return graph;
	}
}
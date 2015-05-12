package ltbl.io;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import ltbl.iface.Output;
import ltbl.iface.OutputSource;
import ltbl.sim.SimPanel;

// The class SimOut displays a virtual light show
// Procedural cohesion. After audio has been fully processed, the output is interpreted as data
// SimOut collaborates with the following classes
// AudioInput - 
// Data coupling. AudioInput is processed before the data reaches SimOut
// Output - 
// Control coupling. Output flags the output to either be of type DMXOut or SimOut and notifies the respective class to perform its tasks
// Runner - 
// Control coupling. Runner controls the flow of classes called from MainMenu

public class SimOut implements Output, Runnable {
	private float[] values;
	private SimPanel panel;
	private JFrame frame;
	ArrayList<OutputSource> sources;
	private volatile boolean running;

    public SimOut() {
    	sources= new ArrayList<OutputSource>();
    	values = new float[512];
    	panel = new SimPanel();
    	frame = new JFrame("Simulator");
    	frame.add(panel);
    	frame.setSize(new Dimension(500, 400));
    	frame.pack();
    	panel.setVisible(true);
    	frame.setVisible(true);
    }

	@Override
	public synchronized void setChannel(int channel, float value) {
		values[channel]=value;
	}
	
	public void addSource(OutputSource o){
		sources.add(o);
	}

	@Override
	public void update() {
		for(OutputSource os : sources)
			os.getOutputs(this);
		panel.update(values);
	}

	@Override
	public void run() {
		running=true;
		while(running){
			update();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void copy(Output other) {
		this.sources = new ArrayList<OutputSource>(other.getSources());
	}

	@Override
	public ArrayList<OutputSource> getSources() {
		return new ArrayList<OutputSource>(sources);
	}

	@Override
	public void stop() {
		running=false;		
	}
	
	public void show(){
		frame.setVisible(true);
	}
}
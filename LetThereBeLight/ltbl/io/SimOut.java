package ltbl.io;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import ltbl.iface.Output;
import ltbl.iface.OutputSource;
import ltbl.sim.SimPanel;

public class SimOut implements Output, Runnable {
	private float[] values;
	private SimPanel panel;
	private JFrame frame;
	ArrayList<OutputSource> sources;

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
		while(true){
			update();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
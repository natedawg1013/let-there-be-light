package ltbl.io;

import java.util.ArrayList;

import ltbl.iface.Output;
import ltbl.iface.OutputSource;
import gnu.io.*;

// The class DMXOut interfaces with the DMX controller hardware
// Functional cohesion. DMXOut only activates when DMX output is desired by the user and directed by the Output class
// The DMXOut class collaborates with the following classes
// AudioInput - Data coupling. AudioInput is processed before the data reaches SimOut
// Output - Control coupling. Output flags the output to either be of type DMXOut or SimOut and notifies the respective class to perform its tasks
// Runner - Control coupling. Runner controls the flow of classes called from MainMenu

public class DMXOut implements Output{
	SerialComm comm;
	ArrayList<OutputSource> sources;
    
	public DMXOut(CommPortIdentifier port, int baud) {
		try {
			comm = new SerialComm(port, baud);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sources=new ArrayList<OutputSource>();
    }
	@Override
	public void setChannel(int channel, float value) {
		byte[] data = new byte[2];
		data[0]=(byte) channel;
		data[1]=(byte) (value*255);
		comm.write(data, 0, 2);
	}
	@Override
	public void update() {
		// Unnecessary, controller will update on its own schedule
	}
	@Override
	public void addSource(OutputSource o) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
    
}
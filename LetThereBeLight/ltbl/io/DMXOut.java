package ltbl.io;

import java.util.ArrayList;

import ltbl.iface.Output;
import ltbl.iface.OutputSource;
import gnu.io.*;

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
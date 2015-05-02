package ltbl.io;

import gnu.io.*;

public class DMXOut implements Output{
	SerialComm comm;
    
	public DMXOut(CommPortIdentifier port, int baud) {
		try {
			comm = new SerialComm(port, baud);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    
}
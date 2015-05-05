package ltbl.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import gnu.io.*;

public class SerialComm{
	public static int[] BAUD_RATES={300,1200,2400,4800,9600,14400,19200,
									28800,38400,57600,115200,230400};
	OutputStream out;
	
	public static List<CommPortIdentifier> listPorts(){
		ArrayList<CommPortIdentifier> ports = new ArrayList<CommPortIdentifier>();
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		while ( portEnum.hasMoreElements() ) 
		{
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			if(portIdentifier.getPortType()==CommPortIdentifier.PORT_SERIAL)
				ports.add(portIdentifier);
		}	
		return ports;
	}
	
	public SerialComm(CommPortIdentifier portIdentifier, int baud) throws Exception{
		connect(portIdentifier, baud);
	}
	
	void connect ( CommPortIdentifier portIdentifier , int baud) throws Exception{
		if ( portIdentifier.isCurrentlyOwned() )
			throw new Exception("Port is currently in use");
		else{
			CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
			if ( commPort instanceof SerialPort ){
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(baud,SerialPort.DATABITS_8,
											   SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
				out = serialPort.getOutputStream();
			}
			else
				System.out.println("Error: Only serial ports are handled by this example.");
		}	 
	}
	
	void write(byte[] in, int off, int len){
		try {
			out.write(in, off, len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
package ltbl.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import gnu.io.*;

/**
 * Class which acts as an interface between the code and the
 * serial port. Provides methods for opening a port and writing
 * data to the port.
 * @author Nathan Bernard
 *
 */
public class SerialComm{
	/**Common baud rates*/
	public static int[] BAUD_RATES={300,1200,2400,4800,9600,14400,19200,
									28800,38400,57600,115200,230400};
	OutputStream out;
	/**
	 * Lists the available COM ports on the computer.
	 * @return list of CommPortIdentifiers
	 */
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
	
	/**
	 * Creates new SerialComm
	 * @param portIdentifier which COM port to connect to
	 * @param baud baud rate (data rate)  for the connection
	 * @throws Exception
	 */
	public SerialComm(CommPortIdentifier portIdentifier, int baud) throws Exception{
		connect(portIdentifier, baud);
	}
	
	/**
	 * connects to a COM port
	 * @param portIdentifier port to connect to
	 * @param baud baud rate (data rate)
	 * @throws Exception
	 */
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
	
	/**
	 * Writes 'len' bytes of data from array 'in' starting at index 'off'
	 * @param in data array
	 * @param off begin index
	 * @param len number of bytes to write
	 */
	void write(byte[] in, int off, int len){
		try {
			out.write(in, off, len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
package ltbl.io;

import java.util.ArrayList;

import ltbl.iface.Output;
import ltbl.iface.OutputSource;

/**
 * Dummy output which prevents errors from being thrown when an output
 * has not been selected. Still keeps track of sources so that when a true
 * output is selected, the references are not lost.
 * @author Nathan Bernard
 *
 */
public class DummyOut implements Output {	
	/** List of sources which provide data to an Output*/
	ArrayList<OutputSource> sources;
	/**
	 * Constructor
	 */
	public DummyOut(){
		sources=new ArrayList<OutputSource>();
	}
	/**
	 * needed to comply with interface
	 */
	@Override
    public  void setChannel(int channel, float value){
    	
    }
	/**
	 * needed to comply with interface
	 */
	@Override
    public  void update(){
    	
    }
	/**
	 * adds output source to list
	 * @param o OutputSource to add to list
	 */
	@Override
	public void addSource(OutputSource o) {
		sources.add(o);
		
	}
	/**
	 * needed to comply with interface
	 */
	@Override
	public void run() {
		
	}
	/**
	 * copies other Output's source list
	 * @param other Output to copy from
	 */
	@Override
	public void copy(Output other) {
		this.sources = new ArrayList<OutputSource>(other.getSources());
	}
	/**
	 * returns a list of sources for copying
	 * @return list of OutputSources
	 */
	@Override
	public ArrayList<OutputSource> getSources() {
		return new ArrayList<OutputSource>(sources);
	}
	/**
	 * needed to comply with interface
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
}
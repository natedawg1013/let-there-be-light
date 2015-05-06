package ltbl.io;

import java.util.ArrayList;

import ltbl.iface.Output;
import ltbl.iface.OutputSource;

public class DummyOut implements Output {	
	ArrayList<OutputSource> sources;
	
	public DummyOut(){
		sources=new ArrayList<OutputSource>();
	}
	
    public  void setChannel(int channel, float value){
    	
    }
    public  void update(){
    	
    }
	@Override
	public void addSource(OutputSource o) {
		sources.add(o);
		
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
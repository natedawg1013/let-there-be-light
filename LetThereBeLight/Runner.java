import java.awt.event.ActionEvent;
import java.util.EventListener;
import java.util.List;


public class Runner implements EventListener{
	private MainMenu menu;
	private OutputSettings os;
	private InputSettings is;
	private PeriodicEffectWindow pe;
	private FFTWindow fw;
	private List<Output> outputs;
	private List<FFTBox> boxes;
	private FourrierAnalysis fourrier;
	
    public static void main(String[] args) {
    	//instantiate runner;
        //instantiate all classes
    	runner.showMainMenu(true);
    }
    
    public void showMainMenu(boolean state){
    	os.setVisible(state);
    }
    
    public void showOutputSettings(boolean state){
    	os.setVisible(state);
    }
    
    public void showInputSettings(boolean state){
    	is.setVisible(state);
    }
    
    public void showPeriodicEffects(boolean state){
    	pe.setVisible(state);
    }
    
    public void showFFTWindow(boolean state){
    	fw.setVisible(state);
    }
    
    public void actionPerformed(ActionEvent e)
    	//for FFTBox in list
    		//give box fourrier analysis
    		//have box update lights
    	//run time based affects (could use their own thread)
    	//update FFTGraph
    	//update bar graph
    	
    }
}
package ltbl;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.swing.JFrame;


public class Runner implements EventListener{
	private JFrame menuFrame;
	private JFrame osFrame;
	private JFrame isFrame;
	private JFrame peFrame;
	private JFrame fwFrame;
	private MainMenu menu;
	private OutputSettings os;
	private InputSettings is;
	private PeriodicEffectWindow pe;
	private FFTWindow fw;
	private List<Output> outputs;
	private List<FFTBox> boxes;
	private FourierAnalysis fourrier;
	
    public static void main(String[] args) {
    	Runner runner = new Runner();
    	//instantiate runner;
        //instantiate all classes
    	runner.showMainMenu(true);
    	
    }
    
    public Runner(){
    	menu = new MainMenu();
    	os=new OutputSettings();
    	is=new InputSettings();
    	pe=new PeriodicEffectWindow();
    	fw=new FFTWindow();
    	outputs = new ArrayList<Output>();
    	boxes = new ArrayList<FFTBox>();
    	fourrier = new FourierAnalysis();
    	
    	menuFrame = new JFrame();
    	osFrame = new JFrame();
    	isFrame = new JFrame();
    	peFrame = new JFrame();
    	fwFrame = new JFrame();
    	
    	menuFrame.add(menu);
    	osFrame.add(os);
    	isFrame.add(is);
    	peFrame.add(pe);
    	fwFrame.add(fw);
    	
    	menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	osFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    	isFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    	peFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    public void showMainMenu(boolean state){
    	menuFrame.setVisible(state);
    }
    
    public void showOutputSettings(boolean state){
    	osFrame.setVisible(state);
    }
    
    public void showInputSettings(boolean state){
    	isFrame.setVisible(state);
    }
    
    public void showPeriodicEffects(boolean state){
    	peFrame.setVisible(state);
    }
    
    public void showFFTWindow(boolean state){
    	fwFrame.setVisible(state);
    }
    
    public void actionPerformed(ActionEvent e){
    	//for FFTBox in list
    		//give box fourier analysis
    		//have box update lights
    	//run time based affects (could use their own thread)
    	//update FFTGraph
    	//update bar graph
    	
    }
}
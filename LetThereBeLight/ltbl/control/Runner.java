package ltbl.control;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.swing.JFrame;

import ltbl.algo.FFTBox;
import ltbl.algo.FourierAnalysis;
import ltbl.io.AudioInput;
import ltbl.io.Output;
import ltbl.ui.FFTWindow;
import ltbl.ui.InputSettings;
import ltbl.ui.MainMenu;
import ltbl.ui.OutputSettings;
import ltbl.ui.PeriodicEffectWindow;


public class Runner implements EventListener, Runnable{
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
	private Thread update;
	
    public static void main(String[] args) {
    	Runner runner = new Runner();
    	//instantiate runner;
        //instantiate all classes
    	runner.showMainMenu(true);    	
    }
    
    public Runner(){
    	menu = new MainMenu(this);
    	os=new OutputSettings(this);
    	is=new InputSettings(this);
    	pe=new PeriodicEffectWindow();
    	fw=new FFTWindow(this);
    	outputs = new ArrayList<Output>();
    	boxes = new ArrayList<FFTBox>();
    	fourrier = new FourierAnalysis();
    	
    	menuFrame = new JFrame("Let There Be Light");
    	osFrame = new JFrame("Output Settings");
    	isFrame = new JFrame("Inout Settings");
    	peFrame = new JFrame("Time-Based Effects");
    	fwFrame = new JFrame("Sound-Based Effects");
    	
    	menuFrame.add(menu);
    	osFrame.add(os);
    	isFrame.add(is);
    	peFrame.add(pe);
    	fwFrame.add(fw);
    	
    	menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	osFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    	isFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    	peFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    	
    	menuFrame.setMinimumSize(new Dimension(500, 100));
    	peFrame.setMinimumSize(new Dimension(400, 300));
    	fwFrame.setMinimumSize(new Dimension(400, 300));
    	
    	menuFrame.pack();
    	menuFrame.setVisible(true);
    	
    	update = new Thread(this, "update");
    	update.start();
    }
    
    public void showMainMenu(boolean state){
    	menuFrame.setVisible(state);
    }
    
    public void showOutputSettings(boolean state){
    	osFrame.pack();
    	osFrame.setVisible(state);
    }
    
    public void showInputSettings(boolean state){
    	isFrame.pack();
    	isFrame.setVisible(state);
    }
    
    public void showPeriodicEffects(boolean state){
    	peFrame.pack();
    	peFrame.setVisible(state);
    }
    
    public void showFFTWindow(boolean state){
    	fwFrame.setVisible(state);
    }
    
    public void updateFourier(AudioInput in){
    	fourrier.setInput(in);
    }
    
    public void actionPerformed(ActionEvent e){
    	//for FFTBox in list
    		//give box fourier analysis
    		//have box update lights
    	//run time based affects (could use their own thread)
    	//update FFTGraph
    	//update bar graph
    	
    }
    
    public void run(){
		while(true){
			menu.getBarGraph().update();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
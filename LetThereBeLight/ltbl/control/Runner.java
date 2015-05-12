package ltbl.control;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;

import ltbl.algo.*;
import ltbl.iface.Output;
import ltbl.io.*;
import ltbl.ui.*;


public class Runner implements ActionListener/*, Runnable*/{
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
	private Output out;
	private List<PeriodicEffect> periodicEffects;
	private List<FFTBox> boxes;
	private FourierAnalysis fourier;
	private Thread outThread;
	
    public static void main(String[] args) throws LineUnavailableException, IOException, URISyntaxException {
    	String loc = Runner.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
    	loc = loc.substring(0, loc.lastIndexOf('/')) + "/lib";
    	addDir(loc);
    	Runner runner = new Runner();
    	//instantiate runner;
        //instantiate all classes
    	runner.showMainMenu(true);
    }
    
    public static void addDir(String s) throws IOException {
		try {
			// This enables the java.library.path to be modified at runtime
			// From a Sun engineer at http://forums.sun.com/thread.jspa?threadID=707176
			// 
			Field field = ClassLoader.class.getDeclaredField("usr_paths");
			field.setAccessible(true);
			String[] paths = (String[])field.get(null);
			for (int i = 0; i < paths.length; i++) {
				if (s.equals(paths[i])) {
					return;
				}
			}
			String[] tmp = new String[paths.length+1];
			System.arraycopy(paths,0,tmp,0,paths.length);
			tmp[paths.length] = s;
			field.set(null,tmp);
			System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + s);
		} catch (IllegalAccessException e) {
			throw new IOException("Failed to get permissions to set library path");
		} catch (NoSuchFieldException e) {
			throw new IOException("Failed to get field handle to set library path");
		}
	}
    
    public Runner(){
    	fourier = new FourierAnalysis(new DummyAudioInput(), 8192);
    	fourier.start();
    	menu = new MainMenu(this);
    	os=new OutputSettings(this);
    	is=new InputSettings(this);
    	pe=new PeriodicEffectWindow(this);
    	fw=new FFTWindow(this);
    	fourier.addUpdateListener(fw);
    	out = new DummyOut();
    	boxes = new ArrayList<FFTBox>();
    	periodicEffects = new ArrayList<PeriodicEffect>();
    	
    	menuFrame = new JFrame("Let There Be Light");
    	osFrame = new JFrame("Output Settings");
    	isFrame = new JFrame("Input Settings");
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
    	
    }
    
    public void addPeriodicEffect(PeriodicEffect e){
    	periodicEffects.add(e);
    }
    
    public void showMainMenu(boolean state){
    	menuFrame.setVisible(state);
    }
    
    public void setOutput(Output o){
    	out.stop();
    	try {
			if(outThread != null) outThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	o.copy(out);
    	out=o;
    	outThread = new Thread(o, "output");
    	outThread.start();
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
    	fwFrame.pack();
    	fwFrame.setVisible(state);
    }
    
    public void updateFourier(TrueAudioInput in){
    	fourier.setInput(in);
    }
    
    public FourierAnalysis getFourier(){
    	return fourier;
    }
    
    public void addBox(FFTBox b) {
    	boxes.add(b);
    }
    
    public void actionPerformed(ActionEvent e){
//    	if(e.getSource()==menu){
//    		String cmd = e.paramString();
//    		cmd = cmd.substring(cmd.indexOf("cmd"));
//    		cmd = cmd.substring(cmd.indexOf('=')+1);
//    		cmd = cmd.substring(0, cmd.indexOf(','));
//    		if(cmd.equals("start")){
//    			running=true;
//				fourier.pause(true);
//    		}
//    		else{
//    			running=false;
//    			fourier.pause(true);
//    		}
//    	}
    	//for FFTBox in list
    		//give box fourier analysis
    		//have box update lights
    	//run time based affects (could use their own thread)
    	//update FFTGraph
    	//update bar graph
    	
    }
    
    /*public void run(){
		while(true){
			if(running){
				float[] points=LinearLog.logFromLinear(fourier.process(), 100);
				//menu.getBarGraph().update(LinearLog.loglog(points));
				menu.getBarGraph().update(points);
				fw.updateGraph(points);
				//fw.updateGraph(LinearLog.loglog(points));
				for(PeriodicEffect e : periodicEffects){
					e.update();
				}
				if(out!=null){
					out.update();
				}
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}*/

	public Output getOutput() {
		return out;
		
	}
}
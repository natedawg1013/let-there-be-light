package ProofOfConcept;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Runner extends JPanel implements ActionListener{
	private static final long serialVersionUID = 667570696430914921L;
	ColorPanel color;
	String[] sources;
	JComboBox<String> input;
	JButton set;
	FourrierAnalysis analysis;
	
	
	public Runner() throws Exception{
		super(new GridBagLayout());
		color = new ColorPanel();
		color.setPreferredSize(new Dimension(100,100));
		sources = AudioIn.audioList();
		set  = new JButton("Set input");
		input = new JComboBox<String>(sources);
		setPositions();
		analysis = null;
		set.addActionListener(this);
	}
	
	void setPositions(){
		GridBagConstraints c = new GridBagConstraints();
		   c.gridx=0;c.gridy=0;c.gridwidth=1;
		   c.anchor=GridBagConstraints.FIRST_LINE_START;
		this.add(input, c);
			c.gridx=1;
		this.add(set, c);
			c.gridx=0;c.gridy=1;c.gridwidth=2;
		this.add(color, c);
	}
	
	void update(){
		float col = (analysis.process()*10)%1.0f;
		color.SetHSV(col*6, 1.0f, 1.0f);
		System.out.println(col);
	}
	
	public static void main(String[] args) { 
		JFrame frame = new JFrame();
		Runner panel = null;
		try {
			panel = new Runner();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		while(true){
			if(!panel.ready()){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				panel.update();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(analysis!=null)
			analysis.end();
		analysis=new FourrierAnalysis(input.getSelectedIndex(), 2048);
		analysis.begin();
	}
	
	boolean ready(){
		if(analysis==null) return false;
		return analysis.ready();
	}
	
}
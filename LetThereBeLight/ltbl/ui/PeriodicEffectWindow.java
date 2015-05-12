package ltbl.ui;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ltbl.control.Runner;

// PeriodicEffectWindow holds the GUI for PeriodicEffectPanel(s)
// Temporal cohesion. PeriodicEffecWindow shows up only when the user wants to open the effect panel of PeriodicEffectPanel
// PeriodicEffectWindow collaborates with the following classes
// PeriodicEffect - Common coupling. PeriodicEffectWindow only contains the elements of PeriodicEffect
// Runner - Control coupling. Runner controls the flow of classes called from MainMenuv


public class PeriodicEffectWindow extends JTabbedPane implements ActionListener {
	private static final long serialVersionUID = 9053219904660535723L;
	
	AddButton button;
	JPanel dummy;
	Runner runner;
	
	public PeriodicEffectWindow(Runner r){
		super();
		button = new AddButton();
		dummy = new JPanel();
		button.addActionListener(this);
		this.addTab("1", dummy);
		this.setTabComponentAt(0, button);
		runner=r;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button.add){
			this.remove(this.getTabCount()-1);
			this.addTab(String.valueOf(this.getTabCount()+1), new PeriodicEffectPanel(runner));
			this.addTab("", dummy);
			this.setTabComponentAt(this.getTabCount()-1, button);
		}
		
	}
	
	class AddButton extends JPanel{
		private static final long serialVersionUID = -1582164838948355271L;
		
		JButton add;
		public AddButton(){
			super(new GridLayout());
			this.setOpaque(false);
			add = new JButton("+");
			add.setMargin(new Insets(0, 0, 0, 0));
			add.setOpaque(false);
			add.setContentAreaFilled(false);
			add.setBorderPainted(false);
			this.add(add);
		}
		
		public void addActionListener(ActionListener l){
			add.addActionListener(l);
		}
	}

}
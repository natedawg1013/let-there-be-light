package ltbl.ui;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class PeriodicEffectWindow extends JTabbedPane implements ActionListener {
	AddButton button;
	JPanel dummy;
	
	public PeriodicEffectWindow(){
		super();
		button = new AddButton();
		dummy = new JPanel();
		button.addActionListener(this);
		this.addTab("1", dummy);
		this.setTabComponentAt(0, button);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button.add){
			this.remove(this.getTabCount()-1);
			this.addTab(String.valueOf(this.getTabCount()+1), new PeriodicEffectPanel());
			this.addTab("", dummy);
			this.setTabComponentAt(this.getTabCount()-1, button);
		}
		
	}
	
	class AddButton extends JPanel{
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
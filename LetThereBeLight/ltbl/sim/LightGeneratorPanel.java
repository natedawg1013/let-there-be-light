package ltbl.sim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class LightGeneratorPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 8270903873127771060L;
	SimPanel panel;
	JComboBox<String> type;
	JSpinner channel0;
	JSpinner channel1;
	JSpinner channel2;
	JSpinner color;
	JLabel lC;
	JLabel lC0;
	JLabel lC1;
	JLabel lC2;
	JLabel lCol;
	JButton add;
	int count;

	public LightGeneratorPanel(SimPanel panel){
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.panel=panel;
		count=1;
		SpinnerModel channel0Model = new SpinnerNumberModel(0, 0, 255, 1);
		SpinnerModel channel1Model = new SpinnerNumberModel(0, 0, 255, 1);
		SpinnerModel channel2Model = new SpinnerNumberModel(0, 0, 255, 1);
		SpinnerModel colorModel = new SpinnerNumberModel(0, -1, 359, 1);
		type = new JComboBox<String>();
		type.addItem("Colored"); type.addItem("RGB");
		channel0=new JSpinner(channel0Model);
		channel1=new JSpinner(channel1Model);
		channel2=new JSpinner(channel2Model);
		color=new JSpinner(colorModel);
		lC=new JLabel("Channel:");
		lC0=new JLabel("Channel 1:");
		lC1=new JLabel("Channel 2:");
		lC2=new JLabel("Channel 2:");
		lCol=new JLabel("Color angle:");
		channel0.setEditor(new JSpinner.NumberEditor(channel0, "#"));
		channel1.setEditor(new JSpinner.NumberEditor(channel1, "#"));
		channel2.setEditor(new JSpinner.NumberEditor(channel2, "#"));
		color.setEditor(new JSpinner.NumberEditor(color, "#"));
		add = new JButton("Add");
		this.add(type);
		this.add(lC);
		this.add(lC0);
		this.add(channel0);
		this.add(lC1);
		this.add(channel1);
		this.add(lC2);
		this.add(channel2);
		this.add(lCol);
		this.add(color);
		this.add(add);
		type.addActionListener(this);
		add.addActionListener(this);
		
		lC.setVisible(true);
		lC0.setVisible(false);
		channel0.setVisible(true);
		lC1.setVisible(false);
		channel1.setVisible(false);
		lC2.setVisible(false);
		channel2.setVisible(false);
		lCol.setVisible(true);
		color.setVisible(true);
		add.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==type){
			if(type.getSelectedIndex()==0){
				lC.setVisible(true);
				lC0.setVisible(false);
				channel0.setVisible(true);
				lC1.setVisible(false);
				channel1.setVisible(false);
				lC2.setVisible(false);
				channel2.setVisible(false);
				lCol.setVisible(true);
				color.setVisible(true);
				add.setVisible(true);
			}
			else if(type.getSelectedIndex()==1){
				lC.setVisible(false);
				lC0.setVisible(true);
				channel0.setVisible(true);
				lC1.setVisible(true);
				channel1.setVisible(true);
				lC2.setVisible(true);
				channel2.setVisible(true);
				lCol.setVisible(false);
				color.setVisible(false);
				add.setVisible(true);
			}
		}
		else if(ae.getSource()==add){
			if(type.getSelectedIndex()==0){
				int nChannel = ((SpinnerNumberModel) channel0.getModel()).getNumber().intValue();
				int nColor = ((SpinnerNumberModel) color.getModel()).getNumber().intValue();
				panel.addLight(new ColoredLight(nChannel, nColor, count));
			}
			else if(type.getSelectedIndex()==1){
				int nChannel0 = ((SpinnerNumberModel) channel0.getModel()).getNumber().intValue();
				int nChannel1 = ((SpinnerNumberModel) channel1.getModel()).getNumber().intValue();
				int nChannel2 = ((SpinnerNumberModel) channel2.getModel()).getNumber().intValue();
				panel.addLight(new RGBLight(nChannel0, nChannel1, nChannel2, count));
			}
			count++;
		}
	}
}
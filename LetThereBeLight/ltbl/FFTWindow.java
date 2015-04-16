package ltbl;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLayeredPane;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FFTWindow extends JPanel implements ActionListener{
    private Runner runner;
    private JLayeredPane graphPane;
    private FFTGraph graph;
    private FFTMousePanel mousePanel;
    private JButton addBox;

    public FFTWindow( Runner r ) {
        super( new GridBagLayout() );
        runner = r;
        graph = new FFTGraph(runner);
        graphPane = new JLayeredPane();
        mousePanel = new FFTMousePanel( runner, graphPane );
        addBox = new JButton("Add");
        addBox.addActionListener(this);
    }
	
    private void setPositions(){
    	graphPane.add( graph, 0 );
    	graphPane.add( mousePanel, 1 );
    	
    	GridBagConstraints c = new GridBagConstraints();
    	c.gridx = c.gridy = 0;
    	c.weightx = .80;
        this.add( graphPane, c );
        c.weightx = .20;
    	c.gridx = 1;
    	this.add( addBox, c );
    	mousePanel.setSize();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==addBox){
            mousePanel.setMode( FFTMousePanel.EMode.ADD );
        }
    }

}
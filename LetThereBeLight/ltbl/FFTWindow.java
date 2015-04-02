package ltbl;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLayeredPane;


public class FFTWindow extends JPanel{
    private Runner runner;
    private JLayeredPane graphPane;
    private FFTGraph graph;
    private FFTMousePanel mousePanel;
    private FFTBoxSettings boxSettings;
    private JButton addBox;

	public FFTWindow( Runner r ) {
	    super( new GridBagLayout() );
        runner = r;
        graph = new FFTGraph(runner);
        graphPane = new graphPane();
        mousePanel = new FFTMousePanel( runner, graphPane );
        boxSettings = new FFTBoxSettings(runner);
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
    	this.add( boxSettings, c );
    	c.gridy = 1;
    	this.add( addBox, c );
    }
    
    @Override
	public void actionPerformed(ActionEvent ae) {
	    if(ae.getSource()==addBox){
			FFTBox box = new FFTBox();
			FFTBoxOverlay over = new FFTBoxOverlay(box);
			Integer layer = graphPane.getLayer(mousePanel);
			graphPane.setLayer( mousePanel, layer + 1 );
            mousePanel.setMode( mousePanel.Mode.ADD );
		}
	}

}
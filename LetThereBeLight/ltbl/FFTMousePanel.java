package ltbl;

import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.awt.Point;

/* NOTE:
 * Might think about using the scroll wheel 
 * to surface panels behind other panels
 * so we can reach completely hidden ones
 */


public class FFTMousePanel extends JPanel {
    
    public enum Mode {
        ADD, ADD_MOUSEDOWN, 
        EDIT, EDIT_LEFTCLICK, EDIT_RIGHTCLICK;
    }
    
    private Mode inputMode;
    private Runner runner;
    private JLayeredPane boxLayers;
    private Point p1, p2;
    // boxes can go anywhere between but not including
    // 0, and the layer the mouse panel is on
    
    
    public FFTMousePanel( Runner r, JLayeredPane g ) {
        super();
        inputMode = Mode.EDIT;
        runner = r;
        boxLayers = g;
        this.setOpaque(false);
        this.addActionListener(this);
    }
    
    public void setMode ( Mode m ) {
        inputMode = m;
    }
    
    @Override
    public void actionPerformed( ActionEvent ae ) {
	    switch (inputMode) {
	        case ADD:
	            
	            
	            break;
            case ADD_MOUSEDOWN:
                
                
                break;
            case EDIT:
                
                
                break;
            case EDIT_LEFTCLICK:
                
                
                break;
            case EDIT_RIGHTCLICK:
                
                
                break;
	    }
    }
    

}
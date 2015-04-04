package ltbl;

import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.awt.Point;
import javax.swing.event;

/* NOTE:
 * Might think about using the scroll wheel 
 * to surface panels behind other panels
 * so we can reach completely hidden ones
 */


public class FFTMousePanel extends JPanel {
    
    public enum Mode {
        ADD, ADD_MOUSEDOWN, ADD_UNFOCUSED,
        EDIT, EDIT_LEFTCLICK, EDIT_RIGHTCLICK,
        EDIT_UNFOCUSED;
    }
    
    private Mode inputMode;
    private Runner runner;
    private JLayeredPane boxLayers;
    private Point p1, p2;
    private int sizeX, sizeY;
    // boxes can go anywhere between but not including
    // 0, and the layer the mouse panel is on
    
    
    public FFTMousePanel ( Runner r, JLayeredPane g ) {
        super();
        inputMode = Mode.EDIT;
        runner = r;
        boxLayers = g;
        sizeX = this.getWidth();
        sizeY = this.getHeight();
        this.setOpaque(false);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }
    
    public void setMode ( Mode m ) {
        inputMode = m;
    }
    
    public void setSize () {
        sizeX = this.getWidth();
        sizeY = this.getHeight();
    }
    
    @Override
    protected void processMouseEvent ( MouseEvent me ) {
        switch ( me.getID() ) {
            case MouseEvent.MOUSE_CLICKED:
                if ( inputMode == EDIT ) {
                    setFocus( me.getX(), me.getY() );
                }
                break;
            case MouseEvent.MOUSE_DRAGGED:
                
                break;
            case MouseEvent.MOUSE_ENTERED:
                
                break;
            case MouseEvent.MOUSE_EXITED:
                
                break;
            case MouseEvent.MOUSE_PRESSED:
                
                break;
            case MouseEvent.MOUSE_RELEASED:
                
                break;
            default:
                ;
        }

    }

    @Override
    protected void processMouseMotionEvent ( MouseEvent me ) {
        
        
    }
    
    @Override
    protected void processMouseWheelEvent(MouseWheelEvent e) {
        
        
    }
    
    

}
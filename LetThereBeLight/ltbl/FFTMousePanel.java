package ltbl;

import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.awt.Point;
import javax.swing.event;

/* NOTE:
 * Might think about using the scroll wheel 
 * to surface panels behind other panels
 * so we can reach completely hidden ones
 *
 * Single click on box brings focus
 * double click on box opens settings menu for box
 */


public class FFTMousePanel extends JPanel {
    
    public enum EMode {
        ADD, ADD_DRAWBOX,
        EDIT, EDIT_MOVEBOX, EDIT_BOX;

        public abstract class Mode {
            public abstract Mode ( FFTMousePanel mp );
            public abstract void processMouseEvent ( MouseEvent me );
            public abstract void processMouseMotionEvent ( MouseEvent me );
            public abstract void processMouseWheelEvent ( MouseWheelEvent mwe );
        }
        
        public class Add extends Mode {
            
            private FFTMousePanel modePanel;
            
            public void processMouseEvent ( MouseEvent me ) {
                // do we reference MouseEvent for enums in it?
                if ( me.getID() == MOUSE_PRESSED && me.getButton() == BUTTON1 ) {
                    AddDrawBox nextMode = new AddDrawBox(modePanel);
                    nextMode.setInitialCoords( me.getX(), me.getY() );
                    modePanel.setMode(nextMode);
                }
            }
            
            public void processMouseMotionEvent ( MouseEvent me ) {
                // do nothing
            }
            
            public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
                // do nothing
            }
            
        }
        
        public class AddDrawBox extends Mode {
            
            private FFTMousePanel modePanel;
            private int x0, y0;
            
            public void processMouseEvent ( MouseEvent me ) {
                if ( me.getID() ==  ) {
                    
                }
            }
            
            public void processMouseMotionEvent ( MouseEvent me ) {
                
            }
            
            public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
                
            }
            
            public void setInitialCoords ( int x, int y ) {
                x0 = x;
                y0 = y;
            }
            
        }

        public class Edit extends Mode {
            
            private FFTMousePanel modePanel;
            
            public void processMouseEvent ( MouseEvent me ) {
                
            }
            
            public void processMouseMotionEvent ( MouseEvent me ) {
                
            }
            
            public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
                
            }
            
        }

        public class EditMoveBox extends Mode {
            
            private FFTMousePanel modePanel;
            
            public void processMouseEvent ( MouseEvent me ) {
                
            }
            
            public void processMouseMotionEvent ( MouseEvent me ) {
                
            }
            
            public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
                
            }
            
        }

        public class EditBox extends Mode {
            
            
        }
        
        public Mode getMode ( EMode m, FFTMousePanel mp ) {
            switch (m) {
                case ADD:
                    return new Add(mp);
                case ADD_DRAWBOX:
                    return new AddDrawBox(mp);
                case EDIT:
                    return new Edit(mp);
                case EDIT_BOX:
                    return new EditBox(mp);
                case EDIT_MOVEBOX:
                    return new EditMoveBox(mp);
            }
        }
        
    }
    
    
    
    
    private Mode inputMode;
    private Runner runner;
    private JLayeredPane boxLayers;
    private Point p1, p2;
    private List<FFTBoxOverlay> boxOverlays;
    private int sizeX, sizeY;
    // boxes can go anywhere between but not including
    // 0, and the layer the mouse panel is on
    
    
    public FFTMousePanel ( Runner r, JLayeredPane g ) {
        super();
        inputMode = Mode.EDIT;
        runner = r;
        boxLayers = g;
        boxOverlays = new List<FFTBoxOverlay>();
        sizeX = this.getWidth();
        sizeY = this.getHeight();
        this.setOpaque(false);
        this.setVisible(false);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addComponentListener(this);
    }
    
    public void setMode ( EMode m ) {
        inputMode = new m.c(this);
    }
    
    public void setSize () {
        sizeX = this.getWidth();
        sizeY = this.getHeight();
    }
    
    @Override
    protected void processMouseEvent ( MouseEvent me ) {
        inputMode.processMouseEvent(me);
    }

    @Override
    protected void processMouseMotionEvent ( MouseEvent me ) {
        inputMode.processMouseMotionEvent(me);
    }
    
    @Override
    protected void processMouseWheelEvent ( MouseWheelEvent mwe ) {
        inputMode.processMouseWheelEvent(me);
    }
    
    public void componentResized ( ComponentEvent ce ) {
        setSize();
        for ( FFTBoxOverlay box in boxOverlays ) {
            box.setOuter( sizeX, sizeY );
        }
    }

}
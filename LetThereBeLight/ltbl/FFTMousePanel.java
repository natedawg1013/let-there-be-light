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
                // when you click down and hold the mouse, get coords for initial box
                // actually make it, and add it on top of the box pile.
                // then transition to the DrawBox state
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
                if ( me.getID() == MOUSE_RELEASED && me.getButton() = BUTTON1 ) {
                    // If you let go of the mouse, switch to the Edit state
                    // and make the box inside the overlay get loaded with ratios of bounding coords to dimensions
                    // should we bring up a menu that has more settings now or later when you edit it?
                }
            }
            
            public void processMouseMotionEvent ( MouseEvent me ) {
                // If you move the mouse, make the size of the box change with the updated mouse coords
                
            }
            
            public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
                // do nothing
            }
            
            public void setInitialCoords ( int x, int y ) {
                x0 = x;
                y0 = y;
            }
            
            public void setBoxSize ( int x, int y ) {
                int level = modePanel.boxLayers.getLayer(modePanel) - 1;
                FFTBoxOverlay box = (FFTBoxOverlay) modePanel.boxLayers.getComponentsInLayer(level)[0];
                box.setDimensions( Math.min( x0, x ), Math.min( y0, y ), Math.max( x0, x ), Math.max( y0, y ) );
            }
            
        }

        public class Edit extends Mode {
            
            private FFTMousePanel modePanel;
            
            public void processMouseEvent ( MouseEvent me ) {
                // If you click on a box "MOUSE_CLICKED" once (getClickCount()), pull box to top of the JLayeredPane
                // If you click on a box twice, get the boxSettings menu up
                // If you right-click on a box, ask to delete box + other options maybe
                // If you left-click and hold down "MOUSE_PRESSED", switch state into EditMoveBox
                // If you left-click and hold down on an EDGE of a box, switch state into EditResizeBox
            }
            
            public void processMouseMotionEvent ( MouseEvent me ) {
                // Do nothing
            }
            
            public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
                // scroll through the different boxes underneath the cursor
            }
            
        }

        public class EditMoveBox extends Mode {
            
            private FFTMousePanel modePanel;
            
            private int[] initBoxCoords, initMouseCoords;
            
            public void processMouseEvent ( MouseEvent me ) {
                // When you release the mouse, switch state into Edit
            }
            
            public void processMouseMotionEvent ( MouseEvent me ) {
                // When you move the mouse, update the box with the new coords
            }
            
            public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
                // Do nothing
            }
            
        }

        public class EditResizeBox extends Mode {
            
            private FFTMousePanel modePanel;
            
            private int side;
            
            public void processMouseEvent ( MouseEvent me ) {
                // When you release the mouse, switch state into Edit
            }
            
            public void processMouseMotionEvent ( MouseEvent me ) {
                // When you move the mouse, update the box with the new coords
            }
            
            public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
                // Do nothing
            }
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
package ltbl.ui;

import javax.swing.JPanel;
import javax.swing.JLayeredPane;

import java.awt.event.*;

import javax.swing.SwingUtilities;

import java.util.ArrayList;

import ltbl.control.Runner;

/* NOTE:
 * Might think about using the scroll wheel 
 * to surface panels behind other panels
 * so we can reach completely hidden ones
 *
 * Single click on box brings focus
 * double click on box opens settings menu for box
 */


public class FFTMousePanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {

	public enum EState {
		ADD, ADD_DRAW_BOX,
		EDIT, EDIT_RESIZE_BOX, EDIT_MOVE_BOX;
	}

	public abstract class State {
		protected final FFTMousePanel statePanel;
		protected State ( FFTMousePanel mp ) { statePanel = mp; }
		public abstract void processMouseEvent ( MouseEvent me );
		public abstract void processMouseMotionEvent ( MouseEvent me );
		public abstract void processMouseWheelEvent ( MouseWheelEvent mwe );
	}

	public class AddState extends State {

		public AddState ( FFTMousePanel mp ) {
			super(mp);
		}

		public void processMouseEvent ( MouseEvent me ) {
			// when you click down and hold the mouse, get coords for initial box
			// actually make it, and add it on top of the box pile.
			// then transition to the DrawBox state
			if ( me.getID() == MouseEvent.MOUSE_PRESSED &&
					SwingUtilities.isLeftMouseButton(me) ) {
				// synchronize?
				FFTBoxOverlay newBox = new FFTBoxOverlay();
				this.statePanel.setState(EState.ADD_DRAW_BOX);
				AddDrawBoxState nextState = (AddDrawBoxState) this.statePanel.inputState;
				nextState.setInitialCoords( me.getX(), me.getY() );
				newBox.setDimensions( me.getX(), me.getY(), me.getX(), me.getY() );
				JLayeredPane layers = this.statePanel.boxLayers;
				int level = layers.getLayer(statePanel);
				layers.setLayer( statePanel, level+1 );
				layers.add( newBox, level );
				nextState.setBox(newBox);
			}
		}

		public void processMouseMotionEvent ( MouseEvent me ) {
			// do nothing
		}

		public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
			// do nothing
		}

	}


	public class AddDrawBoxState extends State {

		private FFTBoxOverlay boxAdded;
		private int x0, y0;

		public AddDrawBoxState ( FFTMousePanel mp ) {
			super(mp);
		}


		public void processMouseEvent ( MouseEvent me ) {
			if ( me.getID() == MouseEvent.MOUSE_RELEASED &&
					SwingUtilities.isLeftMouseButton(me) ) {
				// If you let go of the mouse, switch to the Edit state
				// and make the box inside the overlay get loaded with ratios of bounding coords to dimensions
				// should we bring up a menu that has more settings now or later when you edit it?
				statePanel.setState(EState.EDIT);
			}
		}

		public void processMouseMotionEvent ( MouseEvent me ) {
			// If you move the mouse, make the size of the box change with the updated mouse coords
			if ( me.getID() == MouseEvent.MOUSE_DRAGGED ) {
				int x1 = me.getX();
				int y1 = me.getY();
				if ( x1 < 0 ) x1 = 0;
				else if ( x1 > statePanel.getWidth() ) x1 = statePanel.getWidth();
				if ( y1 < 0 ) y1 = 0;
				else if ( y1 > statePanel.getHeight() ) y1 = statePanel.getHeight();
				boxAdded.setDimensions( Math.min(x0, x1), Math.min(y0, y1), Math.max(x0, x1), Math.max(y0, y1) );
			}
		}

		public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
			// do nothing
		}

		public void setInitialCoords ( int x, int y ) {
			x0 = x;
			y0 = y;
		}

		public void setBox( FFTBoxOverlay newBox ) {
			boxAdded = newBox;
		}

		public void setBoxSize ( int x, int y ) {
			boxAdded.setDimensions( Math.min( x0, x ), Math.min( y0, y ), Math.max( x0, x ), Math.max( y0, y ) );
		}

	}



	public class EditState extends State {

		public EditState ( FFTMousePanel mp ) {
			super(mp);
		}

		public void processMouseEvent ( MouseEvent me ) {
			// If you click on a box "MOUSE_CLICKED" once (getClickCount()), pull box to top of the JLayeredPane
			// If you click on a box twice, get the boxSettings menu up
			// If you right-click on a box, ask to delete box + other options maybe
			// If you left-click and hold down "MOUSE_PRESSED", switch state into EditMoveBoxState
			// If you left-click and hold down on an EDGE of a box, switch state into EditResizeBoxState *extra*
			if ( me.getID() == MouseEvent.MOUSE_CLICKED ) {
				FFTBoxOverlay boxFocused = getFocus( me.getX(), me.getY() );
				if ( boxFocused != null ) {
					if ( SwingUtilities.isLeftMouseButton(me) &&
							me.getClickCount() > 1 ) {
						// Haven't implemented boxSettings yet, but stuff should go here
					}
					else if ( SwingUtilities.isRightMouseButton(me) ) {
						// Need to implement right click menu
					}
				}
			}

			if ( me.getID() == MouseEvent.MOUSE_PRESSED &&
					SwingUtilities.isLeftMouseButton(me) ) {
				FFTBoxOverlay boxFocused = getFocus( me.getX(), me.getY() );
				if ( boxFocused != null ) {
					// check for edges
					statePanel.setState(EState.EDIT_MOVE_BOX);
					EditMoveBoxState nextState = (EditMoveBoxState) statePanel.inputState;
					nextState.setBox(boxFocused);
					nextState.setInitialCoords( me.getX(), me.getY() );
				}
			}
		}

		public void processMouseMotionEvent ( MouseEvent me ) {
			// Do nothing
		}

		public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
			// scroll through the different boxes underneath the cursor
			// need to implement cycleFocus() first
		}

		// grab the highest box on the stack and move it to the top of the stack
		private FFTBoxOverlay getFocus ( int x, int y ) {
			JLayeredPane stack = statePanel.boxLayers;
			int stackHeight = stack.getLayer(statePanel);
			for ( int i = stackHeight-1; i > 0; --i ) {
				if ( stack.getComponentCountInLayer(i) == 1 ) { 
					FFTBoxOverlay box = (FFTBoxOverlay) stack.getComponentsInLayer(i)[0];
					if ( box.contains(x,y) ) {
						// switch focus to this box
						FFTBoxOverlay oldFocus = (FFTBoxOverlay) stack.getComponentsInLayer(stackHeight-1)[0];
						stack.setLayer( oldFocus, i );
						stack.setLayer( box, stackHeight-1 );
						return box;
					}
				}
			}
			return null;
		}

	}



	public class EditMoveBoxState extends State {

		private FFTBoxOverlay moveBox;
		private int[] initBoxCoords, initMouseCoords;

		public EditMoveBoxState ( FFTMousePanel mp ) {
			super(mp);
		}

		public void setBox ( FFTBoxOverlay b ) {
			moveBox = b;
			initBoxCoords = b.getDimensions();
		}

		public void setInitialCoords ( int x, int y ) {
			initMouseCoords = new int[2];
			initMouseCoords[0] = x;
			initMouseCoords[1] = y;
		}

		public void updateCoords ( int x, int y ) {
			int diffx = x-initMouseCoords[0];
			int diffy = y-initMouseCoords[1];

			int[] newCoords = new int[4];
			newCoords[0] = initBoxCoords[0]+diffx;
			newCoords[1] = initBoxCoords[1]+diffy;
			newCoords[2] = initBoxCoords[2]+diffx;
			newCoords[3] = initBoxCoords[3]+diffy;

			if ( newCoords[0] < 0 ) {
				newCoords[2] -= newCoords[0];
				newCoords[0] = 0;
			}
			else if ( newCoords[2] > statePanel.getWidth() ) {
				newCoords[0] += statePanel.getWidth() - newCoords[2];
				newCoords[2] = statePanel.getWidth();
			}

			if ( newCoords[1] < 0 ) {
				newCoords[3] -= newCoords[1];
				newCoords[1] = 0;
			}
			else if ( newCoords[3] > statePanel.getHeight() ) {
				newCoords[1] += statePanel.getHeight() - newCoords[3];
				newCoords[3] = statePanel.getHeight();
			}

			moveBox.setDimensions( newCoords[0], newCoords[1], newCoords[2], newCoords[3] );
		}

		public void processMouseEvent ( MouseEvent me ) {
			// When you release the mouse, switch state into Edit
			if ( me.getID() == MouseEvent.MOUSE_RELEASED &&
			SwingUtilities.isLeftMouseButton(me) ) {
				statePanel.setState(EState.EDIT);
			}
		}

		public void processMouseMotionEvent ( MouseEvent me ) {
			// When you move the mouse, update the box with the new coords
			if ( me.getID() == MouseEvent.MOUSE_DRAGGED ) {
				updateCoords( me.getX(), me.getY() );
			}
		}

		public void processMouseWheelEvent ( MouseWheelEvent mwe ) {
			// Do nothing
		}

	}


	public class EditResizeBoxState extends State {

		private int side;

		public EditResizeBoxState ( FFTMousePanel mp ) {
			super(mp);
		}

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







	private State inputState;
	private Runner runner;
	private JLayeredPane boxLayers;
	private ArrayList<FFTBoxOverlay> boxOverlays;
	private int sizeX, sizeY;
	// boxes can go anywhere between but not including
	// 0, and the layer the mouse panel is on


	public FFTMousePanel ( Runner r, JLayeredPane g ) {
		super();
		runner = r;
		boxLayers = g;
		boxOverlays = new ArrayList<FFTBoxOverlay>();
		sizeX = this.getWidth();
		sizeY = this.getHeight();
		this.setOpaque(false);
		this.setVisible(false);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addComponentListener(this);
	}

	public void setState ( EState m ) {
		inputState = getState( m, this );
	}

	public void setSize () {
		sizeX = this.getWidth();
		sizeY = this.getHeight();
	}

	public State getState ( EState m, FFTMousePanel mp ) {
		switch (m) {
		case ADD:
			return new AddState(mp);
		case ADD_DRAW_BOX:
			return new AddDrawBoxState(mp);
		case EDIT:
			return new EditState(mp);
		case EDIT_RESIZE_BOX:
			return new EditResizeBoxState(mp);
		case EDIT_MOVE_BOX:
			return new EditMoveBoxState(mp);
		default:
			return null;
		}
	}

	public void mouseClicked ( MouseEvent me ) {
		inputState.processMouseEvent(me);
	}

	public void mousePressed ( MouseEvent me ) {
		inputState.processMouseEvent(me);
	}

	public void mouseReleased ( MouseEvent me ) {
		inputState.processMouseEvent(me);
	}

	public void mouseEntered ( MouseEvent me ) {
		inputState.processMouseEvent(me);
	}

	public void mouseExited ( MouseEvent me ) {
		inputState.processMouseEvent(me);
	}

	public void mouseMoved ( MouseEvent me ) {
		inputState.processMouseMotionEvent(me);
	}

	public void mouseDragged ( MouseEvent me ) {
		inputState.processMouseMotionEvent(me);
	}

	public void mouseWheelMoved ( MouseWheelEvent mwe ) {
		inputState.processMouseWheelEvent(mwe);
	}

	public void componentResized ( ComponentEvent ce ) {
		setSize();
		for ( FFTBoxOverlay box: boxOverlays ) {
			box.setOuter( sizeX, sizeY );
		}
	}

	public void componentMoved ( ComponentEvent ce ) {
		// do nothing
	}

	public void componentShown ( ComponentEvent ce ) {
		setState(EState.EDIT);
		setSize();
		for ( FFTBoxOverlay box: boxOverlays ) {
			box.setOuter( sizeX, sizeY );
		}
	}

	public void componentHidden ( ComponentEvent ce ) {
		// do nothing
	}


}
package gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import event.ProjectControlsEvent;
import event.SlateControlsListener;
import event.SlateEventsManager;
import pointer.PointerControler;
import pointer.PointerSize;
import pointer.PointerState;
import shape.Line;
import shape.Oval;
import shape.Point;
import shape.Rectangle;
import shape.Shape;
import shape.Stamp;
import utils.History;

/**
 * This is the board where we can draw
 */
public class DrawingBoard extends JPanel implements SlateComponent {

	private static final long serialVersionUID = 1L;
	
	private static final int CURSOR_IMG_HEIGHT = 30;
	
	private Vector<SlateControlsListener> listeners = new Vector<SlateControlsListener>();
	private SlateEventsManager manager = new SlateEventsManager(this, listeners);
	private PopupMenu menu = new PopupMenu(manager);
	
	// Contains parameter of the pointer (state, size, color)
	private PointerControler pointer = new PointerControler(PointerState.BRUSH, PointerSize.LIGHT, Color.BLACK);
	
	// List of points drawn
	private List<Point> points = new ArrayList<Point>();
	
	// Counter of actions
	private int actionCount = 0;
	
	// History that store the list of point at each action count
	private History history = new History();
	

	public DrawingBoard(SlateControlsListener listener) {
		
		listeners.add(listener);
		
		// Add in history the initial state when the board is clean
		storePoints(points); 
		
		addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e){		
				if (e.getButton() == MouseEvent.BUTTON1) { // BUTTON1 : left, BUTTON2 : center, BUTTON3 : right
					if (pointer.getState().equals(PointerState.PIPETTE)) {
						pipetteActionPressed(e);
					}
					else {
						defaultActionPressed(e);
						updateCursorImage();
					}
				}
			}
			
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) { // Left key released
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		// Actions related to the movements of the mouse
		addMouseMotionListener(new MouseMotionListener(){
			
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) { 
					if (pointer.isShape()) {
						int n = points.size() - 1;
						Stamp stamp = (Stamp) points.get(n);
						Shape shape = pointerStateToShape(pointer.getState());
						int ratio = pointer.getSize().getRatio();
						points.set(n, new Stamp(stamp.getX(), stamp.getY(), e.getX(), e.getY(), ratio, shape, pointer.getColor()));
					}
					else {
						addPoint(e.getX(), e.getY());
					}
					storePoints(points);
					repaint();
				}
			}
					
			// Modification of the image cursor
			public void mouseMoved(MouseEvent e) {
				updateCursorImage();
			}
		});		
	}
	
	/**
	 * Draw the points of the list
	 */
	@Override
	public void paintComponent(Graphics g) {	
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		for(Point p : points){
			g.setColor(p.getColor());
			p.draw(g);
		}
		g.dispose();
	}
	
	/**
	 * Default action when pressed mouse event
	 * @param e a mouseEvent
	 */
	private void defaultActionPressed(MouseEvent e){
		actionCount++;
		history.update(actionCount); // Remove the keys in history whose the value is lower than actionCount in case of some back event
		addPoint(e.getX(), e.getY());
		storePoints(points);
		for (SlateControlsListener l : listeners) {
			((Slate) l).updateBackNext(actionCount);
			ProjectControlsEvent ev = new ProjectControlsEvent(this);
			ev.setProjectUpToDate(false);
			((Slate) l).setProjectTitle(ev);
		}
		repaint();
	}
	
	/**
	 * Action for the pipette when pressed mouse event
	 * @param e a mouseEvent
	 */
	private void pipetteActionPressed(MouseEvent e){
		try {
			Robot robot = new Robot();
			java.awt.Point p = e.getLocationOnScreen();
			pointer.setColor(robot.getPixelColor(p.x + 5, p.y + 2 * CURSOR_IMG_HEIGHT));
		} catch (AWTException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * The image cursor change according to the material used
	 */
	private void updateCursorImage() {
		String filename = "../res/material/" + pointer.getState().getId().toLowerCase() + ".gif";
		File file = new File(filename);
			
		// If it is a painting material then the cursor will take the shape of this material, else if it is 
		// a stamp then the cursor will be a cross
		if (file.exists()) {
			Toolkit kit = Toolkit.getDefaultToolkit();
			Image img = kit.getImage(filename).getScaledInstance(70, 70, 0);
			Cursor cursor = kit.createCustomCursor(img, new java.awt.Point(), pointer.getState().getId().toLowerCase());
			setCursor(cursor);
		}
		else {
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
	}
	
	/**
	 * Add a points in the list of points
	 * @param x X-coordinate of the point
	 * @param y Y-coordinate of the point
	 */
	private void addPoint(int x, int y) {
		
		int size = pointer.getSize().getRatio() * pointer.getState().getSize();
		Color color = pointer.getColor();

		// In accordance with painting material, the appearance of render must be different. For example, we
		// prefer a sequence of rectangle for a highlighter
		switch(pointer.getState()){
			case BRUSH : 		points.add(new Oval(x, y + 2 * CURSOR_IMG_HEIGHT, size, color)); break;
			case PENCIL : 		points.add(new Line(x, y + 2 * CURSOR_IMG_HEIGHT, size, 60, color)); break;
			case FOUNTAIN_PEN : points.add(new Oval(x, y + 2 * CURSOR_IMG_HEIGHT, size, color)); break;
			case FELT_TIP : 	points.add(new Oval(x, y + 2 * CURSOR_IMG_HEIGHT, size, color)); break;
			case HIGHLIGHTER : 	points.add(new Rectangle(x, y + 2 * CURSOR_IMG_HEIGHT, size, size/2, color)); break;
			case BIG_BRUSH : 	points.add(new Oval(x, y + 2 * CURSOR_IMG_HEIGHT, size, color)); break;
			case ERASER : 		points.add(new Oval(x, y + 2 * CURSOR_IMG_HEIGHT, size, Color.WHITE)); break;
			// Stamp
			case OVAL : 		points.add(new Stamp(x, y, x, y, size, Shape.OVAL, color)); break;
			case RECTANGLE : 	points.add(new Stamp(x, y, x, y, size, Shape.RECTANGLE, color)); break;
			case TRIANGLE : 	points.add(new Stamp(x, y, x, y, size, Shape.TRIANGLE, color)); break;
			case DIAMOND : 		points.add(new Stamp(x, y, x, y, size, Shape.DIAMOND, color)); break;
			case ARROW_RIGHT : 	points.add(new Stamp(x, y, x, y, size, Shape.ARROW_RIGHT, color)); break;
			case ARROW_UP : 	points.add(new Stamp(x, y, x, y, size, Shape.ARROW_UP, color)); break;
			case STAR : 		points.add(new Stamp(x, y, x, y, size, Shape.STAR, color)); break;
			case HEART : 		points.add(new Stamp(x, y, x, y, size, Shape.HEART, color)); break;
			default : break;
		}
	}
	
	/**
	 * Clear all the points
	 */
	public void clearPoints() {
		points.clear();
		repaint();
	}

	/**
	 * Store the current list of points in history
	 * @param points a list of points
	 */
	public void storePoints(List<Point> points) {
		List<Point> list = new ArrayList<Point>();
		points.forEach(point -> list.add(point));
		history.put(actionCount, new Date(), list);
	}
	
	/**
	 * Convert a shape state pointer to a shape
	 * @param state to convert
	 * @return a shape
	 */
	private Shape pointerStateToShape(PointerState state) {
		switch(state) {
			case OVAL : 		return Shape.OVAL;
			case RECTANGLE : 	return Shape.RECTANGLE;
			case TRIANGLE : 	return Shape.TRIANGLE;
			case DIAMOND :		return Shape.DIAMOND;
			case ARROW_RIGHT : 	return Shape.ARROW_RIGHT;
			case ARROW_UP : 	return Shape.ARROW_UP;
			case STAR : 		return Shape.STAR;
			case HEART : 		return Shape.HEART;
			default: 			return null;
		}
	}
	
	
	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public int getActionCount() {
		return actionCount;
	}

	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	public PointerState getPointerState() {
		return pointer.getState();
	}
	
	public PointerSize getPointerSize() {
		return pointer.getSize();
	}
	
	public Color getPointerColor() {
		return pointer.getColor();
	}

	public void setPointerState(PointerState state) {
		pointer.setState(state);
	}
	
	public void setPointerSize(PointerSize size) {
		pointer.setSize(size);
	}
	
	public void setPointerColor(Color color) {
		pointer.setColor(color);
	}
	
}

package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * Used to draw different shapes
 */
public class Stamp extends Point {

	private Shape shape;
	private int x1;
	private int y1;
	private int stroke;
	
	public Stamp(int x0, int y0, int x1, int y1, int stroke, String shape, Color color){
		super(x0, y0, color);
		this.x1 = x1;
		this.y1 = y1;
		this.stroke = stroke;
		switch(shape) {
			case "OVAL" : 			this.shape = Shape.OVAL; break;
			case "RECTANGLE" : 		this.shape = Shape.RECTANGLE; break;
			case "TRIANGLE" : 		this.shape = Shape.TRIANGLE; break;
			case "DIAMOND" : 		this.shape = Shape.DIAMOND; break;
			case "ARROW_RIGHT" : 	this.shape = Shape.ARROW_RIGHT; break;
			case "ARROW_UP" : 		this.shape = Shape.ARROW_UP; break;
			case "STAR" : 			this.shape = Shape.STAR; break;
			case "HEART" : 			this.shape = Shape.HEART; break;
			default : break;
		}
	}
	
	public Stamp(int x0, int y0, int x1, int y1, int stroke, Shape shape, Color color){
		super(x0, y0, color);
		this.x1 = x1;
		this.y1 = y1;
		this.stroke = stroke;
		this.shape = shape;
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3 * stroke));
		draw(g2);
	}
	
	private void draw(Graphics2D g2){
		
		if (shape.equals(Shape.OVAL)) {
			int width = x1 - x;
			int height = y1 - y;
			// Rotation when their dimensions are negatives
			if (width > 0 && height > 0) g2.drawOval(x, y, width, height);
			if (width < 0 && height > 0) g2.drawOval(x1, y1 - height, -width, height);
			if (width > 0 && height < 0) g2.drawOval(x, y1, width, - height);
			if (width < 0 && height < 0) g2.drawOval(x1, y1, - width, -height);
		}
			
		else if (shape.equals(Shape.RECTANGLE)) {
			g2.drawLine(x, y, x1, y);
			g2.drawLine(x1, y, x1, y1);
			g2.drawLine(x, y, x, y1);
			g2.drawLine(x, y1, x1, y1);
		}
		
		else if (shape.equals(Shape.TRIANGLE)) {
			g2.drawLine(x, y1, (x+x1)/2, y);
			g2.drawLine((x+x1)/2, y, x1, y1);
			g2.drawLine(x, y1, x1, y1);
		}
			
		else if (shape.equals(Shape.DIAMOND)) {
			g2.drawLine((x+x1)/2, y, x1, (y+y1)/2);
			g2.drawLine(x1, (y+y1)/2, (x+x1)/2, y1);
			g2.drawLine((x+x1)/2, y1, x, (y+y1)/2);
			g2.drawLine(x, (y+y1)/2, (x+x1)/2, y);
		}
			
		else if (shape.equals(Shape.ARROW_RIGHT)) {
			int[] xPoints1 = {x, (x+2*x1)/3, (x+2*x1)/3, x1, (x+2*x1)/3, (x+2*x1)/3, x};
			int[] yPoints1 = {(2*y+y1)/3, (2*y+y1)/3, y, (y+y1)/2, y1, (y+2*y1)/3, (y+2*y1)/3};
			g2.drawPolygon(new Polygon(xPoints1, yPoints1, xPoints1.length));
		}
			
		else if (shape.equals(Shape.ARROW_UP)) {
			int[] xPoints2 = {(2*x+x1)/3, (2*x+x1)/3, x, (x+x1)/2, x1, (x+2*x1)/3, (x+2*x1)/3};
			int[] yPoints2 = {y, (y+2*y1)/3, (y+2*y1)/3, y1, (y+2*y1)/3, (y+2*y1)/3, y};
			g2.drawPolygon(new Polygon(xPoints2, yPoints2, xPoints2.length));
		}

		else if (shape.equals(Shape.STAR)) {
			int[] xPoints3 = {(15*x+9*x1)/24, (x+x1)/2, (9*x+15*x1)/24, x1, (7*x+17*x1)/24, (x+5*x1)/6, (x+x1)/2, (5*x+x1)/6, (17*x+7*x1)/24, x};
			int[] yPoints3 = {(15*y+9*y1)/24, y, (15*y+9*y1)/24, (15*y+9*y1)/24, (5*y+7*y1)/12, y1, (y+3*y1)/4, y1, (5*y+7*y1)/12, (15*y+9*y1)/24};
			g2.drawPolygon(new Polygon(xPoints3, yPoints3, xPoints3.length));
		}
			
		else if (shape.equals(Shape.HEART)) {
			if (x1 > x && y1 > y) {
				g2.drawArc(x, y, (x1-x)/2, (y1-y)/2, 0, 180);
				g2.drawArc((x+x1)/2, y, (x1-x)/2, (y1-y)/2, 0, 180);
				g2.drawArc((3*x-x1)/2, (3*y-y1)/2, 3*(x1-x)/2, 3*(y1-y)/2, 0, -71);
				g2.drawArc(x, (3*y-y1)/2, 3*(x1-x)/2, 3*(y1-y)/2, -180, 71);
			}
					
			else if (x1 < x && y1 > y) {
				g2.drawArc(x1, y, (x-x1)/2, (y1-y)/2, 0, 180);
				g2.drawArc((x+x1)/2, y, (x-x1)/2, (y1-y)/2, 0, 180);
				g2.drawArc(x1, (3*y-y1)/2, 3*(x-x1)/2, 3*(y1-y)/2, -180, 71);
				g2.drawArc((3*x1-x)/2, (3*y-y1)/2, 3*(x-x1)/2, 3*(y1-y)/2, -71, 71);
			}
					
			else if (x1 > x && y1 < y) {
				g2.drawArc(x, (y+y1)/2, (x1-x)/2, (y-y1)/2, 0, -180);
				g2.drawArc((x+x1)/2, (y+y1)/2, (x1-x)/2, (y-y1)/2, 0, -180);
				g2.drawArc((3*x-x1)/2, y1, 3*(x1-x)/2, 3*(y-y1)/2, 0, 71);
				g2.drawArc(x, y1, 3*(x1-x)/2, 3*(y-y1)/2, 110, 71);
			}
					
			else if (x1 < x && y1 < y) {
				g2.drawArc(x1, (y+y1)/2, (x-x1)/2, (y-y1)/2, 0, -180);
				g2.drawArc((x+x1)/2, (y+y1)/2, (x-x1)/2, (y-y1)/2, 0, -180);
				g2.drawArc((3*x1-x)/2, y1, 3*(x-x1)/2, 3*(y-y1)/2, 0, 71);
				g2.drawArc(x1, y1, 3*(x-x1)/2, 3*(y-y1)/2, 110, 71);
			}
		}
	}
	
	@Override
	public String toString(){
		return "STAMP" + ";" 
				+ super.toString() + ";"
				+ x1 + ";"
				+ y1 + ";"
				+ stroke + ";"
				+ shape.getId();	
	}
}

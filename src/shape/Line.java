package shape;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Point {
	
	// Length of the line
	private int length;
	// Angle of the line in degrees
	private int degree;

	/**
	 * @param x X-coordinate of the start point
	 * @param y Y-coordinate of the start point
	 * @param length
	 * @param degree of angle
	 * @param color
	 */
	public Line(int x, int y, int length, int degree, Color color) {
		super(x, y, color);
		this.length = length;
		this.degree = degree;
	}
	
	@Override
	public void draw(Graphics g) {
		double angle = Math.toRadians(degree);
		int x1 = (int) (x + length * Math.cos(angle));
		int y1 = (int) (y - length * Math.sin(angle));
		g.drawLine(x, y, x1, y1);
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	@Override
	public String toString(){
		return "LINE" + ";" 
				+ super.toString() + ";"
				+ length + ";" 
				+ degree;
	}
}

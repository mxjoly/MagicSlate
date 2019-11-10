package shape;

import java.awt.Color;
import java.awt.Graphics;

public class Point {
	
	protected int x;
	protected int y;
	protected Color color;
	
	public Point(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public void draw(Graphics g){
		g.fillOval(x, y, 1, 1);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public String toString(){
		float red = (float) color.getRed() / 255f;
		float green = (float) color.getGreen() / 255f;
		float blue = (float) color.getBlue() / 255f;
		float alpha = (float) color.getAlpha() / 255f;
		return x + ";" + y + ";" + red + ";" + green + ";" + blue + ";" + alpha;
	}
}

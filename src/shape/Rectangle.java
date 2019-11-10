package shape;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Point {
	
	private int width;
	private int height;

	public Rectangle(int x, int y, int width, int height, Color color) {
		super(x, y, color);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(Graphics g){
		g.fillRoundRect(x, y, width, height, 5, 5);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public String toString(){
		return "RECTANGLE" + ";" 
				+ super.toString() + ";"
				+ width + ";" 
				+ height;	
	}
}


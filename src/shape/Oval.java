package shape;

import java.awt.Color;
import java.awt.Graphics;

public class Oval extends Point {
	
	private int diameter;

	public Oval(int x, int y, int diameter, Color color) {
		super(x, y, color);
		this.diameter = diameter;
	}
	
	@Override
	public void draw(Graphics g){
		g.fillOval(x, y, diameter, diameter);
	}
	
	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}
	
	@Override
	public String toString(){
		return "OVAL" + ";" 
				+ super.toString() + ";"
				+ diameter;	
	}
}

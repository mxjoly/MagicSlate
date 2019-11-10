package component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

/**
 * Icon which is a simple colored rectangle 
 */
public class ColoredIcon implements javax.swing.Icon {
	
	private Color color;
	private int width;
	private int height;
	
	public ColoredIcon(Color color, int width, int height) {
		this.color = color;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(color);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);
		g.dispose();
	}
	
	public Color getColor() {
		return color;
	}

	@Override
	public int getIconHeight() {
		return height;
	}

	@Override
	public int getIconWidth() {
		return width;
	}
}

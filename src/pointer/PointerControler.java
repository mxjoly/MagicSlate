package pointer;

import java.awt.Color;

public class PointerControler {
	
	private PointerState state;
	private PointerSize size;
	private Color color;
	
	public PointerControler() {
		this.state = null;
		this.size = null;
		this.color = null;
	}
	
	public PointerControler(PointerState state, PointerSize size, Color color) {
		this.state = state;
		this.size = size;
		this.color = color;
	}
	
	public boolean isShape() {
		return state.equals(PointerState.OVAL) || state.equals(PointerState.RECTANGLE) || state.equals(PointerState.TRIANGLE)
				|| state.equals(PointerState.DIAMOND) || state.equals(PointerState.ARROW_RIGHT) || state.equals(PointerState.ARROW_UP)
				|| state.equals(PointerState.STAR) || state.equals(PointerState.HEART);
	}
	
	public boolean isMaterial() {
		return state.equals(PointerState.BRUSH) || state.equals(PointerState.PENCIL) || state.equals(PointerState.FOUNTAIN_PEN) ||
				state.equals(PointerState.FELT_TIP) || state.equals(PointerState.HIGHLIGHTER) || state.equals(PointerState.BIG_BRUSH) ||
				state.equals(PointerState.ERASER) || state.equals(PointerState.PIPETTE);
	}

	public PointerState getState() {
		return state;
	}

	public void setState(PointerState state) {
		this.state = state;
	}

	public PointerSize getSize() {
		return size;
	}

	public void setSize(PointerSize size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}

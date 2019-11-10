package event;

import java.awt.Color;
import java.awt.Event;

import pointer.PointerControler;
import pointer.PointerSize;
import pointer.PointerState;

public class EditionControlsEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	private PointerControler pointer = new PointerControler();

	public EditionControlsEvent(Object source) {
		super(source, 0, null);
	}
	
	/**
	 * Constructs a event due to the state changed.
	 * @param source source of the event
	 * @param state new pointer state
	 * @return new event object
	 */
	public static EditionControlsEvent getPointerState(Object source, PointerState state) {
		EditionControlsEvent event = new EditionControlsEvent(source);
		event.getPointer().setState(state);
		return event;
	}
	
	/**
	 * Constructs a event due to the size changed.
	 * @param source source of the event
	 * @param size new size of pointer
	 * @return new event object
	 */
	public static EditionControlsEvent getPointerSize(Object source, PointerSize size) {
		EditionControlsEvent event = new EditionControlsEvent(source);
		event.getPointer().setSize(size);
		return event;
	}
	
	/**
	 * Constructs a event due to the color changed.
	 * @param source source of the event
	 * @param color new color
	 * @return new event object
	 */
	public static EditionControlsEvent getPointerColor(Object source, Color color) {
		EditionControlsEvent event = new EditionControlsEvent(source);
		event.getPointer().setColor(color);
		return event;
	}
	
	
	public PointerControler getPointer() {
		return pointer;
	}

	public void setPointer(PointerControler pointer) {
		this.pointer = pointer;
	}

}

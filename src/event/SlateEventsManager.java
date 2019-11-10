package event;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import gui.SlateComponent;
import pointer.PointerSize;
import pointer.PointerState;

/**
 * Class that contains all the slate events to avoid redundancy
 * in the slate components
 */
public class SlateEventsManager {
	
	private SlateComponent component;
	private Vector<SlateControlsListener> listeners;
	
	public SlateEventsManager(SlateComponent component, Vector<SlateControlsListener> listeners) {
		this.component = component;
		this.listeners = listeners;
	}

	public void setPointerState(PointerState state) {
		EditionControlsEvent event = EditionControlsEvent.getPointerState(component, state);
		for (SlateControlsListener l : listeners) {
			l.setPointerState(event);
		}
	}

	public void setPointerSize(PointerSize size) {
		EditionControlsEvent event = EditionControlsEvent.getPointerSize(component, size);
		for (SlateControlsListener l : listeners) {
			l.setPointerSize(event);
		}
	}
	
	public void setPointerColor(Color color) {
		EditionControlsEvent event = EditionControlsEvent.getPointerColor(component, color);
		for (SlateControlsListener l : listeners) {
			l.setPointerColor(event);
		}
	}
	
	public void setProjectTitle(Boolean upToDate) {
		ProjectControlsEvent event = new ProjectControlsEvent(component);
		for (SlateControlsListener l : listeners) {
			l.setProjectTitle(event);
		}
	}
	
	// ============================================================================ //
	
	public NewActionListener getNewActionListener() {
		return new NewActionListener();
	}
	
	public OpenActionListener getOpenActionListener() {
		return new OpenActionListener();
	}
	
	public SaveActionListener getSaveActionListener() {
		return new SaveActionListener();
	}
	
	public SaveAsActionListener getSaveAsActionListener() {
		return new SaveAsActionListener();
	}
	
	public ExitActionListener getExitActionListener() {
		return new ExitActionListener();
	}
	
	public PrintActionListener getPrintActionListener() {
		return new PrintActionListener();
	}
	
	public PropertiesActionListener getPropertiesActionListener() {
		return new PropertiesActionListener();
	}
	
	public BackActionListener getBackActionListener() {
		return new BackActionListener();
	}
	
	public NextActionListener getNextActionListener() {
		return new NextActionListener();
	}
	
	public RefreshActionListener getRefreshActionListener() {
		return new RefreshActionListener();
	}
	
	public ColorSelectionActionListener getColorSelectionActionListener() {
		return new ColorSelectionActionListener();
	}
	
	// ============================================================================ //
	
	public class NewActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ProjectControlsEvent event = new ProjectControlsEvent(component);
			for (SlateControlsListener l : listeners) {
				l.newProject(event);
			}
		}	
	}
	
	public class OpenActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ProjectControlsEvent event = new ProjectControlsEvent(component);
			for (SlateControlsListener l : listeners) {
				l.openProject(event);
			}
		}	
	}
	
	public class SaveActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ProjectControlsEvent event = new ProjectControlsEvent(component);
			for (SlateControlsListener l : listeners) {
				l.saveProject(event);
			}
		}	
	}
	
	public class SaveAsActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ProjectControlsEvent event = new ProjectControlsEvent(component);
			for (SlateControlsListener l : listeners) {
				l.saveAsProject(event);
			}
		}	
	}
	
	public class ExitActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ProjectControlsEvent event = new ProjectControlsEvent(component);
			for (SlateControlsListener l : listeners) {
				l.exitProject(event);
			}
		}	
	}
	
	public class PrintActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ProjectControlsEvent event = new ProjectControlsEvent(component);
			for (SlateControlsListener l : listeners) {
				l.printProject(event);
			}
		}	
	}
	
	public class PropertiesActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ProjectControlsEvent event = new ProjectControlsEvent(component);
			for (SlateControlsListener l : listeners) {
				l.propertiesProject(event);
			}
		}	
	}
	
	public class BackActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EditionControlsEvent event = new EditionControlsEvent(component);
			for (SlateControlsListener l : listeners) {
				l.backSelected(event);
			}
		}	
	}
	
	public class NextActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EditionControlsEvent event = new EditionControlsEvent(component);
			for (SlateControlsListener l : listeners) {
				l.nextSelected(event);
			}
		}	
	}
	
	public class RefreshActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ProjectControlsEvent event = new ProjectControlsEvent(component);
			for (SlateControlsListener l : listeners) {
				l.refreshSelected(event);
			}
		}	
	}
	
	public class ColorSelectionActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EditionControlsEvent event = new EditionControlsEvent(component);
			for (SlateControlsListener l : listeners) {
				l.setPointerColorByChooser(event);
			}
		}	
	}
	
}

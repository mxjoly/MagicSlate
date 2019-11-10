package gui;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import event.SlateControlsListener;
import event.SlateEvents;
import event.SlateEventsManager;
import utils.IconLoader;

public class MenuBar extends JMenuBar implements SlateComponent {

	private static final long serialVersionUID = 1L;
		
	private Vector<SlateControlsListener> listeners 	= new Vector<SlateControlsListener>();
	private SlateEventsManager manager 					= new SlateEventsManager(this, listeners);
	private Map<SlateEvents, JMenuItem> items 			= new HashMap<SlateEvents, JMenuItem>();
	private IconLoader iconLoader 						= new IconLoader(30, 30, "control");
	
	
	public MenuBar(SlateControlsListener listener) {
		
		listeners.add(listener);
		
		JMenu jmFile = new JMenu("File");
		JMenu jmEdition = new JMenu("Edit");
		
		JMenuItem jmiNew = new JMenuItem(SlateEvents.NEW.getDesc(), iconLoader.getIcon(SlateEvents.NEW.getId())),
				jmiOpen = new JMenuItem(SlateEvents.OPEN.getDesc(), iconLoader.getIcon(SlateEvents.OPEN.getId())),
				jmiSave = new JMenuItem(SlateEvents.SAVE.getDesc(), iconLoader.getIcon(SlateEvents.SAVE.getId())),
				jmiSaveAs = new JMenuItem(SlateEvents.SAVE_AS.getDesc(), iconLoader.getIcon(SlateEvents.SAVE_AS.getId())),
				jmiPrint = new JMenuItem(SlateEvents.PRINT.getDesc(), iconLoader.getIcon(SlateEvents.PRINT.getId())),
				jmiProperties = new JMenuItem(SlateEvents.PROPERTIES.getDesc(), iconLoader.getIcon(SlateEvents.PROPERTIES.getId())),
				jmiExit = new JMenuItem(SlateEvents.EXIT.getDesc(), iconLoader.getIcon(SlateEvents.EXIT.getId())),
				jmiBack = new JMenuItem(SlateEvents.BACK.getDesc(), iconLoader.getIcon(SlateEvents.BACK.getId())),
				jmiNext = new JMenuItem(SlateEvents.NEXT.getDesc(), iconLoader.getIcon(SlateEvents.NEXT.getId())),
				jmiRefresh = new JMenuItem(SlateEvents.REFRESH.getDesc(), iconLoader.getIcon(SlateEvents.REFRESH.getId())),
				jmiColor = new JMenuItem(SlateEvents.COLOR_SELECTION.getDesc(), iconLoader.getIcon(SlateEvents.COLOR_SELECTION.getId()));
				
		items.put(SlateEvents.NEW, jmiNew);
		items.put(SlateEvents.OPEN, jmiOpen);
		items.put(SlateEvents.SAVE, jmiSave);
		items.put(SlateEvents.SAVE_AS, jmiSaveAs);
		items.put(SlateEvents.PRINT, jmiPrint);
		items.put(SlateEvents.PROPERTIES, jmiProperties);
		items.put(SlateEvents.EXIT, jmiExit);
		items.put(SlateEvents.BACK, jmiBack);
		items.put(SlateEvents.NEXT, jmiNext);
		items.put(SlateEvents.REFRESH, jmiRefresh);
		items.put(SlateEvents.COLOR_SELECTION, jmiColor);
		
		// - - - - - - -  PROJECT - - - - - - - - //

		jmiNew.addActionListener(manager.getNewActionListener());
		jmiOpen.addActionListener(manager.getOpenActionListener());
		jmiSave.addActionListener(manager.getSaveActionListener());
		jmiSaveAs.addActionListener(manager.getSaveAsActionListener());
		jmiPrint.addActionListener(manager.getPrintActionListener());
		jmiProperties.addActionListener(manager.getPropertiesActionListener());
		jmiExit.addActionListener(manager.getExitActionListener());
		
		jmiNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,	KeyEvent.CTRL_DOWN_MASK));
		jmiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		jmiSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK));
		jmiPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
			
		jmFile.add(jmiNew);
		jmFile.add(jmiOpen);
		jmFile.add(jmiSave);
		jmFile.add(jmiSaveAs);
		jmFile.addSeparator();
		jmFile.add(jmiPrint);
		jmFile.addSeparator();
		jmFile.add(jmiProperties);
		jmFile.addSeparator();
		jmFile.add(jmiExit);
		
		
		// - - - - - - -  EDITION - - - - - - - - //
		
		jmiBack.addActionListener(manager.getBackActionListener());
		jmiNext.addActionListener(manager.getNextActionListener());
		jmiRefresh.addActionListener(manager.getRefreshActionListener());
		jmiColor.addActionListener(manager.getColorSelectionActionListener());
		
		jmiBack.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		jmiNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		jmiRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
			
		jmEdition.add(jmiBack);
		jmEdition.add(jmiNext);
		jmEdition.add(jmiRefresh);
		jmEdition.addSeparator();
		jmEdition.add(jmiColor);
		
		jmiBack.setEnabled(false);
		jmiNext.setEnabled(false);
		
		add(jmFile);
		add(jmEdition);
	}
	
	/**
	 * @param event the event associated to the item desired
	 * @return an item
	 */
	public JMenuItem getMenuItem(SlateEvents event) {
		return items.get(event);
	}
}

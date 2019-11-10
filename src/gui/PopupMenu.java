package gui;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import event.SlateEvents;
import event.SlateEventsManager;
import utils.IconLoader;

public class PopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	
	private IconLoader iconLoader = new IconLoader(25, 25, "control");
	
	public PopupMenu(SlateEventsManager manager) {
			
		JMenuItem jmiNew = new JMenuItem(SlateEvents.NEW.getDesc(), iconLoader.getIcon(SlateEvents.NEW.getId()));
		JMenuItem jmiOpen = new JMenuItem(SlateEvents.OPEN.getDesc(), iconLoader.getIcon(SlateEvents.OPEN.getId()));
		JMenuItem jmiSave = new JMenuItem(SlateEvents.SAVE.getDesc(), iconLoader.getIcon(SlateEvents.SAVE.getId()));
		JMenuItem jmiSaveAs = new JMenuItem(SlateEvents.SAVE_AS.getDesc(), iconLoader.getIcon(SlateEvents.SAVE_AS.getId()));
		JMenuItem jmiBack = new JMenuItem(SlateEvents.BACK.getDesc(), iconLoader.getIcon(SlateEvents.BACK.getId()));
		JMenuItem jmiNext = new JMenuItem(SlateEvents.NEXT.getDesc(), iconLoader.getIcon(SlateEvents.NEXT.getId()));
		JMenuItem jmiRefresh = new JMenuItem(SlateEvents.REFRESH.getDesc(), iconLoader.getIcon(SlateEvents.REFRESH.getId()));
		JMenuItem jmiColor = new JMenuItem(SlateEvents.COLOR_SELECTION.getDesc(), iconLoader.getIcon(SlateEvents.COLOR_SELECTION.getId()));
			
		jmiNew.addActionListener(manager.getNewActionListener());
		jmiOpen.addActionListener(manager.getOpenActionListener());
		jmiSave.addActionListener(manager.getSaveActionListener());
		jmiSaveAs.addActionListener(manager.getSaveAsActionListener());
		jmiBack.addActionListener(manager.getBackActionListener());
		jmiNext.addActionListener(manager.getNextActionListener());
		jmiRefresh.addActionListener(manager.getRefreshActionListener());
		jmiColor.addActionListener(manager.getColorSelectionActionListener());
			
		add(jmiNew);
		add(jmiOpen);
		add(jmiSave);
		add(jmiSaveAs);
		addSeparator();
		
		add(jmiBack);
		add(jmiNext);
		add(jmiRefresh);
		addSeparator();
			
		add(jmiColor);
	}

}

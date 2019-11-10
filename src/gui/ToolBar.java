package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import component.ButtonPallet;
import component.ColoredIcon;
import component.CustomComboBox;
import event.AbstractActionListener;
import event.SlateControlsListener;
import event.SlateEvents;
import event.SlateEventsManager;
import pointer.PointerSize;
import pointer.PointerState;
import utils.ColorsGenerator;
import utils.IconLoader;

public class ToolBar extends JToolBar implements SlateComponent {

	private static final long serialVersionUID = 1L;
	
	private static final Color BACKGROUND_COLOR = new Color(202, 207, 210);
	
	private Vector<SlateControlsListener> listeners 	= new Vector<SlateControlsListener>();
	private SlateEventsManager manager 					= new SlateEventsManager(this, listeners);
	
	private IconLoader controlsIL 	= new IconLoader(50, 50, "control");
	private IconLoader materialsIL 	= new IconLoader(30, 30, "material");
	private IconLoader shapesIL 	= new IconLoader(30, 30, "shape");
	private IconLoader sizesIL 		= new IconLoader(30, 15, "size");
	
	private ButtonPallet controlPallet;
	private ButtonPallet materialPallet;
	private ButtonPallet shapePallet;
	private ButtonPallet colorPallet;
	private CustomComboBox sizeCombo;
	

	public ToolBar(SlateControlsListener listener){
		
		this.setBackground(BACKGROUND_COLOR);
		this.setLayout(new GridBagLayout());
		this.setFloatable(false);
		
		listeners.add(listener);
		
		// ========================================================================================== //
		
		SlateEvents[] controls = {
				SlateEvents.NEW, 
				SlateEvents.OPEN, 
				SlateEvents.SAVE, 
				SlateEvents.SAVE_AS, 
				SlateEvents.PRINT,
				SlateEvents.BACK,
				SlateEvents.NEXT,
				SlateEvents.REFRESH
		};
		
		ActionListener[] controlActions = {
				manager.getNewActionListener(),
				manager.getOpenActionListener(),
				manager.getSaveActionListener(),
				manager.getSaveAsActionListener(),
				manager.getPrintActionListener(),
				manager.getBackActionListener(),
				manager.getNextActionListener(),
				manager.getRefreshActionListener()
		};
		
		Icon[] controlIcons = new Icon[controls.length];
		for (int i = 0 ; i < controls.length ; i++) {
			controlIcons[i] = controlsIL.getIcon(controls[i].getId());
		}
		
		controlPallet = new ButtonPallet(controlIcons, controlActions, 450, 90, 8, 1, "Controls");
		JToggleButton[] buttonControls = controlPallet.getButtons();
		
		for (int i = 0 ; i < buttonControls.length ; i++) {
			String name = controls[i].getId();
			String tips = controls[i].getDesc();
			buttonControls[i].setName(name);
			buttonControls[i].setToolTipText(tips);
			buttonControls[i].setBorderPainted(false);
			buttonControls[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			// Buttons BACK and NEXT are not enable at initialization because there is no still drawing
			if (name.equals(SlateEvents.BACK.getId()) || name.equals(SlateEvents.NEXT.getId())) {
				buttonControls[i].setEnabled(false);
			}
		}
		
		
		// ========================================================================================== //
		

		PointerState[] materials = {
				PointerState.BRUSH, 
				PointerState.PENCIL, 
				PointerState.FOUNTAIN_PEN, 
				PointerState.FELT_TIP, 
				PointerState.HIGHLIGHTER, 
				PointerState.BIG_BRUSH, 
				PointerState.ERASER, 
				PointerState.PIPETTE
		};	
		Icon[] materialIcons = new Icon[materials.length];
		ActionListener[] materialActions = new ActionListener[materials.length];

		for (int i = 0 ; i < materials.length ; i++){
			materialIcons[i] = materialsIL.getIcon(materials[i].getId());
			materialActions[i] = 
					new AbstractActionListener(i) {
						public void actionPerformed(ActionEvent e) {
							manager.setPointerState(materials[getIndex()]);
						}
					};
		}
		
		materialPallet = new ButtonPallet(materialIcons, materialActions, 200, 90, 4, 2, "Materials");
		materialPallet.getButton(0, 0).setSelected(true); // At initialization, the first button of pallet is selected	
		
		
		// ========================================================================================== //
		

		PointerState[] shapes = {
				PointerState.OVAL, 
				PointerState.RECTANGLE, 
				PointerState.TRIANGLE, 
				PointerState.DIAMOND, 
				PointerState.ARROW_RIGHT, 
				PointerState.ARROW_UP, 
				PointerState.STAR, 
				PointerState.HEART
		};	
		Icon[] shapeIcons = new Icon[shapes.length]; 
		ActionListener[] shapeActions = new ActionListener[shapes.length];
		
		for (int i = 0 ; i < shapes.length ; i++){
			shapeIcons[i] = shapesIL.getIcon(shapes[i].getId());
			shapeActions[i] = 
					new AbstractActionListener(i) {
						public void actionPerformed(ActionEvent e) {
							manager.setPointerState(shapes[getIndex()]);
						}
					};
		}
			
		shapePallet = new ButtonPallet(shapeIcons, shapeActions, 170, 90, 4, 2, "Shapes");
		
		
		// ========================================================================================== //

		
		Color[] colors = ColorsGenerator.getColors();	
		Icon[] colorIcons = new Icon[colors.length]; 
		ActionListener[] colorActions = new ActionListener[colors.length];
	
		for (int i = 0 ; i < colors.length ; i++){
			colorIcons[i] = new ColoredIcon(colors[i], 20, 20);
			colorActions[i] = 
					new AbstractActionListener(i) {
						public void actionPerformed(ActionEvent e) {
							manager.setPointerColor(colors[getIndex()]);
						}
					};
		}
		
		colorPallet = new ButtonPallet(colorIcons, colorActions, 220, 90, 16, 4, "Colors");
		
		
		// ========================================================================================== //
		
		
		PointerSize[] sizes = {PointerSize.LIGHT, PointerSize.MEDIUM, PointerSize.LARGE};
		String[] sizeString = {PointerSize.LIGHT.getDesc(), PointerSize.MEDIUM.getDesc(), PointerSize.LARGE.getDesc()};
		Icon[] sizeIcons = new Icon[sizes.length];
		ActionListener sizeAction = new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											setPointerSize();
										}
									};
		
		for (int i = 0 ; i < sizes.length ; i++) {
			sizeIcons[i] = sizesIL.getIcon(sizes[i].getId());
		}
		
		sizeCombo = new CustomComboBox(sizeString, sizeIcons, sizeAction, "Stroke");
		sizeCombo.setPreferredSize(new Dimension(130, 50));
		sizeCombo.setBackground(BACKGROUND_COLOR);
		
		
		// ========================================================================================== //
		
		
		add(controlPallet);
		addSeparator();
		add(materialPallet);
		addSeparator();
		add(shapePallet);
		addSeparator();	
		add(colorPallet);
		addSeparator();	
		add(sizeCombo);
	}

	private void setPointerSize() {
		PointerSize size;
		String selected = sizeCombo.getSelectedItem();
		if 		(selected.equals(PointerSize.LIGHT.getId())) 	size = PointerSize.LIGHT;
		else if (selected.equals(PointerSize.MEDIUM.getId()))	size = PointerSize.MEDIUM;
		else if (selected.equals(PointerSize.LARGE.getId()))	size = PointerSize.LARGE;
		else return;
		manager.setPointerSize(size);
	}
	
	/**
	 * Get a button from the control pallet
	 * @param event of the button
	 * @return the button
	 */
	public JToggleButton getControlButton(SlateEvents event) {
		JToggleButton[] buttons = controlPallet.getButtons();
		for (int i = 0 ; i < buttons.length ; i++) {
			if (buttons[i].getName().equals(event.getId())) {
				return buttons[i];
			}
		}
		return null;
	}
	
	public ButtonPallet getShapePallet() {
		return shapePallet;
	}
	
	public CustomComboBox getSizeComboBox() {
		return sizeCombo;
	}

}

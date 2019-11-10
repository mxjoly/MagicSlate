package component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

/**
 * Pallet that is a grid of buttons associated with a single data
 */
public class ButtonPallet extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private ButtonGroup group = new ButtonGroup();
	
	private final int nbCols;
	private final int nbRows;
	private JToggleButton[][] tab;
	
	/**
	 * @param icons list of icons displayed
	 * @param actions list of listeners associated to icons
	 * @param width of pallet i n pixels
	 * @param height of pallet in pixels
	 * @param nbCols number of icons in a row
	 * @param nbRows number of icons in a column
	 * @param title of the pallet
	 */
	public ButtonPallet(Icon[] icons, ActionListener[] actions, int width, int height, int nbCols, int nbRows, String title){
		
		this.setLayout(new GridLayout(nbRows, nbCols));
		this.setPreferredSize(new Dimension(width, height));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.nbCols = nbCols;
		this.nbRows = nbRows;
		this.tab = new JToggleButton[nbCols][nbRows];
		
		int i = 0;
		for (int r = 0 ; r < nbRows ; r++) {
			for (int c = 0 ; c < nbCols ; c++) {
				JToggleButton button = new JToggleButton(icons[i]);
				button.setPreferredSize(new Dimension(30, 30));
				button.addActionListener(actions[i]);
				tab[c][r] = button;
				group.add(button);
				add(button);
				i++;
			}
		}
		
		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(), 
				title,
				TitledBorder.CENTER,
				TitledBorder.BOTTOM, null, Color.BLACK)
		);	
	}
	
	/**
	 * Clear the selection
	 */
	public void clear() {
		group.clearSelection();
	}
	
	/**
	 * @param col of the button
	 * @param row of the button
	 * @return the button at the specified position
	 */
	public JToggleButton getButton(int col, int row){
		return tab[col][row];
	}
	
	/**
	 * @return the buttons
	 */
	public JToggleButton[] getButtons(){
		JToggleButton[] buttons = new JToggleButton[group.getButtonCount()];
		int i = 0;
		for (int r = 0 ; r < nbRows ; r++) {
			for (int c = 0 ; c < nbCols ; c++) {
				buttons[i++] = tab[c][r];
			}
		}
		return buttons;
	}

}

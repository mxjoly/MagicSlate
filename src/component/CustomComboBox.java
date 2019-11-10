package component;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * A JComboBox whose each items have icons and text
 */
public class CustomComboBox extends JPanel {

	private static final long serialVersionUID = 795565633870638546L;
	
	private JComboBox<Integer> box;
	private String[] itemNames;
	private Icon[] itemIcons;
	
	/**
	 * @param itemNames a table that contains the names of items
	 * @param itemIcons a table that contains the icons of items
	 * @param name of the component that will be displayed
	 */
    public CustomComboBox(String[] itemNames, Icon[] itemIcons, ActionListener actions, String name) {

    	this.itemNames = itemNames;
    	this.itemIcons = itemIcons;	
    	
    	box = new JComboBox<Integer>();
    	for (int i = 0 ; i < itemNames.length ; i++){
    		box.addItem(i);
    	}
		
		box.setRenderer(new ComboBoxRenderer());
		box.setMaximumRowCount(3);
		box.addActionListener(actions);
			
		this.add(box);
		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(), 
				name,
				TitledBorder.CENTER,
				TitledBorder.TOP, null, Color.BLACK)
		);	
		
    }
    
    /**
     * @return the name of selected item
     */
    public String getSelectedItem(){
		return itemNames[box.getSelectedIndex()].toUpperCase();
	}
    
    /**
     * Reset the items selecting the first one
     */
    public void reset(){
    	box.setSelectedIndex(0);
    }

    class ComboBoxRenderer extends JLabel implements ListCellRenderer<Integer> {

		private static final long serialVersionUID = 1L;
		
        public ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
        }

        /**
         * Display cell with icon and description
         */
        @Override
		public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index, boolean isSelected, boolean cellHasFocus) {
			
   			if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } 
   			else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
  	
   			setIcon(itemIcons[value]);
   			setText(itemNames[value]);
            return this;
		}

    }
}

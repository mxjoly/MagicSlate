package event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class created for initializing ActionListeners in loop
 */
public abstract class AbstractActionListener implements ActionListener {
	
	private int index;
	
	public AbstractActionListener(int index) {
		this.index = index;
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);
	
	public int getIndex() {
		return index;
	}

}

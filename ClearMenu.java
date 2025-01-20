/* This is a JMenu containing items that allow the user clear certain
 * squares in the grid, reverting them to the default EMPTY state.   
 * ActionListeners are added to the items when the class is used in
 * the MurdleGridGenerator class. */

package john.game.murdle;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ClearMenu extends JMenu implements Constants {

	// Constructor
	
	public ClearMenu() {
		
		super("Clear squares");
		setMnemonic(KeyEvent.VK_C);
		JMenuItem item1 = new JMenuItem("Question marks");
		item1.setMnemonic(KeyEvent.VK_Q);
		add(item1);
		JMenuItem item2 = new JMenuItem("All");
		item2.setMnemonic(KeyEvent.VK_A);
		add(item2);
		
	}
	
}

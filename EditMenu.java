/* This JMenu contains items related to editing the names of suspects,
 * weapons, locations, and such other categories as may be present in
 * the puzzle.  ActionListeners are added to the items in the
 * MurdleGridGenerator class. */

package john.game.murdle;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class EditMenu extends JMenu implements Constants {

	// Fields
	
	JMenuItem[] menuItems;
	
	// Constructor
	
	public EditMenu() {
		
		super("Edit labels");
		setMnemonic(KeyEvent.VK_E);
		menuItems = new JMenuItem[CATEGORIES.length];
		for (int i = 0; i < CATEGORIES.length; i++) {
			StringBuilder itemText = new StringBuilder(CATEGORIES[i]);
			if (i != OTHER) itemText.append("s");
			menuItems[i] = new JMenuItem(itemText.toString());
			char c = CATEGORIES[i].charAt(0);
			menuItems[i].setMnemonic(KeyEvent.getExtendedKeyCodeForChar(c));
			add(menuItems[i]);
		}
		
	}
	
}

/* This menu contains items related to certain exhibits in Murdle,
 * Volumes 1 and 2. */

package john.game.murdle;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ExhibitMenu extends JMenu {

	// Fields
	
	ExhibitA exhibitA;
	ExhibitB exhibitB;
	ExhibitC exhibitC;
	
	// Constructor
	
	public ExhibitMenu() {
		
		super("Exhibits");
		setMnemonic(KeyEvent.VK_X);
		exhibitA = new ExhibitA();
		JMenuItem itemA = new JMenuItem("Vol. 1, Ex. A",KeyEvent.VK_A);
		itemA.addActionListener(e -> exhibitADialog());
		add(itemA);
		exhibitB = new ExhibitB();
		JMenuItem itemB = new JMenuItem("Vol. 2, Ex. B",KeyEvent.VK_B);
		itemB.addActionListener(e -> exhibitBDialog());
		add(itemB);
		exhibitC = new ExhibitC();
		JMenuItem itemC = new JMenuItem("Vol. 2, Ex. C",KeyEvent.VK_C);
		itemC.addActionListener(e -> exhibitCDialog());
		add(itemC);
		
	}
	
	// Methods
	
	private void exhibitADialog() {
		
		JOptionPane.showMessageDialog(getParent().getParent(),exhibitA,"Volume 1, Exhibit A",JOptionPane.PLAIN_MESSAGE);
		
	}
	
	private void exhibitBDialog() {
		
		JOptionPane.showMessageDialog(getParent().getParent(),exhibitB,"Volume 2, Exhibit B",JOptionPane.PLAIN_MESSAGE);
		
	}

	private void exhibitCDialog() {
		
		JOptionPane.showMessageDialog(getParent().getParent(),exhibitC,"Volume 2, Exhibit C",JOptionPane.PLAIN_MESSAGE);
		
	}

}

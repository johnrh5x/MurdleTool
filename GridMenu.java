/* This menu contains items related to setting the size of the grid,
 * including the number of elements per category and whether or not to
 * include a category other than suspects, weapons, and locations.
 * ActionListeners are added to the items in the MurdleGridGenerator
 * class. */

package john.game.murdle;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

public class GridMenu extends JMenu implements Constants {

	// Fields
	
	ButtonGroup            elementsRadioGroup;
	JRadioButtonMenuItem[] elementsRadioButtons;
	JCheckBoxMenuItem      motivesCheckBox;                         
	
	// Constructor
	
	public GridMenu() {
		
		super("Grid properties");
		setMnemonic(KeyEvent.VK_G);
		
		// Elements
				
		elementsRadioGroup = new ButtonGroup();
		elementsRadioButtons = new JRadioButtonMenuItem[MAX_ELEMENTS - MIN_ELEMENTS + 1];
		for (int i = 0; i < elementsRadioButtons.length; i++) {
			elementsRadioButtons[i] = new JRadioButtonMenuItem((MIN_ELEMENTS + i) + " elements");
			elementsRadioGroup.add(elementsRadioButtons[i]);
			add(elementsRadioButtons[i]);
		}
		elementsRadioButtons[0].setSelected(true);
		
		// Motives
		
		addSeparator();
		motivesCheckBox = new JCheckBoxMenuItem("Other category",false);
		add(motivesCheckBox);
		
	}
	
	// Methods
	
	public void addElementsActionListener(ActionListener l) {
		
		for (JRadioButtonMenuItem b : elementsRadioButtons) b.addActionListener(l);
		
	}
	
	public void addMotivesActionListener(ActionListener l) {
		
		motivesCheckBox.addActionListener(l);
		
	}
	
	public int getElements() {
		
		int output = -1;
		for (int i = 0; i < elementsRadioButtons.length; i++) {
			if (elementsRadioButtons[i].isSelected()) {
				output = MIN_ELEMENTS + i;
				break;
			}
		}
		return output;
		
	}

	public int getCategories() {
		
		int output = 3;
		if (motivesCheckBox.isSelected()) output++;
		return output;
		
	}

}

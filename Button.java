/* The Button class is a customized JButton that represents one square
 * in the interactive Murdle grid.  A Button can exist in one of five
 * states:
 * 
 * - EMPTY, the default state, meaning that the user has yet to deduce
 *   anything about this square
 * 
 * - FALSE_USER, for when the user deduces that the combination of
 *   suspects, weapons, locations, or other information represented by 
 *   this square is false
 * 
 * - FALSE_AUTO, for when the combination must be false because the user
 *   has specified that a different combination is true
 * 
 * - TRUE, for when the user deduces that the combination is true
 * 
 * - QUESTION_FALSE, for use with puzzles which require the user to
 *   determine which suspect is lying
 * 
 *   and
 * 
 * - QUESTION_TRUE, as above.
 * 
 * Clicking on a Button will cause it to cycle through the possible
 * states, as appropriate.  No amount of clicking, for example, can
 * cause a Button to take on the FALSE_AUTO state, and clicking on a
 * Button in the FALSE_AUTO state does nothing.  Setting a button to
 * TRUE will cause the other Buttons in the same row or column and block
 * to take the value FALSE_AUTO, and so on.
 * 
 * Each state is represented by a different icon and the Button's icon
 * changes to reflect changes in its state.*/

package john.game.murdle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;


public class Button extends JButton implements Constants {
	
	// Fields
	
	private static boolean     flag = true;
	private static ImageIcon[] icons;
	
	private int state, row, column;
	
	// Constructor
	
	public Button(int row, int column) {
		
		super();
		
		// Icons
		
		if (flag) {
			icons = new ImageIcon[ICON_FILES.length];
			for (int i = 0; i < ICON_FILES.length; i++) {
				InputStream stream = Button.class.getResourceAsStream(ICON_FILES[i]);
				if (stream != null) {
					try {
						icons[i] = new ImageIcon(ImageIO.read(stream));
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println("Null InputStream for " + ICON_FILES[i] + ".");
				}
			}
			flag = false;
		}
		
		// Instance-specific operations
		
		this.row = row;
		this.column = column;
		state = EMPTY;
		setIcon(icons[state]);
		int w = 2*DEFAULT_INSET + icons[state].getIconWidth();
		int h = 2*DEFAULT_INSET + icons[state].getIconHeight();
		setPreferredSize(new Dimension(w,h));
		
	}
	
	// Methods
	
	public boolean canOverwrite() {
		
		return state == EMPTY || state == QUESTION_TRUE || state == QUESTION_FALSE;
		
	}
	
	private void cycleState() {
		
		switch (state) {
			case EMPTY:          state = FALSE_USER;     break;
			case FALSE_USER:     state = TRUE;           break;
			case TRUE:           state = QUESTION_FALSE; break;
			case QUESTION_FALSE: state = QUESTION_TRUE;  break;
			case QUESTION_TRUE:  state = EMPTY;          break;
		}
		setIcon(icons[state]);
		
	}

	public int getColumn() {return column;}

	public int getRow() {return row;}

	public int getState() {return state;}
	
	public void setState(int state) {
		
		this.state = state;
		setIcon(icons[state]);
	
	}
	
}

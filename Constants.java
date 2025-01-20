/* The Constants interface contains static variables and a layout
 * method which are useful for multiple classes in the package. */

package john.game.murdle;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

public interface Constants {

	// Fields
	
	public static final String[] CATEGORIES     = {"Suspect","Weapon","Location","Other"};
	public static final int      SUSPECTS       = 0;
	public static final int      WEAPONS        = 1;
	public static final int      LOCATIONS      = 2;
	public static final int      OTHER          = 3;
	public static final String[] ICON_FILES     = {"empty.png","false_user.png","false_auto.png","true.png","question_false.png","question_true.png"};
	public static final int      EMPTY          = 0;
	public static final int      FALSE_USER     = 1;
	public static final int      FALSE_AUTO     = 2;
	public static final int      TRUE           = 3;
	public static final int      QUESTION_FALSE = 4;
	public static final int      QUESTION_TRUE  = 5;
	public static final int      DEFAULT_INSET  = 5;
	public static final int      MIN_ELEMENTS   = 3;
	public static final int      MAX_ELEMENTS   = 5;
	public static final Font     BOLD_FONT      = (new JLabel()).getFont();
	public static final Font     PLAIN_FONT     = BOLD_FONT.deriveFont(Font.PLAIN);

	// Methods
	
	default GridBagConstraints constraints(int x, int y, int w, int h) {
		
		GridBagConstraints output = new GridBagConstraints();
		output.gridx = x;
		output.gridy = y;
		output.gridwidth = w;
		output.gridheight = h;
		output.insets = new Insets(0,0,DEFAULT_INSET,DEFAULT_INSET);
		if (x == 0) output.insets.left = DEFAULT_INSET;
		if (y == 0) output.insets.top = DEFAULT_INSET;
		return output;
		
	}

}

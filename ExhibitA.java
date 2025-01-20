/* The ExhibitA panel allows the user to enter and decode coded messages
 * using the substitution cypher in Murdle, Volume 1, Exhibit A. */

package john.game.murdle;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ExhibitA extends JPanel {
	
	// Fields
	
	private static final int      COLUMNS  = 40;
	private static final String[] ALPHABET = {"A","B","C","D","E","F",
		                                      "G","H","I","J","K","L",
		                                      "M","N","O","P","Q","R",
		                                      "S","T","U","V","W","X",
		                                      "Y","Z"};
	
	JTextField encodedText;
	JLabel     decodedText;
	
	// Constructor
	
	public ExhibitA() {
		
		super(new GridBagLayout());
		encodedText = new JTextField(COLUMNS);
		encodedText.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {decode();}
			@Override
			public void insertUpdate(DocumentEvent e) {decode();}
			@Override
			public void removeUpdate(DocumentEvent e) {decode();}
		});
		decodedText = new JLabel();
		add(new JLabel("Encoded text:"),constraints(0,0));
		add(encodedText,constraints(1,0));
		add(new JLabel("Decoded text:"),constraints(0,1));
		add(decodedText,constraints(1,1));
		
	}
	
	// Methods
	
	private GridBagConstraints constraints(int x, int y) {
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.insets = new Insets(0,0,5,5);
		if (x == 0) c.insets.left = 5;
		if (y == 0) c.insets.top = 5;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
		
	}
	
	private void decode() {
		
		String message = encodedText.getText();
		int length = message.length();
		if (length > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < length; i++) {
				int position = -1;
				for (int j = 0; j < ALPHABET.length; j++) {
					if (message.substring(i,i+1).equalsIgnoreCase(ALPHABET[j])) {
						position = j;
						break;
					}
				}
				switch (position) {
					case -1: sb.append(message.substring(i,i+1));             break;
					default: sb.append(ALPHABET[ALPHABET.length-1-position]); break;
				}
			}
			decodedText.setText(sb.toString());
		} else {
			decodedText.setText("");
		}
		
	}
	
}

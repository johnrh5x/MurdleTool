/* The ExhibitB panel allows the user solve clues related to Murdle,
 * Volume 2, Exhibit B. */

package john.game.murdle;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ExhibitB extends JPanel {

	// Fields
	
	private static final int        COLUMNS      = 40;
	private static final String[][] VALUES       = {{"A","B","C","D","E","F","G","H","I"},
		                                            {"J","K","L","M","N","O","P","Q","R"},
		                                            {"S","T","U","V","W","X","Y","Z"}};
	private static final String[]   ASSOCIATIONS = {"Origins, opportunities",
		                                            "Unity, leadership",
		                                            "Duality, the Dialectic",
		                                            "Creativity, magick",
		                                            "Matter, minerals, work",
		                                            "Adventure, nature",
		                                            "Death, sincerity, home",
		                                            "Luck, spirituality, life",
		                                            "Money, motivation",
		                                            "Pain, revolution"};
	
	JTextField textField;
	JLabel     codeField, figureField, associationField;
	
	// Constructor
	
	public ExhibitB() {
		
		super(new GridBagLayout());
		textField = new JTextField(COLUMNS);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {encode();}
			@Override
			public void removeUpdate(DocumentEvent e) {encode();}
			@Override
			public void changedUpdate(DocumentEvent e) {encode();}
		});
		codeField = new JLabel();
		figureField = new JLabel();
		associationField = new JLabel();
		int row = 0;
		add(new JLabel("Plain text:"),constraints(0,row,1,1));
		add(textField,constraints(1,row++,1,1));
		add(new JLabel("Encoded text:"),constraints(0,row,1,1));
		add(codeField,constraints(1,row++,1,1));
		add(new JLabel("Significant figure:"),constraints(0,row,1,1));
		add(figureField,constraints(1,row++,1,1));
		add(new JLabel("Association:"),constraints(0,row,1,1));
		add(associationField,constraints(1,row++,1,1));
		
	}
	
	// Methods
	
	private GridBagConstraints constraints(int x, int y, int w, int h) {
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = w;
		c.gridheight = h;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,5,5);
		if (x == 0) c.insets.left = 5;
		if (y == 0) c.insets.top = 5;
		return c;
		
	}
	
	private void encode() {
		
		String input = textField.getText();
		int length = input.length();
		if (length > 0) {
			StringBuilder sb = new StringBuilder();
			int sum = 0;
			for (int i = 0; i < length; i++) {
				int row = 0;
				int col = 0;
				boolean match = false;
				while (!match && row < VALUES.length) {
					match = input.substring(i,i+1).equalsIgnoreCase(VALUES[row][col]);
					if (match) {
						sum += col + 1;
						sb.append(String.valueOf(row+1));
						sb.append(String.valueOf(col+1));
						if (i < input.length() - 1) sb.append(" ");
					}
					col++;
					if (col == VALUES[row].length) {
						col = 0;
						row++;
					}
				}
			}
			codeField.setText(sb.toString());
			int counter = 0;
			while (sum > 9 && counter++ < 100) sum = sumOfDigits(sum);
			if (counter == 100) {
				System.out.println("Maximum iterations in significant figure exceeded.");
				System.exit(1);
			}
			figureField.setText(String.valueOf(sum));
			associationField.setText(ASSOCIATIONS[sum]);
		} else {
			codeField.setText("");
			figureField.setText("");
			associationField.setText("");
		}
		
	}

	private int sumOfDigits(int input) {
				
		int output = 0;
		int divisor = 1;
		while (input >= divisor*10) divisor *= 10;
		int remainder = input;
		while (divisor > 1) {
			output += remainder/divisor;
			remainder = remainder%divisor;
			divisor /= 10;
		}
		output += remainder;
		return output;
		
	}
	
}

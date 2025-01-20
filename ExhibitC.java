/* The Exhibit C panel allows the user to solve clues related to Murdle,
 * Volume 2, Exhibit C. */

package john.game.murdle;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ExhibitC extends JPanel {
	
	// Fields
	
	private static final int        ROWS    = 3;
	private static final int        COLUMNS = 40;
	private static final int        PARTIAL = 0;
	private static final int        FULL    = 1;
	private static final String[][] DNA = {{"Bishop Azure",          "CCTGTTTGTAGCATTAA"},
		                                   {"Cosomonaut Bluski",     "ATAAAGCTGCGCATGAT"},
		                                   {"Manservant Brownstone", "ACATCCGGCACAGTTGA"},
		                                   {"Secretary Celadon",     "TTTCGAACATGACGAGC"},
		                                   {"Comrade Champagne",     "CATTTGGGCATGTTCCT"},
		                                   {"General Coffee",        "GTCTCCAGGTCTAGATT"},
		                                   {"Officer Copper",        "TCAAAAGTGGTTTTTCA"},
		                                   {"Dr. Crimson",           "TACTTCAGATGAGGGTC"},
		                                   {"Vicount Eminence",      "CGCTCTGAGAAAGCGCC"},
		                                   {"Earl Grey",             "GCTGCCGCTGACCTATT"},
		                                   {"Sergeant Gunmetal",     "TACTGAAGGCCATGAGT"},
		                                   {"Sister Lapis",          "CCCGTCGAGCCGCTGGC"},
		                                   {"Baron Maroon",          "ACATAAAATTCGTTGCA"},
		                                   {"Uncle Midnight",        "TGGACCGATTGAGTATA"},
		                                   {"Patriarch Porpoise",    "GGAAGAGATAATGTGCC"},
		                                   {"Coach Raspberry",       "TTATGTTTACAAGTACC"},
		                                   {"High Alchemist Raven",  "TCGCTGCTATGGTAAAG"},
									       {"Major Red",             "GGCGCTTTCTCAGAGCG"},
									       {"Sir Rulean",            "CAGCTGCTCACTTCTTT"},
									       {"Aristocrat Sable",      "TTATCTAGGGGTATTGG"},
										   {"Duchess of Vermillion", "CGAACTAAACAGTGCGC"},
										   {"President White",       "GCTGGCGTACGACCGAG"},
										   {"Mysterious Sample",     "GCGAGACTCTTTCGCGG"}};
	
	JTextField     sequenceField;
	JTextArea      matchesTextArea;
	
	// Constructor
	
	public ExhibitC() {
		
		super(new GridBagLayout());
		sequenceField = new JTextField(COLUMNS);
		sequenceField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {search();}
			@Override
			public void insertUpdate(DocumentEvent e) {search();}
			@Override
			public void removeUpdate(DocumentEvent e) {search();}
		});
		matchesTextArea = new JTextArea(ROWS,COLUMNS);
		matchesTextArea.setEditable(false);
		matchesTextArea.setLineWrap(true);
		matchesTextArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(matchesTextArea);
		add(new JLabel("Sequence:"),constraints(0,0));
		add(sequenceField,constraints(1,0));
		add(new JLabel("Matches:"),constraints(0,1));
		add(scrollPane,constraints(1,1));

	}
	
	// Methods
	
	private GridBagConstraints constraints(int x, int y) {
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,5,5);
		if (x == 0) c.insets.left = 5;
		if (y == 0) c.insets.top = 5;
		return c;
		
	}
	
	private void search() {
		
		// Convert sequence to all-caps
		
		String sequence = sequenceField.getText();
		int length = sequence.length();
		char[] values = new char[length];
		for (int i = 0; i < length; i++) {
			values[i] = Character.toUpperCase(sequence.charAt(i));
		}
		String capitals = new String(values);
		
		// Look for matches
		
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		for (int i = 0; i < DNA.length; i++) {
			if (DNA[i][1].contains(capitals)) {
				if (counter++ > 0) sb.append(", ");
				sb.append(DNA[i][0]);
			}
		}
		if (counter == 0) sb.append("No matches found.");

		// Show matches
		
		matchesTextArea.setText(sb.toString());
		
	}

}

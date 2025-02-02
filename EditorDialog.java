/* This dialog allows the user to specify names for suspects, weapons,
 * locations, and other categories. */

package john.game.murdle;

import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditorDialog extends JDialog implements Constants {
	
	// Fields
	
	private static final String[] BUTTON_TEXT = {"OK","Cancel"};
	private static final int      OK          = 0;
	private static final int      CANCEL      = 1;
	
	private int          category, elements;
	private JLabel[]     labels;
	private JTextField[] fields;
	private JButton[]    buttons;
	private JPanel       editPanel, buttonPanel;
	private boolean      ok;
	
	// Constructor
	
	public EditorDialog(JFrame owner) {
		
		// Layout
		
		super(owner,"Editor",true);
		getContentPane().setLayout(new GridBagLayout());
		editPanel = new JPanel(new GridBagLayout());
		labels = new JLabel[MAX_ELEMENTS];
		fields = new JTextField[MAX_ELEMENTS];
		for (int i = 0; i < MAX_ELEMENTS; i++) {
			labels[i] = new JLabel();
			fields[i] = new JTextField(20);
		}
		buttonPanel = new JPanel(new GridBagLayout());
		buttons = new JButton[BUTTON_TEXT.length];
		for (int i = 0; i < BUTTON_TEXT.length; i++) {
			buttons[i] = new JButton(BUTTON_TEXT[i]);
			buttonPanel.add(buttons[i],constraints(i,0,1,1));
		}
		buttons[OK].setMnemonic(KeyEvent.VK_O);
		buttons[CANCEL].setMnemonic(KeyEvent.VK_C);
		getContentPane().add(editPanel,constraints(0,0,1,1));
		getContentPane().add(buttonPanel,constraints(0,1,1,1));
		
		// Logic
		
		buttons[OK].addActionListener(e -> onClick(true));
		buttons[CANCEL].addActionListener(e -> onClick(false));
		KeyAdapter k = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) onEnter(e);
			}
		};
		for (JTextField f : fields) f.addKeyListener(k);
		for (JButton b : buttons) b.addKeyListener(k);
		
		
	}
	
	// Methods
		
	public void edit(int category, int elements, String[] values) {
		
		this.category = category;
		this.elements = elements;
		ok = false;
		editPanel.removeAll();
		for (int i = 0; i < elements; i++) {
			labels[i].setText(CATEGORIES[category] + " " + (i+1));
			fields[i].setText(values[i]);
			editPanel.add(labels[i],constraints(0,i,1,1));
			editPanel.add(fields[i],constraints(1,i,1,1));
		}
		pack();
		setLocationRelativeTo(getOwner());
		setVisible(true);
		fields[0].requestFocusInWindow();
		fields[0].selectAll();
		
	}

	public int getCategory() {return category;}

	public String[] getValues() {
		
		String[] output = new String[elements];
		for (int i = 0; i < elements; i++) output[i] = fields[i].getText();
		return output;
		
	}

	public boolean isOK() {return ok;}

	private void onClick(boolean b) {ok = b; setVisible(false);}

	private void onEnter(KeyEvent e) {
		
		if (e.getSource().equals(buttons[OK])) {
			onClick(true);
		} else if (e.getSource().equals(buttons[CANCEL])) {
			onClick(false);
		} else {
			int index = -1;
			for (int i = 0; i < elements; i++) {
				if (e.getSource().equals(fields[i])) {
					index = i;
					break;
				}
			}
			if (index > -1 && index < elements - 1) {
				fields[index+1].requestFocusInWindow();
				fields[index+1].selectAll();
			} else if (index == elements - 1) {
				onClick(true);
			} else {
				System.out.println("Unexpected KeyEvent source.");
			}
		}
		
	}
	
	
}

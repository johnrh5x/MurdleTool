/* A MurdleGrid is a panel containing Buttons and Labels representing
 * all the possible combinations of suspects, weapons, locations, and
 * such other categories as may be involved. */

package john.game.murdle;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MurdleGrid extends JPanel implements Constants {
	
	// Fields
	
	private int          categories;
	private int          elements;
	private Button[][]   buttons;
	private Label[]      rowLabels;
	private Label[]      columnLabels;
	
	// Constructor
	
	public MurdleGrid(String[][] data) {
		
		super(new GridBagLayout());
		
		// Process data
		
		categories = data.length;
		elements = data[0].length;
		
		// Create buttons and add them to the layout
		
		int gridSize = (categories - 1)*elements;
		buttons = new Button[gridSize][];
		int counter = 0;
		int columns = gridSize;
		for (int i = 0; i < gridSize; i++) {
			buttons[i] = new Button[columns];
			for (int j = 0; j < columns; j++) {
				buttons[i][j] = new Button(i,j);
				add(buttons[i][j],constraints(j+1,i+1,1,1));
			}
			if (++counter == elements) {
				counter = 0;
				columns -= elements;
			}
		}

		// Create row and column labels and add them to the layout

		rowLabels = new Label[gridSize];
		columnLabels = new Label[gridSize];
		int rowCategory = 1;
		int columnCategory = 0;
		counter = 0;
		for (int i = 0; i < gridSize; i++) {
			rowLabels[i] = new Label(data[rowCategory][counter],false);
			columnLabels[i] = new Label(data[columnCategory][counter],true);
			add(rowLabels[i],constraints(0,i+1,1,1));
			add(columnLabels[i],constraints(i+1,0,1,1));
			if (++counter == elements) {
				counter = 0;
				rowCategory++;
				switch(columnCategory) {
					case 0:  columnCategory = categories - 1; break;
					default: columnCategory--;                break;
				}
			}
		}
		
		/* Set up a MouseAdapter to change the look of the row and
		 * column labels as the mouse pointer enters and exits buttons. */
		 
		MouseAdapter adapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Button b = (Button)e.getSource();
				rowLabels[b.getRow()].setFont(BOLD_FONT);
				columnLabels[b.getColumn()].setFont(BOLD_FONT);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Button b = (Button)e.getSource();
				rowLabels[b.getRow()].setFont(PLAIN_FONT);
				columnLabels[b.getColumn()].setFont(PLAIN_FONT);
			}
		};
		
		/* Add the MouseAdapter to the buttons.  Also add a listener
		 * that changes the state of the buttons in the grid when the
		 * user clicks a button. */
		
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].addMouseListener(adapter);
				buttons[i][j].addActionListener(e -> autoFill(e));
			}
		}

	}
	
	// Methods
	
	private void autoFill(ActionEvent e) {
		
		/* Cycle through button values. */
		
		Button b = (Button)e.getSource();
		switch (b.getState()) {
			case EMPTY:
				b.setState(FALSE_USER);
				break;
			case FALSE_USER:
				if (trueInRowOrColumn(b)) {
					b.setState(FALSE_AUTO);
				} else {
					b.setState(TRUE);
				}
				break;
			case TRUE:
				b.setState(QUESTION_FALSE);
				break;
			case QUESTION_FALSE:
				b.setState(QUESTION_TRUE);
				break;
			case QUESTION_TRUE:
				b.setState(EMPTY);
				break;	
		}
		
		/* Auto-fill. */
		
		for (int i = 0; i < buttons.length; i++) { 
			for (int j = 0; j < buttons[i].length; j++) {
				if (i == b.getRow() || j == b.getColumn()) {
					switch (buttons[i][j].getState()) {
						case EMPTY:
							if (trueInRowOrColumn(buttons[i][j])) buttons[i][j].setState(FALSE_AUTO);
							break;
						case FALSE_AUTO:
							if (!trueInRowOrColumn(buttons[i][j])) buttons[i][j].setState(EMPTY);
							break;
						case QUESTION_FALSE:
							if (trueInRowOrColumn(buttons[i][j])) buttons[i][j].setState(FALSE_AUTO);
							break;
						case QUESTION_TRUE:
							if (trueInRowOrColumn(buttons[i][j])) buttons[i][j].setState(FALSE_AUTO);
							break;
					}
				}
			}
		}
		
	}

	
	public void clearAll() {
		
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].setState(EMPTY);
			}
		}
		
	}
	
	public void clearQuestion() {
		
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				int state = buttons[i][j].getState();
				if (state == QUESTION_FALSE || state == QUESTION_TRUE) {
					buttons[i][j].setState(EMPTY);
				}
			}
		}
		
	}

	private boolean trueInRowOrColumn(Button b) {
		
		int row = b.getRow();
		int column = b.getColumn();
		int minRow = (row/elements)*elements;
		int maxRow = minRow + elements - 1;
		int minCol = (column/elements)*elements;
		int maxCol = minCol + elements - 1;
		boolean rowFlag = false;
		for (int i = minCol; i <= maxCol; i++) {
			rowFlag = buttons[row][i].getState() == TRUE;
			if (rowFlag) break;
		}
		boolean columnFlag = false;
		for (int i = minRow; i <= maxRow; i++) {
			columnFlag = buttons[i][column].getState() == TRUE;
			if (columnFlag) break;
		}
		return rowFlag || columnFlag;
		
	}
	
}

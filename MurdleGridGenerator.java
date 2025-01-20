/* This is the main class for the Murdle Tool. */

package john.game.murdle;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MurdleGridGenerator extends JFrame implements Constants {
	
	// Fields
	
	private String[][]   data;
	private MurdleGrid   grid;
	private GridMenu     gridMenu;
	private EditorDialog editorDialog;
	
	// Constructor
	
	public MurdleGridGenerator() {
		
		super("Murdle Tool");
		data = new String[CATEGORIES.length][MAX_ELEMENTS];
		for (int i = 0; i < CATEGORIES.length; i++) {
			for (int j = 0; j < MAX_ELEMENTS; j++) {
				data[i][j] = CATEGORIES[i] + " " + (j + 1);
			}
		}
		
	}
	
	// Methods
		
	private void createAndShowGui() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			setIconImage(ImageIO.read(MurdleGridGenerator.class.getResourceAsStream("murdle_logo.png")));
		} catch (IOException e) {
			System.out.println("Could not load icon image.");
			System.out.println(e.getMessage());
		}
		setJMenuBar(new JMenuBar());
	
		// Add menu for grid properties
		
		gridMenu = new GridMenu();
		gridMenu.addElementsActionListener(e -> createGrid());
		gridMenu.addMotivesActionListener(e -> createGrid());
		getJMenuBar().add(gridMenu);
		
		// Add menu for editing label text
		
		EditMenu editMenu = new EditMenu();
		for (int i = 0; i < CATEGORIES.length; i++) {
			final int index = i;
			editMenu.getItem(i).addActionListener(e -> edit(index));
		}
		getJMenuBar().add(editMenu);
		
		// Add a menu for clearing all or portions of the panel
		
		ClearMenu clearMenu = new ClearMenu();
		clearMenu.getItem(0).addActionListener(e -> grid.clearQuestion());
		clearMenu.getItem(1).addActionListener(e -> grid.clearAll());
		getJMenuBar().add(clearMenu);
		
		// Add a menu for dealing with select Exhibits from Vol. 1 & 2
		
		ExhibitMenu exhibitMenu = new ExhibitMenu();
		getJMenuBar().add(exhibitMenu);
		
		// Create a dialog for editing label text
		
		editorDialog = new EditorDialog(this);
		WindowAdapter w = new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				updateLabels();
			}
		};
		editorDialog.addWindowListener(w);
		
		// Create a Murdle grid to act as the content pane
		
		createGrid();
		
		// Show frame

		setVisible(true);
		
	}

	public void createGrid() {
		
		int elements = gridMenu.getElements();
		if (elements == -1) elements = MIN_ELEMENTS;
		int categories = gridMenu.getCategories();
		String[][] gridData = new String[categories][elements];
		for (int i = 0; i < categories; i++) {
			for (int j = 0; j < elements; j++) {
				gridData[i][j] = data[i][j];
			}
		}
		grid = new MurdleGrid(gridData);
		setContentPane(grid);
		pack();
		setLocationRelativeTo(null);
		
	}

	private void edit(int index) {
	
		editorDialog.edit(index,gridMenu.getElements(),data[index]);
		
	}
	
	public static void main(String[] args) {
		
		MurdleGridGenerator mgg = new MurdleGridGenerator();
		SwingUtilities.invokeLater(() -> mgg.createAndShowGui());
		
	}
	
	private void updateLabels() {
		
		if (editorDialog.isOK()) {
			int category = editorDialog.getCategory();
			String[] newValues = editorDialog.getValues();
			for (int i = 0; i < newValues.length; i++) {
				data[category][i] = newValues[i];
			}
			createGrid();
		}
		
	}

}

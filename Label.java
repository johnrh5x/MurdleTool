/* The Label class is for row and column labels in the Murdle grid.
 * It extends the JLabel class so that text can be displayed vertically
 * (that is, rotated 90 degrees) rather than horizontally when the
 * Label is used for a column label. */

package john.game.murdle;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import javax.swing.JLabel;

public class Label extends JLabel implements Constants {

	// Fields
	
	private boolean vertical; // If true, rotate text 90 degrees clockwise
	private int     x, y;     // Internal text coordinates
	private double  rx, ry;   // Internal rotation coordinates
	
	// Constructor
	
	public Label(String text, boolean vertical) {
		
		super(text);
		this.vertical = vertical;
        
        // Get glyph bounds
			
		FontMetrics fm = getFontMetrics(getFont());
		GlyphVector gv = getFont().createGlyphVector(fm.getFontRenderContext(),text);
		Rectangle2D r = gv.getVisualBounds();
		x = (int)r.getX();          // Horizontal offset from 0
		y = (int)r.getY();          // Vertical offset from 0
		int w = (int)r.getWidth();  // Glpyh width
	    int h = (int)r.getHeight(); // Glyph height
			
		// Set preferred size
		
		setFont(BOLD_FONT);
		if (vertical) {	
			setPreferredSize(new Dimension(h + 2*DEFAULT_INSET, w + 2*DEFAULT_INSET));
		} else {
			setPreferredSize(new Dimension(w + 2*DEFAULT_INSET, h + 2*DEFAULT_INSET));
		}
		setFont(PLAIN_FONT);
			
		// Set internal text drawing and rotation coordinates
			
		x = (getPreferredSize().width - w)/2 - x;  // Center text horizontally
		y = (getPreferredSize().height - h)/2 - y; // Center text vertically
		rx = 0.5*getPreferredSize().getWidth();    // Rotate around center of label
		ry = 0.5*getPreferredSize().getHeight();
		
	}
	
	// Methods

	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2D = (Graphics2D)g;
		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		if (vertical) g2D.rotate(0.5*Math.PI,rx,ry);
		g2D.drawString(getText(),x,y);
		
	}

}

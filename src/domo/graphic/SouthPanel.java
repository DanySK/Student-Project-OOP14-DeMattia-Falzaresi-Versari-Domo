package domo.graphic;


import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class add the help string (in this project in the bottom).
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public class SouthPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4847808262714176820L;
	private final JLabel helpLabel = new JLabel(); 
	
	/**
	 * Create the panel.
	 */
	public SouthPanel() {
		super(new FlowLayout(10,10,FlowLayout.LEADING));
		helpLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
		//helpLabel.setSize(new Dimension(10, this.mainFrame.getWidth()));
		helpLabel.setText("Help: ...");
		this.add(helpLabel);
		
	}
	
	/**
	 * set the string text.
	 * @param text the text
	 */
	public void setText(final String text) {
		this.helpLabel.setText(text);
	}
}

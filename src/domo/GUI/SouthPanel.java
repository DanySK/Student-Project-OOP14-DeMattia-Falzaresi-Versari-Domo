package domo.GUI;


import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SouthPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4847808262714176820L;
	private final JLabel helpLabel = new JLabel(); 
	
	public SouthPanel() {
		super(new FlowLayout(10,10,FlowLayout.LEADING));
		helpLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
		//helpLabel.setSize(new Dimension(10, this.mainFrame.getWidth()));
		helpLabel.setText("Help: ...");
		this.add(helpLabel);
		
	}
	
	public void setText(String text) {
		this.helpLabel.setText(text);
	}
}

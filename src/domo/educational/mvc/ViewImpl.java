package domo.educational.mvc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public class ViewImpl extends JFrame implements ViewInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5382453651359158867L;

	private AbstractObserverInterface observer;
	private final JTextField txtchangeText = new JTextField("Insert Text Here...");
	private final JLabel lblTheText = new JLabel("... no text ... :(");
	private final JButton btnChangeText = new JButton("Change text!!!");
	
	
	/**
	 * 
	 * @param title the JFrame title
	 */
	public ViewImpl(final String title) {
		super(title);
		final JPanel panel = new JPanel(new GridLayout(3, 1));
		this.add(panel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnChangeText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (observer != null) {
					observer.textChangedFromView(txtchangeText.getText());
				}
				
			}
		});
		
		lblTheText.setBorder(BorderFactory.createLineBorder(Color.black));
		txtchangeText.setBorder(BorderFactory.createLineBorder(Color.black));
		btnChangeText.setBorder(BorderFactory.createLineBorder(Color.black));
	
		panel.add(lblTheText);
		panel.add(txtchangeText);
		panel.add(btnChangeText);
		
		this.setVisible(true);
		
		final int width = Math.max(Math.max(lblTheText.getWidth(), btnChangeText.getWidth()), txtchangeText.getWidth());
		final int height = Math.max(Math.max(lblTheText.getHeight(), btnChangeText.getHeight()), txtchangeText.getHeight());
		
		this.setMinimumSize(new Dimension(width, height));
		this.setSize(this.getMinimumSize());
		this.setMaximumSize(new Dimension(width * 2, height * 2));
	}


	/**
	 * 
	 * @param text -
	 */
	public void updateView(final String text) {
		this.lblTheText.setText(text);
	}
	
	/**
	 * @param tObserver - 
	 */
	public void setObserver(final AbstractObserverInterface tObserver) {
		this.observer = tObserver;
	}
}

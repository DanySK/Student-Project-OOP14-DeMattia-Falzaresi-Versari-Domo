package domo.GUI;

import java.awt.Dimension;
import java.awt.MenuBar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class FlatGUI {

	private JFrame mainFrame = new JFrame();
	private JMenuBar menuBar = new JMenuBar();
	
	private double xScale = 0.7;
	private double yScale = 0.7;
	
	public FlatGUI(String title) {
		if (System.getProperty("os.name").contains("Mac")) {
			  System.setProperty("apple.laf.useScreenMenuBar", "true");
			}
		
		mainFrame.setTitle(title);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		double width 	= Toolkit.getDefaultToolkit().getScreenSize().getWidth() * xScale;
		double height 	= Toolkit.getDefaultToolkit().getScreenSize().getHeight() * yScale;
		mainFrame.setSize(new Dimension((int)width,(int)height));
		
		JMenu menu = new JMenu("File");
		//menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("");
		JMenuItem menuItem = new JMenuItem("A text-only menu item",
                KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("premuto ITEM");
				
			}
		});
		menu.add(menuItem);

		
		menuBar.add(menu);
		
		
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setVisible(true);
	}
}

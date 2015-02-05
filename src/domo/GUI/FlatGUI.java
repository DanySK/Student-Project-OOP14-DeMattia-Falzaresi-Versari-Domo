package domo.GUI;

import java.awt.Dimension;
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
		
		
		//Implementazione della barra menu'
		JMenu menuFile = new JMenu("File");
		//nuovo oggetto menu
		JMenuItem menuNew = new JMenuItem("New",
                KeyEvent.VK_N);
		menuNew.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("premuto New");
			}
		});
		menuFile.add(menuNew);
		
		JMenuItem menuOpen = new JMenuItem("Open",
                KeyEvent.VK_O);
		menuOpen.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_O, ActionEvent.ALT_MASK));
		menuOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("premuto Open");
			}
		});
		menuFile.add(menuOpen);
		
		JMenuItem menuClose = new JMenuItem("Close",
                KeyEvent.VK_Q);
		menuClose.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		menuClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FlatGUI.this.mainFrame.dispose();
			}
		});
		menuFile.add(menuClose);
		menuBar.add(menuFile);
		
		JMenu menuInsert = new JMenu("Insert");
		JMenuItem menuAddRoom = new JMenuItem("Insert New Room",
                KeyEvent.VK_R);
		menuAddRoom.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_R, ActionEvent.ALT_MASK));
		menuAddRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		menuInsert.add(menuAddRoom);
		menuBar.add(menuInsert);
		
		
		
		
		
		
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setVisible(true);
	}
}

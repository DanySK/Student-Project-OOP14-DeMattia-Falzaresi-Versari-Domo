package domo.educational.gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;

import domo.graphic.ImageView;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This Class create a simple interface using the "javax.swing" library
 * this example use some of swing object
 * - JFrame is the main object of a Gui and all the elements need to be added to it
 * - JPanel is a panel added to the JFrame, panels are useful when the content of the Gui need to be organized
 * - JButton is a button, in our case I've added to JButton an image to make the Gui more "friendly"
 * 
 */
public class ViewGUI {
	private static final String IMAGE_ROOT_FOLDER = "res";
	private static final String SYSTEM_SEPARATOR = System.getProperty("file.separator").toString();
	private final JFrame myFrame;
	private final JPanel mainPanel;
	private JPanel northPanel;
	
	/**
	 * This is the Gui constructor, for a easier comprehension the most hard panel construction is in the private 
	 * method "createNorthPanel".
	 */
	public ViewGUI() {
		myFrame = new JFrame();
		
		/* the main panel is created with a "Border Layout"
		 * this layout organize all the elements in the panel with cardinal regions North,South,East,West,Center
		 */
		mainPanel = new JPanel(new BorderLayout());
		
		/* Set Title gives the possibility to set the Window name */
		myFrame.setTitle("Domotic Application");
		
		/* Default closing operation is important because tells what's happen
		 * when the Gui is closed (if not correctly configured a JVM instance may remain active)
		 */
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		 * This method is useful to automatically set the windows dimension so the interface when opened
		 * will fit the screen size (in our case screen size /2) this is a good choice is the application
		 * must work in different size screens
		 */
		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		myFrame.setSize(screen.width / 2, screen.height / 2);
		myFrame.setMinimumSize(myFrame.getSize());
		
		createNorthPanel();
		setImage();
		/* All the panels are added to the Frame */
		myFrame.add(northPanel, BorderLayout.NORTH);
		myFrame.add(mainPanel, BorderLayout.CENTER);
		
		/* Set Visible make the frame visible */
		myFrame.setVisible(true);
		
	}
	
	/**
	 * this Private method draw the our north panel where all the interactive button are inserted.
	 */
	private void createNorthPanel() {
		
		/* All the button icons are created */
		final ImageIcon imgNew = new ImageIcon(IMAGE_ROOT_FOLDER + SYSTEM_SEPARATOR + "new.png");
		final ImageIcon imgOpen = new ImageIcon(IMAGE_ROOT_FOLDER + SYSTEM_SEPARATOR + "open.png");
		final ImageIcon imgSave = new ImageIcon(IMAGE_ROOT_FOLDER + SYSTEM_SEPARATOR + "save.jpg");
		
		/* 3 new Jbutton are created and an image is set to all of them */
		final JButton btnNew = new JButton(imgNew);
		final JButton btnOpen = new JButton(imgOpen);
		final JButton btnSave = new JButton(imgSave);
		
		/* North panel is created with a different layout
		 * the "FlowLayout" put all the elements in a row in this case starting from the left side of the panel
		 * with 1 pixel between the components 
		 */
		northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
		northPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		
		/* all buttons are added to the panel */
		northPanel.add(btnNew);
		northPanel.add(btnOpen);
		northPanel.add(btnSave);
		
		/*
		 * The row below indicate the "ActionListener" this method indicate what happen when the button is pressed
		 * in our case a MessageDialog will popup 
		 */
		btnNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				JOptionPane.showMessageDialog(myFrame, "New Pressed");
			}
		});
		
		btnOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				JOptionPane.showMessageDialog(myFrame, "Open Pressed");
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				JOptionPane.showMessageDialog(myFrame, "Save Pressed");
			}
		});
	}
	/**
	 * This Private method open a previously decided image and draw it in the background of the application
	 */
	private void setImage() {
		ImageView bgImage;
		BufferedImage imageBuf;
		try {
			imageBuf = ImageIO.read(new File(IMAGE_ROOT_FOLDER + SYSTEM_SEPARATOR + "domo.jpg"));
			bgImage = new ImageView(imageBuf, this.myFrame.getBounds());
			this.mainPanel.add(bgImage, BorderLayout.CENTER);
		} catch (IOException e) {
			System.out.println("Unable to Load Image");
		}
		
	}

}

package domo.educational.mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

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

	private static final String IMAGE_ROOT_FOLDER = "res";
	private static final String SYSTEM_SEPARATOR = System.getProperty("file.separator").toString();
	private AbstractObserverInterface observer;
	private final JPanel mainPanel;
	private final JFrame myFrame;
	private JPanel northPanel;
	
	
	/**
	 * 
	 * @param title the JFrame title
	 */
	public ViewImpl(final String title) {
		this.myFrame = new JFrame(title);
		mainPanel = new JPanel(new BorderLayout());
		this.add(mainPanel);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		myFrame.setSize(screen.width / 2, screen.height / 2);
		myFrame.setMinimumSize(myFrame.getSize());
		
		createNorthPanel();
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
				if (observer != null) {
					final String imgAddress = ViewImpl.this.openFile(new FileNameExtensionFilter("Image file", "jpg", "jpeg", "png", "bmp", "gif"));
					ViewImpl.this.setImage(imgAddress);
					observer.newProject(imgAddress);
				}
				
			}
		});
		
		btnOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (observer != null) {
					final String resAdd = ViewImpl.this.openFile(new FileNameExtensionFilter("Txt file", "txt"));
					String resImaAdd = observer.openProject(resAdd);
					if (resImaAdd != null) {
						setImage(resImaAdd);
						JOptionPane.showMessageDialog(myFrame, "Restore Completed!");
					} else {
						JOptionPane.showMessageDialog(myFrame, "Error in restore procedure\n check console for errors!");
					}
				}
				
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (observer != null) {
					final String backupAdd = ViewImpl.this.saveFile(new FileNameExtensionFilter("Txt file", "txt"));
					observer.saveProject(backupAdd+".txt");
					JOptionPane.showMessageDialog(myFrame,"Backup Done!");
				}
				
			}
		});
	}
	
	private String openFile(final FileNameExtensionFilter filter) {
		final JFileChooser openFile = new JFileChooser();
		openFile.setFileFilter(filter);
		final int result = openFile.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			return openFile.getSelectedFile().getPath();
		}
		return null;
	}
	
	private String saveFile(final FileNameExtensionFilter filter) {
		final JFileChooser openFile = new JFileChooser();
		openFile.setFileFilter(filter);
		final int result = openFile.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			return openFile.getSelectedFile().getPath();
		}
		return null;
	}
	/**
	 * This Private method open a previously decided image and draw it in the background of the application
	 */
	private void setImage(String imageFile) {
		ImageView imageJp;
		Dimension dim = new Dimension(mainPanel.getWidth(), mainPanel.getHeight());
		imageJp = new ImageView(imageFile,dim);
		this.mainPanel.add(imageJp, BorderLayout.CENTER);
		myFrame.repaint();
		
	}

	/**
	 * 
	 * @param text -
	 */
	public void updateView(final String text) {
		//this.lblTheText.setText(text);
	}
	
	/**
	 * @param tObserver - 
	 */
	public void setObserver(final AbstractObserverInterface tObserver) {
		this.observer = tObserver;
	}
}

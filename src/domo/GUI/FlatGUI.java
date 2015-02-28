package domo.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

import org.omg.CORBA.TypeCodePackage.Bounds;



public class FlatGUI {

	private JFrame mainFrame = new JFrame();
	private JMenuBar menuBar = new JMenuBar();
	private final List<?> roomContainer = new ArrayList<>();
	private final double xScale = 1;
	private final double yScale = 1;
	private final String USER_HOME_FOLDER = System.getProperty("user.home").toString();
	private final String SISTEM_SEPARATOR = System.getProperty("file.separator").toString();
	//private final Flat a = null;
	
	
	public FlatGUI(String title) {
		
		mainFrame = new JFrame();
		
		mainFrame.setTitle(title);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		double width 	= Toolkit.getDefaultToolkit().getScreenSize().getWidth() * xScale;
		double height 	= Toolkit.getDefaultToolkit().getScreenSize().getHeight() * yScale;
		mainFrame.setSize(new Dimension((int) width, (int) height));
		
		
		//Implementazione della barra menu'
		JMenu menuFile = new JMenu("File");
		//nuovo oggetto menu
		JMenuItem menuNew = new JMenuItem("New",
                KeyEvent.VK_N);
		menuNew.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
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
			public void actionPerformed(final ActionEvent e) {
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
			public void actionPerformed(final ActionEvent e) {
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
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					FlatGUI.this.addFile();
				}catch(IOException er){
					System.out.println("ERRORE APERTURA FILE");
				}
			}
		});
		menuInsert.add(menuAddRoom);
		menuBar.add(menuInsert);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainFrame.setContentPane(mainPanel);
		
		JPanel leftPanel = new JPanel(new GridLayout(5, 1));
		mainPanel.add(leftPanel, BorderLayout.WEST);
		
		for (int i = 0; i < 5; i++){
			JButton a = new JButton("n " + i);
			leftPanel.add(a);
		}
		try {
			
			BufferedImage myPicture = ImageIO.read(getClass().getResource(SISTEM_SEPARATOR + "bgDomo_h.png"));			
			
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			mainFrame.getContentPane().add(picLabel, BorderLayout.CENTER);
		}catch(IOException ex){

		}



		mainFrame.setJMenuBar(menuBar);
		mainFrame.setVisible(true);
	}
	
	
	private void addFile() throws IOException {
		JFileChooser openFile = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("IMAGE FILES", "jpg", "image");
		openFile.setFileFilter(filter);
		int result = openFile.showOpenDialog(mainFrame);
		if(result == JFileChooser.APPROVE_OPTION){
			
			BufferedImage image = ImageIO.read(new File(openFile.getSelectedFile().getPath()));
			ImageIcon icon = new ImageIcon(image);
	        JLabel label = new JLabel(icon);
	        label.setSize(new Dimension(image.getWidth(), image.getHeight()));
			//JImageView label = new JImageView (openFile.getSelectedFile().getPath());
			//System.out.println(""+openFile.getSelectedFile().getPath());
	        //label.setBounds(new Rectangle(10, 30, 750, 500));
	        
	        mainFrame.getContentPane().add(label, BorderLayout.CENTER);
	        mainFrame.repaint();
		}
		
	}
	private class JImageView extends JLabel {
		
		
		public JImageView (String imagePath) throws IOException{
			
			BufferedImage image = ImageIO.read(new File(imagePath));
			ImageIcon icon = new ImageIcon(image);
			this.setIcon(icon);
			this.repaint();
		}
		
		public JImageView (String imagePath, Rectangle bounds) throws IOException{
			super(imagePath);
			BufferedImage t = ImageIO.read(new File(imagePath));
			//ridimensionamento
			BufferedImage scaledT = (BufferedImage) t.getScaledInstance(bounds.width, bounds.height, Image.SCALE_DEFAULT);
			ImageIcon newImage = new ImageIcon(scaledT);
			this.setBounds(bounds);
			this.setIcon(newImage);
			this.repaint();
		}
		
		public void setBounds(Rectangle bounds) {
			this.setBounds(bounds);
			//prendo l'immagine già presente nella label
			ImageIcon resizedIcon = (ImageIcon)this.getIcon();
			Image tImage = resizedIcon.getImage();
			BufferedImage t = (BufferedImage) tImage;
			//ridimensionamento
			BufferedImage scaledT = (BufferedImage) t.getScaledInstance(bounds.width, bounds.height, Image.SCALE_DEFAULT);
			ImageIcon newImage = new ImageIcon(scaledT);
			this.setBounds(bounds);
			this.setIcon(newImage);
		}
		
		public void setPosition(Point position) {
			Rectangle newBounds = this.getBounds();
			newBounds.x = position.x;
			newBounds.y = position.y;
		}
	}
	
}

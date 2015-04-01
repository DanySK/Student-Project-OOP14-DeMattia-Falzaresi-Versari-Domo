package domo.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import domo.general.Flat;
import domo.GUI.GUIAbstractObserver;
import domo.GUI.ImageView;
import domo.GUI.ImageView.ColorFilter;


public class GUIFlatImpl implements GUIFlat, ActionListener {

	private GUIAbstractObserver controller;
	
	private JFrame mainFrame = new JFrame();
	private JPanel mainPanel;
	private JLayeredPane centerPane;
	
	private final JMenuBar menuBar = new JMenuBar();
	
	private final List<?> roomContainer = new ArrayList<>();
	
	private final double xScale = 0.7;
	private final double yScale = 0.7;
	private final String USER_HOME_FOLDER = System.getProperty("user.home").toString();
	private final String SISTEM_SEPARATOR = System.getProperty("file.separator").toString();
	
	public GUIFlatImpl(final String title) {	
		mainFrame = new JFrame();
		mainFrame.setTitle(title);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		double width 	= Toolkit.getDefaultToolkit().getScreenSize().getWidth() * xScale;
		double height 	= Toolkit.getDefaultToolkit().getScreenSize().getHeight() * yScale;
		double x = (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - width) / 2; 
		double y = (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - height) / 2; 
		mainFrame.setSize(new Dimension((int) width, (int) height));
		mainFrame.setBounds((int) x, (int) y, (int) width, (int) height);
		
		mainPanel = new JPanel(new BorderLayout());
		//mainPanel.setBackground(Color.darkGray);
		mainFrame.setContentPane(mainPanel);
		
		centerPane = new JLayeredPane();
		centerPane.setBorder(BorderFactory.createLineBorder(Color.black));
		//centerPane.setOpaque(true);
		//centerPane.setBackground(Color.CYAN);
		
		centerPane.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(final MouseEvent e) {				
				//System.out.println("Move");
			}
			
			@Override
			public void mouseDragged(final MouseEvent e) {
				//System.out.println("Drag");
			}
		});
		
		mainPanel.add(centerPane, BorderLayout.CENTER);
		
		createJMenu();
		createNorthMenu();
		
		JPanel leftPanel = new JPanel(new GridLayout(5, 1));
		mainPanel.add(leftPanel, BorderLayout.WEST);
		
		mainFrame.setVisible(true);
	}
	
	private void createJMenu() {
		//Implementazione della barra menu'
		JMenu menuFile = new JMenu("File");
		//nuovo oggetto menu
		JMenuItem menuNew = new JMenuItem("New", KeyEvent.VK_N);
		menuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("premuto New");
				
				newFile();
				if (controller != null) {
					controller.newProject();
				}
			}
		});
		menuFile.add(menuNew);
		
		JMenuItem menuOpen = new JMenuItem("Open", KeyEvent.VK_O);
		menuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
		menuOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {

				System.out.println("Premuto Open");
				String pathFile = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("DOMO PROJECT FILE", "dprj", "dprj"));
				
				if (controller != null) {
					controller.load(pathFile);
				}
			}
		});
		menuFile.add(menuOpen);
		
		JMenuItem menuClose = new JMenuItem("Close", KeyEvent.VK_Q);
		menuClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		menuClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {

				System.out.println("Premuto close");
				if (controller != null) {
					controller.closeProgram();
				}
				GUIFlatImpl.this.mainFrame.dispose();
			}
		});
		menuFile.add(menuClose);
		menuBar.add(menuFile);
		
		JMenu menuInsert = new JMenu("Insert");
		JMenuItem menuAddRoom = new JMenuItem("Insert New Room", KeyEvent.VK_R);
		menuAddRoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		menuAddRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Premuto Insert new Room");
				createRoomFrame();
				//newRoom();
			}
		});
		menuInsert.add(menuAddRoom);
		menuBar.add(menuInsert);
		mainFrame.setJMenuBar(menuBar);
	}
	
	private void createNorthMenu() {
		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 100, 1));
		
		JButton btnNew = new JButton("New");
		JButton btnOpen = new JButton("Open");
		JButton btnSave = new JButton("Save");
		JButton btnAddRoom = new JButton("Add Room");
		JButton btnAddSensor = new JButton("AddSensor");
		
		northPanel.add(btnNew);
		northPanel.add(btnOpen);
		northPanel.add(btnSave);
		northPanel.add(btnAddRoom);
		northPanel.add(btnAddSensor);
		
		mainPanel.add(northPanel, BorderLayout.NORTH);
		
	}
	
	private String openFile(final FileNameExtensionFilter filter) {
		JFileChooser openFile = new JFileChooser();
		openFile.setFileFilter(filter);
		int result = openFile.showOpenDialog(mainFrame);
		if (result == JFileChooser.APPROVE_OPTION) {
			return openFile.getSelectedFile().getPath();
		}
		return null;
	}

	private void newRoom() {
		
		if (controller != null) {
			controller.roomAdded(null);
			
		}
	}
	
	private void createRoomFrame() {
		
		JFrame addRoomFrame = new JFrame("New Room");
		JTextField txtname = new JTextField(10);
		JLabel lblNome = new JLabel("nome stanza:");
		lblNome.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
		JButton btnOk = new JButton("Ok");
		JButton btnCancel = new JButton("Cancel");
		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		panel.add(lblNome);
		panel.add(txtname);
		panel.add(btnOk);
		panel.add(btnCancel);
		
		addRoomFrame.setContentPane(panel);
		addRoomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		addRoomFrame.setVisible(true);
		addRoomFrame.setMinimumSize(new Dimension(addRoomFrame.getPreferredSize().width, addRoomFrame.getPreferredSize().height));
		addRoomFrame.setMaximumSize(new Dimension(addRoomFrame.getPreferredSize().width, addRoomFrame.getPreferredSize().height));
	}
	
	private void newFile() {
		String imgAddress = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("JPEG file", "jpg", "jpeg"));
		if (imgAddress != null) {
			System.out.println("" + imgAddress);
			try {
				BufferedImage imageBuf = ImageIO.read(new File(imgAddress));
				ImageView imageV = new ImageView(imageBuf, centerPane.getBounds());
				
		        centerPane.add(imageV, 0);
				mainFrame.repaint();
		        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	
	
	

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRoom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFlat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSensor() {
		// TODO Auto-generated method stub
		
	}

	
	
}

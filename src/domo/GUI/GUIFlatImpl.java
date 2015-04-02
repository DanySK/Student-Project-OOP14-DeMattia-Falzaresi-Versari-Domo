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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
import javax.swing.JOptionPane;
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
	
	private final List<ImageView> imageViewList = new ArrayList<>(); 
	private ImageView mainImage;
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
		
		

		mainFrame.getRootPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(final ComponentEvent e) {
                if (imageViewList.size() > 0) {
                	imageViewList.forEach(a->{
                		//a.setAspectFillToParent(centerPane.getBounds());
                	});
                }
            }
        });
		
		createJMenu();
		createNorthMenu();
		
		JPanel leftPanel = new JPanel(new GridLayout(5, 1));
		mainPanel.add(leftPanel, BorderLayout.WEST);
		
		mainPanel.add(centerPane, BorderLayout.CENTER);
		
		
		
		
		
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
		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
		
		//ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		//System.out.println(GUIFlatImpl.class.getResource("/"));
		ImageIcon imgNew = new ImageIcon("res/new.png");
		ImageIcon imgOpen = new ImageIcon("res/open.png");
		ImageIcon imgSave = new ImageIcon("res/save.jpg");
		ImageIcon imgAddRoom = new ImageIcon("res/addRoom.png");
		ImageIcon imgAddSensor = new ImageIcon("res/addSensor.png");
		ImageIcon imgTrash = new ImageIcon("res/trash.png");
		
		JButton btnNew = new JButton(imgNew);
		JButton btnOpen = new JButton(imgOpen);
		JButton btnSave = new JButton(imgSave);
		JButton btnAddRoom = new JButton(imgAddRoom);
		JButton btnAddSensor = new JButton(imgAddSensor);
		JButton btnTrash = new JButton(imgTrash);
		
		btnNew.setSize(new Dimension(50, 50));
		btnOpen.setSize(new Dimension(50, 50));
		btnSave.setSize(new Dimension(50, 50));
		btnAddRoom.setSize(new Dimension(50, 50));
		btnAddSensor.setSize(new Dimension(50, 50));
		btnTrash.setSize(new Dimension(50, 50));
		
		btnNew.setToolTipText("New Project");
		btnOpen.setToolTipText("Open Project");
		btnSave.setToolTipText("Save Project");
		btnAddRoom.setToolTipText("Add Sensor To Room");
		btnAddSensor.setToolTipText("Insert Sensor");
		btnTrash.setToolTipText("Delete Sensor/s");
		
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIFlatImpl.this.newFile();
			}
		});
		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {			
				String pathFile = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("DOMO PROJECT FILE", "dprj", "dprj"));				
			}
		});
		
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		
		btnAddRoom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIFlatImpl.this.createRoomFrame();
			}
		});
		
		btnAddSensor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ImageView t = new ImageView("res/addSensor.png");
					imageViewList.add(t);
					t.setMouseEnabled(true);
					t.setLocation(new Point(centerPane.getWidth() / 2, centerPane.getHeight() / 2));
					centerPane.add(t, 0);
					centerPane.moveToFront(t);
					
					mainFrame.repaint();
					
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnTrash.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList <ImageView> toRemove = new ArrayList<ImageView>(imageViewList.size());
				for (ImageView t : imageViewList) {
					if (t.getIsSelect()) {
						toRemove.add(t);
					}
				}
				for (ImageView t : toRemove) {
					if (t.getIsSelect()) {
						centerPane.remove(t);
					}
				}
				imageViewList.removeAll(toRemove);
				centerPane.repaint();
			}
		});

		northPanel.add(btnNew);
		northPanel.add(btnOpen);
		northPanel.add(btnSave);
		northPanel.add(btnAddRoom);
		northPanel.add(btnAddSensor);
		northPanel.add(btnTrash);

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

	private void newFile() {
		if (mainImage != null) {
			int choose = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the current project. All changes will be lost!", "ATTENTION!", JOptionPane.OK_CANCEL_OPTION);
			if(choose == 0){
				String imgAddress = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("JPEG file", "jpg", "jpeg"));
				if (imgAddress != null) {
					try {
						BufferedImage imageBuf = ImageIO.read(new File(imgAddress));
						centerPane.remove(mainImage);
						mainImage = new ImageView(imageBuf, centerPane.getBounds());
						centerPane.add(mainImage, 0);
						mainFrame.repaint();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			String imgAddress = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("JPEG file", "jpg", "jpeg"));
			if (imgAddress != null) {
				try {
					BufferedImage imageBuf = ImageIO.read(new File(imgAddress));
					mainImage = new ImageView(imageBuf, centerPane.getBounds());
					centerPane.add(mainImage, 0);
					mainFrame.repaint();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void createRoomFrame() {

		JFrame addRoomFrame = new JFrame("New Room");
		addRoomFrame.setLocation(new Point(this.mainFrame.getX() + 10, this.mainFrame.getY()+ 10));
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
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRoomFrame.dispose();
			}
		});
		
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (controller != null) {
					controller.roomAdded(null);
				}
				addRoomFrame.dispose();
			}
		});

		addRoomFrame.setContentPane(panel);
		addRoomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		addRoomFrame.setVisible(true);
		addRoomFrame.setMinimumSize(new Dimension(addRoomFrame.getPreferredSize().width, addRoomFrame.getPreferredSize().height));
		addRoomFrame.setMaximumSize(new Dimension(addRoomFrame.getPreferredSize().width, addRoomFrame.getPreferredSize().height));
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

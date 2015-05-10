package domo.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import domo.devices.Sensor;
import domo.general.Flat;
import domo.general.Room;



/**
 * 
 * @author Simone simone.demattia@studio.unibo.it
 */
public class GUIFlatImpl implements GUIFlat {

	/**
	 * object controller
	 */
	private GUIAbstractObserver controller;

	private JFrame mainFrame = new JFrame();
	private JPanel mainPanel;

	/**
	 * Object that heandle the working area 
	 * background image, sensor, move, resize, color ...
	 */
	private GUIWorkingArea workingArea;

	private final JMenuBar menuBar = new JMenuBar();

	/**
	 * the type sensor list
	 */
	private List <Map <String, String>> sensorTypeList;
	
	/**
	 * The project room list
	 */
	private List <Room> roomList;

	/**
	 * bottom panel object
	 */
	private SouthPanel southPanel;
	/**
	 * left panel object
	 */
	private WestPanel westPanel;
	
	/**
	 * background image path
	 */
	private String projectImagePath;
	
	/**
	 * Factor scale to present the max width frame
	 */
	private static final double W_SCREEN_MAX_SCALE = 0.7;
	/**
	 * Factor scale to present the max height frame
	 */
	private static final double H_SCREEN_MAX_SCALE = 0.7;
	/**
	 * Factor scale to present the min width frame
	 */
	private static final double W_SCREEN_MIN_SCALE = 0.1;
	/**
	 * Factor scale to present the min height frame
	 */
	private static final double H_SCREEN_MIN_SCALE = 0.18;

	/**
	 * Standard icon squere dimension
	 */
	private static final int BUTTON_ICON_DIMENSION = 50;


	//private final String USER_HOME_FOLDER = System.getProperty("user.home").toString();
	/**
	 * system separator
	 */
	private static final String SYSTEM_SEPARATOR = System.getProperty("file.separator").toString();


	/**
	 * 
	 * @param title the Frame title (normally the project name)
	 * @param sensorsTypes Sensor type list. This need to create top menu button
	 */
	public GUIFlatImpl(final String title, List<Map <String, String>> sensorsTypes) {	
		mainFrame = new JFrame();
		mainFrame.setTitle(title);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		double maxWidth 	= Toolkit.getDefaultToolkit().getScreenSize().getWidth() * W_SCREEN_MAX_SCALE;
		double maxHeight 	= Toolkit.getDefaultToolkit().getScreenSize().getHeight() * H_SCREEN_MAX_SCALE;
		double minWidth 	= Toolkit.getDefaultToolkit().getScreenSize().getWidth() * W_SCREEN_MIN_SCALE;
		double minHeight 	= Toolkit.getDefaultToolkit().getScreenSize().getHeight() * H_SCREEN_MIN_SCALE;

		double x = (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - maxWidth) / 2; 
		double y = (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - maxHeight) / 2; 
		mainFrame.setSize(new Dimension((int) maxWidth, (int) maxHeight));
		mainFrame.setMinimumSize(new Dimension((int) minWidth, (int) minHeight));
		mainFrame.setBounds((int) x, (int) y, (int) maxWidth, (int) maxHeight);

		mainPanel = new JPanel(new BorderLayout());
		//mainPanel.setBackground(Color.darkGray);
		mainFrame.setContentPane(mainPanel);

		workingArea = new GUIWorkingArea();

		mainFrame.getRootPane().addComponentListener(new ComponentAdapter() {
			public void componentResized(final ComponentEvent e) {
				GUIFlatImpl.this.workingArea.resize();
			}
		});

		mainPanel.add(workingArea, BorderLayout.CENTER);
		this.sensorTypeList = sensorsTypes;
		if (controller != null && controller.getRoomList() != null) {
			this.roomList = new ArrayList<>(controller.getRoomList());
		} else {
			this.roomList = new ArrayList<>();
		}
		
		
		createJMenu();
		createNorthMenu();
		
		//create the south menu of main windows (help panel)
		southPanel = new SouthPanel();
		mainFrame.add(southPanel, BorderLayout.SOUTH);
		
		//create the left panel that show rooms and sensors
		if (controller != null) {
			if(controller.getRoomList() != null && controller.getRoomList().size() > 0) {
				westPanel = new WestPanel(controller.getRoomList());
				mainFrame.add(westPanel, BorderLayout.WEST);
				
			}
		}
		
		mainFrame.setVisible(true);
	}

	/**
	 * Create the JMenu
	 */
	private void createJMenu() {
		//Implementazione della barra menu'
		JMenu menuFile = new JMenu("File");
		//nuovo oggetto menu
		JMenuItem menuNew = new JMenuItem("New", KeyEvent.VK_N);
		menuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				System.out.println("premuto New");

				GUIFlatImpl.this.newFile();
				
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
				if (GUIFlatImpl.this.controller != null) {
					GUIFlatImpl.this.openFile();
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
				if (GUIFlatImpl.this.controller != null) {
					GUIFlatImpl.this.controller.closeProgram();
				}
				GUIFlatImpl.this.mainFrame.dispose();
			}
		});
		menuFile.add(menuClose);
		menuBar.add(menuFile);

		JMenu menuEdit = new JMenu("Edit");
		JMenuItem menuRefreshSensor = new JMenuItem("Refresh Sensor List", KeyEvent.VK_F5);
		menuRefreshSensor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.ALT_MASK));
		menuRefreshSensor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.out.println("Premuto Refresh");
				if (GUIFlatImpl.this.controller != null) {
					GUIFlatImpl.this.controller.refreshSensorList();
				}
			}
		});
		menuEdit.add(menuRefreshSensor);
		menuBar.add(menuEdit);

		JMenu menuInsert = new JMenu("Insert");
		JMenuItem menuAddRoom = new JMenuItem("Add sensor to a room", KeyEvent.VK_R);
		menuAddRoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		menuAddRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.out.println("Premuto Insert new Room");
				if(workingArea.getSelectedSensor().size() > 0) {
					GUIFlatImpl.this.createRoomFrame();
				}
			}
		});
		menuInsert.add(menuAddRoom);
		menuBar.add(menuInsert);

		mainFrame.setJMenuBar(menuBar);
	}

	/**
	 * Create the north Menu
	 * 
	 */
	private void createNorthMenu() {
		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
		northPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));


		ImageIcon imgNew = new ImageIcon("res" + SYSTEM_SEPARATOR + "new.png");
		ImageIcon imgOpen = new ImageIcon("res" + SYSTEM_SEPARATOR + "open.png");
		ImageIcon imgSave = new ImageIcon("res" + SYSTEM_SEPARATOR + "save.jpg");
		ImageIcon imgAddRoom = new ImageIcon("res" + SYSTEM_SEPARATOR + "addRoom.png");


		ImageIcon imgTrash = new ImageIcon("res" + SYSTEM_SEPARATOR + "trash.png");

		JButton btnNew = new JButton(imgNew);
		JButton btnOpen = new JButton(imgOpen);
		JButton btnSave = new JButton(imgSave);
		JButton btnAddRoom = new JButton(imgAddRoom);

		JButton btnTrash = new JButton(imgTrash);

		btnNew.setSize(new Dimension(BUTTON_ICON_DIMENSION, BUTTON_ICON_DIMENSION));
		btnOpen.setSize(new Dimension(BUTTON_ICON_DIMENSION, BUTTON_ICON_DIMENSION));
		btnSave.setSize(new Dimension(BUTTON_ICON_DIMENSION, BUTTON_ICON_DIMENSION));
		btnAddRoom.setSize(new Dimension(BUTTON_ICON_DIMENSION, BUTTON_ICON_DIMENSION));
		btnTrash.setSize(new Dimension(BUTTON_ICON_DIMENSION, BUTTON_ICON_DIMENSION));

		btnNew.setToolTipText("New Project");
		btnOpen.setToolTipText("Open Project");
		btnSave.setToolTipText("Save Project");
		btnAddRoom.setToolTipText("Add Sensor To Room");
		btnTrash.setToolTipText("Delete Sensor/s");

		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				GUIFlatImpl.this.newFile();
			}
		});

		btnNew.addMouseListener(
				new MouseAdapter() {
					public void mouseEntered(final MouseEvent e) {
						southPanel.setText("Create new project");
					}

					public void mouseExited(final MouseEvent e) {
						southPanel.setText(" ");
					}
				}
				);

		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {			
				GUIFlatImpl.this.openFile();
			}
		});

		btnOpen.addMouseListener(
				new MouseAdapter() {
					public void mouseEntered(final MouseEvent e) {
						southPanel.setText("Open exist project");
					}

					public void mouseExited(final MouseEvent e) {
						southPanel.setText(" ");
					}
				}
				);

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				GUIFlatImpl.this.saveFile();
			}
		});

		btnSave.addMouseListener(
				new MouseAdapter() {
					public void mouseEntered(final MouseEvent e) {
						southPanel.setText("Save project");
					}

					public void mouseExited(final MouseEvent e) {
						southPanel.setText(" ");
					}
				}
				);

		btnAddRoom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if(workingArea.getSelectedSensor().size() > 0) {
					GUIFlatImpl.this.createRoomFrame();
				}
			}
		});

		btnAddRoom.addMouseListener(
				new MouseAdapter() {
					public void mouseEntered(final MouseEvent e) {
						southPanel.setText("Add selected Sensor to a Room");
					}

					public void mouseExited(final MouseEvent e) {
						southPanel.setText(" ");
					}
				}
				);


		btnTrash.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {

				//imageViewList.removeAll(toRemove);
				
				if(controller != null && controller.getRoomList().size() > 0) {
					
					controller.deleteSensors(workingArea.getSelectedSensor());
					
					workingArea.removeSelectSensor();
					workingArea.repaint();
					
					System.out.println("numero Room: " + controller.getRoomList().size());
					System.out.println("Sensor count: " + controller.getRoomList().get(0).getSensor().size());
					
					westPanel.refreshWestPane(controller.getRoomList());
				}
				//old style
				//centerPane.repaint();
			}
		});

		btnTrash.addMouseListener(
				new MouseAdapter() {
					public void mouseEntered(final MouseEvent e) {
						southPanel.setText("Delete selected Sensor");
					}

					public void mouseExited(final MouseEvent e) {
						southPanel.setText(" ");
					}
				}
				);

		northPanel.add(btnNew);
		northPanel.add(btnOpen);
		northPanel.add(btnSave);
		northPanel.add(btnAddRoom);
		if (sensorTypeList != null) {
			for (Map<String, String> map : sensorTypeList) {

				ImageIcon imgAddSensor = new ImageIcon(map.get("image")); 
				JButton btnAddSensor = new JButton(imgAddSensor);
				btnAddSensor.setSize(new Dimension(BUTTON_ICON_DIMENSION, BUTTON_ICON_DIMENSION));
				btnAddSensor.setToolTipText("Insert Sensor");
				btnAddSensor.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						if (controller != null) {
							Sensor newSensor = controller.addSensorWithName(map.get("name"));
							workingArea.addSensor(newSensor);
							//workingArea.addSensor(map.get("image"));
							if(controller.getRoomList() != null && controller.getRoomList().size() > 0) {
								//westPanel.refreshWestPane(controller.getRoomList());
							}
							workingArea.repaint();
						}
					}
				});
				btnAddSensor.addMouseListener(
						new MouseAdapter() {
							public void mouseEntered(final MouseEvent e) {
								southPanel.setText("Add \"" + map.get("name") + "\" Sensor to flat");
							}

							public void mouseExited(final MouseEvent e) {
								southPanel.setText(" ");
							}
						}
						);
				btnAddSensor.setName(map.get("name"));
				northPanel.add(btnAddSensor);

			}
		}
		northPanel.add(btnTrash);
		northPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.add(northPanel, BorderLayout.NORTH);

	}

	/** 
	 * Refresh the top menu
	 * Need because refresh the sensor type (add a sensor at runtime)
	 */
	public void refreshMenu() {
		BorderLayout layout = (BorderLayout) this.mainPanel.getLayout();
		mainPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
		this.createNorthMenu();
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

	/**
	 * Create a new project
	 * Heandle the controller call too (newProject())
	 */
	private void newFile() {
		if (workingArea.isSetBackground()) {
			int choose = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the current project. All changes will be lost!", "ATTENTION!", JOptionPane.OK_CANCEL_OPTION);
			if (choose == 0) {
				String imgAddress = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("Image file", "jpg", "jpeg", "png", "bmp", "gif"));
				if (imgAddress != null) {
					workingArea.setImage(imgAddress);
					this.projectImagePath = imgAddress;
					mainFrame.repaint();
					if (controller != null) {
						controller.newProject();
					}
				}
			}
		} else {
			String imgAddress = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("Image file", "jpg", "jpeg", "png", "bmp", "gif"));
			if (imgAddress != null) {
				
				workingArea.setImage(imgAddress);
				mainFrame.repaint();
				controller.newProject();
			}
		}
	}
	
	/**
	 * Open a project.
	 * Heandle the controller call too (load(pathFile))
	 */
	private void openFile() {	
		if (controller != null) {
			String pathFile = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("DOMO PROJECT FILE", "dprj"));
			if(pathFile != null) {
				controller.load(pathFile);
			} else {
				JOptionPane.showConfirmDialog(null, "Same error occur during open project...", "OPS...", JOptionPane.CANCEL_OPTION);
			}
			
		
		}
	}
	
	/**
	 * Save the current project 
	 * Heandle the controller call too (save(pathFile))
	 */
	private void saveFile() {
		if (controller != null) {
			JFileChooser openFile = new JFileChooser();
			openFile.setFileFilter(new FileNameExtensionFilter("Domo project file", "dprj"));
			//openFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = openFile.showSaveDialog(mainFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				controller.save(openFile.getSelectedFile().getPath() + ".dprj", this.projectImagePath);
			}
		}
	}

	/**
	 * Create the frame that add to a exist o new room a group of sensors (or only one sensor)
	 */
	private void createRoomFrame() {

		JFrame addRoomFrame = new JFrame("Add Sensor to Room");
		addRoomFrame.setLocation(new Point(this.mainFrame.getX() + 10, this.mainFrame.getY() + 10));

		JComboBox<String> cmbRoomName;
		if (controller != null ) {
			if (controller.getRoomList() != null && controller.getRoomList().size() > 0) {
				HashMap<String, Integer> roomsNames = new HashMap<>();
				roomList = new ArrayList<>(controller.getRoomList());
				String[] t = new String[controller.getRoomList().size() + 1];
				t[0] = " ";
				int index = 1;
				for (Room r : roomList) {
					roomsNames.put(r.getName(), r.getId());
					t[index] = r.getName();
					index++;
				}
				cmbRoomName = new JComboBox<>(t);
			}else {
				cmbRoomName = new JComboBox<String>();
			}
			
		}else {
			cmbRoomName = new JComboBox<String>();
			
		}
		
		cmbRoomName.setEditable(true);

		JLabel lblNome = new JLabel("nome stanza:");
		lblNome.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
		JButton btnOk = new JButton("Ok");
		JButton btnCancel = new JButton("Cancel");
		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		panel.add(lblNome);
		panel.add(cmbRoomName);
		panel.add(btnOk);
		panel.add(btnCancel);
		cmbRoomName.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent e) {
				System.out.println(e.getStateChange());
				if (cmbRoomName.getSelectedIndex() == 0) {
					cmbRoomName.setEditable(true);
				} else {
					cmbRoomName.setEditable(false);
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				addRoomFrame.dispose();
			}
		});

		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (controller != null) {
					System.out.println(cmbRoomName.getSelectedIndex());
					if (cmbRoomName.getSelectedIndex() <= 0) {
						if(cmbRoomName.getSelectedItem() != null) {
							controller.addRoomWithNameAndSensors((String) cmbRoomName.getSelectedItem(), workingArea.getSelectedSensor());
							if(controller.getRoomList().size() > 0) {
								if (westPanel == null) {
									westPanel = new WestPanel(controller.getRoomList());
									mainFrame.add(westPanel, BorderLayout.WEST);
								}
								westPanel.refreshWestPane(controller.getRoomList());
							}
							
							workingArea.resize();
						}
						if (controller.getRoomList() != null) {
							roomList = new ArrayList<>(controller.getRoomList());
							workingArea.resize();
						}
					} else {
						controller.addSensorToRoom(workingArea.getSelectedSensor(), roomList.get(cmbRoomName.getSelectedIndex() - 1));
					}
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

	/**
	 * Set a list of sensor in allarm state 
	 * (change left panel led color and the color filter in 
	 *  main window)
	 * @param room the sensor's room
	 * @param sensors sensors list to set in allarm
	 */
	public void setSensorsInAllarm(Room room, ArrayList<Sensor> sensors) {
		for (Sensor sen : sensors) {
			//left panel update
			westPanel.setRedLightToSensor(room, sen.getId());
			//update workingArea??
			workingArea.setInAllarmToSensor(sensors);
		}
	}
	
	/**
	 * Reset a list of sensor from in allarm state to 'not in allarm' state
	 * (change left panel led color and the color filter in 
	 *  main window)
	 * @param room the sensor's room
	 * @param sensors sensors list to set 'not in allarm'
	 */
	public void resetSensorsInAllarm(Room room, ArrayList<Sensor> sensors) {
		for (Sensor sen : sensors) {
			//left panel update
			westPanel.setGreenLightToSensor(room, sen.getId());
			//update workingArea??
			workingArea.resetAllarmToSensor(sensors);
		}
	}
	
	/**
	 * Set the observer for the Graphic Interface
	 * 
	 * @param observer the class observer
	 */
	public void setController(GUIAbstractObserver observer){
		controller = observer;	
	}


}

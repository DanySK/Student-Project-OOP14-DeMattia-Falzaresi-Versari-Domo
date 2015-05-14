package domo.graphic;

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
import java.util.Collection;
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
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 */
public class GUIFlatImpl extends JFrame implements GUIFlat {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8554427174409953147L;

	/**
	 * object controller
	 */
	private GUIAbstractInterface controller;

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

	private static final String FILE_DOMO_EXTENTION = "dprj";

	private static final String IMAGE_ROOT_FOLDER = "res";

	private static final int ADD_ROOM_FRAME_BORDER = 15;

	private static final int STANDARD_BORDER = 5;

	/**
	 * Standard icon square dimension
	 */
	private static final int BUTTON_ICON_DIMENSION = 50;

	//private final String USER_HOME_FOLDER = System.getProperty("user.home").toString();
	/**
	 * system separator
	 */
	private static final String SYSTEM_SEPARATOR = System.getProperty("file.separator").toString();

	/**
	 * The Graphic Main Frame
	 */
	//private final JFrame mainFrame = new JFrame();
	/**
	 * The main Panel with BorderLayout layout
	 */
	private final JPanel mainPanel;

	/**
	 * left panel object
	 */
	private WestPanel westPanel;

	private JPanel northPanel;

	/**
	 * bottom panel object
	 */
	private final SouthPanel southPanel;

	private final JPanel primaryNorthPanel;

	/**
	 * Object that handle the working area 
	 * background image, sensor, move, resize, color ...
	 */
	private final GUIWorkingArea workingArea;

	/**
	 * The top menu
	 */
	private final JMenuBar menuBar = new JMenuBar();

	/**
	 * the type sensor list
	 */
	private Collection<Map <String, String>> sensorTypeList;

	/**
	 * The project room list
	 */
	private List<Room> roomList;

	/**
	 * background image path
	 */
	private String projectImagePath;

	/**
	 * 
	 * @param title the Frame title (normally the project name)
	 * @param sensorsTypes Sensor type list. This need to create top menu button
	 */
	public GUIFlatImpl(final String title, final List<Map <String, String>> sensorsTypes) {	
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final double maxWidth 	= Toolkit.getDefaultToolkit().getScreenSize().getWidth() * W_SCREEN_MAX_SCALE;
		final double maxHeight 	= Toolkit.getDefaultToolkit().getScreenSize().getHeight() * H_SCREEN_MAX_SCALE;
		final double minWidth 	= Toolkit.getDefaultToolkit().getScreenSize().getWidth() * W_SCREEN_MIN_SCALE;
		final double minHeight 	= Toolkit.getDefaultToolkit().getScreenSize().getHeight() * H_SCREEN_MIN_SCALE;

		final double x = (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - maxWidth) / 2; 
		final double y = (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - maxHeight) / 2; 
		this.setSize(new Dimension((int) maxWidth, (int) maxHeight));
		this.setMinimumSize(new Dimension((int) minWidth, (int) minHeight));
		this.setBounds((int) x, (int) y, (int) maxWidth, (int) maxHeight);

		mainPanel = new JPanel(new BorderLayout());
		//mainPanel.setBackground(Color.darkGray);
		this.setContentPane(mainPanel);

		workingArea = new GUIWorkingArea();

		this.getRootPane().addComponentListener(new ComponentAdapter() {
			public void componentResized(final ComponentEvent e) {
				GUIFlatImpl.this.workingArea.resize();
			}
		});
		mainPanel.add(workingArea, BorderLayout.CENTER);
		this.sensorTypeList = sensorsTypes;
		if (controller == null || controller.getRoomList() == null) {
			this.roomList = new ArrayList<>();
		} else {
			this.roomList = new ArrayList<>(controller.getRoomList());
		}
		createJMenu();
		primaryNorthPanel = new JPanel(new BorderLayout(10, 10));
		this.add(primaryNorthPanel, BorderLayout.NORTH);
		createNorthMenu();
		southPanel = new SouthPanel();
		this.add(southPanel, BorderLayout.SOUTH);
		westPanel = new WestPanel(null);
		this.add(westPanel, BorderLayout.WEST);
		this.setVisible(true);
	}

	/** 
	 * Refresh the top menu.
	 * Need because refresh the sensor type (add a sensor at runtime)
	 */
	public void refreshMenu() {

		this.sensorTypeList = controller.refreshSensorList();
		if (northPanel != null) {
			primaryNorthPanel.remove(northPanel);
		}
		this.createNorthMenu();
		primaryNorthPanel.repaint();
		primaryNorthPanel.revalidate();
	}

	/**
	 * Set a list of sensor in alarm state .
	 * (change left panel led color and the color filter in 
	 *  main window)
	 * @param room the sensor's room
	 * @param sensors sensors list to set in alarm
	 */
	public void setSensorsInAllarm(final Room room, final Collection<Sensor> sensors) {

		workingArea.setInAllarmToSensor(sensors);
		westPanel.refreshWestPane(controller.getRoomList());
	}

	/**
	 * Reset a list of sensor from in alarm state to 'not in alarm' state.
	 * (change left panel led color and the color filter in 
	 *  main window)
	 * @param room the sensor's room
	 * @param sensors sensors list to set 'not in alarm'
	 */
	public void resetSensorsInAllarm(final Room room, final Collection<Sensor> sensors) {
		workingArea.resetAllarmToSensor(sensors);
		westPanel.refreshWestPane(controller.getRoomList());
	}

	/**
	 * Set the observer for the Graphic Interface.
	 * 
	 * @param observer the class observer
	 */
	public void setController(final GUIAbstractInterface observer) {
		controller = observer;	
	}

	/**
	 * Create the JMenu top bar
	 */
	private void createJMenu() {
		final JMenu menuFile = new JMenu("File");
		final JMenuItem menuNew = new JMenuItem("New", KeyEvent.VK_N);
		menuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				System.out.println("premuto New");

				GUIFlatImpl.this.newFile();

			}
		});
		menuFile.add(menuNew);

		final JMenuItem menuOpen = new JMenuItem("Open", KeyEvent.VK_O);
		menuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
		menuOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final String pathFile = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("DOMO PROJECT FILE", FILE_DOMO_EXTENTION));
				if (GUIFlatImpl.this.controller != null) {
					GUIFlatImpl.this.openFile();
					controller.load(pathFile);
				}
			}
		});
		menuFile.add(menuOpen);

		final JMenuItem menuClose = new JMenuItem("Close", KeyEvent.VK_Q);
		menuClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		menuClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.out.println("Premuto close");
				if (GUIFlatImpl.this.controller != null) {
					GUIFlatImpl.this.controller.closeProgram();
				}
				GUIFlatImpl.this.dispose();
			}
		});
		menuFile.add(menuClose);
		menuBar.add(menuFile);

		final JMenu menuEdit = new JMenu("Edit");
		final JMenuItem menuRefreshSensor = new JMenuItem("Refresh Sensor List", KeyEvent.VK_F5);
		menuRefreshSensor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.ALT_MASK));
		menuRefreshSensor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.out.println("Premuto Refresh");
				if (GUIFlatImpl.this.controller != null) {
					GUIFlatImpl.this.controller.refreshSensorList();
					GUIFlatImpl.this.refreshMenu();
				}
			}
		});
		menuEdit.add(menuRefreshSensor);
		menuBar.add(menuEdit);

		final JMenu menuInsert = new JMenu("Insert");
		final JMenuItem menuAddRoom = new JMenuItem("Add sensor to a room", KeyEvent.VK_R);
		menuAddRoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		menuAddRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.out.println("Premuto Insert new Room");
				if (workingArea.getSelectedSensor().size() > 0) {
					GUIFlatImpl.this.createRoomFrame();
				}
			}
		});
		menuInsert.add(menuAddRoom);
		menuBar.add(menuInsert);

		this.setJMenuBar(menuBar);
	}

	/**
	 * Create the north Menu
	 * 
	 */
	private void createNorthMenu() {
		northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
		northPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		final ArrayList<JButton> btnSensorsList = new ArrayList<>();
		final ImageIcon imgNew = new ImageIcon(IMAGE_ROOT_FOLDER + SYSTEM_SEPARATOR + "new.png");
		final ImageIcon imgOpen = new ImageIcon(IMAGE_ROOT_FOLDER + SYSTEM_SEPARATOR + "open.png");
		final ImageIcon imgSave = new ImageIcon(IMAGE_ROOT_FOLDER + SYSTEM_SEPARATOR + "save.jpg");
		final ImageIcon imgAddRoom = new ImageIcon(IMAGE_ROOT_FOLDER + SYSTEM_SEPARATOR + "addRoom.png");
		final ImageIcon imgTrash = new ImageIcon(IMAGE_ROOT_FOLDER + SYSTEM_SEPARATOR + "trash.png");

		final JButton btnNew = new JButton(imgNew);
		final JButton btnOpen = new JButton(imgOpen);
		final JButton btnSave = new JButton(imgSave);
		final JButton btnAddRoom = new JButton(imgAddRoom);
		final JButton btnTrash = new JButton(imgTrash);

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

		if (!workingArea.isSetBackground()) {
			btnAddRoom.setEnabled(false);
			btnSave.setEnabled(false);
			btnTrash.setEnabled(false);
		}

		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final boolean res = GUIFlatImpl.this.newFile();
				if (res) {
					btnAddRoom.setEnabled(true);
					btnSave.setEnabled(true);
					btnTrash.setEnabled(true);
					for (final JButton btnS : btnSensorsList) {
						btnS.setEnabled(true);
					}
				}
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
				final boolean res = GUIFlatImpl.this.openFile();
				if (res) {
					btnAddRoom.setEnabled(true);
					btnSave.setEnabled(true);
					btnTrash.setEnabled(true);
					for (final JButton btnS : btnSensorsList) {
						btnS.setEnabled(true);
					}
				}
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
				});
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
				});
		btnAddRoom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (workingArea.getSelectedSensor().size() > 0) {
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
				});
		btnTrash.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {

				if (controller != null) {
					controller.deleteSensors(new ArrayList<Sensor>(workingArea.getSelectedSensor()));
					workingArea.removeSelectSensor();
					workingArea.repaint();

					if (controller.getRoomList() != null && controller.getRoomList().size() > 0) {
						westPanel.refreshWestPane(controller.getRoomList());
						westPanel.repaint();
					}
					GUIFlatImpl.this.repaint();
				}
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
				});
		northPanel.add(btnNew);
		northPanel.add(btnOpen);
		northPanel.add(btnSave);
		northPanel.add(btnAddRoom);
		if (sensorTypeList != null) {
			for (final Map<String, String> map : sensorTypeList) {
				final ImageIcon imgAddSensor = new ImageIcon(map.get("image")); 
				final JButton btnAddSensor = new JButton(imgAddSensor);
				btnAddSensor.setSize(new Dimension(BUTTON_ICON_DIMENSION, BUTTON_ICON_DIMENSION));
				btnAddSensor.setToolTipText("Insert Sensor");
				btnSensorsList.add(btnAddSensor);
				if (!workingArea.isSetBackground()) {
					btnAddSensor.setEnabled(false);
				}
				btnAddSensor.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						if (controller != null && workingArea.isSetBackground()) {
							final Sensor newSensor = controller.addSensorWithName(map.get("name"));
							workingArea.addSensor(newSensor);
							//workingArea.addSensor(map.get("image"));
							if (controller.getRoomList() != null && controller.getRoomList().size() > 0) {
								westPanel.refreshWestPane(controller.getRoomList());
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
						});
				btnAddSensor.setName(map.get("name"));
				northPanel.add(btnAddSensor);
			}
		}
		northPanel.add(btnTrash);
		northPanel.setBorder(BorderFactory.createEmptyBorder(STANDARD_BORDER, STANDARD_BORDER, STANDARD_BORDER, STANDARD_BORDER));
		primaryNorthPanel.add(northPanel, BorderLayout.CENTER);
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

	/**
	 * Create a new project
	 * Handle the controller call too (newProject())
	 */
	private boolean newFile() {
		if (workingArea.isSetBackground()) {
			final int choose = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the current project. All changes will be lost!", "ATTENTION!", JOptionPane.OK_CANCEL_OPTION);
			if (choose == 0) {
				final String imgAddress = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("Image file", "jpg", "jpeg", "png", "bmp", "gif"));
				if (imgAddress == null) {
					return false;
				} else {
					workingArea.setImage(imgAddress);
					this.projectImagePath = imgAddress;
					this.repaint();
					if (controller != null) {
						controller.newProject();
					}
				}
			}
		} else {
			final String imgAddress = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("Image file", "jpg", "jpeg", "png", "bmp", "gif"));
			if (imgAddress == null) {
				return false;
			} else {
				workingArea.setImage(imgAddress);
				this.projectImagePath = imgAddress;
				this.repaint();
				controller.newProject();
			}
		}
		return true;
	}

	/**
	 * Open a project.
	 * Handle the controller call too (load(pathFile))
	 */
	private boolean openFile() {	
		if (controller == null) {
			return false;
		} else {
			final String pathFile = GUIFlatImpl.this.openFile(new FileNameExtensionFilter("DOMO PROJECT FILE", FILE_DOMO_EXTENTION));
			if (pathFile != null) {
				final Flat file = controller.load(pathFile);
				workingArea.setImage(file.getImagePath());
				this.projectImagePath = file.getImagePath();	
				final ArrayList <Sensor> sensors = new ArrayList<>();
				for (final Room room : file.getRooms()) {
					sensors.addAll(room.getSensor());
				}
				workingArea.addSensors(sensors);
				westPanel.refreshWestPane(controller.getRoomList());
				this.repaint();
				return true;
			}
		}
		return false;
	}

	/**
	 * Save the current project 
	 * This function call the controller.save(pathFile) abstract method
	 */
	private void saveFile() {
		if (controller != null) {
			final JFileChooser openFile = new JFileChooser();
			openFile.setFileFilter(new FileNameExtensionFilter("Domo project file", FILE_DOMO_EXTENTION));
			final int returnVal = openFile.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				controller.save(openFile.getSelectedFile().getPath() + "." + FILE_DOMO_EXTENTION, this.projectImagePath);
			}
		}
	}

	/**
	 * Create the frame that add to a exist o new room a group of sensors (or only one sensor)
	 */
	private void createRoomFrame() {

		final JFrame addRoomFrame = new JFrame("Add Sensor to Room");
		addRoomFrame.setLocation(new Point(this.getX() + 10, this.getY() + 10));

		JComboBox<String> cmbRoomName;
		if (controller == null) {
			cmbRoomName = new JComboBox<String>();
		} else {
			if (controller.getRoomList() != null && controller.getRoomList().size() > 0) {
				final 	HashMap<String, Integer> roomsNames = new HashMap<>();
				roomList = new ArrayList<>(controller.getRoomList());
				String[] t = new String[controller.getRoomList().size() + 1];
				t[0] = " ";
				int index = 1;
				for (final Room r : roomList) {
					roomsNames.put(r.getName(), r.getId());
					t[index] = r.getName();
					index++;
				}
				cmbRoomName = new JComboBox<>(t);
			} else {
				cmbRoomName = new JComboBox<String>();
			}
		}
		cmbRoomName.setEditable(true);
		final JLabel lblNome = new JLabel("nome stanza:");
		lblNome.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
		final JButton btnOk = new JButton("Ok");
		final JButton btnCancel = new JButton("Cancel");
		final JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.setBorder(BorderFactory.createEmptyBorder(ADD_ROOM_FRAME_BORDER, ADD_ROOM_FRAME_BORDER, ADD_ROOM_FRAME_BORDER, ADD_ROOM_FRAME_BORDER));
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
						if (cmbRoomName.getSelectedItem() != null) {
							controller.addRoomWithNameAndSensors((String) cmbRoomName.getSelectedItem(), new ArrayList<Sensor>(workingArea.getSelectedSensor()));
							if (controller.getRoomList().size() > 0 && westPanel == null) {
								westPanel = new WestPanel(controller.getRoomList());
								GUIFlatImpl.this.add(westPanel, BorderLayout.WEST);
								mainPanel.repaint();
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
				westPanel.refreshWestPane(controller.getRoomList());
				addRoomFrame.dispose();
				workingArea.deselectAllSensor();
			}
		});
		addRoomFrame.setContentPane(panel);
		addRoomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addRoomFrame.setVisible(true);
		addRoomFrame.setMinimumSize(new Dimension(addRoomFrame.getPreferredSize().width, addRoomFrame.getPreferredSize().height));
		addRoomFrame.setMaximumSize(new Dimension(addRoomFrame.getPreferredSize().width, addRoomFrame.getPreferredSize().height));
	}


}

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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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


public class GUIFlatImpl implements GUIFlat, ActionListener {

	private GUIAbstractObserver controller;
	
	private JFrame mainFrame = new JFrame();
	protected JPanel mainPanel;
	
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
		mainPanel.setBackground(Color.darkGray);
		mainFrame.setContentPane(mainPanel);
		
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
				newRoom();
			}
		});
		menuInsert.add(menuAddRoom);
		menuBar.add(menuInsert);

		JPanel leftPanel = new JPanel(new GridLayout(5, 1));
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setVisible(true);
	}
	
	private String openFile(final FileNameExtensionFilter filter) {
		JFileChooser openFile = new JFileChooser();
		openFile.setFileFilter(filter);
		int result = openFile.showOpenDialog(mainFrame);
		if (result == JFileChooser.APPROVE_OPTION) {
//			BufferedImage image;
//			try {
//				image = ImageIO.read(new File(openFile.getSelectedFile().getPath()));
//				ImageIcon icon = new ImageIcon(image);
//		        JLabel label = new JLabel(icon);
//		        label.setSize(new Dimension(image.getWidth(), image.getHeight()));
//		        
//				//JImageView label = new JImageView (openFile.getSelectedFile().getPath());
//				//System.out.println(""+openFile.getSelectedFile().getPath());
//		        //label.setBounds(new Rectangle(10, 30, 750, 500));
//		        
//		        mainFrame.getContentPane().add(label, BorderLayout.CENTER);
//		        mainFrame.repaint();
//		        return new File(openFile.getSelectedFile().getPath());
//		        
//			} catch (IOException e) {

//				e.printStackTrace();
//				return null;
//			}
			return openFile.getSelectedFile().getPath();
		}
		return null;
	}

	private void newRoom() {
		
		if (controller != null) {
			controller.addedRoom(null);
		}
	}
	
	
	
	public class AddRoomFrame extends JFrame {

		private String roomName;
		private String roomImage;
		private JTextField txtname = new JTextField(10);
		private JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		public AddRoomFrame() {
			
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setSize(400, 200);
			JLabel lblNome = new JLabel("nome stanza:");
			JLabel lblImg = new JLabel("immagine: ");
			JButton btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AddRoomFrame.this.dispose();
				}
			});
			JButton btnOk = new JButton("Ok");
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//if(txtname.getText() != null) {
						AddRoomFrame.this.dispose();
					//}	
				}
			});
	
			panel.add(lblNome);
			panel.add(txtname);
			panel.add(lblImg);
			panel.add(btnCancel);
			panel.add(btnOk);
			this.setContentPane(panel);
			
			this.setVisible(true);
		}
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}

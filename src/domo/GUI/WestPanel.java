/**
 * 
 */
package domo.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domo.devices.Sensor;
import domo.general.Room;

/**
 * @author Simone
 *
 */
public class WestPanel extends JPanel{

	final HashMap <Room, ArrayList<ItemForWestPanel>> labelForRoom = new HashMap<>();
	
	public WestPanel(ArrayList<Room> roomList) {
		super(new BorderLayout(10,10));
		this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		
		JPanel griglia = new JPanel(new GridLayout(roomList.size(), 1));
		griglia.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5), "Rooms List"));
		for (Room room : roomList) {
			
			JPanel viewPanel = new JPanel(new GridLayout(room.getSensor().size(), 1));
			viewPanel.setBorder(BorderFactory.createTitledBorder(room.getName()));
			
			for (Sensor sensor : room.getSensor()) {
				if(!labelForRoom.containsKey(room)) {
					labelForRoom.put(room, new ArrayList<ItemForWestPanel>());
				}
				ItemForWestPanel sensorItem = new ItemForWestPanel(sensor.getName(), sensor.getId());
				viewPanel.add(sensorItem);
				labelForRoom.get(room).add(sensorItem);
			}
			
			ItemForWestPanel sensorItem = new ItemForWestPanel("Prova aaa aaa aaa", 20);
			viewPanel.add(sensorItem);

			viewPanel.add(sensorItem);
			
			griglia.add(viewPanel);
		}
		
		this.add(griglia);
	}
	
	private class ItemForWestPanel extends JPanel {
		
		private final JLabel imageLabel = new JLabel();
		private final JLabel textLabel = new JLabel();
		private int sensorId;
		
	
		private final ImageIcon redLedImage = new ImageIcon("res" + System.getProperty("file.separator").toString() + "redLed.png");
		private final ImageIcon greenLedImage = new ImageIcon("res" + System.getProperty("file.separator").toString() + "greenLed.png");
		
		public ItemForWestPanel(String textForSensor, int id) {
			super(new FlowLayout(FlowLayout.LEADING, 5, 5));
			this.sensorId = id;
			//textLabel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
//			textLabel.setBorder(BorderFactory.createLineBorder(Color.black));
//			imageLabel.setBorder(BorderFactory.createLineBorder(Color.black));
			//imageLabel.setBorder(BorderFactory.createEmptyBorder(5,2,2,2));
			imageLabel.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
			textLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
			imageLabel.setIcon(redLedImage);
			imageLabel.setSize(new Dimension(redLedImage.getIconWidth(), redLedImage.getIconHeight()));
			
			textLabel.setText(textForSensor);
			this.add(imageLabel);
			this.add(textLabel);
		}
		
		public void setGreenLed() {
			this.imageLabel.setIcon(greenLedImage);
		}

		public void setRedLed() {
			this.imageLabel.setIcon(redLedImage);
		}

		public int getSensorId() {
			return sensorId;
		}
		
	}
}

/**
 * 
 */
package domo.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -1185955850039860687L;
	private final HashMap <Room, ArrayList<ItemForWestPanel>> labelForRoom = new HashMap<>();
	private JPanel griglia;

	public WestPanel(ArrayList<Room> roomList) {
		super(new BorderLayout(10,10));
		this.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		this.refreshWestPane(roomList);
	}

	public void refreshWestPane(ArrayList<Room> roomList) {

		if(griglia != null) {
			this.remove(griglia);
		}
		griglia = null;
		griglia = new JPanel();
		griglia.setLayout(new BoxLayout(griglia, BoxLayout.Y_AXIS));
		griglia.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5), "Rooms List"));
		for (Room room : roomList) {
			if(room.getSensor() != null && room.getSensor().size() > 0) {
				//JPanel viewPanel = new JPanel(new GridLayout(room.getSensor().size(), 1));
				JPanel viewPanel = new JPanel();
				viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
				viewPanel.setBorder(BorderFactory.createTitledBorder(room.getName()));

				for (Sensor sensor : room.getSensor()) {
					if(!labelForRoom.containsKey(room)) {
						labelForRoom.put(room, new ArrayList<ItemForWestPanel>());
					}
					ItemForWestPanel sensorItem;
					if(sensor.isInAlert()) {
						sensorItem = new ItemForWestPanel(sensor.getName(), sensor.getId(), true);
					} else {
						sensorItem = new ItemForWestPanel(sensor.getName(), sensor.getId(), false);
					}
					viewPanel.add(sensorItem);
					labelForRoom.get(room).add(sensorItem);
				}

				griglia.add(viewPanel);
			}
		}

		this.add(griglia);
		this.repaint();
	}

	public void setGreenLightToSensor(Room room, int sensorId) {
		ArrayList<ItemForWestPanel> t = labelForRoom.get(room);
		for (ItemForWestPanel i : t) {
			if (i.getSensorId() == sensorId) {
				i.setGreenLed();
			}
		}
	}

	public void setRedLightToSensor(Room room, int sensorId) {
		ArrayList<ItemForWestPanel> t = labelForRoom.get(room);
		for (ItemForWestPanel i : t) {
			if (i.getSensorId() == sensorId) {
				i.setRedLed();
			}
		}
	}

	private class ItemForWestPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3811536795951095239L;
		private final JLabel imageLabel = new JLabel();
		private final JLabel textLabel = new JLabel();
		private int sensorId;

		private final ImageIcon redLedImage = new ImageIcon("res" + System.getProperty("file.separator").toString() + "redLed.png");
		private final ImageIcon greenLedImage = new ImageIcon("res" + System.getProperty("file.separator").toString() + "greenLed.png");

		public ItemForWestPanel(String textForSensor, int id, boolean isInAlert) {
			super(new FlowLayout(FlowLayout.LEADING, 5, 5));
			this.sensorId = id;
			imageLabel.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
			textLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
			imageLabel.setIcon(redLedImage);
			imageLabel.setSize(new Dimension(redLedImage.getIconWidth(), redLedImage.getIconHeight()));

			textLabel.setText(textForSensor);
			this.add(imageLabel);
			this.add(textLabel);
			if (isInAlert) {
				this.setRedLed();
			} else 
			{
				this.setGreenLed();
			}
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

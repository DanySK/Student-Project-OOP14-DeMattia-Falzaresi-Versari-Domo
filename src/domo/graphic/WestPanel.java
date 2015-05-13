package domo.graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domo.devices.Sensor;
import domo.general.Room;

/**
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 *
 */
public class WestPanel extends JPanel {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -1185955850039860687L;
	private static final int BORDER_5 = 5;
	private static final int BORDER_15 = 15;
	private final Map <Room, ArrayList<ItemForWestPanel>> labelForRoom = new HashMap<>();
	private JPanel griglia;

	/**
	 * create a west panel with a specific room list.
	 * @param roomList tha room list
	 */
	public WestPanel(final ArrayList<Room> roomList) {
		super(new BorderLayout(10, 10));
		this.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		this.refreshWestPane(roomList);
	}

	/**
	 * Refresh the panel with room list data.
	 * @param roomList the room list
	 */
	public void refreshWestPane(final ArrayList<Room> roomList) {

		if (griglia != null) {
			this.remove(griglia);
		}
		if (roomList != null && roomList.isEmpty()) {
			
			griglia = new JPanel();
			griglia.setLayout(new BoxLayout(griglia, BoxLayout.Y_AXIS));
			griglia.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(BORDER_15, BORDER_5, BORDER_5, BORDER_5), "Rooms List"));
			for (final Room room : roomList) {
				final JPanel viewPanel = new JPanel();
				viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
				if (room.getSensor() != null && room.getSensor().size() > 0) {
					viewPanel.setBorder(BorderFactory.createTitledBorder(room.getId() + " " + room.getName()));
					for (final Sensor sensor : room.getSensor()) {
						if (!labelForRoom.containsKey(room)) {
							labelForRoom.put(room, new ArrayList<ItemForWestPanel>());
						}
						ItemForWestPanel sensorItem;
						if (sensor.isInAlert()) {
							sensorItem = new ItemForWestPanel(sensor.getName(), sensor.getId(), true);
						} else {
							sensorItem = new ItemForWestPanel(sensor.getName(), sensor.getId(), false);
						}
						sensorItem.setAlert();
						viewPanel.add(sensorItem);
						labelForRoom.get(room).add(sensorItem);
					}
					
				} else {
					//if (!room.getName().equals("Default Room")) {
						viewPanel.setBorder(BorderFactory.createTitledBorder(room.getId() + " " + room.getName()));
						viewPanel.add(new JLabel("                 "));
						viewPanel.add(new JLabel("No Sensors              :(  "));
					//}
				}
				griglia.add(viewPanel);
			}


		} else {
			griglia = new JPanel();
			griglia.setLayout(new BoxLayout(griglia, BoxLayout.Y_AXIS));
			griglia.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(BORDER_15, BORDER_5, BORDER_5, BORDER_5), ""));
			griglia.add(new JLabel("No Room              :(  "));
		}

		this.add(griglia);
		this.repaint();
		this.revalidate();
	}

	/**
	 * reset a sensor to normal state.
	 * @param room room sensor
	 * @param sensorId sensor id to reset state
	 */
	public void resetSensorColor(final Room room, final int sensorId) {
		final ArrayList<ItemForWestPanel> t = labelForRoom.get(room);
		for (final ItemForWestPanel i : t) {
			if (i.getSensorId() == sensorId) {
				i.setGreenLed();
			}
		}
	}

	/**
	 * Set a sensor to alarm state.
	 * @param room sensor room
	 * @param sensorId sensor id to set in alarm
	 */
	public void setRedLightToSensor(final Room room, final int sensorId) {
		final ArrayList<ItemForWestPanel> t = labelForRoom.get(room);
		for (final ItemForWestPanel i : t) {
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
		private final int sensorId;
		private boolean isInAlert;

		private final ImageIcon redLedImage = new ImageIcon("res" + System.getProperty("file.separator").toString() + "redLed.png");
		private final ImageIcon greenLedImage = new ImageIcon("res" + System.getProperty("file.separator").toString() + "greenLed.png");

		public ItemForWestPanel(final String textForSensor, final int id, final boolean isAlert) {
			super(new FlowLayout(FlowLayout.LEFT, BORDER_5, BORDER_5));
			this.sensorId = id;
			imageLabel.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
			textLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
			imageLabel.setIcon(redLedImage);
			imageLabel.setSize(new Dimension(redLedImage.getIconWidth(), redLedImage.getIconHeight()));
			this.isInAlert = isAlert;
			textLabel.setText(id + " " + textForSensor);
			this.add(imageLabel);
			this.add(textLabel);
		}

		public void setAlert() {
			if (isInAlert) {
				this.setRedLed();
			} else {
				this.setGreenLed();
			}
		}
		
		public void setGreenLed() {
			this.imageLabel.setIcon(greenLedImage);
			this.isInAlert = false;
		}

		public void setRedLed() {
			this.imageLabel.setIcon(redLedImage);
			this.isInAlert = true;
		}

		public int getSensorId() {
			return sensorId;
		}

	}
}

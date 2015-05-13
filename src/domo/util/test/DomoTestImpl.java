package domo.util.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domo.devices.Sensor;
import domo.general.Flat;
import domo.general.Room;

public class DomoTestImpl extends JFrame implements DomoTest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8867083422211938170L;

	private AbstracTestInterface observer;

	private JPanel panel;
	private JPanel griglia;

	public DomoTestImpl() {
		super("Test Console");
		panel = new JPanel(new BorderLayout(10, 10));

		this.add(panel);

		this.setMinimumSize(new Dimension(200, 500));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void refresh(Flat flat) {

		if (panel != null) {
			this.remove(panel);
		}
		if (flat.getRooms() != null && flat.getRooms().size() > 0) {
			panel = new JPanel(new BorderLayout(10, 10));

			this.add(panel);
			griglia = null;
			griglia = new JPanel();
			griglia.setLayout(new BoxLayout(griglia, BoxLayout.Y_AXIS));
			griglia.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5), "Rooms List"));
			for (Room room : flat.getRooms()) {
				JPanel viewPanel = new JPanel();
				viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
				if (room.getSensor() != null && room.getSensor().size() > 0) {
					viewPanel.setBorder(BorderFactory.createTitledBorder(room.getId() + " " + room.getName()));
					for (Sensor sensor : room.getSensor()) {

						TestEntity sensorItem;
						if (sensor.isInAlert()) {
							sensorItem = new TestEntity(sensor, true);
						} else {
							sensorItem = new TestEntity(sensor, false);
						}
						viewPanel.add(sensorItem);
					}

				} else {
					viewPanel.setBorder(BorderFactory.createTitledBorder(room.getId() + " " + room.getName()));
					viewPanel.add(new JLabel("                 "));
					viewPanel.add(new JLabel("No Sensors              :(  "));
				}
				griglia.add(viewPanel);
			}


		} else {
			griglia = null;
			griglia = new JPanel();
			griglia.setLayout(new BoxLayout(griglia, BoxLayout.Y_AXIS));
			griglia.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5), ""));
			griglia.add(new JLabel("No Room              :(  "));
		}

		this.panel.add(griglia);
		this.panel.requestFocus();
		this.panel.repaint();
		this.panel.revalidate();
		this.panel.requestFocus();

	}



	@Override
	public void setObserver(final AbstracTestInterface testObserver) {
		this.observer = testObserver;

	}


	private class TestEntity extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3811536795951095239L;
		private final JCheckBox checkbox;

		public TestEntity(final Sensor sensor, final boolean isInAlert) {
			super(new FlowLayout(FlowLayout.LEFT, 5, 5));
			checkbox = new JCheckBox(sensor.getName());
			checkbox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(final ItemEvent e) {
					if (checkbox.isSelected()) {
						sensor.setAlert(true);
					} else {
						sensor.setAlert(false);
					}
					observer.sensorStateChange();

				}
			});

			if (isInAlert) {
				checkbox.setSelected(true);
			} else {
				checkbox.setSelected(false);
			}
			this.add(checkbox);
		}


	}

}

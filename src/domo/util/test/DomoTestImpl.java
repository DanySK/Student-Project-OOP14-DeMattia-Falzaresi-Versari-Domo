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

/**
 * Domo test class implementation.
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public class DomoTestImpl extends JFrame implements DomoTest {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -8867083422211938170L;

	/**
	 * abstract observer reference
	 */
	private AbstracTestInterface observer;

	private static final int BORDER_5 = 5;
	private static final int BORDER_15 = 15;
	private static final int START_DIMENSION = 200;
	/**
	 * primary panel
	 */
	private JPanel panel;
	/**
	 * Panel for component vertical order
	 */
	private JPanel griglia;

	/**
	 * Create DomoTestImpl instance
	 * Create the frame, the items inside and the 
	 * sensors initial state.
	 */
	public DomoTestImpl() {
		super("Test Console");
		panel = new JPanel(new BorderLayout(10, 10));

		this.add(panel);

		this.setMinimumSize(new Dimension(START_DIMENSION, START_DIMENSION * 2));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Refresh the frame, ex when is added a sensor to the project.
	 * or a room is create
	 * @param flat the flat with the change
	 */
	public void refresh(final Flat flat) {

		if (panel != null) {
			this.remove(panel);
		}
		if (flat.getRooms() != null && flat.getRooms().size() > 0) {
			panel = new JPanel(new BorderLayout(10, 10));
			this.add(panel);
			griglia = new JPanel();
			griglia.setLayout(new BoxLayout(griglia, BoxLayout.Y_AXIS));
			griglia.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(BORDER_15, BORDER_5, BORDER_5, BORDER_5), "Rooms List"));
			for (final Room room : flat.getRooms()) {
				final JPanel viewPanel = new JPanel();
				viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
				if (room.getSensor() != null && room.getSensor().size() > 0) {
					viewPanel.setBorder(BorderFactory.createTitledBorder(room.getId() + " " + room.getName()));
					for (final Sensor sensor : room.getSensor()) {

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
			griglia = new JPanel();
			griglia.setLayout(new BoxLayout(griglia, BoxLayout.Y_AXIS));
			griglia.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(BORDER_15, BORDER_5, BORDER_5, BORDER_5), ""));
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
			super(new FlowLayout(FlowLayout.LEFT, BORDER_5, BORDER_5));
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

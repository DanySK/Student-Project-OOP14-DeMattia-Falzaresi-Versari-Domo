import java.awt.Dimension;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import devices.Sensor;

/**
 * @author Marco Versari
 *
 */
public class RoomImpl implements Room {
	
	private final int id;
	private final String name;
	private final Set<Sensor> listSensor;
	private Point location;
	private Dimension size;
	
	/**
	 * Initialize Room Class.
	 * @param pId Room id.
	 * @param pName The room name;
	 */
	public RoomImpl(final int pId, final String pName) {
		id = pId;
		name = pName;
		listSensor = new HashSet<Sensor>();
	}

	@Override
	public int getId() {		
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Dimension getSize() {
		return new Dimension(size);
	}

	@Override
	public Point getLocation() {
		return new Point(location);
	}

	@Override
	public void setLocation(final Point destination) {
		location = new Point(destination);
	}

	@Override
	public void addSensor(final Sensor sensor) {
		listSensor.add(sensor);
	}

	@Override
	public void removeSensor(final int pId) {
		listSensor.forEach(x-> {
			if (x.getId() == pId) {
				listSensor.remove(x);
			}
		});		
	}

	@Override
	public void moveSensor(final int pId, final Point to) {
		// TODO Auto-generated method stub
	}

}

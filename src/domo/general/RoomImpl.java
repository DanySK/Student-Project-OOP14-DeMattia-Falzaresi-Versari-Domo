package domo.general;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import domo.devices.Sensor;
import domo.devices.util.counter.Counter;
import domo.devices.util.counter.CounterImpl;

/**
 * @author Marco Versari
 *
 */
public class RoomImpl implements Room {
	
	private final String name;
	private final Map<Integer, Sensor> listSensor;
	private final Counter counter;
	private int id;
	
	/**
	 * Initialize Room Class.
	 * @param pId Room id.
	 * @param pName The room name;
	 */
	public RoomImpl(final int pId, final String pName) {
		id = pId;
		name = pName;
		listSensor = new HashMap<Integer, Sensor>();
		counter = new CounterImpl(0);
	}
	
	/**
	 * Initialize Room Class.	 * 
	 * @param pName The room name;
	 */
	public RoomImpl(final String pName) {
		name = pName;
		listSensor = new HashMap<Integer, Sensor>();
		counter = new CounterImpl(0);
	}

	@Override
	public int getId() {		
		return id;
	}
	
	@Override
	public void setId(final int pId) {		
		this.id = pId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int addSensor(final Sensor sensor) {
		final int ret = counter.incCounter();
		listSensor.put(ret, sensor);
		return ret;
	}

	@Override
	public void removeSensor(final int pId) {
		if (listSensor.containsKey(pId)) {
			listSensor.remove(pId);
		}		
	}

	@Override
	public void moveSensor(final int pId, final Point to) {
		if (listSensor.containsKey(pId)) {
			listSensor.get(pId).setLocation(to);
		}
	}

	@Override
	public Set<Sensor> getSensor() {		
		return new HashSet<Sensor>(listSensor.values());
	}

}

package domo.general;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import domo.devices.Sensor;
import domo.devices.loader.DynamicLoader;
import domo.devices.loader.DynamicLoaderImpl;
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
	private final DynamicLoader<Sensor> instance;
	
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
		instance = new DynamicLoaderImpl<Sensor>("domo.devices", "Sensor", "AbstractSensor");
	}
	
	/**
	 * Initialize Room Class.	 * 
	 * @param pName The room name;
	 */
	public RoomImpl(final String pName) {
		name = pName;
		listSensor = new HashMap<Integer, Sensor>();
		counter = new CounterImpl(0);
		instance = new DynamicLoaderImpl<Sensor>("domo.devices", "Sensor", "AbstractSensor");
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
		sensor.setId(ret);
		listSensor.put(ret, sensor);
		return ret;
	}
	
	@Override
	public int addSensor(final String classPath, final String module) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		instance.setModulePath(classPath);
		final Sensor instSensor = instance.createClassInstance(module);
		instSensor.setId(counter.incCounter());
		listSensor.put(instSensor.getId(), instSensor);
		return instSensor.getId();
	}

	@Override
	public void removeSensor(final int pId) {
		if (listSensor.containsKey(pId)) {
			listSensor.remove(pId);
		}		
	}

	@Override
	public void moveSensor(final int pId, final double x, final double y) {
		if (listSensor.containsKey(pId)) {
			listSensor.get(pId).setLocation(x, y);
		}
	}

	@Override
	public Set<Sensor> getSensor() {		
		return new HashSet<Sensor>(listSensor.values());
	}
}

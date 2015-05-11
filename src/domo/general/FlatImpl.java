package domo.general;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import domo.devices.Sensor;
import domo.devices.util.counter.Counter;
import domo.devices.util.counter.CounterImpl;
import domo.devices.util.pair.Pair;
import domo.devices.util.pair.PairImpl;

	/**
	 * 
	 * @author Marco Versari
	 *  
	 */
public class FlatImpl implements Flat {
	
	private final String name;
	private final Map<Integer, Room> listRoom;
	private final Map<Integer, Pair<Room, Sensor>> listSensor;
	
	private final Counter roomCounter;
	private final Counter sensorCounter;
	
	private String imagePath;
	
	/**
	 * initialize the flat class.
	 * @param pName The flat name.
	 * @param pImagePath The flat Image.
	 */	
	public FlatImpl(final String pName, final String pImagePath) {
		imagePath = pImagePath;
		name = pName;
		
		listRoom = new HashMap<Integer, Room>();		
		listSensor = new HashMap<Integer, Pair<Room, Sensor>>();
		
		roomCounter = new CounterImpl(0);
		sensorCounter = new CounterImpl(0);
	}
	
	/**
	 * initialize the flat class.
	 * @param pName The flat name.
	 */
	public FlatImpl(final String pName) {
		this(pName, null);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int addRoom(final Room room) {
		final int ret = roomCounter.incCounter();
		room.setId(ret);
		listRoom.put(ret, room);
		return ret;
	}
	
	@Override
	public Set<Room> getRooms() {		
		return new HashSet<>(listRoom.values());
	}

	@Override
	public void removeRoom(final int id) {		
		listRoom.remove(id);
	}

	@Override
	public String getImagePath() {
		return imagePath;
	}

	@Override
	public void setImagePath(final String path) {
		imagePath = path;		
	}

	@Override
	public Pair<Integer, Integer> addSensorToRoom(final Room room, final Sensor sensor) {
		final int ret = sensorCounter.incCounter();
		listSensor.put(ret, new PairImpl<>(room, sensor));
		room.addSensor(sensor);
		return new PairImpl<>(ret, sensor.getId());
	}
}

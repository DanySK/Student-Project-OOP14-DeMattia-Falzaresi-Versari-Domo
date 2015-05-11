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

	private final Counter generalCounter;
	
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

		generalCounter = new CounterImpl(0);
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
	public int addRoom(final String pName) {
		final int ret = generalCounter.incCounter();		
		Room room = new RoomImpl(ret, pName);
		listRoom.put(ret, room);
		return ret;
	}
	
	@Override
	public Set<Room> getRooms() {		
		return new HashSet<>(listRoom.values());
	}
	
	@Override
	public Room getRoom(final int id) {		
		if (!listRoom.containsKey(id)) {
			return null;
		}		
		return listRoom.get(id);
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
		return new PairImpl<>(room.getId(), room.addSensor(generalCounter.incCounter(), sensor));
	}
}

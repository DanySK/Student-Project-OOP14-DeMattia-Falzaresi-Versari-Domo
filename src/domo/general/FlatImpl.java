package domo.general;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import domo.devices.util.counter.Counter;
import domo.devices.util.counter.CounterImpl;

	/**
	 * 
	 * @author Marco Versari
	 *  
	 */
public class FlatImpl implements Flat {
	
	private final String name;
	private final Map<Integer, Room> listRoom;
	private final Counter counter;
	
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
		counter = new CounterImpl(0);
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
		final int ret = counter.incCounter();
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
}

package domo.general;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

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
	
	/**
	 * initialize the flat class.
	 * @param pName The flat name.
	 */
	public FlatImpl(final String pName) {
		name = pName;
		listRoom = new HashMap<Integer, Room>();
		counter = new CounterImpl(0);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int addRoom(final Room room) {
		final int ret = counter.incCounter();
		listRoom.put(ret, room);
		return ret;
	}

	@Override
	public void removeRoom(final int id) {		
		listRoom.remove(id);
	}

	@Override
	public void moveRoom(final int idRoom, final Point to) {
		if (listRoom.containsKey(idRoom)) {
			listRoom.get(idRoom).setLocation(to);
		}		
	}

}

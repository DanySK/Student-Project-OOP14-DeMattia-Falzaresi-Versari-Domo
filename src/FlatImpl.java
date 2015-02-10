import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Marco Versari
 *  
 */
public class FlatImpl implements Flat {
	
	private final String name;
	private final List<Room> listRoom;
	
	/**
	 * initialize the flat class.
	 * @param pName The flat name.
	 */
	public FlatImpl(final String pName) {
		name = pName;
		listRoom = new ArrayList<Room>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addRoom(final Room room) {		
		listRoom.add(room);
	}

	@Override
	public void removeRoom(final int id) {		
		listRoom.remove(id);
	}

	@Override
	public void moveRoom(final int idRoom, final Point to) {
		// TODO Auto-generated method stub		
	}

}

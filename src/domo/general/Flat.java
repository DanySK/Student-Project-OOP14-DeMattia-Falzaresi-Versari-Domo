package domo.general;

import java.awt.Point;
import java.util.Set;

/**
 * 
 * @author Marco Versari
 * 
 *  The is an interface that represents our flat.
 *
 */
public interface Flat {
	/**
	 * Get the name of the flat.
	 * @return the name of the flat.
	 */
	String getName();
	
	/**
	 * Add a room in the list by id.
	 * @param room the room to add.
	 * @return The room id
	 */
	int addRoom(Room room);
	
	/**
	 * Get the room list.
	 * @return all rooms in the flat.
	 */
	Set<Room> getRooms();
	
	/**
	 * Remove room in the list by id.
	 * @param id room to remove.
	 */
	void removeRoom(int id);
	
	/**
	 * Move the room position.
	 * @param idRoom the id of the room to move.
	 * @param to the point to move the room.
	 */
	void moveRoom(int idRoom, Point to);
}

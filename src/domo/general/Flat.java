package domo.general;

import java.awt.Point;

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
	 */
	void addRoom(Room room);
	
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

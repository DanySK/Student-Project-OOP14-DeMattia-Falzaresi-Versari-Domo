package domo.general;

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
	 * Get the image path.
	 * @return the image path,
	 */
	String getImagePath();
	
	/**
	 * Set the image path.
	 * @param path The image path to set.
	 */
	void setImagePath(String path);
}

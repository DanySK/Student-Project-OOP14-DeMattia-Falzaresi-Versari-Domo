package mvc;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This class design a "Flat" object, this object has a name and a list of rooms
 * some Custom Exception has been inserted to check if the name selected from the user is correct, if not an exception is thrown
 *
 */
public class Flat {
	
	private String flatName;
	private String flatImage;
	private final List<Room> rooms;
	/**
	 * The constructor of the flat, the name to set is a mandatory parameter.
	 * @param fName name to set on the flat
	 */
	public Flat(final String fName) {
		try {
			checkName(fName);
		} catch (CustomExceptions e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		this.flatName = fName;
		this.rooms = new LinkedList<>();
	}
	
	/**
	 * This private method check the name inserted and if is not correct throws an exception
	 * @param stCheck the string to check
	 * @throws CustomExceptions	a Custom Exception for name not correct
	 */
	private void checkName(final String stCheck) throws CustomExceptions {
		if (stCheck == null || stCheck.equals("")) {
			throw new CustomExceptions("The name inserted is not correct");
		}
	}
	/**
	 * 
	 * @return -
	 */
	public String getImage() {
		return this.flatImage;
	}
	
	/**
	 * 
	 * @param s -
	 */
	public void setImage(final String s) {
		this.flatImage = s;
	}
	/**
	 * This method return the name of the flat.
	 * @return name of the element
	 */
	public String getName() {
		return this.flatName;
	}
	
	/**
	 * This method permit to change the name of the flat.
	 * @param fName name to set
	 * @throws CustomExceptions 
	 */
	public void setName(final String fName) throws CustomExceptions {
		checkName(fName);
		this.flatName = fName;
	}
	
	/**
	 * This method return a list of all the rooms.
	 * @return list of room in the flat
	 */
	public List<Room> getRooms() {
		return this.rooms;
	}
	
	/** 
	 * Add a room element to the rooms list.
	 * @param r the room to add
	 * @throws CustomExceptions 
	 */
	public void addRoom(final Room r) throws CustomExceptions {
		if (r == null) {
			throw new CustomExceptions("Room cannot be null!");
		}
		this.rooms.add(r);
	}
	
	@Override
	public String toString() {
		return this.rooms == null ? "Flat Name: " + this.flatName + "This Flat is Empty" : "Flat Name: " + this.flatName + " Number of Rooms: " + this.rooms.size();
	}

}

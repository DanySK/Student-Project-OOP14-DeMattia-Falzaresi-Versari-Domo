package domo.devices;

/**
 * 
 * @author Marco Versari
 *
 */
public interface Scheduler {
	
	/**
	 * Add a scheduling with a specific start and stop hours.
	 * @param id the device id.
	 * @param start Start hour.
	 * @param stop Stop hour.
	 * @param status The status of the device.
	 */
	void addStartStopScheduling(int id, int start, int stop, boolean status);
	
	/**
	 * Add the scheduling start with a specific range.
	 * @param id the device id.
	 * @param start Start hour.
	 * @param duration the range of time.
	 * @param status The status of the device.
	 */
	void addDurationScheduling(int id, int start, int duration, boolean status);
	
	/**
	 * Add a scheduling with a specific hour only.
	 * @param id the device id.
	 * @param hour the hour to active the status.
	 * @param status The status of the device.
	 */
	void addHourScheduling(int id, int hour, boolean status);
	
	/**
	 * Remove the scheduling of a device.
	 * @param id the device id.
	 */
	void removeScheduling(int id);
}

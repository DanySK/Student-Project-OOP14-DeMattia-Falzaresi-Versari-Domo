package domo.devices.sensor;

import domo.devices.SensorImpl;
import domo.devices.SensorTypology;

/**
 * 
 * @author Marco Versari
 *
 */
public class MotionSensor extends SensorImpl {
	
	private static final String IMAGE_PATH = "..";
	
	private void ciao() {
		
	}

	/**
	 * Create a motion sensor object.
	 * @param pId id of the device
	 * @param pName name of the device
	 */
	public MotionSensor(final int pId, final String pName) {
		super(pId, pName, SensorTypology.MOTION, IMAGE_PATH);
	}
}
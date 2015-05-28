package io;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This enumerator show the types of sensor available
 *
 */
public enum SensorTypology {
	/**
	 * Types available.
	 */
	MOTION, CAMERA, GLASSBREAK, DOOR;
	
	/**
	 * This method returns the types of sensor with the ability to move lenses (pan and tilt).
	 * @return true if is a camera
	 */
	public boolean isTiltable(){
		return this == CAMERA;
	}
}

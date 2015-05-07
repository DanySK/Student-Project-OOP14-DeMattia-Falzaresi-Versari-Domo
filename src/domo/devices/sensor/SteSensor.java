package domo.devices.sensor;

import domo.devices.AbstractSensor;
import domo.devices.SensorTypology;

/**
 * 
 * @author Marco Versari
 *
 */

public class SteSensor extends AbstractSensor  {
	
	private static final String NAME 			= "STEZSTAZ SENSOR";
	private static final SensorTypology TYPE 	= SensorTypology.MOTION;	
	private static final String IMAGE 			= "res" + System.getProperty("file.separator").toString() + "stestaz.jpg";	
	
	/**
	 * Motion Sensor class.
	 */
	public SteSensor() {
		super();
	}
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public SensorTypology getType() {
		return TYPE;
	}

	@Override
	public String getImagePath() {	
		return IMAGE;
	}	
}

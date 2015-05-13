package domo.devices.sensor;

import domo.devices.AbstractSensor;
import domo.devices.SensorTypology;

/**
 * 
 * @author Marco Versari
 *
 */

public class SimoSensor extends AbstractSensor  {
	
	private static final String NAME 			= "SIMO SENSOR";
	private static final SensorTypology TYPE 	= SensorTypology.MOTION;	
	private static final String IMAGE 			= "res" + System.getProperty("file.separator").toString() + "simoSensor.jpg";	
	
	/**
	 * Motion Sensor class.
	 */
	public SimoSensor() {
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

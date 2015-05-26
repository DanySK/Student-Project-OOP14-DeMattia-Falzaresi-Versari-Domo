package domo.devices.sensor;

import domo.devices.AbstractSensor;
import domo.devices.SensorTypology;

/**
 * Ste Sensor class.
 * @author Marco Versari
 *
 */

public class SteSensor extends AbstractSensor  {
	
	private static final String NAME 			= "STESTAZ SENSOR";
	private static final SensorTypology TYPE 	= SensorTypology.MOTION;	
	private static final String IMAGE 			= "classi" + System.getProperty("file.separator").toString() + "stestaz.jpg";	
	
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

package domo.GUI;

import domo.devices.Sensor;

public interface GUISensor {

	/**
	 * Set sensor as a new sensor (not from a load file)
	 */
	public void setSensorAsNew();

	/**
	 * Update sensor location base to sensor load from file
	 */
	public void updateLocationFromLoad();

	/**
	 * set location. this set the sensor location and 
	 * limit the position inside the parent bounds (working area background image)
	 */
	public void setLocation(int x, int y);

	/**
	 * set the image scale. Typically when parent frame resize
	 */
	public void setScale(double imgScale);

	/** 
	 * set the image color filter to red
	 */
	public void setRedColorFilter();

	/** 
	 * set the image color filter to default (no filter color)
	 */
	public void setResetFilter();

	/**
	 * set the mouse enable
	 * @param enableMouse 	true - mouse enabled 
	 * 						false - mouse disabled
	 */
	public void setMouseEnabled(boolean enableMouse);

	/**
	 * Tell if the mouse is enabled or not
	 * @return 	true - mouse is enable
	 * 			false - mouse is disabled
	 */
	public boolean getMouseEnabled();

	/**
	 * Tell if a sensor is selected
	 * @return 	true - the sensor is selected
	 * 			false - the sensor is not selected
	 */
	public boolean isSelect();

	/**
	 * 
	 * @return the specific sensor that the GUISensorImpl class instance represent
	 */
	public Sensor getSensor();

	/**
	 * 
	 * @param sens the specific sensor that the GUISensorImpl class instance must be represent
	 */
	public void setSensor(Sensor sens);

}
package domo.GUI;

import java.util.ArrayList;

import domo.devices.Sensor;

public interface GUIWorkingArea {

	/**
	 * set the background image 
	 * @param imagePath the path to the image to set as background
	 */
	public void setImage(String imagePath);

	/**
	 * call when need to update the panel (in case of parent resize)
	 */
	public void resize();

	/**
	 * remove all selected sensors from view
	 */
	public void removeSelectSensor();

	/** 
	 * add a sensor to the area
	 * @param sensor the sensor instance to add.
	 */
	public void addSensor(Sensor sensor);

	/**
	 * add a sensor list to the area
	 * @param sensors list of sensors to add
	 */
	public void addSensors(ArrayList<Sensor> sensors);

	/**
	 * get the selected sensors list
	 * @return a sensors list
	 */
	public ArrayList<Sensor> getSelectedSensor();

	/**
	 * set a sensor list in alarm. The change is graphic only
	 * @param sens sensor list
	 */
	public void setInAllarmToSensor(ArrayList<Sensor> sens);

	/**
	 * set a sensor list not in alarm. The change is graphic only
	 * @param sens sensor list
	 */
	public void resetAllarmToSensor(ArrayList<Sensor> sens);

	/**
	 * tell if the background object is set or not
	 * @return true or false if is set or not a background
	 */
	public boolean isSetBackground();

}
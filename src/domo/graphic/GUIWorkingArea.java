package domo.graphic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import domo.devices.Sensor;
/**
 * Represent the working area where the user can add, move, delete.
 * the sensors in a flat
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 *
 */
public class GUIWorkingArea extends JLayeredPane {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = -3555881320995216997L;
	/**
	 * the background image, an ImageView class instance
	 */
	private ImageView bgImage;
	/**
	 * A sensor graphic representation list
	 */
	private final Collection <GUISensor> sensorList = new ArrayList<>();
	
	/**
	 * create a standard GUIWorkingArea object instance.
	 */
	public GUIWorkingArea() {
		super();
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
	}
	
	/**
	 * set the background image.
	 * @param imagePath the path to the image to set as background
	 */
	public void setImage(final String imagePath) {
		
		BufferedImage imageBuf;
		try {
			imageBuf = ImageIO.read(new File(imagePath));
			if (this.bgImage != null) {
				this.remove(bgImage);
			}
			bgImage = new ImageView(imageBuf, this.getBounds());
			this.add(bgImage, 0);
		} catch (IOException e) {
			System.out.println("Exception GUIWorkingArea: error set image");
		}
		
	}
	
	/**
	 * call when need to update the panel (in case of parent resize).
	 */
	public void resize() {
		if (bgImage != null) {
			bgImage.setAspectFillToParent(this.getBounds());
			for (final GUISensor t : sensorList)  {
				t.setScale(bgImage.getScale());
			}
		}
	}
	
	/**
	 * remove all selected sensors from view.
	 */
	public void removeSelectSensor() {
		for (final GUISensor t : sensorList) {
			if (t.isSelect()) {
				this.remove(t);
			}
		}
	}
	
	/** 
	 * add a sensor to the area.
	 * @param sensor the sensor instance to add.
	 */
	public void addSensor(final Sensor sensor) {
		final String imgPath = sensor.getImagePath();
		if (this.bgImage != null) {
			GUISensor t;
			try {
				t = new GUISensor(imgPath, this.bgImage, sensor);
				t.setSensorAsNew();
				t.setScale(this.bgImage.getScale());
				sensorList.add(t);
				this.add(t, 0);
				this.moveToFront(t);
				
			} catch (IOException e) {
				System.out.println("Exception GUIWorkingArea: error set image sensor");
			}
		}
	}
	
	/**
	 * add a sensor list to the area.
	 * @param sensors list of sensors to add
	 */
	public void addSensors(final Collection<Sensor> sensors) {
		for (final Sensor sensor : sensors) {
			GUISensor t;
			try {
				t = new GUISensor(sensor.getImagePath(), this.bgImage, sensor);
				sensorList.add(t);
				this.add(t, 0);
				this.moveToFront(t);
				t.setScale(this.bgImage.getScale());
				t.updateLocationFromLoad();
			} catch (IOException e) {
				System.out.println("Exception GUIWorkingArea: error set image sensor list");
			}
		}
	}
	
	/**
	 * get the selected sensors list.
	 * @return a sensors list
	 */
	public Collection<Sensor> getSelectedSensor() {
		final ArrayList<Sensor> sel = new ArrayList<>();
		for (final GUISensor sens : sensorList) {
			if (sens.isSelect()) {
				sel.add(sens.getSensor());
			}
		}
		return sel;
	}

	/**
	 * 
	 * @param sens   		the sensor to change color
	 * @param lightIndex 	index that represent the color:	0 - IN ALLARM
	 * 															1 - NOT IN ALLARM
	 */
	private void setLightToSensor(final Sensor sens, final int lightIndex) {
		switch (lightIndex) {
		case 0:
			for (final GUISensor tSens : sensorList) {
				if (tSens.getSensor().equals(sens)) {
					tSens.setRedColorFilter();
					return;
				}
			}
			break;
		case 1:
			for (final GUISensor tSens : sensorList) {
				if (tSens.getSensor().equals(sens)) {
					tSens.setResetFilter();
					return;
				}
			}
			break;
		default:
			this.setLightToSensor(sens, 0);
			break;
		}
	}
	
	/**
	 * set a sensor list in alarm. The change is graphic only
	 * @param sens sensor list
	 */
	public void setInAllarmToSensor(final Collection<Sensor> sens) {
		for (final Sensor sensor: sens) {
			this.setLightToSensor(sensor, 0);
		}
	}
	/**
	 * set a sensor list not in alarm. The change is graphic only.
	 * @param sens sensor list
	 */
	public void resetAllarmToSensor(final Collection<Sensor> sens) {
		for (final Sensor sensor: sens) {
			this.setLightToSensor(sensor, 1);
		}
	}
	
	/**
	 * tell if the background object is set or not.
	 * @return true or false if is set or not a background
	 */
	public boolean isSetBackground() {
		if (this.bgImage != null) {
			return true;
		}
		return false;
	}
	
	/**
	 *  Unselect all sensors.
	 */
	public void deselectAllSensor() {
		for (final GUISensor sensor : sensorList) {
			sensor.setSelect(false);
		}
	}
}

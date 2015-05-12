package domo.GUI;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import domo.devices.Sensor;
/**
 * Represent the working area where the user can add, move, delete 
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
	private final ArrayList <GUISensorImpl> sensorList = new ArrayList<>();
	
	/**
	 * create a standard GUIWorkingArea object instance
	 */
	public GUIWorkingArea() {
		super();
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
	}
	
	/**
	 * set the background image 
	 * @param imagePath the path to the image to set as background
	 */
	public void setImage(String imagePath) {
		
		BufferedImage imageBuf;
		try {
			imageBuf = ImageIO.read(new File(imagePath));
			if (this.bgImage != null) {
				this.remove(bgImage);
			}
			bgImage = new ImageView(imageBuf, this.getBounds());
			this.add(bgImage, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * call when need to update the panel (in case of parent resize)
	 */
	public void resize() {
		if (bgImage != null) {
			bgImage.setAspectFillToParent(this.getBounds());
			for (GUISensorImpl t : sensorList)  {
				t.setScale(bgImage.getScale());
			}
		}
	}
	
	/**
	 * remove all selected sensors from view
	 */
	public void removeSelectSensor() {
		for (GUISensorImpl t : sensorList) {
			if (t.isSelect()) {
				this.remove(t);
				t = null;
			}
		}
	}
	
	/** 
	 * add a sensor to the area
	 * @param sensor the sensor instance to add.
	 */
	public void addSensor(Sensor sensor) {
		String imgPath = sensor.getImagePath();
		if(this.bgImage != null) {
			GUISensorImpl t;
			try {
				t = new GUISensorImpl(imgPath, this.bgImage, sensor);
				t.setSensorAsNew();
				t.setScale(this.bgImage.getScale());
				sensorList.add(t);
				this.add(t, 0);
				this.moveToFront(t);
				
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	
	/**
	 * add a sensor list to the area
	 * @param sensors list of sensors to add
	 */
	public void addSensors(ArrayList<Sensor> sensors) {
		for (Sensor sensor : sensors) {
			GUISensorImpl t;
			try {
				t = new GUISensorImpl(sensor.getImagePath(), this.bgImage, sensor);
				sensorList.add(t);
				this.add(t, 0);
				this.moveToFront(t);
				t.setScale(this.bgImage.getScale());
				t.updateLocationFromLoad();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * get the selected sensors list
	 * @return a sensors list
	 */
	public ArrayList<Sensor> getSelectedSensor(){
		ArrayList<Sensor> sel = new ArrayList<>();
		for (GUISensorImpl sens : sensorList) {
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
	private void setLightToSensor(Sensor sens, int lightIndex) {
		switch (lightIndex) {
		case 0:
			for (GUISensorImpl tSens : sensorList) {
				if(tSens.getSensor().equals(sens)) {
					tSens.setRedColorFilter();
					return;
				}
			}
			break;
		case 1:
			for (GUISensorImpl tSens : sensorList) {
				if(tSens.getSensor().equals(sens)) {
					tSens.setRedColorFilter();
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
	public void setInAllarmToSensor(ArrayList<Sensor> sens) {
		for (Sensor sensor: sens) {
			this.setLightToSensor(sensor, 0);
		}
	}
	/**
	 * set a sensor list not in alarm. The change is graphic only
	 * @param sens sensor list
	 */
	public void resetAllarmToSensor(ArrayList<Sensor> sens) {
		for (Sensor sensor: sens) {
			this.setLightToSensor(sensor, 1);
		}
	}
	
	/**
	 * tell if the background object is set or not
	 * @return true or false if is set or not a background
	 */
	public boolean isSetBackground() {
		return (this.bgImage != null);
	}
}

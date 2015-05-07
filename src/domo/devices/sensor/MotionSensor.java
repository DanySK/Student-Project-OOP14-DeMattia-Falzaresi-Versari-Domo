package domo.devices.sensor;


import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.Observable;

import domo.devices.Sensor;
import domo.devices.SensorStatus;
import domo.devices.SensorTypology;

/**
 * 
 * @author Marco Versari
 *
 */

public class MotionSensor extends Observable implements Sensor  {
	
	private SensorStatus status;
	private Point position;
	private double xPosition;
	private double yPosition;
	private Dimension size;
	private boolean alert;
	
	private static final Integer ID 			= 1234;
	private static final String NAME 			= "DOMO STANDARD MOTION SENSOR";
	private static final SensorTypology TYPE 	= SensorTypology.MOTION;	
	private static final String IMAGE 			= "res" + System.getProperty("file.separator").toString() + "addSensor.png";	
	
	@Override
	public int getId() {
		return ID;
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
	public SensorStatus getStatus() {
		return status;
	}

	@Override
	public double getXPosition() {
		return xPosition;
	}
	
	@Override
	public double getYPosition() {
		return yPosition;
	}

	@Override
	public boolean isInAlert() {
		return alert;		
	}

	@Override
	public void setLocation(double x, double y) {
		xPosition = x;
		yPosition = y;
	}

	@Override
	public String getImagePath() {	
		return IMAGE;
	}	
	
	@Override
	public Dimension getSize() {
		return size;
	}

	@Override
	public void setSize(final Dimension pSize) {
		size = pSize;		
	}

	@Override
	public String toString() {
		return "SensorImpl [status=" + status + ", position=" + xPosition
				+ ", size=" + size + ", alert=" + alert + ", id=" + ID
				+ ", name=" + NAME + ", type=" + TYPE + ", image=" + IMAGE
				+ "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
//		result = prime * result + (alert ? 1231 : 1237);
//		result = prime * result
//				+ ((xPosition == null) ? 0 : xPosition.hashCode());
//		result = prime * result + ((size == null) ? 0 : size.hashCode());
//		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MotionSensor)) {
			return false;
		}
		MotionSensor other = (MotionSensor) obj;
		if (alert != other.alert) {
			return false;
		}
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		if (size == null) {
			if (other.size != null) {
				return false;
			}
		} else if (!size.equals(other.size)) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		return true;
	}

	@Override
	public Point getLocation() {
		return null;
	}

	@Override
	public void setLocation(Point point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAlert(boolean pAlert) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatus(SensorStatus pStatus) {
		// TODO Auto-generated method stub
		
	}
}

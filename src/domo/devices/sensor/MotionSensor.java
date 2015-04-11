package domo.devices.sensor;


import java.awt.Dimension;
import java.awt.Point;
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
	private Dimension size;
	private boolean alert;
	
	private static final Integer ID = 1234;
	private static final String NAME = "DOMO STANDARD MOTION SENSOR";
	private static final SensorTypology TYPE = SensorTypology.MOTION;	
	private static final String IMAGE = "..";	
	
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
	public Point getLocation() {
		return position;
	}

	@Override
	public boolean isInAlert() {
		return alert;		
	}

	@Override
	public void setLocation(final Point destination) {
		position = new Point(destination);
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
		return "SensorImpl [status=" + status + ", position=" + position
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
		result = prime * result + (alert ? 1231 : 1237);
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
}

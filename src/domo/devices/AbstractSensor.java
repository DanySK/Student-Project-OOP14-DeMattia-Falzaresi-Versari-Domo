package domo.devices;

import java.awt.Dimension;
import java.util.Observable;

import domo.devices.util.pair.PairImpl;

/**
 * 
 * @author Marco Versari
 *
 */
public abstract class AbstractSensor extends Observable implements Sensor {

	private SensorStatus status;
	private double xPosition;
	private double yPosition;
	private Dimension size;
	private boolean alert;
	private int id;
	
	@Override
	public abstract String getName();

	@Override
	public abstract SensorTypology getType();
	
	@Override
	public abstract String getImagePath();
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(final int pId) {
		this.id = pId;
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
	public void setLocation(final double x, final double y) {
		xPosition = x;
		yPosition = y;
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
				+ ", size=" + size + ", alert=" + alert + ", id=" + id
				+ "]";
	}

	@Override
	public void setAlert(final boolean pAlert) {
		this.alert = pAlert;		
		if (pAlert) {
			notifyObservers(new PairImpl<NotificationsType, Boolean>(NotificationsType.ALERT, pAlert));
		}
	}

	@Override
	public void setStatus(final SensorStatus pStatus) {
		this.status = pStatus;
		notifyObservers(new PairImpl<NotificationsType, SensorStatus>(NotificationsType.STATUS, pStatus));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	} 

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AbstractSensor)) {
			return false;
		}
		AbstractSensor other = (AbstractSensor) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

}

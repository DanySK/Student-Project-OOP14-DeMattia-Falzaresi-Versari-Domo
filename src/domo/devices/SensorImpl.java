package domo.devices;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Observable;

/**
 * 
 * @author Marco Versari
 *
 */

public class SensorImpl extends Observable implements Sensor  {
	
	private SensorStatus status;
	private Point position;
	private Dimension size;
	private boolean alert;
	
	private final Integer id;
	private final String name;
	private final SensorTypology type;
	private final String image;	
	
	/**
	 * Create A generic Sensor.
	 * @param pId id of the device
	 * @param pName name of the device
	 * @param pType type of the device
	 * @param pImage path o the device images
	 */
	public SensorImpl(final int pId, final String pName, final SensorTypology pType, final String pImage)  {
		id = pId;
		name = pName;
		type = pType;
		image = pImage;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SensorTypology getType() {
		return type;
	}

	@Override
	public SensorStatus getStatus() {
		return status;
	}

	@Override
	public Dimension getSize() {
		return size;
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
	public void setAlert(final boolean pAlert) {
		alert = pAlert;
		notifyObservers(alert);
	}

	@Override
	public void setStatus(final SensorStatus pStatus) {		
		status = pStatus;		
	}

	@Override
	public String getImagePath() {	
		return image;
	}

	@Override
	public void setSize(final Dimension pSize) {
		size = pSize;		
	}

	@Override
	public String toString() {
		return "SensorImpl [status=" + status + ", position=" + position
				+ ", size=" + size + ", alert=" + alert + ", id=" + id
				+ ", name=" + name + ", type=" + type + ", image=" + image
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alert ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (!(obj instanceof SensorImpl)) {
			return false;
		}
		final SensorImpl other = (SensorImpl) obj;
		if (alert != other.alert) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (image == null) {
			if (other.image != null) {
				return false;
			}
		} else if (!image.equals(other.image)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
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
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public double getXPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getYPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLocation(double x, double y) {
		// TODO Auto-generated method stub
		
	}

}

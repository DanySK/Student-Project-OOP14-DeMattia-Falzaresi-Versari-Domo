package domo.educational.inheritance;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 *
 */

public class SensorImpl implements Sensor {

	private int id;
	private String name;
	private boolean alert;
	protected int alertCount;

	public SensorImpl(String name,int id) {
		this.id = id;
		this.name = name;
		this.alert = false;
		this.alertCount = 0;
	}

	public int getId() {
		return this.id;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isInAlert() {
		return this.alert;
	}

	public void setAlert(boolean alert) {
		if (!this.alert && alert) {
			this.alertCount++;
		}
		this.alert = alert;
	}
	
	public int getAlertCount(){
		return this.alertCount;
	}

	@Override
	public String toString() {
		return "Sensor Name: " + this.name + "\nSensor Id: " + this.id + "\nAlert Count: " + this.alertCount;
	}
	
}

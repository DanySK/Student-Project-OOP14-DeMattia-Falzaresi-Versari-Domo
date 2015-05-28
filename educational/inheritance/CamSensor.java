package inheritance;
/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * The "CamSensor" class extends the standard sensor class and add a new "feature" the possibility to move (only in the X axis) the camera
 * to do it a new constructor is needed with an additional parameter "range" and two new method has been added, one to get the position and the
 * other one to set the position of the camera.
 * In addition the "toString" method has been override to print position and range 
 *
 */
public class CamSensor extends SensorImpl {
	
	private int range;
	private int position;
	
	/**
	 * the constructor of this class needs one additional parameter "range".
	 * @param name cam name
	 * @param id cam id
	 * @param range maximum range of rotation
	 */
	public CamSensor(String name, int id, int range) {
		super(name, id);
		this.range = range;
	}
	
	/**
	 * this method set the position of the camera lens, if the position is out of range nothing happen.
	 * @param pos position to be set
	 */
	public void setPosition(int pos) {
		if (pos <= this.range) {
			this.position = pos;
		}
	}
	
	/**
	 * this method enable the user to get the camera lens position.
	 * @return camera lens position
	 */
	public int getPosition() {
		return this.position;
	}
	
	@Override
	public String toString() {
		return "This is a Camera!\n" + super.toString() + "\nSensor Range: " + this.range + "\nSensor Position: " + this.position;
	}

}

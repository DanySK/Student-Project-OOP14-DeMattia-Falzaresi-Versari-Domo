package domo.graphic;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;

import javax.swing.BorderFactory;

import domo.devices.Sensor;
/**
 * Class that represent a sensor entity graphics representation.
 * 
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 *
 */
public class GUISensor extends ImageView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4801241871307624092L;
	/**
	 * X position.
	 * The position is normalized (from 0 to 1) to handle the screen different dimension 
	 */
	private double xScaleFactorPos;
	/**
	 * Y position.
	 * The position is normalized (from 0 to 1) to handle the screen different dimension 
	 */
	private double yScaleFactorPos;
	/**
	 * ImageView object that represent the 
	 */
	private ImageView parent;
	/**
	 * set the min scale for position and scale 
	 */
	private static final double MIN_FACTOR_SCALE = 0.0001;

	/**
	 * the sensor object to represent
	 */
	private Sensor sensor;

	/**
	 * activate or deactivate the mouse interaction
	 */
	private boolean mouseEnabled;
	/**
	 * the mouse event for take the last arrow mouse position
	 */
	private MouseEvent pressed;
	/**
	 * the last position used in image movement
	 */
	private Point pPoint;
	/**
	 * if the sensor is select or not
	 */
	private boolean select = true;

	/**
	 * GUISensorImpl constructor: create an empty graphic sensor representation
	 * after that must to call 'setSensorAsNew()' or 'updateLocationFromLoad()'.
	 * @param imagePath the sensor image path
	 * @param parentBounds the parent bound to fit sensor
	 * @param sens the sensor data structure to represent
	 * @throws IOException exception when load image file fail
	 */
	public GUISensor(final String imagePath, final ImageView parentBounds, final Sensor sens) throws IOException {
		super(imagePath);
		this.sensor = sens;
		this.setMouseEnabled(true);
		this.parent = parentBounds;
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));

		if (this.getMouseListeners().length == 0) {
			this.addMouseListener(
					new MouseAdapter() {

						public void mouseClicked(final MouseEvent e) {
							if (!sensor.isInAlert()) {
								if ((e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) && select && mouseEnabled) {
									GUISensor.this.rotate90(true);
									sensor.setDegree(GUISensor.this.getRotationDegree());
								}
								if (e.getButton() == MouseEvent.BUTTON1 && mouseEnabled) {
									select = select ? false : true;
									if (select) {
										GUISensor.this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
									} else {
										GUISensor.this.setBorder(null);
									}
								}
							}
						}

						public void mousePressed(final MouseEvent e) {
							if (e.getSource() == GUISensor.this && mouseEnabled && !sensor.isInAlert()) {
								pressed = e;
								pPoint = GUISensor.this.getLocation();
							}
						}
					}
					);

			this.addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(final MouseWheelEvent e) {
					if (e.getSource() == GUISensor.this && mouseEnabled && select && !sensor.isInAlert()) {
						if (e.getPreciseWheelRotation() > 0) {
							GUISensor.this.rotate90(true);
							sensor.setDegree(GUISensor.this.getRotationDegree());
						} else {
							GUISensor.this.rotate90(false);
							sensor.setDegree(GUISensor.this.getRotationDegree());
						}
					}
				}
			});

			this.addMouseMotionListener(
					new MouseMotionAdapter() {
						public void mouseDragged(final MouseEvent e) {
							if (e.getSource() == GUISensor.this && mouseEnabled && select && !sensor.isInAlert()) {
								pPoint = GUISensor.this.getLocation(pPoint);
								final int x = GUISensor.this.getLocation().x +  (e.getXOnScreen() - pressed.getXOnScreen());
								final int y = GUISensor.this.getLocation().y +  (e.getYOnScreen() - pressed.getYOnScreen());
								GUISensor.this.setLocation(x, y);
								GUISensor.this.updateFactors();
							}
							pressed = e;
						}
					}
					);  
		}
	}

	/**
	 * Update the position factor base to mouse drag move
	 */
	private void updateFactors() {
		this.xScaleFactorPos = Math.max(((double) this.getX() - (double) parent.getX()) / (double) parent.getWidth(), MIN_FACTOR_SCALE);
		this.yScaleFactorPos = Math.max(((double) this.getY() - (double) parent.getY()) / (double) parent.getHeight(), MIN_FACTOR_SCALE);
		if (sensor != null) {
			sensor.setLocation(xScaleFactorPos, yScaleFactorPos);
		}
	}

	/**
	 * Set sensor as a new sensor (not from a load file).
	 */
	public void setSensorAsNew() {
		this.setLocation(10, 10);
		this.updateFactors();
	}

	/**
	 * Update sensor location base to sensor load from file.
	 */
	public void updateLocationFromLoad() {
		final double xFactor = sensor.getXPosition();
		final double yFactor = sensor.getYPosition();
		this.xScaleFactorPos = xFactor;
		this.yScaleFactorPos = yFactor;
		final double newX = parent.getWidth() * xFactor + parent.getX();
		final double newY = parent.getHeight() * yFactor + parent.getY();
		this.setLocation((int) newX, (int) newY);
		this.rotate(sensor.getDegree());

	}

	/**
	 * set location. this set the sensor location and 
	 * limit the position inside the parent bounds (working area background image)
	 * @param x the X position
	 * @param y the Y position
	 */
	public void setLocation(final int x, final int y) {

		int xPos = x - this.parent.getX();
		int yPos = y - this.parent.getY();

		xPos = (int) (Math.min(xPos, this.parent.getWidth() - this.getWidth() - 1));
		yPos = (int) (Math.min(yPos, this.parent.getHeight() - this.getHeight() - 1));
		xPos = (int) (Math.max(xPos, 1));
		yPos = (int) (Math.max(yPos, 1));

		xPos += this.parent.getX();
		yPos += this.parent.getY();

		super.setLocation(xPos, yPos);

	}

	/**
	 * set the image scale (0 - 1 range value). Typically when parent frame resize
	 * @param imgScale the image scale factor (0 - 1)
	 */
	public void setScale(final double imgScale) {
		super.setScale(imgScale);
		int newX = (int) ((double) this.parent.getWidth() * xScaleFactorPos);
		int newY = (int) ((double) this.parent.getHeight() * yScaleFactorPos);
		newX += this.parent.getX();
		newY += this.parent.getY();
		this.setLocation(newX, newY);
	}

	/** 
	 * set the image color filter to red.
	 */
	public void setRedColorFilter() {
		if (sensor.isInAlert() && this.select) {
			this.setSelect(false);
		}
		this.setColorBorder(ColorFilter.COLOR_FILTER_RED);
		this.repaint();
	}

	/** 
	 * set the image color filter to default (no filter color).
	 */
	public void setResetFilter() {
		if (this.select) {
			this.select = true;
			this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		} else {
			this.setColorBorder(ColorFilter.COLOR_FILTER_NONE);
			this.repaint();
		}
	}

	/**
	 * set the mouse enable.
	 * @param enableMouse 	true - mouse enabled 
	 * 						false - mouse disabled.
	 */
	public void setMouseEnabled(final boolean enableMouse) {
		this.mouseEnabled = enableMouse;
	}

	/**
	 * Tell if the mouse is enabled or not.
	 * @return 	true - mouse is enable
	 * 			false - mouse is disabled.
	 */
	public boolean isMouseEnabled() {
		return this.mouseEnabled;
	}

	/**
	 * Tell if a sensor is selected.
	 * @return 	true - the sensor is selected
	 * 			false - the sensor is not selected
	 */
	public boolean isSelect() {
		return this.select;
	}

	/**
	 * 
	 * @return the specific sensor that the GUISensorImpl class instance represent
	 */
	public Sensor getSensor() {
		return this.sensor;
	}

	/**
	 * 
	 * @param sens the specific sensor that the GUISensorImpl class instance must be represent
	 */
	public void setSensor(final Sensor sens) {
		this.sensor = sens;
	}

	/**
	 * set if sensor is select or not.
	 * @param sel true / false
	 */
	public void setSelect(final boolean sel) {
		this.select = sel;
		GUISensor.this.setBorder(null);
	}
}

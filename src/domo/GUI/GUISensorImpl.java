package domo.GUI;

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
 * Class that represent a sensor entity graphics representation
 * 
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 *
 */
public class GUISensorImpl extends ImageViewimpl implements GUISensor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4801241871307624092L;
	/**
	 * X position.
	 * The position is normalized (from 0 to 1) to handle the screen different dimension 
	 */
	double xScaleFactorPos;
	/**
	 * Y position.
	 * The position is normalized (from 0 to 1) to handle the screen different dimension 
	 */
	double yScaleFactorPos;
	/**
	 * ImageView object that represent the 
	 */
	ImageViewimpl parent;
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
	private boolean isMouseEnabled = false;
	/**
	 * the mouse event for take the last arrow mouse position
	 */
	private MouseEvent pressed;
	/**
	 * the last position 
	 */
	private Point pPoint;
	/**
	 * if the sensor is select or not
	 */
	private boolean isSelect = true;
	
	/**
	 * GUISensorImpl constructor: create an empty graphic sensor representation
	 * after that must to call 'setSensorAsNew()' or 'updateLocationFromLoad()'
	 * @param imagePath the sensor image path
	 * @param parentBounds the parent bound to fit sensor
	 * @param sens the sensor data structure to represent
	 * @throws IOException exception when load image file fail
	 */
	public GUISensorImpl (String imagePath, ImageViewimpl parentBounds, Sensor sens) throws IOException {
		super(imagePath);
		this.sensor = sens;
		this.setMouseEnabled(true);
		this.parent = parentBounds;
		this.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		
		if (this.getMouseListeners().length == 0) {
			this.addMouseListener(
					new MouseAdapter() {
						
						public void mouseClicked(final MouseEvent e) {
							if ((e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) && isSelect && isMouseEnabled) {
								GUISensorImpl.this.rotate90(true);
								sensor.setDegree(GUISensorImpl.this.getRotationDegree());
							}
							if (e.getButton() == MouseEvent.BUTTON1 && isMouseEnabled) {
								isSelect = !isSelect;
								if (isSelect) {
									GUISensorImpl.this.setBorder(BorderFactory.createLineBorder(Color.red, 1));
								} else {
									GUISensorImpl.this.setBorder(null);
								}
							}
						}

						public void mousePressed(final MouseEvent e) {
							if (e.getSource() == GUISensorImpl.this && isMouseEnabled) {
								pressed = e;
								pPoint = GUISensorImpl.this.getLocation();
							}
						}
					}
				);

			this.addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(final MouseWheelEvent e) {
					if (e.getSource() == GUISensorImpl.this && isMouseEnabled && isSelect) {
						if (e.getPreciseWheelRotation() > 0) {
							GUISensorImpl.this.rotate90(true);
							sensor.setDegree(GUISensorImpl.this.getRotationDegree());
						} else {
							GUISensorImpl.this.rotate90(false);
							sensor.setDegree(GUISensorImpl.this.getRotationDegree());
						}
					}
				}
			});

			this.addMouseMotionListener(
					new MouseMotionAdapter() {
						public void mouseDragged(final MouseEvent e) {
							if (e.getSource() == GUISensorImpl.this && isMouseEnabled && isSelect) {
								pPoint = GUISensorImpl.this.getLocation(pPoint);
								int x = GUISensorImpl.this.getLocation().x +  (e.getXOnScreen() - pressed.getXOnScreen());
								int y = GUISensorImpl.this.getLocation().y +  (e.getYOnScreen() - pressed.getYOnScreen());
								GUISensorImpl.this.setLocation(x, y);
								GUISensorImpl.this.updateFactors();
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
		if(sensor != null) {
			sensor.setLocation(xScaleFactorPos, yScaleFactorPos);
		}
	}
	
	/**
	 * Set sensor as a new sensor (not from a load file)
	 */
	public void setSensorAsNew() {
		this.setLocation(10, 10);
		this.updateFactors();
	}
	
	/**
	 * Update sensor location base to sensor load from file
	 */
	public void updateLocationFromLoad() {
		double xFactor = sensor.getXPosition();
		double yFactor = sensor.getYPosition();
		this.xScaleFactorPos = xFactor;
		this.yScaleFactorPos = yFactor;
		double newX = (parent.getWidth() * xFactor) + parent.getX();
		double newY = (parent.getHeight() * yFactor) + parent.getY();
		this.setLocation((int) newX, (int) newY);
		this.rotate(sensor.getDegree());

	}
	
	/**
	 * set location. this set the sensor location and 
	 * limit the position inside the parent bounds (working area background image)
	 */
	public void setLocation(int x, int y) {

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
	 * set the image scale. Typically when parent frame resize
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
	 * set the image color filter to red
	 */
	public void setRedColorFilter() {
		this.parent.setColorFilter(ColorFilter.COLOR_FILTER_RED);
	}
	
	/** 
	 * set the image color filter to default (no filter color)
	 */
	public void setResetFilter() {
		this.parent.setColorFilter(ColorFilter.COLOR_FILTER_NONE);
	}
	
	/**
	 * set the mouse enable
	 * @param enableMouse 	true - mouse enabled 
	 * 						false - mouse disabled
	 */
	public void setMouseEnabled(final boolean enableMouse) {
		this.isMouseEnabled = enableMouse;
	}
	
	/**
	 * Tell if the mouse is enabled or not
	 * @return 	true - mouse is enable
	 * 			false - mouse is disabled
	 */
	public boolean getMouseEnabled() {
		return this.isMouseEnabled;
	}
	
	/**
	 * Tell if a sensor is selected
	 * @return 	true - the sensor is selected
	 * 			false - the sensor is not selected
	 */
	public boolean isSelect() {
		return this.isSelect;
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
	public void setSensor(Sensor sens) {
		this.sensor = sens;
	}
}

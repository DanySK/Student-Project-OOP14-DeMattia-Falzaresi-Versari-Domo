package domo.GUI;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;

import javax.swing.BorderFactory;

import domo.devices.Sensor;

public class GUISensorImpl extends ImageView{
	
	double xScaleFactorPos;
	double yScaleFactorPos;
	ImageView parent;
	private static final double MIN_FACTOR_SCALE = 0.0001;

	private Sensor sensor;
	
	private boolean isMouseEnabled = false;
	private MouseEvent pressed;
	private Point pPoint;
	private boolean isSelect = true;
	
	public GUISensorImpl (String imagePath, ImageView parentBounds) throws IOException {
		super(imagePath);
		this.setMouseEnabled(true);
		this.parent = parentBounds;
		this.setLocation(10, 10);
		this.updateFactors();
		this.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		
		if (this.getMouseListeners().length == 0) {
			this.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(final MouseEvent e) {
							if ((e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) && isSelect && isMouseEnabled) {
								GUISensorImpl.this.rotate90(true);
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
						} else {
							GUISensorImpl.this.rotate90(false);
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

	private void updateFactors() {
		this.xScaleFactorPos = Math.max(((double) this.getX() - (double) parent.getX()) / (double) parent.getWidth(), MIN_FACTOR_SCALE);
		this.yScaleFactorPos = Math.max(((double) this.getY() - (double) parent.getY()) / (double) parent.getHeight(), MIN_FACTOR_SCALE);
		if(sensor != null) {
			sensor.setLocation(xScaleFactorPos, yScaleFactorPos);
		}
		
	}
	
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
	
	public void setScale(final double imgScale) {
		super.setScale(imgScale);
		
		int newX = (int) ((double) this.parent.getWidth() * xScaleFactorPos);
		int newY = (int) ((double) this.parent.getHeight() * yScaleFactorPos);
	
		newX += this.parent.getX();
		newY += this.parent.getY();
		
		this.setLocation(newX, newY);
	}
	
	public void setMouseEnabled(final boolean enableMouse) {
		this.isMouseEnabled = enableMouse;
	}
	
	public boolean getMouseEnabled() {
		return this.isMouseEnabled;
	}
	
	public boolean isSelect() {
		return this.isSelect;
	}

	public Sensor getSensor() {
		return this.sensor;
	}
	
	public void setSensor(Sensor sens) {
		this.sensor = sens;
	}
}

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

public class GUISensorImpl extends ImageView{
	
	double xScaleFactorPos;
	double yScaleFactorPos;
	ImageView parent;
	
	private boolean isMouseEnabled = false;
	private MouseEvent pressed;
	private Point pPoint;
	private boolean isSelect = false;
	
	public GUISensorImpl (String imagePath, ImageView parentBounds) throws IOException {
		super(imagePath);
		this.setMouseEnabled(true);
		this.parent = parentBounds;
		this.updateFactors();
		if (this.getMouseListeners().length == 0) {
			this.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(final MouseEvent e) {
							System.out.println("image click" + e.getButton());
							if ((e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) && isSelect && isMouseEnabled) {
								GUISensorImpl.this.rotate90();
							}
							if (e.getButton() == MouseEvent.BUTTON1 && isMouseEnabled) {
								isSelect = !isSelect;
								if (isSelect) {
									//ImageView.this.setColorFilter(ColorFilter.COLOR_FILTER_RED);
									GUISensorImpl.this.setBorder(BorderFactory.createLineBorder(Color.red, 1));
								} else {
									//ImageView.this.setColorFilter(ColorFilter.COLOR_FILTER_NONE);
									GUISensorImpl.this.setBorder(null);

								}
							}
						}
						
						public void mousePressed(final MouseEvent e) {
							//ImageView.this.setScale(0.90);
							//System.out.println("scala: " + ImageView.this.totalScaleFactor);
							if (e.getSource() == GUISensorImpl.this && isMouseEnabled) {
								pressed = e;
								pPoint = GUISensorImpl.this.getLocation();
								System.out.println("image press");
							}
						}
					}
					);

			this.addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(final MouseWheelEvent e) {
					
					if (e.getSource() == GUISensorImpl.this && isMouseEnabled && isSelect) {
						double rotationAngle = e.getPreciseWheelRotation() > 0 ? 90 : -90;
						System.out.println("wheel: " + e.getPreciseWheelRotation() + "\nAngolo: " + rotationAngle);
						GUISensorImpl.this.rotate(rotationAngle);
					}
				}
			});
			
			this.addMouseMotionListener(
					new MouseMotionAdapter() {
						public void mouseDragged(final MouseEvent e) {
							if (e.getSource() == GUISensorImpl.this && isMouseEnabled) {
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
		
		this.xScaleFactorPos = Math.max(((double) this.getX() - (double) parent.getX()) / (double) parent.getWidth(), 0.0001);
		this.yScaleFactorPos = Math.max(((double) this.getY() - (double) parent.getY()) / (double) parent.getHeight(), 0.0001);
		System.out.println("this.X: " + getX());
		System.out.println("this.Y: " + getY() + "");
		System.out.println("parent.X: " + parent.getX());
		System.out.println("parent.Y: " + parent.getY() + "");
		System.out.println("parent.W: " + parent.getWidth());
		System.out.println("parent.H: " + parent.getHeight() + "\n");
	}
	
	public void setLocation(int x, int y) {
		//this.parent = newParentBounds;

//		System.out.println("x: " + x);
//		System.out.println("y: " + y);
		//this.updateFactors();
	
		int xPos = x - this.parent.getX();
		int yPos = y - this.parent.getY();
		xPos = (int) (Math.min(xPos, this.parent.getWidth() - this.getWidth() - 1));
		yPos = (int) (Math.min(yPos, this.parent.getHeight() - this.getHeight() - 1));
		xPos = (int) (Math.max(xPos, 1));
		yPos = (int) (Math.max(yPos, 1));
//		System.out.println("xPos: " + xPos);
//		System.out.println("yPos: " + yPos);
		xPos += this.parent.getX();
		yPos += this.parent.getY();
//		System.out.println("xPos: " + xPos);
//		System.out.println("yPos: " + yPos + "\n");
		
		super.setLocation(xPos, yPos);
		
	}
	
	public void setScale(final double imgScale) {
		super.setScale(imgScale);
		//this.parent = toParentBounds;
		//this.updateFactors();

//		System.out.println("xOld: " + this.getX());
//		System.out.println("yOld: " + getY());
//		System.out.println("xFactor: " + xScaleFactorPos);
//		System.out.println("yFactor: " + yScaleFactorPos);
		//this.updateFactors();
		
		System.out.println("xFactor: " + xScaleFactorPos);
		System.out.println("yFactor: " + yScaleFactorPos);
		
		int newX = (int) ((double) this.parent.getWidth() * xScaleFactorPos);
		int newY = (int) ((double) this.parent.getHeight() * yScaleFactorPos);
		
		System.out.println("newX: " + newX);
		System.out.println("newY: " + newY);
		newX += this.parent.getX();
		newY += this.parent.getY();
		
//		System.out.println("parentWidth: " + this.parent.getWidth() + "\tnuovo delta X" + this.parent.getWidth() * this.xScaleFactorPos);
//		System.out.println("parentHeight: " + this.parent.getHeight() + "\tnuovo delta Y" + this.parent.getHeight() * this.yScaleFactorPos);
//		
		System.out.println("newX: " + newX);
		System.out.println("newY: " + newY + "\n");
		this.setLocation(newX, newY);
//		System.out.println("newGetX: " + getX());
//		System.out.println("newGetY: " + getY() + "\n");
		
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
	
}

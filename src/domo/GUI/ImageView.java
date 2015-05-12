package domo.GUI;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import domo.GUI.ImageViewimpl.ColorFilter;

public interface ImageView {

	/**
	 * set the background image
	 * @param image the image BufferedImage object 
	 */
	public void setImage(BufferedImage image);

	/**
	 * set the background image
	 * @param imagePath the image path to set as background
	 * @throws IOException exception throw when open file fail
	 */
	public void setImage(String imagePath) throws IOException;

	/**
	 * rotate the image to a degree
	 * @param degree rotation in degree
	 */
	public void rotate(double degree);

	/**
	 * rotate in 90 degree step
	 * @param clockwise <br>- true clockwise  <br> - false counterclockwise
	 * 
	 */
	public void rotate90(boolean clockwise);

	/**
	 * set the color filter 
	 * 
	 * @param filter the color filter base to the enum ColorFilter
	 */
	public void setColorFilter(ColorFilter filter);

	/**
	 *  fit the image dimension to maximize 
	 * in its parent
	 * @param parentBound the parent to fit
	 */
	public void setAspectFillToParent(Rectangle parentBound);

	/**
	 * set the image scale
	 * @param imgScale double factor scale 
	 */
	public void setScale(double imgScale);

	/**
	 * tell if a point is contained in image 
	 * @param point the point to control
	 * @return true if is contained, false if is not contained
	 */
	public boolean containsPoint(Point point);

	/**
	 * get the current scale factor
	 * @return scale factor
	 */
	public double getScale();

	/**
	 * get the current rotation
	 * @return current rotation
	 */
	public double getRotationDegree();

}
package domo.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * 
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 *
 */
public class ImageView extends JLabel {
/**
 * Color filter type:<br><br>COLOR_FILTER_NONE,<br>
						COLOR_FILTER_RED,<br>
						COLOR_FILTER_GREEN<br>.
		
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 *
 */
	public enum ColorFilter {
		/**
		 * To reset the filter.
		 */
		COLOR_FILTER_NONE,
		/**
		 * color filter red.
		 */
		COLOR_FILTER_RED,
		/**
		 * color filter green.
		 */
		COLOR_FILTER_GREEN
		
	}
	/**
	 * serial version
	 */
	private static final long serialVersionUID = -1218944812028248824L;

	private static final long RIGHT_ANGLE = 90;
	/**
	 * the image show in parent JPanel
	 * used because it lost definition when change from 
	 * scale down to scale up
	 */
	private BufferedImage currentImage;
	
	/**
	 * the original image reference
	 */
	private BufferedImage originalImage;
	/**
	 * the min scale factor available
	 */
	private static final double MIN_FACTOR_SCALE = 0.0001;

	/**
	 * the total rotation according to (total radius + radius to add) % 360
	 */
	private double totalRotationDegree;
	/**
	 * total scale factor
	 */
	private double totalScaleFactor = 1;
	
	/**
	 * standard constructor that create an empty object.
	 */
	public ImageView()	{
		super();
		this.setOpaque(true);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
		
	}
	
	/**
	 * create an instance with a image.
	 * @param imagePath the image path
	 * @throws IOException throw when open file fail
	 */
	public ImageView(final String imagePath) throws IOException {
		super();
		this.setImage(imagePath);
	}
	
	/**
	 * create an instance with a image.
	 * @param imageBuf the BufferedImage object to set as image
	 */
	public ImageView(final BufferedImage imageBuf) {
		super();
		this.setImage(imageBuf);
	}
	
	/**
	* create an instance with a image and put in a middle position.
	 * @param imageBuf the BufferedImage object to set as image
	 * @param middlePoint the middle point
	 */
	public ImageView(final BufferedImage imageBuf, final Point middlePoint) {
		this(imageBuf);
		middlePoint.x = middlePoint.x - (this.getWidth() / 2);
		middlePoint.y = middlePoint.y - (this.getHeight() / 2);
		this.setLocation(middlePoint);
	}
	
	/**
	 * create an instance with a image and fit its dimension to maximize.
	 * in its parent
	 * @param imageBuf the BufferedImage object to set as image
	 * @param parentBound the parent to fit
	 */
	public ImageView(final BufferedImage imageBuf, final Rectangle parentBound) {
		this(imageBuf);
		this.setAspectFillToParent(parentBound);
	}
/**
 * Set the image.
 * @param image the image BufferedImage object
 */
	public void setImage(final BufferedImage image) {
		this.setIcon(new ImageIcon(image));
		this.setSize(new Dimension(image.getWidth(), image.getHeight()));
		this.repaint();
		
		this.currentImage = image;
		if (this.originalImage == null) {
			 this.originalImage = copyImage(this.currentImage);
		}
	}
	/**
	 * set the image base to image path.
	 * @param imagePath the image path
	 * @throws IOException throw when open file fail
	 */
	public void setImage(final String imagePath) throws IOException {
		this.setImage(ImageIO.read(new File(imagePath)));
	}

	/**
	 * rotate the image base to the totalrotation private variable
	 */
	private void rotate() {
		final double radians = this.totalRotationDegree * Math.PI / 180;
		final AffineTransform transform = new AffineTransform();
	    transform.rotate(radians, this.getWidth() / 2, this.getHeight() / 2);
	    final  AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    this.currentImage = op.filter(this.currentImage, null);
	    this.setImage(this.currentImage);
	}
	
	/**
	 * rotate the image.
	 * @param degree degree to rotate
	 */
	public void rotate(final double degree) {
	    this.totalRotationDegree  = (this.totalRotationDegree + degree) % 360;
	    this.rotate();
	}
	
	/**
	 *  rotate by 90 degree step .
	 * @param clockwise rotate to clockwise or counterclockwise
	 */
	public void rotate90(final boolean clockwise) {
		final double radians = clockwise ? 90 * Math.PI / 180 : -RIGHT_ANGLE * Math.PI / (RIGHT_ANGLE * 2);
		final AffineTransform transform = new AffineTransform();
	    transform.rotate(radians, this.getWidth() / 2, this.getHeight() / 2);
	    final AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    this.currentImage = op.filter(this.currentImage, null);
	    this.setImage(this.currentImage);
	    this.totalRotationDegree = (this.totalRotationDegree + 90) % 360;
	    
		if (this.totalRotationDegree % 180 != 0) {
			final Dimension t = this.getSize();
			this.setSize(t.height, t.width);
		}
	}
	
	/**
	 * set the border color.
	 * @param filter the color
	 */
	public void setColorFilter(final ColorFilter filter) {

		if (filter == ColorFilter.COLOR_FILTER_RED) {
			this.setBorder(BorderFactory.createLineBorder(Color.red, 3, true));
		}
		if (filter == ColorFilter.COLOR_FILTER_NONE) {
			this.setBorder(BorderFactory.createEmptyBorder());
		}
	}
	
	/**
	 *  set the image dimension to fit the parent dimension.
	 * @param parentBound the parent dimension and position
	 */
	public void setAspectFillToParent(final Rectangle parentBound) {
		parentBound.setLocation(new Point(0, 0));
		final double widthFactor = parentBound.getWidth() / this.currentImage.getWidth();
		final double heightFactor = parentBound.getHeight() / this.currentImage.getHeight();
		
		final double factorToUse = widthFactor >= heightFactor ? heightFactor : widthFactor;
		this.setScaleForAspetcFill(factorToUse);
		final Point middlePoint = new Point((int) parentBound.getCenterX(), (int) parentBound.getCenterY());
		middlePoint.x = middlePoint.x - (this.getWidth() / 2);
		middlePoint.y = middlePoint.y - (this.getHeight() / 2);
		
		this.setLocation(middlePoint);
	}
	
	/**
	 * private scale for aspect fit method
	 * @param imgScale double factor scale 
	 */
	private void setScaleForAspetcFill(final double imgScale) {
		this.totalScaleFactor *= imgScale;
		this.setScale(this.totalScaleFactor);
	}
	
	/**
	 * set image scale.
	 * @param imgScale scale factor (0 - 1)
	 */
	public void setScale(final double imgScale) {
		final BufferedImage imgToResize = copyImage(this.originalImage);
		this.totalScaleFactor = Math.max(MIN_FACTOR_SCALE, imgScale);
	
		final int newWidth = new Double(this.originalImage.getWidth() * this.totalScaleFactor).intValue();
		final int newHeight = new Double(this.originalImage.getHeight() * this.totalScaleFactor).intValue();
		
		final BufferedImage retImg = new BufferedImage(newWidth, newHeight, this.originalImage.getType());
		final Graphics2D g = retImg.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(imgToResize, 0, 0, newWidth, newHeight, 0, 0, imgToResize.getWidth(), imgToResize.getHeight(), null);
	    g.dispose();
	    
		this.currentImage = copyImage(retImg);
		this.setImage(this.currentImage);
		
		this.rotate();
	}

	/**
	 * tell that the point passed is contained into image.
	 * @param point the point to control
	 * @return true if is contained, false otherwise
	 */
	public boolean containsPoint(final Point point) {
		return this.getBounds().contains(point);
	}
	
/**
 * get the scale factor.
 * @return scale factor
 */
	public double getScale() {
		return this.totalScaleFactor;
	}
	
	/**
	 * create a Buffered Image object copy
	 * @param source BufferedImage to copy
	 * @return a BufferedImage copy
	 */
	private static BufferedImage copyImage(final BufferedImage source) {
		final BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		final Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	
	/**
	 * get the rotation degree object.
	 * @return the total rotation
	 */
	public double getRotationDegree() {
		return this.totalRotationDegree;
	}

}

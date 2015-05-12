package domo.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * 
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 *
 */
public class ImageViewimpl extends JLabel implements ImageView {
/**
 * Color filter type:<br><br>COLOR_FILTER_NONE,<br>
						COLOR_FILTER_RED,<br>
						COLOR_FILTER_GREEN<br>
		
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 *
 */
	public enum ColorFilter {
		COLOR_FILTER_NONE,
		COLOR_FILTER_RED,
		COLOR_FILTER_GREEN
		
	}
	/**
	 * serial version
	 */
	private static final long serialVersionUID = -1218944812028248824L;

	/**
	 * the image show in parent JPanel
	 * used because it lost definition when change from 
	 * scale down to scale up
	 */
	private BufferedImage currentImage;
	/**
	 * the filtered image 
	 */
	private BufferedImage filtImage;
	/**
	 * the original image reference
	 */
	private BufferedImage originalImage;
	/**
	 * the min scale factor available
	 */
	private final double MIN_FACTOR_SCALE = 0.0001;

	/**
	 * the total rotation according to (total radius + radius to add) % 360
	 */
	private double totalRotationDegree = 0;
	/**
	 * total scale factor
	 */
	private double totalScaleFactor = 1;
	
	/**
	 * standard constructor that create an empty object
	 */
	public ImageViewimpl ()	{
		super();
		this.setOpaque(true);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
		
	}
	
	/**
	 * create an instance with a image
	 * @param imagePath the image path
	 * @throws IOException throw when open file fail
	 */
	public ImageViewimpl (final String imagePath) throws IOException	{
		super();
		this.setImage(imagePath);
	}
	
	/**
	 * create an instance with a image
	 * @param imageBuf the BufferedImage object to set as image
	 */
	public ImageViewimpl (final BufferedImage imageBuf) {
		super();
		this.setImage(imageBuf);
	}
	
	/**
	* create an instance with a image and put in a middle position
	 * @param imageBuf the BufferedImage object to set as image
	 * @param middlePoint the middle point
	 */
	public ImageViewimpl (final BufferedImage imageBuf, Point middlePoint) {
		this(imageBuf);
		middlePoint.x = middlePoint.x - (this.getWidth() / 2);
		middlePoint.y = middlePoint.y - (this.getHeight() / 2);
		this.setLocation(middlePoint);
	}
	
	/**
	 * create an instance with a image and fit its dimension to maximize 
	 * in its parent
	 * @param imageBuf the BufferedImage object to set as image
	 * @param parentBound the parent to fit
	 */
	public ImageViewimpl (final BufferedImage imageBuf, Rectangle parentBound) {
		this(imageBuf);
		this.setAspectFillToParent(parentBound);
	}
/* (non-Javadoc)
 * @see domo.GUI.ImageView#setImage(java.awt.image.BufferedImage)
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
	/* (non-Javadoc)
	 * @see domo.GUI.ImageView#setImage(java.lang.String)
	 */
	public void setImage(final String imagePath) throws IOException {
		this.setImage(ImageIO.read(new File(imagePath)));
	}

	/**
	 * rotate the image base to the totalrotation private variable
	 */
	private void rotate() {
		double radians = this.totalRotationDegree * Math.PI / 180;
		AffineTransform transform = new AffineTransform();
	    transform.rotate(radians, this.getWidth() / 2, this.getHeight() / 2);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    this.currentImage = op.filter(this.currentImage, null);
	    this.setImage(this.currentImage);
	}
	
	/* (non-Javadoc)
	 * @see domo.GUI.ImageView#rotate(double)
	 */
	public void rotate(final double degree) {
	    this.totalRotationDegree  = (this.totalRotationDegree + degree) % 360;
	    this.rotate();
	}
	
	/* (non-Javadoc)
	 * @see domo.GUI.ImageView#rotate90(boolean)
	 */
	public void rotate90(boolean clockwise) {
		double radians = clockwise ? 90 * Math.PI / 180 : -90 * Math.PI / 180;
		AffineTransform transform = new AffineTransform();
	    transform.rotate(radians, this.getWidth() / 2, this.getHeight() / 2);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    this.currentImage = op.filter(this.currentImage, null);
	    this.setImage(this.currentImage);
	    this.totalRotationDegree = (this.totalRotationDegree + 90) % 360;
	    
		if (this.totalRotationDegree % 180 != 0) {
			Dimension t = this.getSize();
			this.setSize(t.height, t.width);
		}
	}
	
	/* (non-Javadoc)
	 * @see domo.GUI.ImageView#setColorFilter(domo.GUI.ImageViewimpl.ColorFilter)
	 */
	public void setColorFilter(final ColorFilter filter) {
		Image tmpImg;
		RGBImageFilter filterRGB;
		if (filter == ColorFilter.COLOR_FILTER_NONE) {
			this.setImage(this.originalImage);
			this.setScale(this.totalScaleFactor);
			this.rotate(this.totalRotationDegree);
			return;
		}
		
		if (filter == ColorFilter.COLOR_FILTER_RED) {
			filterRGB = new RGBImageFilter() {
				
				@Override
				public int filterRGB(final int x, final int y, final int rgb) {
					return rgb & 0xFFCCD6CC;
				}
			};
			
		} else {
			filterRGB = new RGBImageFilter() {
				
				@Override
				public int filterRGB(final int x, final int y, final int rgb) {
					return rgb & 0xFF00FF00;
				}
			};
			
		}
		tmpImg = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(this.currentImage.getSource(), filterRGB));
		BufferedImage retImg;
		if (tmpImg instanceof BufferedImage) {
	        retImg = (BufferedImage) tmpImg;
	    } else {

	    	retImg = new BufferedImage(tmpImg.getWidth(null), tmpImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    	Graphics2D bGr = retImg.createGraphics();
	    	bGr.drawImage(tmpImg, 0, 0, null);
	    	bGr.dispose();
	    }
		this.filtImage = retImg;
		this.setImage(this.filtImage);

	}
	
	/* (non-Javadoc)
	 * @see domo.GUI.ImageView#setAspectFillToParent(java.awt.Rectangle)
	 */
	public void setAspectFillToParent(Rectangle parentBound) {
		parentBound.setLocation(new Point(0, 0));
		double widthFactor = parentBound.getWidth() / this.currentImage.getWidth();
		double heightFactor = parentBound.getHeight() / this.currentImage.getHeight();
		
		double factorToUse = widthFactor >= heightFactor ? heightFactor : widthFactor;
		this.setScaleForAspetcFill(factorToUse);
		Point middlePoint = new Point((int) parentBound.getCenterX(), (int) parentBound.getCenterY());
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
	
	/* (non-Javadoc)
	 * @see domo.GUI.ImageView#setScale(double)
	 */
	public void setScale(final double imgScale) {
		BufferedImage imgToResize = copyImage(this.originalImage);
		this.totalScaleFactor = Math.max(MIN_FACTOR_SCALE, imgScale);
	
		int newWidth = new Double(this.originalImage.getWidth() * this.totalScaleFactor).intValue();
		int newHeight = new Double(this.originalImage.getHeight() * this.totalScaleFactor).intValue();
		
		BufferedImage retImg = new BufferedImage(newWidth, newHeight, this.originalImage.getType());
	    Graphics2D g = retImg.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(imgToResize, 0, 0, newWidth, newHeight, 0, 0, imgToResize.getWidth(), imgToResize.getHeight(), null);
	    g.dispose();
	    
		this.currentImage = null;
		this.currentImage = copyImage(retImg);
		this.setImage(this.currentImage);
		
		this.rotate();
	}

	/* (non-Javadoc)
	 * @see domo.GUI.ImageView#containsPoint(java.awt.Point)
	 */
	public boolean containsPoint(Point point) {
		return this.getBounds().contains(point);
	}
	/* (non-Javadoc)
	 * @see domo.GUI.ImageView#getScale()
	 */
	public double getScale() {
		return this.totalScaleFactor;
	}
	
	/**
	 * create a Buffered Image object copy
	 * @param source BufferedImage to copy
	 * @return a BufferedImage copy
	 */
	private static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	
	/* (non-Javadoc)
	 * @see domo.GUI.ImageView#getRotationDegree()
	 */
	public double getRotationDegree() {
		return this.totalRotationDegree;
	}

}

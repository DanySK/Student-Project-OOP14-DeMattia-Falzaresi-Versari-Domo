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

public class ImageView extends JLabel {

	public enum ColorFilter {
		COLOR_FILTER_NONE,
		COLOR_FILTER_RED,
		COLOR_FILTER_GREEN
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1218944812028248824L;

	//private JLabel imageLabel;
	private BufferedImage currentImage;
	private BufferedImage filtImage;
	private BufferedImage originalImage;
	private final double MIN_FACTOR_SCALE = 0.0001;

	
	private double totalRotationDegree = 0;
	private double totalScaleFactor = 1;
	
	public ImageView ()	{
		super();
		this.setOpaque(true);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
		
	}
	
	public ImageView (final String imagePath) throws IOException	{
		super();
		this.setImage(imagePath);
	}
	
	public ImageView (final BufferedImage imageBuf) {
		super();
		this.setImage(imageBuf);
	}
	
	public ImageView (final BufferedImage imageBuf, Point middlePoint) {
		this(imageBuf);
		middlePoint.x = middlePoint.x - (this.getWidth() / 2);
		middlePoint.y = middlePoint.y - (this.getHeight() / 2);
		this.setLocation(middlePoint);
	}
	
	public ImageView (final BufferedImage imageBuf, Rectangle parentBound) {
		this(imageBuf);
		this.setAspectFillToParent(parentBound);
	}

	public void setImage(final BufferedImage image) {
		this.setIcon(new ImageIcon(image));
		this.setSize(new Dimension(image.getWidth(), image.getHeight()));
		this.repaint();
		
		this.currentImage = image;
		if (this.originalImage == null) {
			 this.originalImage = copyImage(this.currentImage);
		}
	}
	
	public void setImage(final String imagePath) throws IOException {
		this.setImage(ImageIO.read(new File(imagePath)));
	}

	private void rotate() {
		double radians = this.totalRotationDegree * Math.PI / 180;
		AffineTransform transform = new AffineTransform();
	    transform.rotate(radians, this.getWidth() / 2, this.getHeight() / 2);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    this.currentImage = op.filter(this.currentImage, null);
	    this.setImage(this.currentImage);
	}
	
	public void rotate(final double degree) {
	    this.totalRotationDegree  = (this.totalRotationDegree + degree) % 360;
	    this.rotate();
	}
	
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
	
	private void setScaleForAspetcFill(final double imgScale) {
		this.totalScaleFactor *= imgScale;
		this.setScale(this.totalScaleFactor);
	}
	
	public void setScale(final double imgScale) {
		Dimension old = this.getSize(); 
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

	public boolean containsPoint(Point point) {
		return this.getBounds().contains(point);
	}
		
	public double getScale() {
		return this.totalScaleFactor;
	}
	
	private static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}

}

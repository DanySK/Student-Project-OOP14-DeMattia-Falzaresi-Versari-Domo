package domo.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	private boolean isMouseEnabled = false;
	private MouseEvent pressed;
	private Point pPoint;
	private double totalRotationDegree = 0;
	private double totalScaleFactor = 1;
	private boolean isSelect = false;
	
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
		if (this.getMouseListeners().length == 0) {
			this.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(final MouseEvent e) {
							System.out.println("image click" + e.getButton());
							if ((e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) && isSelect && isMouseEnabled) {
								ImageView.this.rotate90();
							}
							if (e.getButton() == MouseEvent.BUTTON1 && isMouseEnabled) {
								isSelect = !isSelect;
								if (isSelect) {
									//ImageView.this.setColorFilter(ColorFilter.COLOR_FILTER_RED);
									ImageView.this.setBorder(BorderFactory.createLineBorder(Color.red, 1));
								} else {
									//ImageView.this.setColorFilter(ColorFilter.COLOR_FILTER_NONE);
									ImageView.this.setBorder(null);

								}
							}
						}

						
						
						public void mousePressed(final MouseEvent e) {
							//ImageView.this.setScale(0.90);
							//System.out.println("scala: " + ImageView.this.totalScaleFactor);
							if (e.getSource() == ImageView.this && isMouseEnabled) {
								pressed = e;
								pPoint = ImageView.this.getLocation();
								System.out.println("image press");
							}
						}
					}
					);

			this.addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(final MouseWheelEvent e) {
					
					if (e.getSource() == ImageView.this && isMouseEnabled && isSelect) {
						double rotationAngle = e.getPreciseWheelRotation() > 0 ? 90 : -90;
						System.out.println("wheel: " + e.getPreciseWheelRotation() + "\nAngolo: " + rotationAngle);
						ImageView.this.rotate(rotationAngle);
					}
				}
			});
			
			this.addMouseMotionListener(
					new MouseMotionAdapter() {
						public void mouseDragged(final MouseEvent e) {
							if (e.getSource() == ImageView.this && isMouseEnabled && isSelect) {
								pPoint = ImageView.this.getLocation(pPoint);
								int x = ImageView.this.getLocation().x +  (e.getXOnScreen() - pressed.getXOnScreen());
								int y = ImageView.this.getLocation().y +  (e.getYOnScreen() - pressed.getYOnScreen());
								ImageView.this.setLocation(x, y);
								//System.out.println("image X: "+ImageView.this.getLocation().x + "\npPoint.X: " + pPoint.x+ "\ne.getX(): " + e.getX()+ "\npressed.getX(): " + pressed.getX()+ "\ndelta X: " + (e.getX() - pressed.getX())+ "\n");
							}
							pressed = e;
						}
					}
					);  
		}
		this.currentImage = image;
		if (this.originalImage == null) {
			 this.originalImage = copyImage(this.currentImage);
		}
	}
	
	public void setImage(final String imagePath) throws IOException {
		this.setImage(ImageIO.read(new File(imagePath)));
	}
	
	public void setScale(final double imgScale) {
		//http://stackoverflow.com/questions/4216123/how-to-scale-a-bufferedimage
		Dimension old = this.getSize(); 
		BufferedImage imgToResize = copyImage(this.originalImage);
		this.totalScaleFactor *= imgScale;
		
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

		int deltaX = (int) (this.getX() + (old.getWidth() - retImg.getWidth()));
		int deltaY = (int) (this.getY() + (old.getHeight() - retImg.getHeight()));
		
		this.setLocation(new Point(deltaX, deltaY));
	}
	
	public void rotate(double degree) {
		double radians = degree * Math.PI / 180;
		AffineTransform transform = new AffineTransform();
	    transform.rotate(radians, this.getWidth() / 2, this.getHeight() / 2);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    this.currentImage = op.filter(this.currentImage, null);
	    this.totalRotationDegree  = (this.totalRotationDegree + degree) % 360;
	    this.setImage(this.currentImage);
	}
	
	public void rotate90() {
		this.rotate(90);
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
		this.setScale(factorToUse);
		Point middlePoint = new Point((int) parentBound.getCenterX(), (int) parentBound.getCenterY());
		middlePoint.x = middlePoint.x - (this.getWidth() / 2);
		middlePoint.y = middlePoint.y - (this.getHeight() / 2);
//		middlePoint.x =(int) ((double) this.getX() * factorToUse);
//		middlePoint.y =(int) ((double) this.getY() * factorToUse);

		this.setLocation(middlePoint);
	}
	
	public boolean containsPoint(Point point) {
		return this.getBounds().contains(point);
	}
	
	public void setMouseEnabled(final boolean move) {
		this.isMouseEnabled = move;
	}
	
	public boolean getMouseEnabled() {
		return this.isMouseEnabled;
	}
	
	public boolean getIsSelect() {
		return this.isSelect;
	}
	
	private static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}

}

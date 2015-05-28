package mvc;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 *
 * This Class create an ImageView object, this object is a custom JPanel used to define an image background with custom size to the panel
 */

public class ImageView extends JPanel {

	private static final long serialVersionUID = 6231408105040536354L;
	private Image img;
	private Dimension dim;
	private float ratio;
	
	/**
	 *  The first constructor accept a String and a Dimension object.
	 * @param imgP the image path to use as background
	 * @param d the dimension of the panel or frame where the new panel need to be inserted
	 */
	public ImageView(final String imgP, final Dimension d) {
	  this(new ImageIcon(imgP).getImage());
	  this.dim = d;
	}
	
	/**
	 *	The second constructor accept only an image object but is private because is called from the public constructor,
	 *	ratio is used to try fo mantain the image ratio during the resizing procedure
	 *
	 * @param img the image to use as background
	 */
	private ImageView(final Image imgF) {
	  this.img = imgF;
	  final Dimension size = new Dimension(imgF.getWidth(null), imgF.getHeight(null));
	  this.ratio = imgF.getHeight(null) / imgF.getWidth(null);
	  if (this.ratio == 0) {
	  	this.ratio = 1;
	  }
	  setPreferredSize(size);
	  setMinimumSize(size);
	  setMaximumSize(size);
	  setSize(size);
	  setLayout(null);
	}
	
	/**
	 *  This method is the standard method called by swing during the drawing procedure of the component,
	 *  here the image is resized starting from the height dimension.
	 */
	@Override
	public void paintComponent(final Graphics g) {
		g.drawImage(img, 0, 0, (int) (this.dim.getHeight() * this.ratio), (int) (this.dim.getHeight()), null);
	}
}

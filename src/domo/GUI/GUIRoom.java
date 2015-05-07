package domo.GUI;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author Simone
 * 
 */
public interface GUIRoom {
	
	
	/**
	 * @return 
	 */
	BufferedImage getImage();

	/**
	 * 
	 * @param image
	 */
	void setImage(BufferedImage image);
		
	/**
	 * @return
	 */
	String getTitle();
	
	/**
	 * @param title
	 */
	void setTitle(String title);
}

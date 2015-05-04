package domo.GUI;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import domo.devices.Sensor;

public class GUIWorkingArea extends JLayeredPane {

	private ImageView bgImage;
	private final ArrayList <GUISensorImpl> sensorList = new ArrayList<>();
	
	public GUIWorkingArea() {
		super();
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void setImage(String imagePath) {
		
		BufferedImage imageBuf;
		try {
			imageBuf = ImageIO.read(new File(imagePath));
			if (this.bgImage != null) {
				this.remove(bgImage);
			}
			bgImage = new ImageView(imageBuf, this.getBounds());
			this.add(bgImage, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void resize() {
		
		if (bgImage != null) {
			bgImage.setAspectFillToParent(this.getBounds());
			for (GUISensorImpl t : sensorList)  {
				t.setScale(bgImage.getScale());
			}
		}
	}
	
	public void removeSelectSensor() {
		for (GUISensorImpl t : sensorList) {
			if (t.isSelect()) {
				this.remove(t);
			}
		}
	}
	
	public void addSensor(String imgPath) {
		if(this.bgImage != null) {
			GUISensorImpl t;
			try {
				t = new GUISensorImpl(imgPath, this.bgImage);
				t.setScale(this.bgImage.getScale());
				sensorList.add(t);
				t.setLocation(this.bgImage.getWidth() / 2, this.bgImage.getHeight() / 2);
				this.add(t, 0);
				this.moveToFront(t);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	
	public boolean isBackgroundExist() {
		return (this.bgImage != null);
	}
}

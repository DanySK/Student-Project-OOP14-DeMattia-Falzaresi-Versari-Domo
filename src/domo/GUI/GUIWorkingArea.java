package domo.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.border.Border;

import domo.devices.Sensor;

public class GUIWorkingArea extends JLayeredPane {

	private ImageView bgImage;
	private final ArrayList <GUISensorImpl> sensorList = new ArrayList<>();
	
	public GUIWorkingArea() {
		super();
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
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
	
	public void addSensor(String imgPath) {//(Sensor sensor) {
		//String imgPath = sensor.getImagePath();
		if(this.bgImage != null) {
			GUISensorImpl t;
			try {
				t = new GUISensorImpl(imgPath, this.bgImage);
				t.setScale(this.bgImage.getScale());
				sensorList.add(t);
				this.add(t, 0);
				this.moveToFront(t);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	
	public Set<Sensor> getSelectedSensor(){
		Set<Sensor> sel = new HashSet<>();
		for (GUISensorImpl sens : sensorList) {
			if (sens.isSelect()) {
				sel.add(sens.getSensor());
			}
		}
		return sel;
	}
	
	public boolean isSetBackground() {
		return (this.bgImage != null);
	}
}

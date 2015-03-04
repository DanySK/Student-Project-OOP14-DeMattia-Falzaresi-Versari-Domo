package domo.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class GUIRoomImpl extends JLabel implements GUIRoom {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1027297993603311816L;

	private BufferedImage img;

	private Point offset;

	public GUIRoomImpl(String imagePath, Point initialPosition) {
		try {
			img = ImageIO.read(new File(imagePath));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		this.offset = new Point(initialPosition);


		MouseAdapter ma = new MouseAdapter() {

			private Point startPoint;
			private boolean isMustMove = false;
			@Override
			public void mousePressed(MouseEvent e) {
					isMustMove = true;
					startPoint = e.getPoint();
					startPoint.x -= offset.x;
					startPoint.y -= offset.y;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				isMustMove = false;
				startPoint = null;
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if(isMustMove){
					Point p = e.getPoint();
					int x = p.x - startPoint.x;
					int y = p.y - startPoint.x;
					offset = new Point(x, y);
					repaint();
				}
			}
		};

		addMouseListener(ma);
		addMouseMotionListener(ma);

	}
	
	public void setOffset(Point point) {
		this.offset.x = point.x;
		this.offset.y = point.y;
		this.repaint();
	}
	
	public Point getOffset() {
		return new Point(offset.x, offset.y);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img != null) {
			Graphics2D g2d = (Graphics2D) g.create();
			if (offset == null) {
				offset = new Point(0, 0);
			}
			g2d.drawImage(img, offset.x, offset.y, this);
			g2d.dispose();
		}
	}



}

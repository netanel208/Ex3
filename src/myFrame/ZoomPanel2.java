package myFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

// example of picture read, picture write and
//  picture zoom with wheel
public class ZoomPanel2 extends JPanel implements MouseListener, MouseWheelListener {

	static final long serialVersionUID = 1L;
	private BufferedImage sourceImage;
	private int sourceImageWidth, sourceImageHeight;
	private int _width = 307, _height = 570;
	private static String picture = "E:\\שנה ב\\OOP\\מטלות\\מטלה 3\\Ex3_data\\data\\Ariel1.png";
	private double _zoom = 1d, _cZoom = 0.01, _minSize = 1;
	private double xPoint0 = 0, yPoint0 = 0;
	private boolean flagPaint = false;
	private int midX, midY; 
	
	public ZoomPanel2() {
		super();
		initializePanel();
	}

	private void initializePanel() {
		this.setPreferredSize(new Dimension(_width, _height));
		try {
			File file = new File(picture);
			sourceImage = ImageIO.read(file);
			sourceImageWidth = sourceImage.getWidth();
			sourceImageHeight = sourceImage.getHeight();
			midX = sourceImageWidth/2;
			midY = sourceImageHeight/2;
			addMouseListener(this);
			addMouseWheelListener(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		int x=0, y=0;
		int width = (int) (sourceImageWidth*_zoom);
		int height = (int) (sourceImageHeight*_zoom);
		// Create a scaled version of this image. A new Image object
		// is returned which will 
		// render the image at the specified width and height by default.
		// Image.SCALE_SMOOTH flag to indicate the type of algorithm to 
		//  use for image resampling.
		Image img = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		g.clearRect(0, 0, _width, _height);
		g.drawImage(img, (_width - width) / 2, (_height - height) / 2, null);
		if (flagPaint) {
			if (xPoint0 >= midX)
				x = (int)((xPoint0 - midX)*_zoom + midX);
			else
				x = (int)(midX - (midX - xPoint0)*_zoom );
			if (yPoint0 > midY)
				y = (int)((yPoint0 - midY)*_zoom + midY);
			else
				y = (int)(midY - (midY - yPoint0)*_zoom );
			g.fillOval(x, y, 7, 7);
			System.out.println("zoom= " + _zoom+", xPoint0 = "+xPoint0+", yPoint0 = "+yPoint0);
			System.out.println("with zoom x = "+ x +", y=" + y);
		}
		else flagPaint = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		if(flagPaint) {
			int width = (int) (sourceImageWidth*_zoom);
			int height = (int) (sourceImageHeight*_zoom);
			if (_width >= width)
				xPoint0 = (p.getX() - (_width - width)/2) / _zoom;
			else
				xPoint0 = (p.getX() + (width - _width)/2) / _zoom;
			if (_height >= height)
				yPoint0 = (p.getY() - (_height - height)/2) / _zoom;
			else
				yPoint0 = (p.getY() + (height - _height)/2) / _zoom;
		}
		repaint();
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int wheelRotation = e.getWheelRotation();
		//System.out.println("wheelRotation= " + wheelRotation);
		double z = _zoom + wheelRotation * _cZoom;
		int w = (int) (sourceImageWidth * z);
		int h = (int) (sourceImageHeight * z);
		if (w >= _minSize && h >= _minSize) {
			_zoom = z;
		}
		repaint();
	}

	public static void main(String[] args) {

		JFrame demo = new JFrame(picture);
		demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		demo.add(new ZoomPanel2());
		demo.pack();
		demo.setVisible(true);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
}

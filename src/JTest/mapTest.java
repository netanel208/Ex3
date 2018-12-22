package JTest;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import Coords.MyCoords;
import File_format.Map;
import Geom.Point3D;

class mapTest {

	final Point3D start = new Point3D(32.10571,35.20237,0);
	final Point3D end = new Point3D(32.10189,35.21239,0);



	final int Height = 642;
	final int Width= 1433;


	final Point3D pixelStart=  new Point3D(0,0,0);
	final Point3D pixelEnd=  new Point3D(1433,642,0);

	Point3D p = new Point3D(32.10503,35.20671,0); // libary in the map
	Point3D p1 = new Point3D(620,114,0); // libary in pixel on map
	@Test
	void testToPixel() {
		
		// read the image map of ariel 
		BufferedImage map = null;
		try {
			map = ImageIO.read(new File("Ariel1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map m= new Map(map,Width,Height,start,end);


		Point3D ans1 =m.toPixel(start);
		assertTrue(ans1.equals(pixelStart));

		Point3D ans2 =m.toPixel(end);
		assertTrue(ans2.equals(pixelEnd));


		Point3D ans3 = m.toPixel(p);
		assertTrue(ans3.equals(p1));

	}

	@Test
	void testToGps() {
		
		
		BufferedImage map = null;
		try {
			map = ImageIO.read(new File("Ariel1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map m= new Map(map,Width,Height,start,end);
		
		Point3D ans1 = m.toGPS(pixelStart);
		assertTrue(ans1.equals(start));

		Point3D ans2 = m.toGPS(pixelEnd);
		assertTrue(ans2.equals(end));

	}
	
	@Test
	void testDistance()
	{
		MyCoords c = new MyCoords();
		int actual = (int)c.distance3d(start, end);
		int excepted = 1034;
		assertEquals(excepted, actual);

	}
	
	@Test
	void  azimuth() {
		MyCoords c = new MyCoords();
		double[] arr  = c.azimuth_elevation_dist(start, end);
		int actual = (int)arr[0];
		int excepted = 114;
		assertEquals(excepted, actual);
	}
}

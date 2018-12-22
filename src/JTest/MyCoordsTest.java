package JTest;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import Coords.MyCoords;
import Geom.Point3D;

class MyCoordsTest {

	Point3D p = new Point3D(32.103315,35.209039,670); // Building 9
	Point3D p1 = new Point3D(32.106352,35.205225,650); // houmos


	
	@Test
	void testAdd() {
		Point3D v =  new Point3D(337.6989921,-359.2492069,-20); //  vector in meter
		MyCoords c = new MyCoords();
		Point3D ans = c.add(p, v);
		Point3D excepted = new Point3D(32.10635200000035,35.20522500000041,650.0);
		assertTrue(excepted.equals(ans));
		
		
	}

	@Test
	void testDistance()
	{
		MyCoords c = new MyCoords();
		int actual = (int)c.distance3d(p, p1);
		int excepted = 493;
		assertEquals(excepted, actual);

	}

	@Test
	void Lon_Norm() {
		MyCoords c = new MyCoords();
		double actual =c.Lon_Norm(p.x());
		double excepted = 0.8470911744834423;
		assertEquals(excepted, actual);

	}

	@Test
	void  vector3D() {
		MyCoords c = new MyCoords();
		Point3D actual =c.vector3D(p,p1);
		Point3D exceped = new Point3D(337.6989920612881,-359.24920693881893,-20.0);
		assertTrue(exceped.equals(actual));

	}
	@Test
	void  azimuth_elevation_dist() {
		MyCoords c = new MyCoords();
		double[] actual  = c.azimuth_elevation_dist(p, p1);
		double[] exceped = {313.23042032646896, -2.322852232927616, 493.05233183241336 };
		boolean ans =Arrays.equals(actual, exceped);
		assertTrue(ans);
		
		Point3D p2 = new Point3D(32.106352,35.209039,650); // parallel to building 9 and houmos


		// elevation == 0 and azimuth == 89.99
		double[] actual_1  = c.azimuth_elevation_dist(p1, p2);
		assertEquals(actual_1[1],0);
		assertEquals(actual_1[0],89.9989864438034);


		//azimuth == 0
		double[] actual_2  = c.azimuth_elevation_dist(p, p2);
		assertEquals(actual_2[0],0);

		Point3D v1 =  new Point3D(100,0,10); // 100 meter north + 10 meters in height 
		Point3D v2 =  new Point3D(-100,0,10); // 100 meter South + 10 meters in height
		Point3D v3 =  new Point3D(0,100,10); // 100 meter East + 10 meters in height
		Point3D v4 =  new Point3D(0,-100,10); // 100 meter West + 10 meters in height



		Point3D P_north = c.add(p, v1);
		double[] V_north  = c.azimuth_elevation_dist(p, P_north);

		
		Point3D P_south = c.add(p, v2);
		double[] V_south  = c.azimuth_elevation_dist(p, P_south);
		
		Point3D P_East = c.add(p, v3);
		double[] V_East  = c.azimuth_elevation_dist(p, P_East);
		
		Point3D P_West = c.add(p, v4);
		double[] V_West  = c.azimuth_elevation_dist(p, P_West);


		// elevation for all the points need to be  == 5.710593137494233
		assertEquals(V_north[1],V_south[1],5.710593137494233);
		assertEquals(V_East[1],V_West[1],5.710593137494233);

	}
	
	@Test
	void   isValid_GPS_Point() {
		MyCoords c = new MyCoords();
		Point3D p2 = new Point3D(-100,35.209039,650); // point with Invalid Latitude
		boolean actual = c.isValid_GPS_Point(p2);
		assertFalse(actual);
		
		Point3D p3 = new Point3D(32.106352,35.209039,-1000); // point with Invalid Alt
		boolean actual1 = c.isValid_GPS_Point(p3);
		assertFalse(actual1);
		
	}

}

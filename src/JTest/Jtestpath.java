package JTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import GIS.Game;
import Geom.Point3D;
import Solution.Path;
import Solution.ShortestPathAlgo;

class Jtestpath {

	Path path = new Path();
	@Test
	void test1() {
		ArrayList<Point3D> arr = new ArrayList<Point3D>();
		arr.add(new Point3D(32.10398445482866,35.20734385281386,0.0));
		arr.add(new Point3D(32.1040915576324,35.20751735930736,0.0));
		arr.add(new Point3D(32.1042046105919,35.20761857142857,0.0));
		arr.add(new Point3D(32.104281962616824,35.20775593073593,0.0));
		path.addAll(arr);
		String actual = path.toString();
		String excepted = "-->32.10398445482866,35.20734385281386,0.0 -->32.1040915576324,35.20751735930736,0.0 "
				+ "-->32.1042046105919,35.20761857142857,0.0 -->32.104281962616824,35.20775593073593,0.0--> null";
		assertTrue(actual.equals(excepted));
	}
	
	@Test
	void test2() {
		ArrayList<Point3D> arr = new ArrayList<Point3D>();
		arr.add(new Point3D(32.10398445482866,35.20734385281386,0.0));
		arr.add(new Point3D(32.1040915576324,35.20751735930736,0.0));
		arr.add(new Point3D(32.1042046105919,35.20761857142857,0.0));
		arr.add(new Point3D(32.104281962616824,35.20775593073593,0.0));
		path.addAll(arr);
		double actual = path.getLength();
		double excepted = 51.534896846148264;
		assertEquals(excepted,actual);
	}
	
	@Test
	void test3() {
		double actual = path.getLength();
		double excepted = 0;
		assertEquals(excepted,actual);
	}

	@Test
	void test4() {
		Game game = new Game("test\\game_1543684662657.csv"); 
		ShortestPathAlgo alg = new ShortestPathAlgo(game);
		path = alg.theShortRoute()[0];
		
		Point3D actual1 = path.getPointInTime(1);
		Point3D excepted1 = new Point3D(32.10456447221604,35.20371396442198,0.0);
		boolean equal1 = excepted1.equals(actual1);
		assertTrue(equal1);
		
		Point3D actual2 = path.getPointInTime(0);
		Point3D excepted2 = (Point3D)game.getPackmens().get(0).getGeom(); 
		boolean equal2 = excepted2.equalsXY(actual2);
		assertTrue(equal2);
		
		int size = alg.theShortRoute().length;
		path = alg.theShortRoute()[size-1];
		
		Point3D actual3 = path.getPointInTime(1);
		Point3D excepted3 = new Point3D(32.104269351711835,35.21035084024204,0.0);
		boolean equal3 = excepted3.equalsXY(actual3);
		assertTrue(equal3);
		
		Point3D actual4 = path.getPointInTime(0);
		Point3D excepted4 = new Point3D(32.1042768,35.21035679,0.0);
		boolean equal4 = excepted4.equalsXY(actual4);
		assertTrue(equal4);

	}
}


package File_format;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Coords.MyCoords;
import Geom.Point3D;


public class Map {

	Image  map;
	int mapWidth, mapHeight;
	Point3D start, end;
	boolean increaseLon, increaseLat;
	double mapLon, mapLat;
	
	

	public Map (Image imageName,int w, int h, Point3D start,Point3D end ) {

		map = imageName;
		mapWidth=w;
		mapHeight=h;
		this.start=start;
		this.end=end;
		increaseLat = isIncrease(start.x(),end.x());
		increaseLon = isIncrease(start.y(),end.y());
		mapLon = Math.abs(start.x()-end.x());
		mapLat = Math.abs(start.y()-end.y());
		
		System.out.println("map"+mapWidth+"//"+mapHeight);

	}
	
/**
 * 
 * @param start
 * @param end
 * @return
 */
	private boolean isIncrease (double start, double end) {
		if ( start-end > 0)
			return false;

		return true;

	}


/**
 * 
 * @param p
 * @return
 */

	public  Point3D toPixel(Point3D p){

		double longitude,latitude;

		if ( this.increaseLon) 
			longitude =p.y() -start.y();

		else 
			longitude = start.y()-p.y();

		if ( this.increaseLat) 
			latitude = p.x()-start.x();

		else
			latitude =start.x()-p.x();

		// set x & y using conversion
		int x = (int) (mapWidth*(longitude/mapLat));
		int y = (int) (mapHeight*(latitude/mapLon));

		Point3D pixelPoint = new Point3D(x,y,0);
		return pixelPoint;
	}

/**
 * 
 * @param p
 * @return
 */
	public Point3D toGPS(Point3D p){
		
		double x, y;
		double diffLon = (p.x()*mapLat)/mapWidth;
		double diffLat = (p.y()*mapLon)/mapHeight;

		if (this.increaseLon) 
			y = start.y() +diffLon;
		

		else 
			y = start.y()-diffLon;

		

		if ( this.increaseLat) 
			x= start.x()+diffLat;

		

		else 
			x= start.x()-diffLat;

		

		Point3D gpsPoint = new Point3D(x,y,0);
		return gpsPoint;

	}
	
	/**
	 * 
	 * @param p0
	 * @param p1
	 * @return
	 */
	public double distance (Point3D p0, Point3D p1) {
		
		double ans;

		Point3D gps0 =  toGPS(p0);
		Point3D gps1 = toGPS(p1);
		
		MyCoords c = new MyCoords(); 
		ans = c.distance3d(gps0,gps1);
		return ans;
	}
	
	/**
	 * 
	 * @param p0
	 * @param p1
	 * @return
	 */
	public double azimuth (Point3D p0, Point3D p1 ) {
		double ans;
		
		Point3D gps0 =  toGPS(p0);
		Point3D gps1 = toGPS(p1);
		MyCoords c = new MyCoords(); 
		ans = (c.azimuth_elevation_dist(gps0, gps1))[0];
		
		return ans;
	}
 


}


package File_format;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Coords.MyCoords;
import Geom.Point3D;

/**
 * This class  represents a map and have the ability to convert image map from pixel
 * to gps coordinate and back to gps
 * This class also have methods for calculate distance and the azimuth between 2 point in pixel
 * @author Carmel
 * @author Netanel
 * In this class we use this code : https://stackoverflow.com/questions/38748832/convert-longitude-and-latitude-coordinates-to-image-of-a-map-pixels-x-and-y-coor
 */
public class Map {

	Image  map;
	int mapWidth, mapHeight;
	Point3D start, end;
	boolean increaseLon, increaseLat;
	double mapLon, mapLat;
	MyCoords c;
	
/**
 * This constactur build a map 
 * @param imageName
 * @param w Width
 * @param h Height
 * @param start point of the image in the top left corner
 * @param end point of the image in the lower right corner
 */
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
		c = new MyCoords(); 

	}
	
/**
 * This method check if the map coordinates are increase or decrease
 * @param @param start point of the image in the top left corner
 * @param end point of the image in the lower right corner
 * @return true if the map coordinates are increase
 */
	private boolean isIncrease (double start, double end) {
		if ( start-end > 0)
			return false;

		return true;

	}


/**
 * This method convert from gps coordinate to pixel point
 * @param p point in gps coordinate
 * @return pixel point
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
 *  This method convert from pixel point to gps point  
 * @param p point in pixel
 * @return  gps point
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
	 * This method calculate the distance between 2 pixel points
	 * @param p0
	 * @param p1
	 * @return distance between 2 pixel points 
	 */
	public double distance (Point3D p0, Point3D p1) {
		
		double ans;

		Point3D gps0 =  toGPS(p0);
		Point3D gps1 = toGPS(p1);
		
		ans = c.distance3d(gps0,gps1);
		return ans;
	}
	
	/**
	 * This method calculate the azimuth between 2 pixel points
	 * @param p0
	 * @param p1
	 * @return the azimuth between 2 pixel points
	 */
	public double azimuth (Point3D p0, Point3D p1 ) {
		double ans;
		
		Point3D gps0 =  toGPS(p0);
		Point3D gps1 = toGPS(p1);
		ans = (c.azimuth_elevation_dist(gps0, gps1))[0];
		
		return ans;
	}
 


}

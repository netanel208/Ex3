package Coords;

import Geom.Point3D;
/**
 * This class implements coords_converter and contains methods that can be done with GPS points
 *@author Netanel
 * @author Carmel
 */

public class MyCoords implements coords_converter{

	private final int RADIUS = 6371000;

	/**
	 * This method computes a new point which is the gps point transformed by a 3D vector (in meters)
	 */
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		
		if (!isValid_GPS_Point(gps) ) {
			throw new RuntimeException("Error: Invalid gps point ");
		}

		double x =local_vector_in_meter.x();
		double y =local_vector_in_meter.y();
		double z =local_vector_in_meter.z();

		double x_radian = Math.asin(x/RADIUS);
		double x_degree =Math.toDegrees(x_radian);

		double y_radian = Math.asin(y/(RADIUS*Lon_Norm(gps.x())));
		double y_degree =Math.toDegrees(y_radian);

		double x_new = x_degree+gps.x();
		double y_new = y_degree+gps.y();
		double z_new = z+gps.z();
		Point3D p = new Point3D(x_new,y_new,z_new);
		return p;

	}

	/**
	 * This method computes the normal of Longitude 
	 * @param x Latitude of the point
	 * @return the value in degree of normal
	 */
	public double Lon_Norm (double x ) {

		double Lon_Norm = Math.cos(x*Math.PI/180);
		return Lon_Norm;

	}

	/**
	 * This method computes the 3D distance (in meters) between the two gps
	 */
	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {
		
		if (!isValid_GPS_Point(gps0) || !isValid_GPS_Point(gps1) ) {
			throw new RuntimeException("Error: Invalid gps point ");
		}

		double diff_lat = gps1.x()-gps0.x();
		double diff_lon = gps1.y()-gps0.y();

		double diff_lat_rad =Math.toRadians(diff_lat);
		double diff_lon_rad =Math.toRadians(diff_lon);

		double lat_meter = toMeterLat(diff_lat_rad);
		double lon_meter = toMeterLon(gps0,diff_lon_rad);

		double distance = Math.sqrt((lat_meter*lat_meter)+(lon_meter*lon_meter));


		return distance;
	}

	/**
	 * This method convert from Latitude in degree to meter
	 * @param x the value of Latitude in degree
	 * @return Latitude in meter
	 */
	private double toMeterLat (double x ) {
		double ans;
		ans = Math.sin(x)*RADIUS;
		return ans;

	}

	/**
	 * This method convert from Longitude in degree to meter
	 * @param gps point to computes the normal
	 * @param y the value of Longitude in degree
	 * @return Longitude in meter
	 */

	private double toMeterLon (Point3D gps , double y) {
		double ans;
		ans = Math.sin(y)*RADIUS*Lon_Norm(gps.x());
		return ans;

	}

	public Point3D toMeterGps(Point3D gps) {
		if (!isValid_GPS_Point(gps)) {
			throw new RuntimeException("Error: Invalid gps point ");
		}
		Point3D zero = new Point3D(0,0,0);
		double x_rad = Math.toRadians(gps.x());
		double y_rad = Math.toRadians(gps.y());
		double x = toMeterLat(x_rad);
		double y = toMeterLon(zero,y_rad);
		Point3D ans = new Point3D(x,y,gps.z());
		return ans;
	}

	/**
	 * This method computes the 3D vector (in meters) between two gps points
	 * @return vector3D 
	 */
	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		
		if (!isValid_GPS_Point(gps0) || !isValid_GPS_Point(gps1) ) {
			throw new RuntimeException("Error: Invalid gps point ");
		}

		double diff_lat = gps1.x()-gps0.x();
		double diff_lon = gps1.y()-gps0.y();
		double diff_alt = gps1.z()-gps0.z();


		double diff_lat_rad =Math.toRadians(diff_lat);
		double diff_lon_rad =Math.toRadians(diff_lon);

		double lat_meter = toMeterLat(diff_lat_rad);
		double lon_meter = toMeterLon(gps0,diff_lon_rad);

		Point3D p = new Point3D(lat_meter,lon_meter,diff_alt);


		return p;
	}

	/**
	 * This method computes an azimuth , elevation , and distance*
	 * @return array that representation of the 3D vector
	 */

	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		
		if (!isValid_GPS_Point(gps0) || !isValid_GPS_Point(gps1) ) {
			throw new RuntimeException("Error: Invalid gps point ");
		}
		
		double[] arr = new double[3];

		//distance
		double dis = distance3d(gps0,gps1);
		arr[2] = dis;

		//Azimuth
		double lat1 = Math.toRadians(gps0.x());
		double lat2 = Math.toRadians(gps1.x());
		double dy = Math.toRadians(gps1.y()-gps0.y());
		double y = Math.sin(dy) * Math.cos(lat2);
		double x = Math.cos(lat1)*Math.sin(lat2) -Math.sin(lat1)*Math.cos(lat2)*Math.cos(dy);
		double alpha = Math.atan2(y, x);
		double azimuth = (Math.toDegrees(alpha)+360)%360;
		arr[0] = azimuth;

		//Elevation
		double dz = gps1.z() - gps0.z();
		double elevation = Math.atan(dz/dis);
		elevation = Math.toDegrees(elevation);
		arr[1] = elevation;

		return arr;
	}


	/**
	 * This method return true if this point is a valid lat, lon , lat coordinate: [-90,+90],[-180,+180],[-450, 9000]
	 * @param p point 3D
	 * @return true if this point is a valid coordinate
	 */

	@Override
	public boolean isValid_GPS_Point(Point3D p) {

		if ( p.x() > 90 || p.x() < -90)
			return false;

		if ( p.y() > 180 || p.y() < -180)
			return false;

		if ( p.z() > 9000 || p.z() < -450)
			return false;


		return true;
	}


}

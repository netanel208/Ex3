package Solution;

import java.util.ArrayList;
import java.util.LinkedList;

import Coords.MyCoords;
import GIS.Fruit;
import GIS.Packmen;
import Geom.Point3D;

/**
 * This class represents a path that contains a collection of 3D points
 * that this path is created for each Pacman in the game.
 * In addition to this class it is possible to calculate for each time point where the pecman was.   
 * @author Netanel
 * @author Carmel
 *
 */
public class Path extends LinkedList<Point3D>{

	double lengthOfPath;
	ArrayList<Double> times;
	
	/**
	 * This constructor initialize the variables of this object.
	 */
	public Path()
	{
		this.lengthOfPath=0;
		this.times = new ArrayList<Double>();
	}

	/**
	 * This method return the length of the path - the sum of distances from one point to the next one 
	 * @return Length of the path
	 */
	public double getLength()
	{
		MyCoords c = new MyCoords();
		for(int i=0 ; i<this.size()-1 ; i++)
		{
			lengthOfPath += c.distance3d(this.get(i), this.get(i+1));
		}
		return lengthOfPath;
	}
	
	/**
	 * This method return the specific Point3D at a point in time 
	 * @param time
	 * @return Point3D
	 */
	public Point3D getPointInTime(double time) {
		Point3D p = null;
		if (this.getTimes().get(0) > time )
			return this.get(0);
		
		if (this.getTimes().get(this.size()-1) < time )
			return this.get(size()-1);
		
		// find the 2 point that the time betwen them 
		for (int i = 0; i < this.size(); i++) {
			if(this.getTimes().get(i) > time) {
				return timePropotion( i-1, i, time);
				
			}
		}
		
		return p;
	}
	
	/**
	 * Auxiliary method, which returns the point at a certain time between two points
	 * @param p1
	 * @param p2
	 * @param time
	 * @return
	 */
	private Point3D timePropotion(int p1, int p2,double  time)
	{
		double n = ((time-this.getTimes().get(p1))/(this.getTimes().get(p2)-this.getTimes().get(p1)));
		double dx =this.get(p2).x()-this.get(p1).x();
		double dy = this.get(p2).y()-this.get(p1).y();
		double x = this.get(p1).x() + dx*n;
		double y =  this.get(p1).y() +dy*n;
		
		Point3D p =new Point3D( x,y,0);
		return p;

	}
	

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		String str = "-->";
		if(this.size() != 0)
		{
			for(int i=0 ; i<this.size()-1 ; i++)
			{
				str += this.get(i).toString()+" -->";
			}
			str += this.get(this.size()-1).toString() +"--> null";
		}
		else {
			str += "null";
		}
		return str;
	}
	
	/**
	 * print the times of all points in path
	 * @return String
	 */
	public String toStringTimes()
	{
		return this.getTimes().toString();
	}
	
	/**
	 * For each point in the path has time that the pacman arrive to this, 
	 * hence all the 'points time' keep in one collection (according the ordinary).
	 * @return ArrayList<Double> times
	 */
	public ArrayList<Double> getTimes() {
		return times;
	}
}

package Solution;

import java.util.ArrayList;
import java.util.LinkedList;

import Coords.MyCoords;
import GIS.Fruit;
import GIS.Packmen;
import Geom.Point3D;

public class Path extends LinkedList<Point3D>{

	double lengthOfPath;
	ArrayList<Double> times;
	

	public Path()
	{
		this.lengthOfPath=0;
		this.times = new ArrayList<Double>();
	}

	public double getLength()
	{
		MyCoords c = new MyCoords();
		for(int i=0 ; i<this.size()-1 ; i++)
		{
			lengthOfPath += c.distance3d(this.get(i), this.get(i+1));
		}
		return lengthOfPath;
	}
	
	public Point3D getPointInTime(double time) {
		Point3D p = null;
		if (this.getTime().get(0) > time )
			return this.get(0);
		
		if (this.getTime().get(this.size()-1) < time )
			return this.get(size()-1);
		
		// find the 2 point that the time betwen them 
		for (int i = 0; i < this.size(); i++) {
			if(this.getTime().get(i) > time) {
				return timePropotion( i-1, i, time);
				
			}
		}
		
		return p;
	}
	
	private Point3D timePropotion(int p1, int p2,double  time)
	{
		double n = ((time-this.getTime().get(p1))/(this.getTime().get(p2)-this.getTime().get(p1)));
		double dx =this.get(p2).x()-this.get(p1).x();
		double dy = this.get(p2).y()-this.get(p1).y();
		double x = this.get(p1).x() + dx*n;
		double y =  this.get(p1).y() +dy*n;
		
		Point3D p =new Point3D( x,y,0);
		return p;

	}
	

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
	
	public String toStringTimes()
	{
		return this.getTime().toString();
	}
	
	public ArrayList<Double> getTime() {
		return times;
	}
}

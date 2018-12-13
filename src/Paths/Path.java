package Paths;

import java.util.LinkedList;

import Coords.MyCoords;
import GIS.Fruit;
import Geom.Point3D;

public class Path extends LinkedList<Point3D>{

	double length = 0;
//	LinkedList<Point3D> path;

//	public Path()
//	{
//		= new LinkedList<Point3D>();
//	}

	public double getLength()
	{
		MyCoords c = new MyCoords();
		for(int i=0 ; i<this.size()-1 ; i++)
		{
			length += c.distance3d(this.get(i), this.get(i+1));
		}
		return length;
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
}

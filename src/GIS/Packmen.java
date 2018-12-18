package GIS;

import java.util.ArrayList;

public class Packmen extends element{

	double speed;
	double radius;
//	ArrayList<Long> times;
	
	
	public Packmen(String[] line)
	{
		super(line);
		speed = Double.parseDouble(line[5]);
		radius = Double.parseDouble(line[6]);
//		times = new ArrayList<Long>();
	}
	public double getSpeed() {
		return speed;
	}
	public Packmen(Packmen packmen)
	{
		super(packmen);
		this.speed = packmen.speed;
		this.radius = packmen.radius;
	}
	
//	public ArrayList<Long> getTimes() {
//		return times;
//	}
	
	public String toString()
	{
		return super.Type+","+super.id+","+super.lat+","+super.lon+","+super.alt+","+this.speed+
				","+this.radius;
	}
}

package GIS;

public class Packmen extends element{

	double speed;
	double radius;
	public Packmen(String[] line)
	{
		super(line);
		speed = Double.parseDouble(line[5]);
		radius = Double.parseDouble(line[6]);
	}
	public Packmen(Packmen packmen)
	{
		super(packmen);
		this.speed = packmen.speed;
		this.radius = packmen.radius;
	}
	
	public String toString()
	{
		return super.Type+","+super.id+","+super.lat+","+super.lon+","+super.alt+","+this.speed+
				","+this.radius;
	}
}

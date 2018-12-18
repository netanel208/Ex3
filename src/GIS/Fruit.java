package GIS;

public class Fruit extends element{

	long time;
	double weight;
	public Fruit(String[] line) {
		super(line);
		weight = Double.parseDouble(line[5]);
	}
	
	public Fruit(Fruit fruit)
	{
		super(fruit);
		this.weight = fruit.weight;
	}
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String toString()
	{
		return super.Type+","+super.id+","+super.lat+","+super.lon+","+super.alt+","+this.weight+",";
	}
	
}

package GIS;

/**
 * This class represent a gis element with meta data like : weight
 * @author Netanel
 * @author Carmel 
 *
 */
public class Fruit extends element{

	long time;
	double weight;
	
	/**
	 * This constructor get array of String and build fruit
	 * @param line 
	 */
	public Fruit(String[] line) {
		super(line);
		weight = Double.parseDouble(line[5]);
	}
	
	/**
	 * Copy constructor
	 * @param fruit
	 */
	public Fruit(Fruit fruit)
	{
		super(fruit);
		this.weight = fruit.weight;
	}
	
	/**
	 * get the time that the fruit is eaten 
	 * @return time 
	 */
	public long getTime() {
		return time;
	}

	/**
	 * set the time that the fruit is eaten
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * print the fruit element
	 */
	public String toString()
	{
		return super.Type+","+super.id+","+super.lat+","+super.lon+","+super.alt+","+this.weight+",";
	}
	
}

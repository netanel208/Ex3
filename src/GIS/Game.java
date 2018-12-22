package GIS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import File_format.CsvArrayList;

/**
 * This class represent a Game that include fruits and pacmans, each one of them contains GPS data.
 * @author Netanel
 * @author Carmel
 *
 */
public class Game {

	CsvArrayList cal;
	ArrayList<Packmen> packmens;
	ArrayList<Fruit> fruits;
	
	/**
	 * This constructor accept csv file name and convert it to collections of pacmans and fruit
	 * @param csvFileName
	 */
	public Game(String csvFileName) {
		cal = new CsvArrayList(csvFileName); 
		packmens = new ArrayList<Packmen>();
		fruits = new ArrayList<Fruit>(); 
		createCollections(cal.getLines());
	}

	/**
	 * This constructor accept collections of pacmans and fruits and convert its to csv file(output.csv)
	 * @param packmens
	 * @param fruits
	 */
	public Game(ArrayList<Packmen> packmens, ArrayList<Fruit> fruits)
	{
		this.packmens = packmens;
		this.fruits = 	fruits;	
		createCsv();
	}
	
	/**
	 * This method create csv file from this fruits and pacmans collections.
	 */
	public void createCsv()
	{
		String fileName = "output.csv";
		PrintWriter pw = null;
		try 
		{
			pw = new PrintWriter(new File(fileName));//create new file with name: "output.csv"
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}
		StringBuilder sb = new StringBuilder();
		
		//the first line in csv file(titels)
		sb.append("Type,id,Lat,Lon,Alt,Speed/Weight,Radius,"+packmens.size()+","+fruits.size()+"\n");
		
		//after write titels write the pacmans details
		for(int i=0 ; i<packmens.size() ; i++)
		{
			sb.append(packmens.get(i).toString()+"\n");
		}
		
		//after write pacmans details write the fruits details
		for(int i=0 ; i<fruits.size() ; i++)
		{
			sb.append(fruits.get(i).toString()+"\n");
		}
		
		pw.write(sb.toString());
		pw.close();
	}
	
	/**
	 * This method accept ArrayList<String[]> and create from it collections of fruits and pacmans
	 * @param lines
	 */
	public void createCollections(ArrayList<String[]> lines)
	{
		for(int i=0 ; i<lines.size() ; i++)
		{
			String[] line = lines.get(i);
			String type = lines.get(i)[0];
			if(type.equals("P"))
			{
				packmens.add(new Packmen(line));
			}
			else if(type.equals("F"))
			{
				fruits.add(new Fruit(line));
			}
		}
	}
	
	/**
	 * @return ArrayList<Packmen>
	 */
	public ArrayList<Packmen> getPackmens() {
		return packmens;
	}

	/**
	 * @return ArrayList<Fruit>
	 */
	public ArrayList<Fruit> getFruits() {
		return fruits;
	}
}

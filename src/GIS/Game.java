package GIS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import File_format.CsvArrayList;

public class Game extends CsvArrayList{

	ArrayList<Packmen> packmens;
	ArrayList<Fruit> fruits;
	
	public Game(String csvFileName) {
		super(csvFileName);
		packmens = new ArrayList<Packmen>();
		fruits = new ArrayList<Fruit>(); 
		createCollections(super.getLines());
	}

	public void createCsv()
	{
		String fileName = "output.csv";
		PrintWriter pw = null;
		try 
		{
			pw = new PrintWriter(new File(fileName));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}
		StringBuilder sb = new StringBuilder();
		
		sb.append("Type,id,Lat,Lon,Alt,Speed/Weight,Radius,"+packmens.size()+","+fruits.size()+"\n");
		for(int i=0 ; i<packmens.size() ; i++)
		{
			sb.append(packmens.get(i).toString()+"\n");
		}
		for(int i=0 ; i<fruits.size() ; i++)
		{
			sb.append(fruits.get(i).toString()+"\n");
		}
		
		pw.write(sb.toString());
		pw.close();
		System.out.println("done!");
	}
	
	public ArrayList<Packmen> getPackmens() {
		return packmens;
	}

	public ArrayList<Fruit> getFruits() {
		return fruits;
	}
	
	private void createCollections(ArrayList<String[]> lines)
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
	public static void main(String[] args) {
		Game g = new Game("E:\\שנה ב\\OOP\\מטלות\\מטלה 3\\Ex3_data\\data\\game_1543684662657.csv");
		g.createCsv();
	}
}

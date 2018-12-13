package Algorithm;

import java.util.ArrayList;

import Coords.MyCoords;
import GIS.Fruit;
import GIS.Game;
import GIS.Packmen;
import Geom.Point3D;
import Paths.Path;

public class ShortestPathAlgo {

	Game game;
	ArrayList<Fruit> _fruits;
	ArrayList<Packmen> _packmens;

	public ShortestPathAlgo(String csvFileName)
	{
		game = new Game(csvFileName);
		_fruits = game.getFruits();
		_packmens = game.getPackmens();
		theShortRoute();
	}

	private Path theShortRoute()
	{
		Path path = new Path();

		//copy
		ArrayList<Fruit> rep_fruits = new ArrayList<Fruit>();
		for(int  i=0 ; i<_fruits.size() ; i++)
		{
			rep_fruits.add(new Fruit(_fruits.get(i)));
		}
		ArrayList<Packmen> rep_packmen = new ArrayList<Packmen>();
		for(int  i=0 ; i<_packmens.size() ; i++)
		{
			rep_packmen.add(new Packmen(_packmens.get(i)));
		}

		
		while(rep_fruits.size() != 0)
		{
			//calculate distance
			MyCoords c = new MyCoords(); 
			double minDistance = Integer.MAX_VALUE;
			int index_fruit = 0;
			for(int i=0 ; i<rep_fruits.size() ; i++)
			{
				double dis = c.distance3d((Point3D)_packmens.get(0).getGeom(),(Point3D)rep_fruits.get(i).getGeom());
				if(minDistance > dis)
				{
					minDistance = dis;
					index_fruit = i;
				}
			}

			//move packmen
			Point3D location = (Point3D)rep_fruits.get(index_fruit).getGeom();
			rep_packmen.get(0).setGeom(new Point3D(location));
			path.add(new Point3D(location));
			rep_fruits.remove(index_fruit);
		}

		return path;

	}
	public static void main(String[] args) {
		ShortestPathAlgo s = new ShortestPathAlgo("E:\\שנה ב\\OOP\\מטלות\\מטלה 3\\Ex3_data\\data\\game_1543693822377.csv");
		System.out.println(s.theShortRoute());
	}
}

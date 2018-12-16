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
	Path[] path;
	ArrayList<Fruit> _fruits;
	ArrayList<Packmen> _packmens;
	double totalTime;

	public ShortestPathAlgo(Game game)
	{
		this.game = game;
		_fruits = game.getFruits();
		_packmens = game.getPackmens();
		path = new Path[_packmens.size()];
		for(int i=0 ; i<path.length ; i++)
		{
			path[i] = new Path();
		}
		totalTime = 0;
	}

	public Path[] theShortRoute()
	{
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

			for (int i = 0; i < rep_packmen.size(); i++) {
				Packmen _pac =rep_packmen.get(i);

				//calculate distance
				MyCoords c = new MyCoords(); 
				double minTime = Integer.MAX_VALUE;
				int index_fruit = 0;
				for(int j=0 ; j<rep_fruits.size() ; j++)
				{
					double speed = _pac.getSpeed();
					double dis = c.distance3d((Point3D)_pac.getGeom(),(Point3D)rep_fruits.get(j).getGeom());
					double time = dis/speed;
					if(minTime > time)
					{
						minTime = time;
						index_fruit = j;
					}
				}

				totalTime += minTime;
				//move packmen
				Point3D location = (Point3D)rep_fruits.get(index_fruit).getGeom();
				_pac.setGeom(new Point3D(location));
				path[i].add(new Point3D(location));
				path[i].getTime().add(totalTime);
				rep_fruits.remove(index_fruit);
			}
		}
		return path;

	}
	
	public String toString()
	{
		String str = "{ ";
		for(int i=0 ; i<path.length ; i++)
		{
			str += path[i].toString()+"\n";
		}
		str += "}";
		return str;
	}
	public double getTime()
	{
		return totalTime;
	}
	
	public static void main(String[] args) {
		Game g = new Game("C:\\Users\\caron\\Desktop\\game_1543693822377.csv");
		ShortestPathAlgo s = new ShortestPathAlgo(g);
		Path[] _p = s.theShortRoute();
		System.out.println(s);
		System.out.println(_p[0].toStringTimes());
	}
}

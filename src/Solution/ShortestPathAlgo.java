package Solution;

import java.util.ArrayList;


import Coords.MyCoords;
import GIS.Fruit;
import GIS.Game;
import GIS.Packmen;
import Geom.Point3D;


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
			Packmen _pac =rep_packmen.get(i);

			path[i].add(new Point3D((Point3D)_pac.getGeom()));
			path[i].getTime().add(0.0);
		}


		while(rep_fruits.size() != 0)
		{

			for (int i = 0; i < rep_packmen.size(); i++) {
				Packmen _pac = rep_packmen.get(i);

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

				if(rep_fruits.size() != 0)
				{
					//move packmen
					Point3D location = (Point3D)rep_fruits.get(index_fruit).getGeom();
					_pac.setGeom(new Point3D(location));
					path[i].add(new Point3D(location));
					
					//add to times of path
					int size = path[i].getTime().size();
					double prev_time = path[i].getTime().get(size-1);
					path[i].getTime().add(prev_time+minTime);
					
					//remove fruit
					rep_fruits.remove(index_fruit);
				}
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
	public double getTotalTime()
	{
		double max = Double.MIN_VALUE;
		for(int i=0 ; i<path.length ; i++)
		{
			int p_size = path[i].size();
			double lastTime = path[i].getTime().get(p_size-1);//last time in path
			if(lastTime > max)
			{
				max = lastTime; 
			}
		}
		totalTime=max;
		return totalTime;
	}
}

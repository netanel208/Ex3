package Solution;

import java.util.ArrayList;


import Coords.MyCoords;
import GIS.Fruit;
import GIS.Game;
import GIS.Packmen;
import Geom.Point3D;

/**
 * This class provides a tool whose main function is to calculate the short path for each Pacman in the game.
 * An object from this class contains information about the algorithm and other data provided by the algorithm
 * such as: a collection of tracks for the Pacmans, the total time of the game .
 * @author Netanel
 * @author Carmel
 *
 */
public class ShortestPathAlgo {

	Game game;
	Path[] path;
	ArrayList<Fruit> _fruits;
	ArrayList<Packmen> _packmens;
	double totalTime;

	/**
	 * This constructor initialize the variables of This 
	 * @param game
	 */
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

	/**
	 * This method calculate the short path for each Pacman by specific algorithm.
	 * @return Path[] - A collection of tracks for all the Pacmans.
	 */
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
			path[i].getTimes().add(0.0);
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
					if(minTime > time)//find the min time of fruit from pacman
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
					int size = path[i].getTimes().size();
					double prev_time = path[i].getTimes().get(size-1);
					double cuurentTime =prev_time+minTime;
					path[i].getTimes().add(cuurentTime);
					 
					int id =rep_fruits.get(index_fruit).getId();
					
					for(int  k=0 ; k<_fruits.size() ; k++) // the orignal fruit
					{
						if (_fruits.get(k).getId()==id)
							_fruits.get(k).setTime((long)cuurentTime); 
					}
					
					//remove fruit
					rep_fruits.remove(index_fruit);
				}
			}
		}
		return path;

	}

	/**
	 * 
	 */
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
	
	/**
	 * This method get the total time of algorithm
	 * @return Total time of the algorithm
	 */
	public double getTotalTime()
	{
		double max = Double.MIN_VALUE;
		for(int i=0 ; i<path.length ; i++)
		{
			int p_size = path[i].size();
			double lastTime = path[i].getTimes().get(p_size-1);//last time in path
			if(lastTime > max)
			{
				max = lastTime; 
			}
		}
		totalTime=max;
		return totalTime;
	}
}

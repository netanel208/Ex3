package File_format;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import GIS.Fruit;
import GIS.Game;
import Geom.Point3D;
import Solution.Path;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.Style;

/**
* This class create a kml file from Game object:
* that contains the Gps points of the element, their names, and the time that they eaten.
* @author Netanel
* @author Carmel
*
*/
public class Convert2Kml {
	
	Game game;
	Path[] paths;
	
	/**
	 * This constructor accept a Game and array of Paths
	 * @param game
	 * @param paths
	 */
	public Convert2Kml (Game game, Path[] paths ) {
		this.game= game;
		this.paths =paths;
	}
	
	/**
	 * This method create a kml file 
	 * @throws FileNotFoundException
	 */
	public void convert () throws FileNotFoundException {

		ArrayList<Fruit> fruits = game.getFruits();

		final Kml kml = new Kml();
		Document doc = kml.createAndSetDocument().withName("project").withOpen(true);

	// create a Folder
		Folder folder = doc.createAndAddFolder();
		folder.withName("Pacman Game").withOpen(true);


		for (int i = 0; i < fruits.size(); i++) {

			Fruit f = fruits.get(i);

			// get the id 
			String id = Integer.toString(f.getId());
			System.out.println("id"+id);

			// Convert from long to String
			long t = f.getTime()*1000; //time of fruit is in second
			System.out.println("time"+t);
			SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSSXXX");
			String dateString = formatter.format(new Date(t));

			Point3D point = (Point3D)f.getGeom();
			
			// create Placemark elements
			createPlacemark(doc,folder,id,dateString,point);
		}

		// save
		kml.marshal(new File("project.kml"));


	}

	/**
	 * This method create Placemark in the kml 
	 * @param document
	 * @param folder
	 * @param id of the fruit
	 * @param time the fruit was eaten
	 * @param point the gps coordination
	 */
	private static void createPlacemark(Document document, Folder folder,String id,String time, Point3D point ) {

		Placemark placemark = folder.createAndAddPlacemark();
		placemark.withName(id);
		placemark.createAndSetPoint().addToCoordinates(point.y(), point.x(),point.z());// set coordinates
		placemark.createAndSetTimeStamp().withWhen(time);//set time
	}
}

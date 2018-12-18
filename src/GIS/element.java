package GIS;

import Coords.MyCoords;
import Geom.Geom_element;
import Geom.Point3D;

public class element implements GIS_element {

	char Type;
	int id;
	double lat;
	double lon;
	double alt;
	Point3D p;
	
	public element(String[] line)
	{
		Type = line[0].charAt(0);
		id = Integer.parseInt(line[1]);
		lat = Double.parseDouble(line[2]);
		lon = Double.parseDouble(line[3]);
		alt = Double.parseDouble(line[4]);
		p = new Point3D(lat, lon, alt);
	}
	
	public element(element el)
	{
		this.Type = el.Type;
		this.id = el.id;
		this.lat = el.lat;
		this.lon = el.lon;
		this.alt = el.alt;
		this.p = el.p;
	}

	public void setGeom(Point3D p) {
		this.p = p;
		this.lat = p.x();
		this.lon = p.y();
		this.alt = p.z();
	}

	@Override
	public Geom_element getGeom() {
		return p;
	}

	@Override
	public void translate(Point3D vec) {
		MyCoords mc = new MyCoords(); 
		p = mc.add(p, vec);
	}
}

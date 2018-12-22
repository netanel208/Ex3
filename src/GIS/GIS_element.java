package GIS;

import Geom.Geom_element;
import Geom.Point3D;

/**
 * This interface represents a GIS element with geometric representation and meta data such as:
 * Orientation, color, string, timing...
 * @author Netanel
 * @author Carmel
 *
 */
public interface GIS_element {
	public Geom_element getGeom();
	public void translate(Point3D vec);
}

package Solution;

/**
 * This class provide a tool for convert2kml
 * @author Netanel
 * @author Carmel
 *
 */
public class Solution {

	Path[] paths;


	public Solution (Path[] paths ) {
		this.paths= paths;
	}

	public Path getPath (int index) {
		return paths[index];
	}
}

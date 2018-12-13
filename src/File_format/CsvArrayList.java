package File_format;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * This class create ArrayList from csv file,
 * every line in csv file is one element in array list.
 * 
 * @author Carmel
 * @author Netanel
 *
 */
public class CsvArrayList {
	ArrayList<String[]> Lines;

	/**
	 * This constructor accept file name to convert to ArrayList.
	 * @param csvFileName
	 */
	public CsvArrayList(String csvFileName)
	{
		Lines = new ArrayList<String[]>();
		String line = "";
		String cvsSplitBy = ",";
		try (BufferedReader br = new BufferedReader(new FileReader(csvFileName)))
		{
			 while ((line = br.readLine()) != null) 
			 {
				 String[] lineInfo = line.split(cvsSplitBy);
				 Lines.add(lineInfo);
			 }
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public ArrayList<String[]> getLines() {
		return Lines;
	}
	
}

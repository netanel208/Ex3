package JTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import File_format.CsvArrayList;

class CsvArrayListTest {
	
	CsvArrayList array = new CsvArrayList("test\\1.csv");


	@Test
	void sizeOfArrayList() {
		
		ArrayList<String[]> temp = array.getLines();
		int excepted = 95;
		assertEquals(excepted,temp.size());
	}
	@Test
	void equals() {
		
		ArrayList<String[]> temp = array.getLines();
		String actual = temp.get(3)[0];
		System.out.println(actual);
		String excepted ="F";
		assertEquals(excepted,actual);	
	}
}

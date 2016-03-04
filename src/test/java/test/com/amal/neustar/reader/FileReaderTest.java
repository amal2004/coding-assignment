package test.com.amal.neustar.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.amal.neustar.reader.FileReader;
import com.google.common.collect.Multimap;


/**
 * @author Rohana Weerathunge
 * Test Cases for FileReader class
 */


public class FileReaderTest {
	
	FileReader fileReader;
	String filePath = "sample2.txt";
	String filePathNull = null;
	
	@Rule
	public ExpectedException exception= ExpectedException.none();

	
	@After
	public void tearDown() throws Exception {
		fileReader = null;
	}

	/**
	 * Tests the File Reader with valid file path
	 */
	@Test
	public void testReadFilePositive() {     
		fileReader = new FileReader(filePath);
		Multimap<String, String> map = fileReader.readFile();
		assertTrue(map.entries().size() >= 0);
	}
	
	/**
	 * Tests the File Reader with null value for file path
	 */
	@Test(expected=NullPointerException.class)
	public void testReadFileNullPath() { 
		fileReader = new FileReader(filePathNull);
		fileReader.readFile();	
	}
	
	/**
	 * Tests for valid category and its count
	 */
	@Test
	public void testCategoryCount() {     
		fileReader = new FileReader(filePath);
		Multimap<String, String> map = fileReader.readFile();
		assertEquals(3, map.get("COMPUTER").size());
	}
	

	/**
	 * Tests for duplicate key value pairs
	 */
	@Test
	public void testDuplicatePairs() {
		fileReader = new FileReader(filePath);
		Multimap<String, String> map = fileReader.readFile();
		assertEquals(2, map.get("PERSON").size());
	}
	
	/**
	 * Tests for category without sub-category
	 */
	@Test
	public void testEmptyCategory() {
		fileReader = new FileReader(filePath);
		Multimap<String, String> map = fileReader.readFile();
		assertEquals(0, map.get("OTHER").size());
	}

	/**
	 * Tests if empty category shows 0 count
	 */
	@Test
	public void zeroForEmptyCategory() {
		
		fileReader = new FileReader(filePath);
		Multimap<String, String> multimap = fileReader.readFile();
		int other = 0;
		other = multimap.get("OTHER").size();
		assertEquals(0, other);
	}
	
	
	/**
	 * Tests for legal category list
	 */
	@Test
	public void testForValidCategories(){
		
		fileReader = new FileReader(filePath);
		Multimap<String, String> multimap = fileReader.readFile();
		
		Map<String, Integer> map = fileReader.printCategoryCounts();
		Set<String> validSet =  map.keySet();
		Set<String> orgSet = multimap.keySet();
		
		assertFalse(validSet.containsAll(orgSet));
	}
	
}

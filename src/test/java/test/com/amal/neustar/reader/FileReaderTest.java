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

	@Test
	public void testReadFilePositive() {     
		fileReader = new FileReader(filePath);
		Multimap<String, String> map = fileReader.readFile();
		assertTrue(map.entries().size() >= 0);
	}
	
	
	@Test(expected=NullPointerException.class)
	public void testReadFileNullPath() { 
		fileReader = new FileReader(filePathNull);
		fileReader.readFile();	
	}
	
	
	@Test
	public void testCategoryCount() {     
		fileReader = new FileReader(filePath);
		Multimap<String, String> map = fileReader.readFile();
		assertEquals(3, map.get("COMPUTER").size());
	}
	

	@Test
	public void testDuplicatePairs() {
		fileReader = new FileReader(filePath);
		Multimap<String, String> map = fileReader.readFile();
		assertEquals(2, map.get("PERSON").size());
	}
	
	
	@Test
	public void testEmptyCategory() {
		fileReader = new FileReader(filePath);
		Multimap<String, String> map = fileReader.readFile();
		assertEquals(0, map.get("OTHER").size());
	}

	@Test
	public void zeroForEmptyCategory() {
		
		fileReader = new FileReader(filePath);
		Multimap<String, String> multimap = fileReader.readFile();
		int other = 0;
		other = multimap.get("OTHER").size();
		assertEquals(0, other);
	}
	
	
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

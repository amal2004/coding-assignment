package com.amal.neustar.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


/**
 * @author Rohana Weerathunge
 * Class to read a file and process its records.
 */

public class FileReader {

	private String filePath;
	private static final Logger LOGGER = LogManager.getLogger(FileReader.class.getName());
			

	/** Constructor takes file path as an argument*/
	public FileReader(String path) {
		this.filePath = path;
	}

	/**
	 * Implements a method to read text file
	 * @return  Multimap Object 
	 */
	public Multimap<String, String> readFile() {

		String line = null;
		String splitBy = " ";
		BufferedReader reader = null;

		 /** Accessing file location */
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(filePath);
		Multimap<String, String> multimap = ArrayListMultimap.create();

		try {
			
			LOGGER.info("Looking for file @: " + filePath);

			 /** Validate file */
			if (filePath == null || filePath.equals(" ")) {
				LOGGER.debug("NullPointerException thrown for filePath");
				throw new NullPointerException();
			}

			reader = new BufferedReader(new InputStreamReader(input));

			LOGGER.info("Start reading first line " + line);

			while ((line = reader.readLine()) != null) {
				String[] dataFields = line.split(splitBy, 2);

				 /** Divide the lines to key value pairs */
				if (dataFields.length == 2) {
					String key = dataFields[0];
					String val = dataFields[1];

					/** Check if value is empty */
					if (val.length() != 0) {

						/** Checking for duplicate key value pairs */
						if (!multimap.containsEntry(key, val)) {
							multimap.put(key, val);
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			LOGGER.debug("FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			LOGGER.debug("IOException: " + e.getMessage());
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					LOGGER.debug("IOException: " + e.getMessage());
				}
		}

		return multimap;
	}

	/**
	 * Implements a method to print Category and Count in an order
	 * @return Map
	 * Returns the map Object with Categories and Count 
	 */
	
	public Map<String, Integer> printCategoryCounts() {

		Multimap<String, String> multimap = readFile();
		Map<String, Integer> sortedmap = new HashMap<String, Integer>();

		int countPerson = 0;
		int countPlace = 0;
		int countAnimal = 0;
		int countComputer = 0;
		int countOther = 0;

		/** Loop through map and select only the valid categories and count */
		for (String key : multimap.keys()) {

			if (key.equals("PERSON")) {
				countPerson = multimap.get("PERSON").size();
			} else if (key.equals("PLACE")) {
				countPlace = multimap.get("PLACE").size();
			} else if (key.equals("ANIMAL")) {
				countAnimal = multimap.get("ANIMAL").size();
			} else if (key.equals("COMPUTER")) {
				countComputer = multimap.get("COMPUTER").size();
			} else if (key.equals("OTHER")) {
				countOther = multimap.get("OTHER").size();
			}

		}

		/** Prints the Category and count in correct order */
		StringBuffer buf = new StringBuffer();
			buf.append("PERSON \t\t" + countPerson + "\n");
			buf.append("PLACE \t\t" + countPlace+ "\n");
			buf.append("ANIMAL \t\t" + countAnimal+ "\n");
			buf.append("COMPUTER \t" + countComputer+ "\n");
			buf.append("OTHER \t\t" + countOther+ "\n");
			System.out.println(buf.toString());
		
		
		/** In order to test on Junit, sorted categories and count added to map */	
		sortedmap.put("PERSON", countPerson);
		sortedmap.put("PLACE", countPlace);
		sortedmap.put("ANIMAL", countAnimal);
		sortedmap.put("COMPUTER", countComputer);
		sortedmap.put("OTHER", countOther);

		return sortedmap;

	}

	
	/**
	 * This method not completed.
	 * 
	 */
/*	public void printCategoryNames() {

		Multimap<String, String> multimap = readFile();

		Set<String> sortedKeys = new HashSet<String>(multimap.keys());

		for (int i = 0, n = 1; i < n; i++) {
			for (String key : sortedKeys) {
				List<String> list = (List<String>) multimap.get(key);
				n = Math.max(n, list.size());
				if (i < list.size()) {
					System.out.printf("%s %s\n", key, list.get(i));

				}
			}
		}
	}*/

	
	
	public static void main(String args[]) {
		try {
			FileReader red = new FileReader("sample.txt");
			red.printCategoryCounts();
			//red.printCategoryNames();
			
		} catch (Exception e) {
			LOGGER.debug("Error: " + e.getMessage());
		}

	}

}

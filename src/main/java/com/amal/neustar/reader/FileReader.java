package com.amal.neustar.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class FileReader {

	private String filePath;
	private static final Logger LOGGER = LogManager.getLogger(FileReader.class.getName());
			
	public FileReader(String path) {
		this.filePath = path;
	}

	/**
     *
     */

	public Multimap<String, String> readFile(){

		String line = null;
		String splitBy = " ";
		BufferedReader br = null;

		InputStream input = this.getClass().getClassLoader().getResourceAsStream(filePath);
				
		Multimap<String, String> multimap = ArrayListMultimap.create();

		try {
			LOGGER.info("Looking for file @: " + filePath);

			if (filePath == null) {
				LOGGER.debug("NullPointerException thrown for filePath");
				throw new NullPointerException();
			}

			br = new BufferedReader(new InputStreamReader(input));

			LOGGER.info("Start reading first line " + line);

			while ((line = br.readLine()) != null) {

				String[] dataFields = line.split(splitBy, 2);

				if (dataFields.length == 2) {
					String key = dataFields[0];
					String val = dataFields[1];

					if (val.length() != 0) {
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
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.debug("IOException: " + e.getMessage());
				}
		}
		System.out.println("org map : " + multimap.toString());
		return multimap;
	}

	
	
	
	public Map<String, Integer> printCategoryCounts() {

		Multimap<String, String> multimap = readFile();
		Map<String, Integer> sortedmap  = new HashMap<String, Integer>();
		
		int countPerson = 0;
		int countPlace = 0;
		int countAnimal = 0; 
		int countComputer = 0;
		int countOther = 0;

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

		System.out.println("PERSON " + countPerson);
		System.out.println("PLACE " + countPlace);
		System.out.println("ANIMAL " + countAnimal);
		System.out.println("COMPUTER " + countComputer);
		System.out.println("OTHER " + countOther);
		
		sortedmap.put("PERSON", countPerson);
		sortedmap.put("PLACE", countPlace);
		sortedmap.put("ANIMAL", countAnimal);
		sortedmap.put("COMPUTER", countComputer);
		sortedmap.put("OTHER", countOther);
		
		return sortedmap;

	}

	
	public void printCategoryNames() {

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
	}

	public static void main(String args[])  {
		try {
			FileReader red = new FileReader("sample.txt");
			red.printCategoryCounts();
			red.printCategoryNames();
		} catch (Exception e) {
			LOGGER.debug("Error + "+ e.getMessage());
		}
		
	}

}

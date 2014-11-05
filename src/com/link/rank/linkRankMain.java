package com.link.rank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class linkRankMain {
	static int counter = 0;
	static HashMap<String, Integer> fieldHashMap = new HashMap<>();
	static String jsonString = null;

	public static void main(String[] args) throws IOException, ParseException {

		// getJSONFile();
		// rankJSONFile();

		ArrayList<File> roots = new ArrayList<File>();
		roots.addAll(Arrays.asList(File.listRoots()));

		for (File file : roots) {
			// new Searcher(file.toString().replace('\\', '/')).search();

			search();
		}

	}

	public static void search() throws IOException, ParseException {
		// System.out.println(root);
		HashMap<String, Integer> linkMap = new HashMap<>();
		File folder = new File(
				"/Users/priyakotwal/Documents/sharedUbuntu/572/jData/");
		File[] listOfFiles = folder.listFiles();
		int totalFiles = 0;
		if (listOfFiles == null)
			return; // Added condition check
		for (File file : listOfFiles) {
			String path = file.getPath().replace('\\', '/');
			System.out.println(path);
			linkMap.putAll(getJSONFile(path));

			if (file.isDirectory()) {
				new Searcher(path + "/").search();
			}
			totalFiles++;
		}
		System.out.println("HashMap");
		Set keys = linkMap.keySet();

		for (Map.Entry<String, Integer> entry : linkMap.entrySet()) {
			float rank = 0;
			String key = entry.getKey();
			int value = entry.getValue();
			rank = (float) value / totalFiles;
			System.out.println("Key: " + key + " Value:" + value);
			addRank(key, rank);
		}
	}

	private static void addRank(String key, float rank) throws IOException,
			ParseException {
		File folder = new File(
				"/Users/priyakotwal/Documents/sharedUbuntu/572/jData/");
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles == null)
			return; // Added condition check
		for (File file : listOfFiles) {

			String path = file.getPath().replace('\\', '/');
			// System.out.println(path);
			JSONParser jsonParser = new JSONParser();
			FileReader reader = null;
			reader = new FileReader(path);

			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			try {
				
				// BufferedWriter bw = new BufferedWriter(fw);

				if (key.equalsIgnoreCase((String) jsonObject.get("jobtype"))) {
					jsonObject.put("rank", rank);
					jsonString = JSONObject.toJSONString(jsonObject);
					System.out.println("jsonString"+jsonString);
					System.out.println("json : "
							+ JSONObject.toJSONString(jsonObject));

				} else {

				}
				if (file.isDirectory()) {
					new Searcher(path + "/").search();
				}
			} finally {
				reader.close();
			}

			FileWriter fw = new FileWriter(path);

			try {
				// fileW.write(jsonString);
				// System.out.println("Successfully Copied JSON Object to File...");
				System.out.println(jsonString);
				fw.write(JSONObject.toJSONString(jsonObject));
				System.out.println("\nJSON Object: " + JSONObject.toJSONString(jsonObject));

			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				fw.flush();
				fw.close();
			}
			// FileWriter fileW = new FileWriter(path);

		}
	}

	private static HashMap<String, Integer> getJSONFile(String filePath)
			throws IOException, ParseException {
		// final String filePath =
		// "/Users/priyakotwal/Documents/sharedUbuntu/572/jsonData/1.json";

		JSONParser jsonParser = new JSONParser();
		FileReader reader;
		try {
			reader = new FileReader(filePath);

			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			String jobtype = (String) jsonObject.get("jobtype");
			System.out.println("The jobtype is: " + jobtype);
			if (fieldHashMap.size() != 0 && fieldHashMap.containsKey(jobtype)) {
				counter++;
				fieldHashMap.put(jobtype, counter);
			} else {
				fieldHashMap.put(jobtype, 0);

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fieldHashMap;

	}

}

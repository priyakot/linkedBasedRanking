package com.link.rank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Link based analysis and ranking on employment job data
 * 
 * @author priyakotwal
 * 
 */

public class linkRankMain {
	// Change the file path of the folder containing json files

	static HashSet<String> classifiedTitleKeywords = new HashSet<String>();
	static HashMap<String, Integer> typeHashMap = new HashMap<>();
	static HashMap<String, Integer> companyHashMap = new HashMap<>();
	static HashMap<String, Integer> titleHashMap = new HashMap<>();
	static HashMap<String, Integer> locHashMap = new HashMap<>();
	static String inputFolderPath = null;
	static String jsonString = null;

	public static void main(String[] args) throws IOException, ParseException {

		parseArguments(args);
		addKeywordsForTitle();
		ArrayList<File> roots = new ArrayList<File>();
		roots.addAll(Arrays.asList(File.listRoots()));
		search();
	}
	
	public static void addKeywordsForTitle() {
		classifiedTitleKeywords.add("Analista".toLowerCase());
		classifiedTitleKeywords.add("Cocinero".toLowerCase());
		classifiedTitleKeywords.add("Consultor".toLowerCase());
		classifiedTitleKeywords.add("Dep�sito".toLowerCase());
		classifiedTitleKeywords.add("Profesor".toLowerCase());
		classifiedTitleKeywords.add("Asistente".toLowerCase());
		classifiedTitleKeywords.add("Mozo".toLowerCase());
		classifiedTitleKeywords.add("Administrativa/o".toLowerCase());
		classifiedTitleKeywords.add("Coordinador".toLowerCase());
		classifiedTitleKeywords.add("Tecnico".toLowerCase());
		classifiedTitleKeywords.add("Supervisor".toLowerCase());
		classifiedTitleKeywords.add("Vigilador".toLowerCase());
		classifiedTitleKeywords.add("Esteticista".toLowerCase());
		classifiedTitleKeywords.add("M�dicos".toLowerCase());
		classifiedTitleKeywords.add("Programador".toLowerCase());
		classifiedTitleKeywords.add("Mec�nico".toLowerCase());
		classifiedTitleKeywords.add("pintor".toLowerCase());
		classifiedTitleKeywords.add("Cobranzas".toLowerCase());
		classifiedTitleKeywords.add("Electricista".toLowerCase());
		classifiedTitleKeywords.add("Redactor".toLowerCase());
	}
	
	public static void parseArguments(String args[]) {

		for (int i = 0; i < args.length; i = i + 2) {
			if (args[i].equals("-i")) {
				inputFolderPath = args[i + 1];
			} 
		}

		if (inputFolderPath == null ) {
			System.out.println("Incomplete or incorrect input command");
			System.exit(0);
		}
	}

	public static void search() throws IOException, ParseException {
		HashMap<String, Integer> linkMap = new HashMap<>();
		/*File folder = new File(
				);
		File[] listOfFiles = folder.listFiles();
		int totalFiles = 0;
		if (listOfFiles == null)
			return; // Added condition check
*/		
		int totalFiles = 0;
		File folder = new File(inputFolderPath);
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				File[] listOfFiles = fileEntry.listFiles();
				
				for (File file : listOfFiles) {
					String path = file.getPath().replace('\\', '/');
					System.out.println(path);
					createMapsForRanking(path);

					if (file.isDirectory()) {
						new Searcher(path + "/").search();
					}
					totalFiles++;
				}				
			}
		}
		
		
		

		/**
		 * Weights jobtype :0.4 location :0.3 title :0.2 company :0.1
		 */

		/**
		 * int typeValue =0, locValue =0, titleValue=0, compValue=0; float
		 * typeRank =0, titleRank=0, compRank=0, locRank =0; String typeKey
		 * =null, titleKey=null, compKey =null, locKey=null; int keySize = 0;
		 **/

		/**
		 * for (Map.Entry<String, Integer> entry : linkMap.entrySet()) { typeKey
		 * = entry.getKey(); typeValue = entry.getValue(); typeRank = (float)
		 * typeValue / totalFiles; System.out.println("Key: " + typeKey +
		 * " Value:" + typeValue);
		 * 
		 * 
		 * //addRank(key, rank); }
		 **/
		for (Map.Entry<String, Integer> entry : typeHashMap.entrySet()) {
			System.out.println("Type Key: " + entry.getKey() + " Value:"
					+ entry.getValue());
		}

		for (Map.Entry<String, Integer> entry1 : titleHashMap.entrySet()) {
			System.out.println("Title Key: " + entry1.getKey() + " Value:"
					+ entry1.getValue());
		}

		for (Map.Entry<String, Integer> entry2 : locHashMap.entrySet()) {
			System.out.println("Loc Key: " + entry2.getKey() + " Value:"
					+ entry2.getValue());
		}

		for (Map.Entry<String, Integer> entry3 : companyHashMap.entrySet()) {
			System.out.println("Comp Key: " + entry3.getKey() + " Value:"
					+ entry3.getValue());
		}

		addRank(totalFiles);
	}

	// private static void addRank(String key, float rank) throws IOException,
	private static void addRank(int totalFiles) throws IOException,
			ParseException {
		double rank = 0;
		double typeAvg = 0, titleAvg = 0, locAvg = 0, compAvg = 0;
		
		File folder = new File(inputFolderPath);
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				File[] listOfFiles = fileEntry.listFiles();
				
				if (listOfFiles == null)
					continue; // Added condition check
				for (File file : listOfFiles) {

					String path = file.getPath().replace('\\', '/');
					JSONParser jsonParser = new JSONParser();
					FileReader reader = null;
					reader = new FileReader(path);

					JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
					try {

						/**
						 * if (key.equalsIgnoreCase((String) jsonObject.get("jobtype")))
						 * { jsonObject.put("rank", rank); jsonString =
						 * JSONObject.toJSONString(jsonObject);
						 * System.out.println("jsonString"+jsonString);
						 * System.out.println("json : " +
						 * JSONObject.toJSONString(jsonObject));
						 * 
						 * } else {
						 * 
						 * }
						 **/
						/**
						 * System.out.println("Total Files: " + totalFiles);
						 * System.out.println("jsonJobType:" +
						 * jsonObject.get("jobtype"));
						 * System.out.println("jsonTitleType:" +
						 * jsonObject.get("title"));
						 * System.out.println("jsonCompanyType:" +
						 * jsonObject.get("company")); System.out.println("jsonLocType:"
						 * + jsonObject.get("location"));
						 * 
						 * System.out.println("hashType: " +
						 * typeHashMap.get(jsonObject.get("jobtype")));
						 * System.out.println("hashTitle: " +
						 * titleHashMap.get(jsonObject.get("title")));
						 * System.out.println("hashType: " +
						 * locHashMap.get(jsonObject.get("location")));
						 * System.out.println("hashType: " +
						 * companyHashMap.get(jsonObject.get("company")));
						 **/

						typeAvg = ((double) typeHashMap.get(jsonObject.get("jobtype")) / totalFiles) * 2;
						locAvg = ((double) locHashMap.get(jsonObject.get("location")) / totalFiles) * 0.75;
						for(String key: classifiedTitleKeywords) {
							if(jsonObject.get("title").toString().toLowerCase().contains(key.toLowerCase())) {
								titleAvg = ((double) titleHashMap.get(key) / totalFiles) * 0.75;
								break;
							}
						}
												
						compAvg = ((double) companyHashMap.get(jsonObject.get("company")) / totalFiles) * 0.1;
						System.out.println("typeAvg: " + typeAvg);
						System.out.println("locAvg: " + locAvg);
						System.out.println("titleAvg: " + titleAvg);
						System.out.println("compAvg: " + compAvg);

						/**
						 * if (typeHashMap.containsKey(jsonObject.get("jobtype")) ) {
						 * typeAvg =
						 * ((typeHashMap.get(jsonObject.get("jobtype"))/totalFiles
						 * )*0.4); System.out.println("typeAvg: "+typeAvg); } else {
						 * typeAvg = 0; }
						 * 
						 * 
						 * } else { compAvg = 0; }
						 **/
						rank = (typeAvg + titleAvg + locAvg + compAvg) / 4;
						System.out.println("Boost: "+ rank);
						jsonObject.put("boost", rank);
						jsonString = JSONObject.toJSONString(jsonObject);
						/**
						 * System.out.println("jsonString" + jsonString);
						 * System.out.println("json : " +
						 * JSONObject.toJSONString(jsonObject));
						 **/

						if (file.isDirectory()) {
							new Searcher(path + "/").search();
						}
					} finally {
						reader.close();
					}

					// rewriting the json file with the new data (rank)
					//FileWriter fw = new FileWriter(path);
					OutputStreamWriter writer = new OutputStreamWriter(
						     new FileOutputStream(path),
						     Charset.forName("UTF-8").newEncoder() 
						 );
					try {

						//System.out.println(jsonString);
						writer.write(JSONObject.toJSONString(jsonObject));
						System.out.println("\nJSON Object: "
								+ JSONObject.toJSONString(jsonObject));

					} catch (Exception e) {
						e.printStackTrace();

					} finally {
						writer.flush();
						writer.close();
					}

				}				
			}
		}
	}

	/**
	 * Fuction to get the countValue of each of the considered fields
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private static HashMap<String, Integer> createMapsForRanking(String filePath)
			throws IOException, ParseException {

		JSONParser jsonParser = new JSONParser();
		FileReader reader;
		try {
			reader = new FileReader(filePath);

			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			String jobtype = (String) jsonObject.get("jobtype");
			String loc = (String) jsonObject.get("location");
			String title = (String) jsonObject.get("title");
			String company = (String) jsonObject.get("company");

			// checking job type
			if (typeHashMap.size() != 0 && typeHashMap.containsKey(jobtype)) {
				// counter++;
				typeHashMap.put(jobtype, typeHashMap.get(jobtype) + 1);
			} else {
				typeHashMap.put(jobtype, 1);

			}

			// checking location
			if (locHashMap.size() != 0 && locHashMap.containsKey(loc)) {
				// locCount++;
				locHashMap.put(loc, locHashMap.get(loc) + 1);
			} else {
				locHashMap.put(loc, 1);

			}

			// checking title
			for(String key: classifiedTitleKeywords) {
				if(title.toLowerCase().contains(key.toLowerCase())) {
					if (titleHashMap.size() != 0 && titleHashMap.containsKey(key)) {
						titleHashMap.put(key, titleHashMap.get(key) + 1);
					} else {
						titleHashMap.put(key, 1);
					}
					break;
				}
			}
			
			// checking company
			if (companyHashMap.size() != 0
					&& companyHashMap.containsKey(company)) {
				companyHashMap.put(company, companyHashMap.get(company) + 1);
			} else {
				companyHashMap.put(company, 1);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return typeHashMap;

	}

}

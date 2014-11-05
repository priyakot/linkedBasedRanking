package com.link.rank;

import java.io.File;

public class Searcher {
	
	 private String root = "/Users/priyakotwal/Documents/sharedUbuntu/572/jsonData/";

	       public Searcher(String root) {
	    	   
	        root = "/Users/priyakotwal/Documents/sharedUbuntu/572/jsonData/";
	    }

	    public void search() {
	     //   System.out.println(root);
	        File folder = new File(root);
	        File[] listOfFiles = folder.listFiles();
	        if(listOfFiles == null) return;  // Added condition check
	        for (File file : listOfFiles) {
	            String path = file.getPath().replace('\\', '/');
	            System.out.println(path);
	            if (file.isDirectory()) {
	                new Searcher(path + "/").search();
	            }
	        }
	    }

}

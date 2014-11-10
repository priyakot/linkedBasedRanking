linkedBasedRanking
==================
Steps to compile and execute the linkBased Algorithm:<br>
1. Import the project in Eclipse<br>
2. The main file is linkRankMain<br>
This file is used to compute the links between the json files based on the meta-data: jobtype,location, title, company.
A weighted average is calculated for these meta-data fields across the entire dataset and stored in a field called boost in the json documents maps with the boost field in etllib while posting data to solr and indexes and ranks the documents according to this boost value.<br>
Formula to calculate the link based ranking:<br>
Assumption: number of incoming links =  number of outgoing links (since we are comparing the values of the fields)
Total number of documents : Td	Total number of fields : Tf and the multiplication numerals are the assumed assigned weights for the respective meta-data fields.<br>
Rank  of the current document = <br>
( [(Number of documents current jobType appears in /Td) * 2]<br>
[(Number of documents current location appears in /Td) * 0.75]<br>
[(Number of documents current title appears in /Td) * 0.75]<br>
[(Number of documents current company appears in /Td) * 0.5] ) /Tf<br><br>

We have also tailored the date type of the date fields: postedDate, firstSeenDate, lastSeenDate in our dataset to the UTC date format to utilize solr date operations and functions for the challenge questions and to maintain consistency.<br><br>

3. Compile the project<br>
4. There is a simple json jar file dependency. Ensure that the buildpath is correct. It is already included in the libs folder in the project.<br>
5. Export the project as an executable java jar file and save it to the desired destination<br>
6. Enter the following command in the command line<br>
java -jar NameOfTheExportedJarFile.jar -i "Path to the folder containing the json files"<br><br>

( The input folder containing the json files should be structured acording to the output of the first assignmnet which is as follows: MainFolder/Folders containing the json files/json files)<br><br>

Note: Please refer the Search Project for query execution<br><br>

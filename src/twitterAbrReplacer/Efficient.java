package twitterAbrReplacer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Efficient {

    // Global abbreviations hash map
    static HashMap<String, String> abr = new HashMap<>();
    // Global number of changes
    static int changes = 0;

    public static void main(String[] args) {
        long preProcStart = System.currentTimeMillis();  // start timer for pre-processing

        // Create abr hash map
        abr = csvToMap("abr.csv"); // O(m) Runtime and Storage, where m is number of abbreviations

        long preProcEnd = System.currentTimeMillis();    // end timer for pre-processing

        if(abr.containsKey(null)) {
            System.out.println("Program Terminated");
            return;
        }
        int count = 0;
        long procStart = 0;
        long procEnd = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("sxswTweets.csv"));
            reader.readLine();
            String row;

            procStart = System.currentTimeMillis();  // start timer for processing

            // O(n) runtime, where n is the number of tweets.
            while ((row = reader.readLine()) != null) {                  // read until end of file
                System.out.println(abrReplace(row));
                count++;
            }

            procEnd = System.currentTimeMillis();  // end timer for processing

            // Catch Exceptions
        } catch(FileNotFoundException e) {
            System.out.println("Cannot Find sxswTweets.csv");
            return;
        } catch (IOException e) {
            System.out.println("Error Reading File");
            return;
        }

        System.out.println("Number of tweets: " + count);
        System.out.println("Number of changes: " + changes);

        System.out.println("Pre-processing time: " + (preProcEnd - preProcStart) + "ms");
        System.out.println("Processing time: " + (procEnd - procStart) + "ms");
    }

    /*
     * Input: A string of the csv filename (with .csv extension) that has 2 columns.
     * Output: A hashmap of the 1st column as the key, and the 2nd column as the value
     *         The values are modified to lowercase.
     * O(m) Runtime, where m is the number of abbreviations
     * O(2m) -> O(m) Storage, where m is the number of abbreviations
     */
    public static HashMap<String, String> csvToMap(String csvName) {
        HashMap<String, String> csvMap = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvName));
            reader.readLine();                                           // read first line to remove column titles

            String row;

            // O(m) runtime, where m is the number of lines(abbreviations)
            while ((row = reader.readLine()) != null) {                  // read until end of file
                String[] kv = row.split(",");                     // split the row into col 1 and 2 based on ,
                csvMap.put(kv[0].toLowerCase(), kv[1].toLowerCase());   // input key value pair into hashmap, both made lower case
            }

            return csvMap;

        // Catch Exceptions
        } catch(FileNotFoundException e) {
            System.out.println(String.format("Cannot Find %s", csvName));
        } catch (IOException e) {
            System.out.println("Error Reading File");
        }

        // If exception is caught, clear the map and return a null map
        csvMap.clear();
        csvMap.put(null, null);
        return csvMap;
    }

    /*
     * Input: An unmodified tweet
     * Return: A tweet with abbreviations replaced with the full meaning
     *  Abbreviations are pulled from the global hashmap abr
     * O(s) runtime and storage, where s is the of words in rawTweet
     */
    public static String abrReplace(String rawTweet) {
        String[] tweetSubstrings = rawTweet.split(" ");  // tweets split on spaces

        for(int i = 0; i < tweetSubstrings.length; i++)
            if (abr.containsKey(removePunc(tweetSubstrings[i].toLowerCase()))) {                // if a substring is an abbreviation
                tweetSubstrings[i] = abr.get(tweetSubstrings[i]);                               // replace it with the full meaning
                changes++;
            }
        return String.join(" ", tweetSubstrings);          // combine tweet, adding back in spaces
    }

    /*
     * Input: A string
     * Return: A word stripped of punctuation
     * O(1) runtime
     */
    public static String removePunc(String word) {
        if(word.length() < 2)
            return word;

        char lastChar = word.charAt(word.length() - 1);
        if (lastChar == '!' | lastChar == '.' | lastChar == ',')
            return word.substring(0, word.length() - 1);

        return word;
    }
}
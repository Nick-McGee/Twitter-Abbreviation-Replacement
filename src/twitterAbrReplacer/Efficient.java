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

    public static void main(String[] args) {

        // TODO: Add timer for cvsToMap pre-processing

        // Create abr hash map
        abr = csvToMap("abr.csv"); // O(m) Runtime and Storage, where m is number of abbreviations
        if(abr.containsKey(null)) {
            System.out.println("Program Terminated");
            return;
        }

        // TODO: Replace tweetsTemp with a dataset
        String[] tweetsTemp = {"This is just a test lol",
                               "Hopefully this works, lol or not"};

        /*
         * Queue Justification:
         *  Once a raw tweet is processed, we no longer need it.
         *  If we kept the raw tweets, at the end of the program we would have O(2n) size of tweets.
         *  By using queues, we maintain a size of O(n) by removing old tweets.
         *  This will be more noticeable on very large datasets.
         */

        // TODO: Add timer for dataset to queue pre-processing
        Queue<String> rawTweets = tweetsToQueue(tweetsTemp);  // queue of raw tweets from dataset


        Queue<String> modifiedTweets = new LinkedList<>();    // tweets with changed abbreviations

        // TODO: Add timer for tweets processing
        for(int i = 0; i <= rawTweets.size(); i++)
            modifiedTweets.add(abrReplace(rawTweets.poll()));

        System.out.println(modifiedTweets.poll());
        System.out.println(modifiedTweets.poll());
        System.out.println(modifiedTweets.poll());
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
     * Input: An array strings containing tweets
     * Output: A queue of the tweets
     * O(n) Runtime and Storage, where n is the number of tweets
     * TODO: Read from a dataset instead of an array
     */
    public static Queue<String> tweetsToQueue(String[] tweets) {
        Queue<String> tweetQueue = new LinkedList<>();

        // O(n) runtime, where n is the number of tweets
        for (String tweet: tweets)
            tweetQueue.add(tweet);

        return tweetQueue;
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
            if (abr.containsKey(tweetSubstrings[i]))                // if a substring is an abbreviation
                tweetSubstrings[i] = abr.get(tweetSubstrings[i]);   // replace it with the full meaning

        return String.join(" ", tweetSubstrings);          // combine tweet, adding back in spaces
    }
}
package twitterAbrReplacer;

import java.io.*;

public class Brute {

    // Global abbreviations 2-d array abr
    static String[][] abr;
    // Global number of changes
    static int changes = 0;

    public static void main(String[] args) {
        long preProcStart = System.currentTimeMillis();  // start timer for pre-processing
        abr = csvToArray("abr.csv");            // convert csv file to the 2d array
        long preProcEnd = System.currentTimeMillis();    // end timer for pre-processing

        if(abr[0][0] == null)  // terminate the program if an exception occurs while creating the array
            return;

        int count = 0;
        long procStart = 0;
        long procEnd = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("sxswTweets.csv"));
            reader.readLine();
            String row;

            procStart = System.currentTimeMillis();  // start timer for processing

            // O(mn) runtime, where m is number of abbreviations, and n is the of words in rawTweet
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
     * Output: A 2d array, with the first index being the abbreviation, and the 2nd column as the full value
     *         The values are modified to lowercase.
     * O(m) Runtime, where m is the number of abbreviations
     * O(2m) -> O(m) Storage, where m is the number of abbreviations
     */
    public static String[][] csvToArray(String csvName) {
        //Notice how the array length must be the size of the number of abbreviations. Another reason to not use this solution.
        String[][] csvArray = new String[74][2];  // create a 2d array that can hold 74 abbreviations

        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvName));
            reader.readLine();                                           // read first line to remove column titles

            String row;
            int index = 0;

            // O(m) runtime, where m is the number of lines(abbreviations)
            while ((row = reader.readLine()) != null) {                  // read until end of file
                String[] kv = row.split(",");                     // split the row into col 1 and 2 based on ,
                csvArray[index][0] = kv[0].toLowerCase();               // set first index to the abbreviation
                csvArray[index][1] = kv[1].toLowerCase();               // set second index to the full value
                index++;
            }

            return csvArray;

            // Catch Exceptions
        } catch (FileNotFoundException e) {
            System.out.println(String.format("Cannot Find %s", csvName));
        } catch (IOException e) {
            System.out.println("Error Reading File");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Too Many Abbreviations");
        }

        // If exception is caught, set first index to null
        csvArray[0][0] = null;
        return csvArray;
    }

    /*
     * Input: An unmodified tweet
     * Return: A tweet with abbreviations replaced with the full meaning
     *  Abbreviations are pulled from the global 2-D String array abr
     * O(ms) runtime, where m is number of abbreviations, and s is the of words in rawTweet
     */
    public static String abrReplace(String rawTweet) {
        String[] tweetSubstrings = rawTweet.split(" ");  // tweets split on spaces

        // loop over every word in the tweet
        for (int i = 0; i < tweetSubstrings.length; i++)
            // loop over every abbreviation
            for(int j = 0; j < abr.length - 1; j++) {
                String subTweet = removePunc(tweetSubstrings[i].toLowerCase());  // remove punctuation and move to lowercase

                // if an abbreviation is found
                if (abr[j][0].equals(subTweet)) {
                    tweetSubstrings[i] = abr[j][1];    // set the word to the full value
                    changes++;                         // increment number of changes made
                    break;                             // move on to the next word
                }
            }
        return String.join(" ", tweetSubstrings);          // combine tweet, adding back in spaces
    }

    /*
     * Input: A string
     * Return: A word stripped of punctuation
     * O(1) runtime
     */
    public static String removePunc(String word) {
        if (word.length() < 2)
            return word;

        char lastChar = word.charAt(word.length() - 1);
        if (lastChar == '!' | lastChar == '.' | lastChar == ',')
            return word.substring(0, word.length() - 1);

        return word;
    }
}
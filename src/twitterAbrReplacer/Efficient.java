package twitterAbrReplacer;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Efficient {
    public static void main(String[] args) {
        HashMap<String, String> abr = csvToMap("abr.csv");
        System.out.println(abr.get("lol"));
    }

    /*
     * Input: A string of the csv filename (with .csv extension) that has 2 columns.
     * Return: A hashmap of the 1st column as the key, and the 2nd column as the value
     *         The values are modified to lowercase.
     */
    public static HashMap<String, String> csvToMap(String csvName) {
        HashMap<String, String> csvMap = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvName));

            String row;
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

        // If exception is caught, make sure not returning NULL by adding 'none' key value pair
        csvMap.put("none", "none");
        return csvMap;
    }
}
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class bruteForceSol {


    public static void main(String [] args){
        HashMap<String, String> map = makeAcrynoms();
        ArrayList<String> sent = makeSentences();
        printWords(map, sent.get(2));

    }


    //Makes an arraylist of common acrynoms, mostly used in texting.
    public static HashMap<String, String> makeAcrynoms(){
        HashMap<String, String> acrynoms = new HashMap<String, String>();
        acrynoms.put("lol", "laugh out loud");
        acrynoms.put("gtg", "got to go");
        acrynoms.put("thx", "thanks");
        acrynoms.put("lmao", "laughing my ass off");
        acrynoms.put("lmk", "let me know");
        acrynoms.put("smh", "shaking my head");
        acrynoms.put("nvm", "never mind");
        acrynoms.put("yolo", "you only live once");
        acrynoms.put("ttyl", "talk to you later");
        return acrynoms;
    }


    //Random sentences to test the program.
    public static ArrayList makeSentences(){
        ArrayList<String> sentence = new ArrayList<>();
        sentence.add("gtg i'll ttyl");
        sentence.add("lol that is funny");
        sentence.add("lmk when you find out!");
        sentence.add("nvm its I got it! thx anyways!");
        sentence.add("yolo");

        return sentence;
    }

    public static void printWords(HashMap acr, String sentence){
        String[] words = sentence.split(" ");

            for(int i = 0 ; i < words.length; i++){
                for(Object k : acr.keySet()){
                    if(words[i].equals(k)){
                        words[i] = (String)acr.get(k);
                    }
                }
            }

        for (int i = 0 ; i < words.length; i++){
            System.out.print(words[i] + " ");
        }
    }



}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.princeton.cs.algs4.TrieST;
import sun.misc.Cache;

public class BoggleSolver{

    TrieST tst = new TrieSt();
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){
        for(int i = 0; i<dictionary.length; i++){
            tst.put(dictionary[i], scoreOf(dictionary[i]));
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){

    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        int wLen = word.length();
        if(wLen == 0){
            throw new IllegalArgumentException();
        }
        else{
            switch(wLen){
                case 0: return 0;
                case 1: return 0;
                case 2: return 0;
                case 3: return 1;
                case 4: return 1;
                case 5: return 2;
                case 6: return 3;
                case 7: return 5;
                
            }
        }
        return 0;    
    }

    public static void main(String args[])throws IOException{
        ArrayList<String> ar = new ArrayList<String>();
        FileReader file = new FileReader("/home/user/Documents/ADS2_2019501091/ADS2_2019501091/Week-3/dictionary-algs4.txt");
        BufferedReader BFile = new BufferedReader(file);
        String s;
        while((s = BFile.readLine()) != null){
            ar.add(s);
        }
        String[] sArray = new String[ar.size()];
        sArray=ar.toArray(sArray);
        for(int i = 0; i<sArray.length; i++){
            System.out.println(sArray[i]);
        }
        BFile.close();
    }
}

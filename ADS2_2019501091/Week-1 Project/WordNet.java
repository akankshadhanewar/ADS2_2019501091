import java.io.*;
import java.util.*;
import edu.princeton.cs.algs4.Digraph;

public class WordNet {
    private Digraph di;
    private final SAP sa;
    private final int ver;

    HashMap<String, List<Integer>> synsets = new HashMap<String, List<Integer>>();
    HashMap<Integer, List<Integer>> hypernyms = new HashMap<Integer, List<Integer>>();
    ArrayList<String> al = new ArrayList<>();
    int count;
    int count1;

    public int ParseSysets(String file){
        try{
            FileReader HFile = new FileReader(file);
            BufferedReader BFile = new BufferedReader(HFile);
            String s1;
            while((s1 = BFile.readLine()) != null){
                String[] str = s1.split(",");
                al.add(Integer.parseInt(str[0]), str[1]);
                String[] str2= str[1].split(" ");
                for (int i = 0; i < str2.length; i++) {
                    if(synsets.get(str2[i]) == null){
                        synsets.put(str2[i],new ArrayList<>());
                    }
                    synsets.get(str2[i]).add(Integer.parseInt(str[0]));
                }
            }   
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return synsets.size();
    }
    
    public Digraph ParseHypernyms(String file){
        try{
            FileReader HFile = new FileReader(file);
            BufferedReader BFile = new BufferedReader(HFile);
            String s1;
            while((s1 = BFile.readLine()) != null){
                List<Integer> val = new ArrayList<>();
                String[] str = s1.split(",",2);
                if(s1.contains(",")){
                    for(String str1 : str[1].split(",")){
                        val.add(Integer.parseInt(str1));
                    }
                    hypernyms.put(Integer.parseInt(str[0]),val);   
                }
                else{
                    hypernyms.put(Integer.parseInt(str[0]),val);
                }
            }
            di = new Digraph(hypernyms.size());
            for(int key: hypernyms.keySet()){
                for(int val: hypernyms.get(key)){
                    di.addEdge(key,val);
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return di;
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        ver = ParseSysets(synsets);
        di = ParseHypernyms(hypernyms);
        sa = new SAP(di);
    }
 
    // returns all WordNet nouns
    public Iterable<String> nouns(){
        List<String> l = new ArrayList<String>();
        for(String s: synsets.keySet()){
            l.add(s);
        }
        return l;
    }
 
    // is the word a WordNet noun?
    public boolean isNoun(String word){
        return synsets.containsKey(word);
    }
 
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(synsets.containsKey(nounA) && synsets.containsKey(nounB)){
            int dist = sa.length(synsets.get(nounA),synsets.get(nounB));
            return dist;
        }
        return -1;
    }
 
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(synsets.containsKey(nounA) && synsets.containsKey(nounB)){
            return al.get(sa.ancestor(synsets.get(nounA),synsets.get(nounB)));
        }
        return null;
    }
 
    // do unit testing of this class
    // public static void main(String[] args)throws Exception{
    //     WordNet wn = new WordNet("/home/user/Documents/ADS2_2019501091/ADS2_2019501091/synsets.txt", "/home/user/Documents/ADS2_2019501091/ADS2_2019501091/hypernyms15Tree.txt");
        
    // }
 }
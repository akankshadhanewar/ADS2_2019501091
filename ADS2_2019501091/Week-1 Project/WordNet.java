import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.princeton.cs.algs4.Digraph;

public class WordNet {
    private Digraph di;
    private SAP sa;
    private final HashMap<String, List<Integer>> synsets = new HashMap<String, List<Integer>>();
    private final HashMap<Integer, List<Integer>> hypernyms = new HashMap<Integer, List<Integer>>();
    private final ArrayList<String> al = new ArrayList<>();

    private void ParseSysets(String file){
        BufferedReader BFile = null;
        try{
            BFile = new BufferedReader(new FileReader(file));
            String s1 = null;
            while((s1 = BFile.readLine()) != null){
                String[] str = s1.split(",");
                al.add(str[1]);
                String[] str2= str[1].split(" ");
                for (int i = 0; i < str2.length; i++){
                    if(synsets.get(str2[i]) == null){
                        synsets.put(str2[i],new ArrayList<>());
                    }
                    synsets.get(str2[i]).add(Integer.parseInt(str[0]));
                }
            }
            //BFile.close();
        }
        catch(FileNotFoundException ex){
            System.out.println(ex);
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally{
            if (BFile != null) {
                try {
                    BFile.close();
                } catch (IOException ioex) {
                    System.out.println(ioex);
                }
            }
        }
    }
    
    private Digraph ParseHypernyms(String file){
        BufferedReader BFile = null;
        try{ 
            BFile = new BufferedReader(new FileReader(file));
            String s1 = null;
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
            //BFile.close();
        }
        catch(FileNotFoundException f){
            System.out.println(f);
        }
        catch(IOException io){
            System.out.println(io);
        }
        finally{
            if (BFile != null) {
                try {
                    BFile.close();
                } catch (IOException fclose) {
                    System.out.println(fclose);
                }
            }
        }
        return di;
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        try{
            ParseSysets(synsets);
            di = ParseHypernyms(hypernyms);
            sa = new SAP(di);
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
        finally{
            if(synsets == null || hypernyms == null){
                throw new IllegalArgumentException();
            }
        }
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
        if(word == null){
            throw new IllegalArgumentException();
        }
        return synsets.containsKey(word);
    }
 
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(nounA == null || nounB == null || nounA == "x" || nounB == "x"){
            throw new IllegalArgumentException();
        }
        try{
            if(synsets.containsKey(nounA) && synsets.containsKey(nounB)){
                int dist = sa.length(synsets.get(nounA),synsets.get(nounB));
                return dist;
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
        return -1;
    }
 
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(nounA == null || nounB == null || nounA == "x" || nounB == "x"){
            throw new IllegalArgumentException();
        }
        try{
            if(synsets.containsKey(nounA) && synsets.containsKey(nounB)){
                return al.get(sa.ancestor(synsets.get(nounA),synsets.get(nounB)));
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
        return null;
    }
}

import java.io.*;
import java.util.*;
import edu.princeton.cs.algs4.Digraph;

public class ParsingFiles{
    HashMap<String, List<Integer>> synsets = new HashMap<String, List<Integer>>();
    HashMap<Integer, List<Integer>> hypernyms = new HashMap<Integer, List<Integer>>();
    int count;
    int count1;

    public void ParseSysets(String file) throws Exception{
        FileReader HFile = new FileReader(file);
        BufferedReader BFile = new BufferedReader(HFile);
        String s1;
        while((s1 = BFile.readLine()) != null){
            String[] str = s1.split(",");
            String[] str2= str[1].split(" ");
            for (int i = 0; i < str2.length; i++) {
                if(synsets.get(str2[i]) == null){
                    synsets.put(str2[i],new ArrayList<>());
                }
                synsets.get(str2[i]).add(Integer.parseInt(str[0]));
            }
        }
        //System.out.println(synsets.toString());
        HFile.close();
        BFile.close();
    }
    
    public void ParseHypernyms(String file) throws Exception{
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
        //System.out.println(hypernyms.toString());
        HFile.close();
        BFile.close();
    }    

    public static void main(String[] args) throws Exception {
        ParsingFiles pf = new ParsingFiles();
        pf.ParseSysets("/home/user/Documents/ADS2_2019501091/ADS2_2019501091/synsets.txt");
        pf.ParseHypernyms("/home/user/Documents/ADS2_2019501091/ADS2_2019501091/hypernyms.txt");
        Digraph d = new Digraph(pf.hypernyms.size());
        for(int key: pf.hypernyms.keySet()){
            pf.count++;
            for(int val: pf.hypernyms.get(key)){
                d.addEdge(key,val);
                pf.count1++;
            }
        }
        System.out.println(pf.count+", "+pf.count1);
    }
}
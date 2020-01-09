import java.io.*;
import java.util.*;

public class DrawDigraph{
    HashMap<String, List<Integer>> synsets = new HashMap<String, List<Integer>>();
    HashMap<Integer, List<Integer>> hypernyms = new HashMap<Integer, List<Integer>>();
    int count;
    int count1;

    public void ParseSysets() throws Exception{
        FileReader HFile = new FileReader("/home/user/Documents/ADS2_2019501091/ADS2_2019501091/synsets.txt");
        BufferedReader BFile = new BufferedReader(HFile);
        
        String s2;
        while((s2 = BFile.readLine()) != null){
            String[] str = s2.split(",");
            String[] str2= str[1].split(" ");
            for (int i = 0; i < str2.length; i++) {
                if(synsets.get(str2[i]) == null){
                    synsets.put(str2[i],new ArrayList<>());
                }
                synsets.get(str2[i]).add(Integer.parseInt(str[0]));
            }
        }
        //System.out.println(synsets.toString());
    }
    
    public void ParseHypernyms() throws Exception{
        FileReader HFile = new FileReader("/home/user/Documents/ADS2_2019501091/ADS2_2019501091/hypernyms.txt");
        BufferedReader BFile = new BufferedReader(HFile);
        String s2;
        while((s2 = BFile.readLine()) != null){
            List<Integer> val = new ArrayList<>();
            String[] str = s2.split(",",2);
            if(s2.contains(",")){
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
    }

    public static void main(String[] args) throws Exception {
        DrawDigraph df = new DrawDigraph();

        df.ParseSysets();
        df.ParseHypernyms();
        Digraph d = new Digraph(df.hypernyms.size());
        for(int key: df.hypernyms.keySet()){
            df.count++;
            for(int val: df.hypernyms.get(key)){
                d.addEdge(key,val);
                df.count1++;
            }
        }
        System.out.println(df.count+", "+df.count1);
    } 
}
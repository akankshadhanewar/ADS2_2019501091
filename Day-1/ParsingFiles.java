import java.io.*;
import java.util.*;

public class ParsingFiles{

    public void ParseSysets() throws Exception{
        FileReader HFile = new FileReader("/home/user/Documents/ADS2_2019501091/Day-1/synsets.txt");
        BufferedReader BFile = new BufferedReader(HFile);
        HashMap<String, List<Integer>> h1 = new HashMap<String, List<Integer>>();
        String s2;
        while((s2 = BFile.readLine()) != null){
            String[] str = s2.split(",");
            String[] str2= str[1].split(" ");
            for (int i = 0; i < str2.length; i++) {
                if(h1.get(str2[i]) == null){
                    h1.put(str2[i],new ArrayList<>());
                }
                h1.get(str2[i]).add(Integer.parseInt(str[0]));
            }
        }
        System.out.println(h1.toString());
    }
    
    public void ParseHypernyms() throws Exception{
        FileReader HFile = new FileReader("/home/user/Documents/ADS2_2019501091/Day-1/hypernyms6TwoAncestors.txt");
        BufferedReader BFile = new BufferedReader(HFile);
        HashMap<Integer, List<Integer>> h1 = new HashMap<Integer, List<Integer>>();
        String s2;
        while((s2 = BFile.readLine()) != null){
            String[] str = s2.split(",",2);
            if(s2.contains(",")){
                List<Integer> val = new ArrayList<>();
                for(String str1 : str[1].split(",")){
                    val.add(Integer.parseInt(str1));
                }
                h1.put(Integer.parseInt(str[0]),val);   
            }
            else{
                h1.put(Integer.parseInt(str[0]),null);
            }
        }
        System.out.println(h1.toString());
    }

    public static void main(String[] args) throws Exception {
        ParsingFiles PF = new ParsingFiles();
        PF.ParseSysets();
        PF.ParseHypernyms();
    }
}
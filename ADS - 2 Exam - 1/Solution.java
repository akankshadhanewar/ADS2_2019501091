import java.io.*;
import java.util.*;

public class Solution{
    HashMap <Integer, String> emails = new HashMap<Integer, String>();
    HashMap<Integer, List<Integer>> email_logs = new HashMap<Integer, List<Integer>>();
    HashMap<Integer, Integer> temp = new HashMap<Integer,Integer>();
    List<Integer> l = new ArrayList<Integer>();
    int count = 1;

    public void ParseEmails(String file) throws Exception{
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        String s;
        while((s=b.readLine()) != null){
            String[] s1 = s.split(";");
            for(int i=0; i<s1.length; i++){
                emails.put(Integer.parseInt(s1[0]), (s1[1]));
            }
        }
    }

    public void ParseEmail_logs(String file) throws Exception{
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        String s;
        while((s=b.readLine()) != null){
            String[]s1 = s.split(",");
            String[] s2 = s1[0].split(" ");
            String[] s3 = s1[1].split(" ",3);
            if(email_logs.containsKey(Integer.parseInt(s3[2]))){
                l=email_logs.get(Integer.parseInt(s3[2]));
                l.add(Integer.parseInt(s2[1]));
            }
            else{
                l=new ArrayList<Integer>();
                l.add(Integer.parseInt(s2[1]));
                email_logs.put(Integer.parseInt(s3[2]), l);
            }   
            //temp.put(Integer.parseInt(s3[2]), Integer.parseInt(s2[1]));
            //System.out.println(temp.values()+","+temp.values().stream().filter(v -> v == Integer.parseInt(s2[1])).count());
        }
        for(long k = 0; k<email_logs.size();k++){
            long count = email_logs.values().stream().filter(v -> v == k).count();
            System.out.println(k + ","+ count);
        }
    }



    public static void main(String args[])throws Exception{
        Solution sol = new Solution();
        sol.ParseEmails("/home/user/Documents/ADS2_2019501091/ADS - 2 Exam - 1/emails.txt");
        sol.ParseEmail_logs("/home/user/Documents/ADS2_2019501091/ADS - 2 Exam - 1/email-logs.txt");
    }

}
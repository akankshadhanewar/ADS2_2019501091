import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private String string;
    private Integer [] index;
    // circular suffix array of s
    public CircularSuffixArray(String str){
        if (str==null){
            throw new IllegalArgumentException();
        }
        string=str;
        index=new Integer[length()];
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        }
        Arrays.sort(index,new Comparator<Integer>() {
            public int compare(Integer a, Integer b){
                int first = a;
                int second = b;
                for (int i = 0; i < string.length(); i++) {
                    char x= string.charAt(first);
                    char y= string.charAt(second);
                    if (x<y){
                        return -1;
                    }
                    else if(x>y){
                        return 1;
                    }
                    ++first;
                    ++second;
                    if (first == string.length()){
                        first =0;
                    }
                    if (second == string.length()){
                        second=0;
                    }
                }
                return 0;
            }
        });

    }

    // length of s
    public int length(){
        return string.length();
    }

    // returns index of ith sorted suffix
    public int index(int i){
        if (i>=string.length() || i<0){
            throw new IllegalArgumentException();
        }
        return index[i];
        
    }

    // unit testing (required)
    public static void main(String[] args){}

}
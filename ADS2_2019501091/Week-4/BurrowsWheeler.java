import java.util.HashMap;
import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int l=8; 
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform(){
        String s= BinaryStdIn.readString();
        CircularSuffixArray circular_s_a = new CircularSuffixArray(s);
        for (int i = 0; i < s.length(); ++i) {
            if(circular_s_a.index(i) == 0){
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < circular_s_a.length(); ++i) {
            int ind= circular_s_a.index(i);
            if(ind ==0){
                BinaryStdOut.write(s.charAt(s.length()-1),l);
            }
            else{
                BinaryStdOut.write(s.charAt(ind-1),l);
            }
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform(){
        int f = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] ch = s.toCharArray();
        HashMap<Character, Queue<Integer>> table
        = new HashMap<Character, Queue<Integer>>();
        for (int i = 0; i < ch.length; ++i) {
            if (!table.containsKey(ch[i])) {
                table.put(ch[i], new Queue<Integer>());
            }
            table.get(ch[i]).enqueue(i);
        }

        Arrays.sort(ch);
        int[] next = new int[ch.length];
        for (int i = 0; i < next.length; ++i) {
            next[i] = table.get(ch[i]).dequeue();
        }

        for (int i = 0; i < next.length; ++i) {
            BinaryStdOut.write(ch[f], 8);
            f = next[f];
        }
        BinaryStdOut.close();

    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args){}

}
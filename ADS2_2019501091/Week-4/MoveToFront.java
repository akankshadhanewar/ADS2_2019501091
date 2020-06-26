import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int r=8;
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode(){
        char[] c_array = new char [256];
        for (int i = 0; i < 256; i++) {
            c_array[i] = (char)i;
        }
        while (!BinaryStdIn.isEmpty()){
            char c = BinaryStdIn.readChar();
            char t1 = c;
            char t2 = c;
            for (int i = 0; i < 256; i++) {
                if (c == c_array[i]){
                    BinaryStdOut.write(i,r);
                    c_array[i]=t1;
                    break;
                }
                t2=c_array[i];
                c_array[i]=t1;
                t1=t2;
            }
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode(){
        char[] c_array = new char [256];
        for (int i = 0; i < c_array.length; i++) {
            c_array[i] = (char)i;
        }
        while (!BinaryStdIn.isEmpty()){
            char ch = BinaryStdIn.readChar();
            char temp1 = c_array[ch];
            char temp2 = c_array[ch];
            BinaryStdOut.write(c_array[ch],r);
            for (int i = 0; i < c_array.length; i++) {
                if(c_array[ch] == c_array[i]){
                    c_array[i]=temp2;
                    break;
                }
                temp2 = c_array[i];
                c_array[i] = temp1;
                temp1=temp2;
            }
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args){}

}
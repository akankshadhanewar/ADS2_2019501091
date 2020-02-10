import java.util.Set;
import java.util.HashSet;

public class BoggleSolver{

    private TrieSET ts;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){
        ts = new TrieSET();
        for(int i = 0; i<dictionary.length; i++){
            ts.add(dictionary[i]);
        }
    }

    private void visited(BoggleBoard board, int i, int j, boolean[][] array, String str, Set<String> s){
        if(array[i][j]){
            return;
        }
        char ch = board.getLetter(i,j);
        String st = str;
        if(ch == 'Q'){
            st = st+"QU";
        }
        else{
            st = st+ch;
        }
        if(!ts.hasPrefix(st)){
            return;
        }
        if(st.length() > 2 && ts.contains(st)){
            s.add(st);
        }
        array[i][j] = true;
        for (int k = -1; k <= 1; k++){
            for (int k2 = -1; k2 <= 1; k2++) {
                if(k == 0 && k2 == 0){
                    continue;
                }
                if(i+k >= 0 && i+k < board.rows() && j+k2 >= 0 && j+k2 < board.cols()){
                    visited(board, i+k, j+k2, array, st, s);
                }
            }
        }
        array[i][j] = false;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        Set<String> set = new HashSet<String>();
        boolean[][] arr = new boolean[board.rows()][board.cols()];
        for (int i = 0; i < board.rows() ; i++){
            for (int j = 0; j < board.cols(); j++) {
                visited(board,i,j,arr,"",set);
            }
        }
        return set;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        if(ts.contains(word)){
            switch(word.length()){
                case 0:
                case 1: 
                case 2: return 0;
                case 3: 
                case 4: return 1;
                case 5: return 2;
                case 6: return 3;
                case 7: return 5;
                default: return 11;
            } 
        }else {
            return 0;
        }   
    }

    // public static void main(String args[])throws IOException{
    //     HashSet<String> hs = new HashSet<String>();
    //     FileReader file = new FileReader("/home/user/Documents/ADS2_2019501091/ADS2_2019501091/Week-3/dictionary-algs4.txt");
    //     BufferedReader BFile = new BufferedReader(file);
    //     String s;
    //     while((s = BFile.readLine()) != null){
    //         hs.add(s);
    //     }
    //     String[] sArray = new String[hs.size()];
    //     sArray = hs.toArray(sArray);
    //     // for(int i = 0; i<sArray.length; i++){
    //     //     System.out.println(sArray[i]);
    //     // }
    //     BoggleSolver bs = new BoggleSolver(sArray);
    //     System.out.println(bs.ts.size());
    //     BFile.close();
    // }
}

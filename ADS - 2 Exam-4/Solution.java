import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    
    static class LongestRepeatedSubstring {
    
        public LongestRepeatedSubstring() { }

        public String lrs(String text) {
            int n = text.length();
            SuffixArray sa = new SuffixArray(text);
            String lrs = "";
            for (int i = 1; i < n; i++) {
                int length = sa.lcp(i);
                if (length > lrs.length()) {
                    // lrs = sa.select(i).substring(0, length);
                    lrs = text.substring(sa.index(i), sa.index(i) + length);
                }
            }
            return lrs;
        }
    }

    class SuffixArray {

        private Suffix[] suffixes;
    
        public SuffixArray(String text) {
            int n = text.length();
            this.suffixes = new Suffix[n];
            for (int i = 0; i < n; i++)
                suffixes[i] = new Suffix(text, i);
            Arrays.sort(suffixes);
        }
    
    
        class Suffix implements Comparable<Suffix> {
            private final String text;
            private final int index;

            private Suffix(String text, int index) {
                this.text = text;
                this.index = index;
            }
            private int length() {
                return text.length() - index;
            }
            private char charAt(int i) {
                return text.charAt(index + i);
            }

            public int compareTo(Suffix that) {
                if (this == that) return 0;  // optimization
                int n = Math.min(this.length(), that.length());
                for (int i = 0; i < n; i++) {
                    if (this.charAt(i) < that.charAt(i)) return -1;
                    if (this.charAt(i) > that.charAt(i)) return +1;
                }
                return this.length() - that.length();
            }

            public String toString() {
                return text.substring(index);
            }
        }

        /**
         * Returns the length of the input string.
         * @return the length of the input string
         */
        public int length() {
            return suffixes.length;
        }


        /**
         * Returns the index into the original string of the <em>i</em>th smallest suffix.
         * That is, {@code text.substring(sa.index(i))} is the <em>i</em>th smallest suffix.
         * @param i an integer between 0 and <em>n</em>-1
         * @return the index into the original string of the <em>i</em>th smallest suffix
         * @throws java.lang.IllegalArgumentException unless {@code 0 <= i < n}
         */
        public int index(int i) {
            if (i < 0 || i >= suffixes.length) throw new IllegalArgumentException();
            return suffixes[i].index;
        }


        /**
         * Returns the length of the longest common prefix of the <em>i</em>th
         * smallest suffix and the <em>i</em>-1st smallest suffix.
         * @param i an integer between 1 and <em>n</em>-1
         * @return the length of the longest common prefix of the <em>i</em>th
         * smallest suffix and the <em>i</em>-1st smallest suffix.
         * @throws java.lang.IllegalArgumentException unless {@code 1 <= i < n}
         */
        public int lcp(int i) {
            if (i < 1 || i >= suffixes.length) throw new IllegalArgumentException();
            return lcpSuffix(suffixes[i], suffixes[i-1]);
        }

        // longest common prefix of s and t
        private int lcpSuffix(Suffix s, Suffix t) {
            int n = Math.min(s.length(), t.length());
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) != t.charAt(i)) return i;
            }
            return n;
        }

        /**
         * Returns the <em>i</em>th smallest suffix as a string.
         * @param i the index
         * @return the <em>i</em> smallest suffix as a string
         * @throws java.lang.IllegalArgumentException unless {@code 0 <= i < n}
         */
        public String select(int i) {
            if (i < 0 || i >= suffixes.length) throw new IllegalArgumentException();
            return suffixes[i].toString();
        }

        /**
         * Returns the number of suffixes strictly less than the {@code query} string.
         * We note that {@code rank(select(i))} equals {@code i} for each {@code i}
         * between 0 and <em>n</em>-1.
         * @param query the query string
         * @return the number of suffixes strictly less than {@code query}
         */
        public int rank(String query) {
            int lo = 0, hi = suffixes.length - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int cmp = compare(query, suffixes[mid]);
                if (cmp < 0) hi = mid - 1;
                else if (cmp > 0) lo = mid + 1;
                else return mid;
            }
            return lo;
        }

        // compare query string to suffix
        private int compare(String query, Suffix suffix) {
            int n = Math.min(query.length(), suffix.length());
            for (int i = 0; i < n; i++) {
                if (query.charAt(i) < suffix.charAt(i)) return -1;
                if (query.charAt(i) > suffix.charAt(i)) return +1;
            }
            return query.length() - suffix.length();
        }

}
    
    public static void main(String[] args) {
        
        
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] s = new String[n];
        for(int i = 0; i<n; i++){
            s[i] = sc.nextLine();
            LongestRepeatedSubstring l = new LongestRepeatedSubstring();
            String str = l.lrs(s[i]);
            int count = 0;
            for (int j = 0; j < s[i].length(); j++) {
                if(str.equals(s[i].substring(j, j+str.length()))){
                    count++;
                }
            }
            if(count == 0 || count == s[i].length()){
                System.out.println("No repetitions found!");
            }
            else{
                System.out.println(str+" "+count);
            }   
        }
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
    }
}
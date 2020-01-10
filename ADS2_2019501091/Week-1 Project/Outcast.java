import edu.princeton.cs.algs4.In;

public class Outcast {
    private WordNet wordnet;
    public Outcast(WordNet wordnet){
        this.wordnet = wordnet;
    }        // constructor takes a WordNet object

    public String outcast(String[] nouns){
        String res = null;
        int d = 0;
        for(String s: nouns){
            int dis_s = 0;
            for(String str: nouns){
                int dis_str = wordnet.distance(s, str);
                dis_s += dis_str;
                if(dis_s>d){
                    d = dis_s;
                    res = s;
                }
            }
        }
        return res;

    }   // given an array of WordNet nouns, return an outcast
    public static void main(String[] args) {
        WordNet wordnet = new WordNet("/home/user/Documents/ADS2_2019501091/ADS2_2019501091/synsets.txt", "/home/user/Documents/ADS2_2019501091/ADS2_2019501091/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            System.out.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
 }
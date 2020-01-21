public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet){
        this.wordnet = wordnet;
    }

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
    }
 }
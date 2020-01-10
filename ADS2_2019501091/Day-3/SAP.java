import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    Digraph g;
    int default_len = Integer.MAX_VALUE;
    int ancestor = -1;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        g = G;       
    }
 
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        if(ancestor(v, w) == -1){
            return -1;
        }
        else{
            return default_len;
        }
    }
 
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(g, w);
        for(int i = 0; i<g.V(); i++){
            if(bfsv.hasPathTo(i) && bfsw.hasPathTo(i)){
                int len_v = bfsv.distTo(i);
                int len_w = bfsw.distTo(i);
                if(len_v + len_w < default_len){
                    default_len = len_v+len_w;
                    ancestor = i;
                }
                
            }
        }
        return ancestor;
    }
 
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        List<Integer> l = new ArrayList<>();
        for(int v1: v){
            for(int w1: w){
                l.add(length(v1, w1));
            }
        }
        return Collections.min(l);
    }
 
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        for(int v1: v){
            for(int w1: w){
                ancestor = ancestor(v1, w1);
            }
        }
        return ancestor;
    }
 
    //do unit testing of this class
    public static void main(String[] args) throws Exception {
        ParsingFiles pf = new ParsingFiles();
        pf.ParseSysets("/home/user/Documents/ADS2_2019501091/ADS2_2019501091/synsets.txt");
        pf.ParseHypernyms("/home/user/Documents/ADS2_2019501091/ADS2_2019501091/hypernyms.txt");
        Digraph d = new Digraph(pf.hypernyms.size());
        for(int key: pf.hypernyms.keySet()){
            pf.count++;
            for(int val: pf.hypernyms.get(key)){
                d.addEdge(key,val);
                pf.count1++;
            }
        }
        SAP s = new SAP(d);
        System.out.println(s.ancestor(7,5));
        System.out.println(s.length(7,5));
        System.out.println(pf.count+", "+pf.count1);
    } 
 }
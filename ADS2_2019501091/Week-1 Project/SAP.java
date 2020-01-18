import java.lang.IllegalArgumentException;
import java.util.Iterator;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private final Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        g = new Digraph(G);
    }

    private int[] sap(int v, int w){
        if(v < 0 && v >= g.V()){
            throw new IllegalArgumentException();
        }
        if(w < 0 && w >= g.V()){
            throw new IllegalArgumentException();
        }
        int result[] = new int[2];
        int default_len = Integer.MAX_VALUE;
        int ancestor = -1;
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(g, w);
        for(int i = 0; i < g.V(); ++i){
            if(bfsv.hasPathTo(i) && bfsw.hasPathTo(i)){
                int len_v = bfsv.distTo(i);
                int len_w = bfsw.distTo(i);
                if(len_v + len_w < default_len){
                    default_len = len_v+len_w;
                    ancestor = i;
                }
            }
        }
        if(ancestor == -1){
            result[0] = -1;
            result[1] = -1;
        }
        else{
            result[0] = default_len;
            result[1] = ancestor;
        }
        return result;
    }

    private int[] sap(Iterable<Integer> v, Iterable<Integer> w){
        for(int v1: v){
            if(v1 < 0 && v1 >= g.V()){
                throw new IllegalArgumentException();
            }
            String s = Integer.toString(v1);
            if(s == null){
                throw new IllegalArgumentException();
            }
        }

        for(int w1: w){
            if(w1 < 0 && w1 >= g.V()){
                throw new IllegalArgumentException();
            }
            String s = Integer.toString(w1);
            if(s == null){
                throw new IllegalArgumentException();
            }
        }
        int result[] = new int[2];
        int default_len = Integer.MAX_VALUE;
        int ancestor = -1;
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(g, w);
        for(int i = 0; i < g.V(); ++i){
            if(bfsv.hasPathTo(i) && bfsw.hasPathTo(i)){
                int len_v = bfsv.distTo(i);
                int len_w = bfsw.distTo(i);
                if(len_v + len_w < default_len){
                    default_len = len_v+len_w;
                    ancestor = i;
                }
            }
        }
        if(ancestor == -1){
            result[0] = -1;
            result[1] = -1;
        }
        else{
            result[0] = default_len;
            result[1] = ancestor;
        }
        return result;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        int length[] = sap(v, w);
        return length[0];
    }
 
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        int ancestor[] = sap(v, w);
        return ancestor[1];
    }
 
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null){
            throw new IllegalArgumentException();
        }
        int length[] = sap(v, w);
        return length[0];
    }
 
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null){
            throw new IllegalArgumentException();
        }
        int ancestor[] = sap(v, w);
        return ancestor[1];
    }
}

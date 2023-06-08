package com.example.integrativetask_ii_ced.structure.interfaces;

import com.example.integrativetask_ii_ced.structure.graph.Vertex;
import com.example.integrativetask_ii_ced.structure.narytree.NaryTree;

import java.util.ArrayList;

public interface Igraph <V>{

    boolean insertVertex(V valueVertex);
    boolean insertEdge(V from, V to);
    boolean deleteVertex(V valueVertex);

    boolean deleteEdge(V from, V to);


    NaryTree<V> bfs(V from);
    ArrayList<NaryTree<V>> dfs();
    void dfsVisit(Vertex<V> from, NaryTree<V> tree);
    Vertex<V> searchVertex(V values);

}

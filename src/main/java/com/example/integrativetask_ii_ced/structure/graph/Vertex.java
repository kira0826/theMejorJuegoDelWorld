package com.example.integrativetask_ii_ced.structure.graph;



import com.example.integrativetask_ii_ced.structure.interfaces.ColorType;

import java.util.ArrayList;

public class Vertex <V> {

    private Vertex<V> father;
    private ArrayList<Vertex<V>> adjacency;
    private V value;
    private Integer distance;
    private ColorType color;

    public Vertex(V value) {
        this.value = value;
        adjacency = new ArrayList<>();
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public ColorType getColor() {
        return color;
    }

    public void setColor(ColorType color) {
        this.color = color;
    }

    public Vertex<V> getFather() {
        return father;
    }

    public void setFather(Vertex<V> father) {
        this.father = father;
    }

    public ArrayList<Vertex<V>> getAdjacency() {
        return adjacency;
    }

    public void setAdjacency(ArrayList<Vertex<V>> adjacency) {
        this.adjacency = adjacency;
    }
}

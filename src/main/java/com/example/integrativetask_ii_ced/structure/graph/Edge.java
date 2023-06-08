package com.example.integrativetask_ii_ced.structure.graph;

public class Edge <V extends Comparable<V>> implements Comparable<Edge<V>> {
    private Vertex<V> from;
    private Vertex<V> to;
    private double weight;

    public Edge(Vertex<V> from, Vertex<V> to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Vertex<V> getFrom() {
        return from;
    }

    public void setFrom(Vertex<V> from) {
        this.from = from;
    }

    public Vertex<V> getTo() {
        return to;
    }

    public void setTo(Vertex<V> to) {
        this.to = to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge<V> o) {
        return Double.compare(this.weight, o.weight);
    }
}

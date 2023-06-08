package com.example.integrativetask_ii_ced.model.drawing;

import java.util.Objects;

public class Coordinate implements Comparable<Coordinate> {

    private double x;
    private double y;

    public Coordinate() {
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {

        Coordinate coordinate = (Coordinate) obj;
        return this.x== coordinate.getX() && this.y == coordinate.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }

    @Override
    public int compareTo(Coordinate o) {
        return 0;
    }
}

package com.example.integrativetask_ii_ced.model.drawing;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
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

    public void setMag(int scalar){
        this.x *= scalar;
        this.y *= scalar;
    }
    public void normalize(){
        double mag = Math.sqrt(x*x + y*y);
        double angle = Math.atan2(y,x);
        this.x = 1*Math.cos(angle);
        this.y = 1*Math.sin(angle);
    }
}

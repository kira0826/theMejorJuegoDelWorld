package com.example.integrativetask_ii_ced.model.drawing;

import javafx.scene.canvas.GraphicsContext;

public class HitBox  {
    protected double x0;
    protected double y0;
    protected double x1;
    protected double y1;

    public HitBox(double x0, double y0, double x1, double y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }


    public void refreshHitBox(double x0, double y0, double x1, double y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }


    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public double getY0() {
        return y0;
    }

    public void setY0(double y0) {
        this.y0 = y0;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public boolean comparePosition(HitBox obj) {
        if((this.x0<=obj.getX1()&&this.x1>=obj.getX0())&&(this.y0<=obj.getY1()&&this.y1>=obj.getY0())){
            return true;
        }
        return false;
    }
}

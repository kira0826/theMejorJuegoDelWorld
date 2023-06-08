package com.example.integrativetask_ii_ced.model.entities;

import com.example.integrativetask_ii_ced.model.drawing.Drawable;
import com.example.integrativetask_ii_ced.model.drawing.HitBox;
import com.example.integrativetask_ii_ced.model.drawing.Vector;
import javafx.scene.image.Image;

public abstract class  Avatar implements Drawable {
    protected Vector position = new Vector(0, 0);
    protected HitBox hitBox;
    protected double width;
    protected double height;

    protected double life;



    public Avatar(double x, double y, double width, double height, double life ) {
        this.position.setX(x);
        this.position.setY(y);
        this.width = width;
        this.height = height;
        this.hitBox = new HitBox(x-(width/2), y-(height/2), x+(width/2), y+(height/2));
        this.life = life;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public HitBox getHitBox() {
        return hitBox;
    }

    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
    }

    public double getLife() {
        return life;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setLife(double life) {
        this.life = life;
    }
}

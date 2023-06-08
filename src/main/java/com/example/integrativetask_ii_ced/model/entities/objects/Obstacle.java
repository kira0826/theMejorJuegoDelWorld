package com.example.integrativetask_ii_ced.model.entities.objects;

import com.example.integrativetask_ii_ced.model.drawing.Drawable;
import com.example.integrativetask_ii_ced.model.drawing.HitBox;
import com.example.integrativetask_ii_ced.model.drawing.Vector;
import com.example.integrativetask_ii_ced.model.entities.Avatar;
import javafx.scene.canvas.GraphicsContext;

public abstract class  Obstacle implements Drawable {

    private static final double WIDTH_SIZE = 80;
    private static final double HEIGHT_SIZE = 80;

    protected HitBox hitBox;
    protected double width;
    protected double height;
    protected Vector position = new Vector(0, 0);
    public Obstacle(double x, double y) {

        this.width = WIDTH_SIZE;
        this.height = HEIGHT_SIZE;
        this.position.setX(x);
        this.position.setY(y);
        this.hitBox = new HitBox(x-(width/2), y-(height/2), x+(width/2), y+(height/2));
    }

    public Obstacle() {
    }

    @Override
    public void draw(GraphicsContext gc) {

    }

    public HitBox getHitBox() {
        return hitBox;
    }

    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
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

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
}

package com.example.integrativetask_ii_ced.model.map;

import com.example.integrativetask_ii_ced.model.drawing.Drawable;
import com.example.integrativetask_ii_ced.model.entities.objects.Obstacle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MapNode extends Obstacle implements Drawable {

    boolean navigable;
    private Image[] design;


    public MapNode(double x, double y, boolean navigable) {
        super(x, y);
        this.navigable = navigable;
    }

    public MapNode(double x, double y) {
        super(x, y);
    }

    public MapNode() {
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (isNavigable()) return;
        gc.setFill(Color.BLUE);
        gc.fillRect(position.getX() - (width / 2), position.getY() - (height/2), getWidth(), getHeight());
        gc.strokeRect(hitBox.getX0(), hitBox.getY0(), width, height);
    }

    public boolean isNavigable() {
        return navigable;
    }

    public void setNavigable(boolean navigable) {
        this.navigable = navigable;
    }

    public Image[] getDesign() {
        return design;
    }

    public void setDesign(Image[] design) {
        this.design = design;
    }
}

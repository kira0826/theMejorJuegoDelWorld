package com.example.integrativetask_ii_ced.model.entities.objects.functional;

import com.example.integrativetask_ii_ced.model.entities.Avatar;
import com.example.integrativetask_ii_ced.model.entities.objects.Obstacle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PressurePlate extends Obstacle {

    public boolean isPressed = false;

    public PressurePlate(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.strokeRect(hitBox.getX0(), hitBox.getY0(), width, height);

        if ( isPressed ) {
            gc.setFill(Color.CORAL);
        } else {
            gc.setFill(Color.GREEN);
        }
        gc.fillRect(position.getX() - (width / 2), position.getY() - (height/2), getWidth(), getHeight());
    }

    public boolean isPressed() {
        return isPressed;
    }

    public boolean isPressed(Avatar avatar) {
        if(hitBox.comparePosition(avatar.getHitBox())){
            isPressed = true;
            return true;
        } else {
            return false;
        }
    }
}

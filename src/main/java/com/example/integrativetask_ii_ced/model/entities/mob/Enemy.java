package com.example.integrativetask_ii_ced.model.entities.mob;

import com.example.integrativetask_ii_ced.model.drawing.HelloController;
import com.example.integrativetask_ii_ced.model.drawing.Vector;
import com.example.integrativetask_ii_ced.model.entities.Avatar;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.Bullet;
import com.example.integrativetask_ii_ced.model.levels.Level;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public  class Enemy extends Avatar implements Runnable, Initializable {
    private Random random = new Random();
    public TypeEnemy typeEnemy;
    public Enemy(double x, double y, double width, double height, double life){
        super(x, y, width, height, life);
        typeEnemy = TypeEnemy.values()[random.nextInt(TypeEnemy.values().length)];
        new Thread(this).start();

    }


    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image("file:src/main/resources/images/Character/run/player-run1.png"),position.getX() - (width / 2), position.getY() - (height/2), width, height);
    }


    @Override
    public void run() {
        while (true) {
            position.setX(random.nextInt(500) + Math.cos(position.getX()) * 2);
            position.setY(random.nextInt(500) + Math.sin(position.getY()) * 2);
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void shoot() {
        double diffX = HelloController.character.getPosition().getX() - this.position.getX();
        double diffY = HelloController.character.getPosition().getY() - this.position.getY();
        Vector diff = new Vector(diffX, diffY);
        diff.normalize();
        diff.setMag(10);
        Bullet bullet = new Bullet(position.getX(), position.getY(), 10, 10, 1, diff, 25);
        HelloController.levels.get(0).bullets.add(bullet);
    }
}


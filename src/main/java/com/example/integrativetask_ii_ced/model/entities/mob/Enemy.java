package com.example.integrativetask_ii_ced.model.entities.mob;

import com.example.integrativetask_ii_ced.model.drawing.Coordinate;
import com.example.integrativetask_ii_ced.model.drawing.HelloController;
import com.example.integrativetask_ii_ced.model.drawing.Vector;
import com.example.integrativetask_ii_ced.model.entities.Avatar;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.Bullet;
import com.example.integrativetask_ii_ced.model.levels.Level;
import com.example.integrativetask_ii_ced.model.map.MapNode;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.swing.*;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Stack;

public  class Enemy extends Avatar implements Runnable, Initializable {
    private Random random = new Random();

    private Stack<Coordinate> path;
    private Vector direction;

    public TypeEnemy typeEnemy;
    public Enemy(double x, double y, double width, double height, double life){
        super(x, y, width, height, life);

        typeEnemy = TypeEnemy.values()[random.nextInt(TypeEnemy.values().length)];

        generatePath();

        new Thread(this).start();
    }


    @Override
    public void draw(GraphicsContext gc) {
        position.setX(position.getX() + direction.getX());
        position.setY(position.getY() + direction.getY());
        gc.drawImage(new Image("file:src/main/resources/images/Character/run/player-run1.png"),position.getX() - (width / 2), position.getY() - (height/2), width, height);
    }


    @Override
    public void run() {

        while(this.life> 0 ){
            for (int i = 0; i < HelloController.levels.get(0).bullets.size(); i++) {
                if (this.hitBox.comparePosition(HelloController.levels.get(0).bullets.get(i).getHitBox())) {
                    this.life -= 1;
                    HelloController.levels.get(0).bullets.remove(i);
                }
            }
            if (!path.isEmpty()){

                Coordinate coordinate = path.peek();
                double pitagoras =
                        Math.sqrt(Math.pow(position.getX() - coordinate.getX(), 2) +
                                Math.pow(position.getY() - coordinate.getY(), 2));
                if (pitagoras < 6) {
                    path.pop();
                    if (!path.isEmpty()) {
                        coordinate = path.peek();
                        double diffX = coordinate.getX() - this.position.getX();
                        double diffY = coordinate.getY() - this.position.getY();
                        Vector diff = new Vector(diffX, diffY);
                        diff.normalize();
                        diff.setMag(5);
                        this.direction = diff;
                    }
                }

            }else {
                generatePath();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void generatePath(){

        MapNode mapNode = HelloController.levels.get(0).getGameMap().associateMapNode
                (HelloController.character.getPosition().getX(), HelloController.character.getPosition().getY());
        Coordinate to = new Coordinate(mapNode.getPosition().getX(), mapNode.getPosition().getY());

        MapNode mapNodeFrom = HelloController.levels.get(0).getGameMap().associateMapNode
                (this.position.getX(), this.getPosition().getY());

        Coordinate from = new Coordinate(mapNodeFrom.getPosition().getX(), mapNodeFrom.getPosition().getY());


        path = HelloController.levels.get(0).getGameMap().shortestPathUsingListAdjacencyBFS(from,to);


        double diffX = path.peek().getX() - this.position.getX();
        double diffY = path.peek().getY() - this.position.getY();
        Vector diff = new Vector(diffX, diffY);
        diff.normalize();
        diff.setMag(5);
        this.direction = diff;

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


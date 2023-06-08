package com.example.integrativetask_ii_ced.model.entities.objects.functional;

import com.example.integrativetask_ii_ced.model.drawing.*;
import com.example.integrativetask_ii_ced.model.entities.Avatar;
import com.example.integrativetask_ii_ced.model.levels.FinalLevel;
import com.example.integrativetask_ii_ced.model.map.MapNode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Stack;

public class MobilePump extends Avatar implements Runnable {

    private static int mobilePumpCounter = 0;
    private Stack<Coordinate> path;
    private Vector direction;

    private double damage;

    public MobilePump(double x, double y, double life, double damage) {
        super(x, y, FinalLevel.gameMap.getNodeSize(), FinalLevel.gameMap.getNodeSize(), life);

        this.damage = damage;

        MapNode mapNode = FinalLevel.gameMap.associateMapNode
                (HelloController.character.getPosition().getX(), HelloController.character.getPosition().getY());
        Coordinate to = new Coordinate(mapNode.getPosition().getX(), mapNode.getPosition().getY());

        MapNode mapNodeFrom = FinalLevel.gameMap.associateMapNode
                (FinalLevel.finalBoss.getPosition().getX(), FinalLevel.finalBoss.getPosition().getY());
        Coordinate from = new Coordinate(mapNodeFrom.getPosition().getX(), mapNodeFrom.getPosition().getY());


        if (mobilePumpCounter < 20){

            path = FinalLevel.gameMap.shortestPathUsingListAdjacencyBFS(from, to);

        }else {    // Here we are using DFS

            path = FinalLevel.gameMap.shortestPathUsingListAdjacencyDFS(from, to);

        }



        double diffX = path.peek().getX() - this.position.getX();
        double diffY = path.peek().getY() - this.position.getY();
        Vector diff = new Vector(diffX, diffY);
        diff.normalize();
        diff.setMag(5);
        this.direction = diff;

        mobilePumpCounter += 1;
        new Thread(this).start();

    }

    @Override
    public void run() {
        while (!path.isEmpty()) {

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

            }else{

            }

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Pump deleted");
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(hitBox.getX0(), hitBox.getY0(), 80, 80);

        position.setX(position.getX() + direction.getX());
        position.setY(position.getY() + direction.getY());
        hitBox.refreshHitBox(position.getX() - (width / 2), position.getY() - (height / 2), position.getX() + (width / 2), position.getY() + (height / 2));
    }


    public boolean outside(double height, double width) {
        return position.getX() > width || position.getX() < 0 || position.getY() > height || position.getY() < 0;
    }

    public boolean giveDamage(Avatar avatar) {
        if (hitBox.comparePosition(avatar.getHitBox())) {
            System.out.println("hit");
            avatar.setLife(avatar.getLife() - this.damage);
            System.out.println(avatar.getLife() - this.damage + " life" + avatar.getLife() + " damage" + this.damage);
            return true;
        }
        return false;
    }

    public Stack<Coordinate> getPath() {
        return path;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setPath(Stack<Coordinate> path) {
        this.path = path;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public static int getMobilePumpCounter() {
        return mobilePumpCounter;
    }

    public static void setMobilePumpCounter(int mobilePumpCounter) {
        MobilePump.mobilePumpCounter = mobilePumpCounter;
    }
}

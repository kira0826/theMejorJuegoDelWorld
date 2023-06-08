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

public  class Enemy extends Avatar implements Runnable {
    private Random random = new Random();

    private Stack<Coordinate> path;
    private Vector direction;

    private int speed;

    private PathType pathType;

    public TypeEnemy typeEnemy;
    private int frame = 0;

    private Image[] idle;
    public Enemy(double x, double y, double width, double height, double life){
        super(x, y, width, height, life);

        this.speed = random.nextInt(1,4);

        int probability = random.nextInt(1,11);

        if (probability >= 8) pathType = PathType.DFS;
        else pathType = PathType.BFS;

        typeEnemy = TypeEnemy.values()[random.nextInt(TypeEnemy.values().length)];
        generatePath();
        new Thread(this).start();
        idle = new Image[6];
        for(int i=1 ; i<=6 ; i++) {
            String uri = "file:src/main/resources/images/Enemy/pistol/player-run-shoott"+i+".png";
            idle[i-1] = new Image(uri);
        }
        shoot();
    }


    @Override
    public void draw(GraphicsContext gc) {
        position.setX(position.getX() + direction.getX());
        position.setY(position.getY() + direction.getY());
        hitBox.refreshHitBox(position.getX()-(width/2), position.getY()-(height/2), position.getX()+(width/2), position.getY()+(height/2));
        gc.drawImage(idle[frame],position.getX() - (width / 2), position.getY() - (height/2), width, height);

    }


    @Override
    public void run() {
        threadfake();
        while(this.life> 0 ){

            frame = (frame + 1) % 6;

            try {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void threadfake(){
        Thread x = new Thread(()->{
            while (this.life>0) {
                if ( !path.isEmpty() ){

                    Coordinate coordinate = path.peek();
                    double pitagoras =
                            Math.sqrt(Math.pow(position.getX() - coordinate.getX(), 2) +
                                    Math.pow(position.getY() - coordinate.getY(), 2));
                    if ( pitagoras < 6 ){
                        path.pop();
                        if ( !path.isEmpty() ){
                            coordinate = path.peek();
                            double diffX = coordinate.getX() - this.position.getX();
                            double diffY = coordinate.getY() - this.position.getY();
                            Vector diff = new Vector(diffX, diffY);
                            diff.normalize();
                            diff.setMag(speed);
                            this.direction = diff;
                        }
                    }
                } else {
                    generatePath();
                }
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        x.start();
    }




    private void generatePath(){

        MapNode mapNode = HelloController.levels.get(0).getGameMap().associateMapNode
                (HelloController.character.getPosition().getX(), HelloController.character.getPosition().getY());
        Coordinate to = new Coordinate(mapNode.getPosition().getX(), mapNode.getPosition().getY());

        MapNode mapNodeFrom = HelloController.levels.get(0).getGameMap().associateMapNode
                (this.position.getX(), this.getPosition().getY());

        Coordinate from = new Coordinate(mapNodeFrom.getPosition().getX(), mapNodeFrom.getPosition().getY());

        if (pathType.equals(PathType.BFS)){
            path = HelloController.levels.get(0).getGameMap().shortestPathUsingListAdjacencyBFS(from,to);
        } else if (pathType.equals(PathType.DFS)) {
            path= HelloController.levels.get(0).getGameMap().shortestPathUsingListAdjacencyDFS(from,to);
        }

        double diffX = path.peek().getX() - this.position.getX();
        double diffY = path.peek().getY() - this.position.getY();
        Vector diff = new Vector(diffX, diffY);
        diff.normalize();
        diff.setMag(5);
        this.direction = diff;

    }

    public void shoot() {
        Thread thread= new Thread(()->{

            while(this.life> 0 ){
                try {
                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                double diffX = HelloController.character.getPosition().getX() - this.position.getX();
                double diffY = HelloController.character.getPosition().getY() - this.position.getY();
                Vector diff = new Vector(diffX, diffY);
                diff.normalize();
                diff.setMag(7);
                Bullet bullet = new Bullet(position.getX(), position.getY(), 10, 10, 1, diff, 25);
                HelloController.levels.get(0).getEnemyBullets().add(bullet);

            }


        });
        thread.start();

    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public Stack<Coordinate> getPath() {
        return path;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public PathType getPathType() {
        return pathType;
    }

    public void setPathType(PathType pathType) {
        this.pathType = pathType;
    }
}


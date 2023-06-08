package com.example.integrativetask_ii_ced.model.levels;

import com.example.integrativetask_ii_ced.model.drawing.Drawable;
import com.example.integrativetask_ii_ced.model.drawing.HelloController;
import com.example.integrativetask_ii_ced.model.entities.Player;
import com.example.integrativetask_ii_ced.model.entities.mob.Enemy;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.Bullet;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.PressurePlate;
import com.example.integrativetask_ii_ced.model.map.GameMap;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Level implements Initializable, Runnable, Drawable {
    public CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();
    public GameMap gameMap = new GameMap(1200,720, 80,3);
    public boolean isFinished = false;
    public CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<>();
    

    public Level(){
        gameMap.initialFillingOfMapWithNodesAndCoordinates();
        gameMap.creatingNotNavigableObstacles();
        gameMap.establishGraphMapRepresentationForMinimumPaths();
    }
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        for (int i = 0; i < gameMap.getMapGuide().get(0).size(); i++) {
            if (gameMap.getMapGuide().get(0).get(i).isNavigable()){
                HelloController.character = new Player(gameMap.getMapGuide().get(0).get(i).getPosition().getX(),gameMap.getMapGuide().get(0).get(i).getPosition().getY(), 60,60,20000);
                break;
            }
        }
        Random random = new Random();
        int amountEnemy = random.nextInt(20);
        for (int i = 0; i < amountEnemy; i++){
            int randomX = random.nextInt(1200);
            int randomY = random.nextInt(720);
            enemies.add(new Enemy(randomX, randomY, 60, 60, 100));
        }
    }

    public void run() {
        while (true) {
        }
    }
    @Override
    public void draw(GraphicsContext gc){

        for (int i = 0; i < bullets.size(); i++){
            bullets.get(i).draw(gc);
        }
        for (int i = 0; i < gameMap.getMapGuide().size(); i++){
            for (int j = 0; j < gameMap.getMapGuide().get(i).size(); j++){
                gameMap.getMapGuide().get(i).get(j).draw(gc);
            }
        }
        for (int i = 0; i < enemies.size(); i++){
            enemies.get(i).draw(gc);
        }

    }


    public CopyOnWriteArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(CopyOnWriteArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public  GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }


    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}

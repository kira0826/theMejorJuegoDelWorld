package com.example.integrativetask_ii_ced.model.levels;

import com.example.integrativetask_ii_ced.model.drawing.Drawable;
import com.example.integrativetask_ii_ced.model.drawing.HelloController;
import com.example.integrativetask_ii_ced.model.entities.Player;
import com.example.integrativetask_ii_ced.model.entities.gun.Gun;
import com.example.integrativetask_ii_ced.model.entities.gun.TypeGun;
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
    private Random random = new Random();
    private Gun[] weapons = new Gun[2];

    public Level(){
        gameMap.initialFillingOfMapWithNodesAndCoordinates();
        gameMap.creatingNotNavigableObstacles();
        gameMap.establishGraphMapRepresentationForMinimumPaths();
        for (int i = 0; i < gameMap.getMapGuide().get(0).size(); i++) {
            if (gameMap.getMapGuide().get(0).get(i).isNavigable()){
                HelloController.character.getPosition().setX(gameMap.getMapGuide().get(0).get(i).getPosition().getX());
                HelloController.character.getPosition().setY(gameMap.getMapGuide().get(0).get(i).getPosition().getY());
                break;
            }
        }

        int counter  = 0;
        int positionY = 0;
        int positionX = 0;

        while (counter <= 7){
            while(true){

                positionY = (int)Math.floor(random.nextInt(720)/gameMap.getNodeSize());
                positionX = (int)Math.floor(random.nextInt(1200)/gameMap.getNodeSize());

                if (gameMap.getMapGuide().get(positionY).get(positionX).isNavigable()){
                    enemies.add(new Enemy(gameMap.getMapGuide().get(positionY).get(positionX).getPosition().getX(),gameMap.getMapGuide().get(positionY).get(positionX).getPosition().getY(), 60, 60, 20000));
                    counter++;
                    break;
                }
            }
        }

        int counter1  = 0;
        int positionY1 = 0;
        int positionX1 = 0;

        while (counter1 <= 1){
            while(true){

                positionY = (int)Math.floor(random.nextInt(720)/gameMap.getNodeSize());
                positionX = (int)Math.floor(random.nextInt(1200)/gameMap.getNodeSize());

                if (gameMap.getMapGuide().get(positionY).get(positionX).isNavigable()){
                    weapons[counter1] = new Gun(gameMap.getMapGuide().get(positionY).get(positionX).getPosition().getX(),gameMap.getMapGuide().get(positionY).get(positionX).getPosition().getY(),80,80, TypeGun.values()[counter1]);
                    counter1++;
                    break;
                }
            }
        }

    }
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

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

        for (int i = 0; i < weapons.length; i++) {
            weapons[i].draw(gc);
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

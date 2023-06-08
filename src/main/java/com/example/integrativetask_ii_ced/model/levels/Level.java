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

    private CopyOnWriteArrayList<Bullet> enemyBullets = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Bullet> avatarBullets = new CopyOnWriteArrayList<>();

    public GameMap gameMap = new GameMap(1200,720, 80,3);
    public boolean isFinished = false;
    public CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<>();
    private Random random = new Random();
    private Gun[] weapons = new Gun[2];
    private PressurePlate pressurePlate = null;

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



        int counter1  = 0;
        int positionY1 = 0;
        int positionX1 = 0;

        while (counter1 <= 1){
            while(true){

                positionY1 = (int)Math.floor(random.nextInt(720)/gameMap.getNodeSize());
                positionX1 = (int)Math.floor(random.nextInt(1200)/gameMap.getNodeSize());

                if (gameMap.getMapGuide().get(positionY1).get(positionX1).isNavigable()){
                    weapons[counter1] = new Gun(gameMap.getMapGuide().get(positionY1).get(positionX1).getPosition().getX(),gameMap.getMapGuide().get(positionY1).get(positionX1).getPosition().getY(),80,80, TypeGun.values()[counter1]);
                    counter1++;
                    break;
                }
            }
        }
        new Thread(this).start();

    }
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(enemies.size());
            if(enemies.size() == 0){
                if ( pressurePlate == null){
                    int counter = 0;
                    int positionY = 0;
                    int positionX = 0;

                    while (counter <= 1) {
                        while (true) {

                            positionY = (int) Math.floor(random.nextInt(720) / gameMap.getNodeSize());
                            positionX = (int) Math.floor(random.nextInt(1200) / gameMap.getNodeSize());

                            if ( gameMap.getMapGuide().get(positionY).get(positionX).isNavigable() ){
                                pressurePlate = new PressurePlate(gameMap.getMapGuide().get(positionY).get(positionX).getPosition().getX(), gameMap.getMapGuide().get(positionY).get(positionX).getPosition().getY());
                                counter++;
                                break;
                            }
                        }
                    }
                }
                else {
                    if ( pressurePlate.isPressed(HelloController.character)){
                        isFinished = true;
                        break;
                    }
                }
            }
        }
    }
    @Override
    public void draw(GraphicsContext gc){

        for (int i = 0; i < getEnemyBullets().size(); i++){
            getEnemyBullets().get(i).draw(gc);
        }
        for (int i = 0; i < getAvatarBullets().size(); i++){
            getAvatarBullets().get(i).draw(gc);
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
        if ( pressurePlate != null){
            pressurePlate.draw(gc);
        }
    }

    public void generateEnemies(){

        int counter  = 0;
        int positionY = 0;
        int positionX = 0;

        while (counter <= 7){
            while(true){

                positionY = (int)Math.floor(random.nextInt(720)/gameMap.getNodeSize());
                positionX = (int)Math.floor(random.nextInt(1200)/gameMap.getNodeSize());

                if (gameMap.getMapGuide().get(positionY).get(positionX).isNavigable()){
                    enemies.add(new Enemy(gameMap.getMapGuide().get(positionY).get(positionX).getPosition().getX(),gameMap.getMapGuide().get(positionY).get(positionX).getPosition().getY(), 60, 60, 3));
                    counter++;
                    break;
                }
            }
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

    public CopyOnWriteArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(CopyOnWriteArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public Gun[] getWeapons() {
        return weapons;
    }

    public void setWeapons(Gun[] weapons) {
        this.weapons = weapons;
    }

    public CopyOnWriteArrayList<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public void setEnemyBullets(CopyOnWriteArrayList<Bullet> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }

    public CopyOnWriteArrayList<Bullet> getAvatarBullets() {
        return avatarBullets;
    }

    public void setAvatarBullets(CopyOnWriteArrayList<Bullet> avatarBullets) {
        this.avatarBullets = avatarBullets;
    }

    public PressurePlate getPressurePlate() {
        return pressurePlate;
    }

    public void setPressurePlate(PressurePlate pressurePlate) {
        this.pressurePlate = pressurePlate;
    }
}

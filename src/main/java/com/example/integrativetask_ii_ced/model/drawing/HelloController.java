package com.example.integrativetask_ii_ced.model.drawing;

import com.example.integrativetask_ii_ced.HelloApplication;
import com.example.integrativetask_ii_ced.model.entities.*;
import com.example.integrativetask_ii_ced.model.entities.mob.Boss;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.MobilePump;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.Bullet;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.PressurePlate;
import com.example.integrativetask_ii_ced.model.levels.Level;
import com.example.integrativetask_ii_ced.model.map.GameMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class HelloController implements Initializable, Drawable{

    private final Image image = new Image("file:src/main/resources/images/background.jpg");

    @FXML
    private Canvas canvas;

    public GraphicsContext gc;
    private int counterlvls;
    public static Player character;
    public static ArrayList<Level> levels;
    private final Cursor customCursor = new ImageCursor(new Image("file:src/main/resources/images/Cursor/nt_normal.png"));

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        counterlvls = 1;
        levels = new ArrayList<>();
        character = new Player(0,0, 60,60,3);
        canvas.setCursor(customCursor);
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        levels.add(new Level("back"+counterlvls));
        levels.get(0).generateEnemies();
        counterlvls++;
        canvas.setOnKeyPressed(character::pressKey);
        canvas.setOnKeyReleased(character::releasedKey);
        canvas.setOnMouseMoved(this::onMouseMoved);
        canvas.setOnMouseClicked(character::shoot);
        new Thread(character).start();
        draw(gc);
    }

    private boolean flag = true;
    @Override
    public void draw(GraphicsContext gc) {
        Thread h = new Thread(() -> {

            while (character.getLife() >0 && flag) {
                Platform.runLater(() -> {

                    gc.setFill(Color.WHITE);
                    gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
                    levels.get(0).draw(gc);



                    // Fuera del mapa
                    for (int i = 0; i < levels.get(0).getEnemyBullets().size(); i++) {
                        if (levels.get(0).gameMap.mapLimit(levels.get(0).getEnemyBullets().get(i).getHitBox())){
                            levels.get(0).getEnemyBullets().remove(i);
                            i--;
                        }
                    }

                    for (int i = 0; i < levels.get(0).getAvatarBullets().size(); i++) {
                        if (levels.get(0).gameMap.mapLimit(levels.get(0).getAvatarBullets().get(i).getHitBox())){
                            levels.get(0).getAvatarBullets().remove(i);
                            i--;
                        }
                    }
                    if ( levels.get(0).isFinished ){
                        if (counterlvls > 3 ){
                            flag = false;
                            Stage stage = (Stage) canvas.getScene().getWindow();
                            stage.close();
                            HelloApplication.openWindow("exitScreen.fxml");
                        } else {
                            levels.set(0, new Level("back"+counterlvls));
                            levels.get(0).generateEnemies();
                            counterlvls++;
                        }
                    }
                    // Colision con nodos no navegables
                    for (int i = 0; i < levels.get(0).getAvatarBullets().size(); i++) {
                        for (int j = 0; j < levels.get(0).getGameMap().getNodeNoNavigable().size(); j++){
                            if (levels.get(0).getAvatarBullets().get(i).getHitBox().comparePosition(levels.get(0).getGameMap().getNodeNoNavigable().get(j).getHitBox())){
                                levels.get(0).getAvatarBullets().remove(i);
                                if (levels.get(0).getAvatarBullets().size() < 1){
                                    i--;
                                    break;
                                }
                            }
                        }
                    }

                    for (int i = 0; i < levels.get(0).getEnemyBullets().size(); i++) {
                        for (int j = 0; j < levels.get(0).getGameMap().getNodeNoNavigable().size(); j++){
                            if (levels.get(0).getEnemyBullets().get(i).getHitBox().comparePosition(levels.get(0).getGameMap().getNodeNoNavigable().get(j).getHitBox())){
                                levels.get(0).getEnemyBullets().remove(i);
                                break;
                            }
                        }
                    }



                    //Daño a los enemigos


                    for (int i = 0; i < levels.get(0).enemies.size(); i++) {
                        for (int j = 0; j <levels.get(0).getAvatarBullets().size(); j++) {

                            if (levels.get(0).enemies.get(i).getHitBox().comparePosition(levels.get(0).getAvatarBullets().get(j).getHitBox())) {

                                levels.get(0).getAvatarBullets().remove(j);
                                levels.get(0).enemies.get(i).setLife(levels.get(0).enemies.get(i).getLife() - 1);

                                if (levels.get(0).enemies.get(i).getLife() <=0)levels.get(0).getEnemies().remove(i);
                            }
                        }
                    }

                    //Daño al avatar

                    for (int i = 0; i < levels.get(0).getEnemyBullets().size(); i++) {

                        if (character.getHitBox().comparePosition(levels.get(0).getEnemyBullets().get(i).getHitBox())){
                            character.setLife(character.getLife()-1);
                            levels.get(0).getEnemyBullets().remove(i);
                        }

                    }



                    character.draw(gc);
                    if (character.getLife() <= 0){
                        Stage stage = (Stage) canvas.getScene().getWindow();
                        stage.close();
                        HelloApplication.openWindow("exitScreen.fxml");
                    }
                });
                character.movement();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        h.start();
    }

    private void onMouseMoved(MouseEvent e) {
        double relativePosition = e.getX()-character.getPosition().getX();
        character.setFacingRight(
                relativePosition > 0
        );
    }



    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Image getImage() {
        return image;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public Cursor getCustomCursor() {
        return customCursor;
    }



    public static Player getCharacter() {
        return character;
    }

    public static void setCharacter(Player character) {
        HelloController.character = character;
    }

}
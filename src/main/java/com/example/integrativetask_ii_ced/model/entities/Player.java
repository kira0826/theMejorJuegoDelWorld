package com.example.integrativetask_ii_ced.model.entities;

import com.example.integrativetask_ii_ced.model.drawing.HelloController;
import com.example.integrativetask_ii_ced.model.drawing.Vector;
import com.example.integrativetask_ii_ced.model.entities.gun.Gun;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.Bullet;
import com.example.integrativetask_ii_ced.model.levels.Level;
import com.example.integrativetask_ii_ced.model.map.MapNode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Player extends Avatar implements Runnable {
    private boolean keyA;
    private boolean keyW;
    private boolean keyS;
    private boolean keyD;
    private boolean keyE;

    private Image[] idle;
    private Image[] run;

    private Image[] died;
    private Image[] runShoot;
    private Image[] idleShoot;
    private Image[] viewLife;
    private int frame = 0;

    private boolean isFacingRight = true;

    private boolean isShooting = false;

    private MapNode mapNodeAssociated;

    private Gun gun;
    public void setFacingRight(boolean facingRight) {
        isFacingRight = facingRight;
    }

    public Player(double x, double y, double width, double height, double life) {
        super(x, y, width, height, life);
        idle = new Image[6];
        for(int i=1 ; i<=6 ; i++) {
            String uri = "file:src/main/resources/images/Character/idle/player-idle"+i+".png";
            idle[i-1] = new Image(uri);
        }
        run = new Image[6];
        for(int i=1 ; i<=6 ; i++) {
            String uri = "file:src/main/resources/images/Character/run/player-run"+i+".png";
            run[i-1] = new Image(uri);
        }
        died = new Image[3];
        for(int i=1 ; i<=3 ; i++) {
            String uri = "file:src/main/resources/images/Character/died/player-died"+i+".png";
            died[i-1] = new Image(uri);
        }
        runShoot = new Image[6];
        for(int i=1 ; i<=6 ; i++) {
            String uri = "file:src/main/resources/images/Character/pistol/player-run-shoott"+i+".png";
            runShoot[i-1] = new Image(uri);
        }
        idleShoot = new Image[3];
        for(int i=1 ; i<=3 ; i++) {
            String uri = "file:src/main/resources/images/Character/shoot/player-shoot"+i+".png";
            idleShoot[i-1] = new Image(uri);
        }
        viewLife = new Image[2];
        for(int i=1 ; i<=2 ; i++) {
            String uri = "file:src/main/resources/images/Character/life/heart"+(i-1)+".png";
            viewLife[i-1] = new Image(uri);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if ( life<1 ){
            gc.drawImage(died[frame], isFacingRight ? position.getX() - (width / 2) : position.getX() + (width / 2), position.getY() - (width / 2), isFacingRight ? width : -width, height);
            return;
        }
        hitBox.refreshHitBox(position.getX()-(width/2), position.getY()-(height/2), position.getX()+(width/2), position.getY()+(height/2));
        gc.drawImage((isMoving() ? (gun != null? runShoot[frame]: run[frame]) : (gun != null? idleShoot[2]:idle[frame])), isFacingRight ? position.getX() - (width / 2) : position.getX() + (width / 2), position.getY() - (width / 2), isFacingRight ? width : -width, height);
        // drawLife
        if(life == 3.0){

            gc.drawImage(viewLife[0], 1050, 10, 70, 70);
            gc.drawImage(viewLife[0], 960, 10, 70, 70);
            gc.drawImage(viewLife[0], 870, 10, 70, 70);
        } else if ( life == 2.0 ){
            gc.drawImage(viewLife[0],1050,  10, 70, 70);
            gc.drawImage(viewLife[0],960,  10, 70, 70);
            gc.drawImage(viewLife[1],870, 10 , 70, 70);
        } else if ( life == 1.0 ){
            gc.drawImage(viewLife[0],1050, 10 , 70, 70);
            gc.drawImage(viewLife[1],960, 10 , 70, 70);
            gc.drawImage(viewLife[1],870, 10 , 70, 70);
        } else if ( life == 0.0 ){
            System.out.println("Game Over");
            gc.drawImage(viewLife[1], 1050, 10, 70, 70);
            gc.drawImage(viewLife[1], 960, 10, 70, 70);
            gc.drawImage(viewLife[1], 870, 10, 70, 70);
        }

        //Draw gun
        if ( gun != null ){

            gc.drawImage(new Image("file:src/main/resources/images/Character/gun/"+gun.getTypeGun()+".png"), 60, 10, 50,50);
            gc.setFill(Color.BLACK);
            gc.fillText((gun.getAvailableAmmo()>0?"Ammo : "+gun.getAvailableAmmo(): "Reloading..."), 60, 70);
        }
    }

    @Override
    public void run() {
        while (true) {
            if ( life<1 ){
                frame = 0;
                if ( frame != 2 ){
                    frame = (frame + 1) % 3;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                frame = (frame + 1) % 6;
            }try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void movement(){
        if ( keyA ){
            hitBox.refreshHitBox((position.getX()-3)-(width/2), position.getY()-(height/2), (position.getX()-3)+(width/2), position.getY()+(height/2));
            if ( HelloController.levels.get(0).getGameMap().mapCollision(this.hitBox)
                    || HelloController.levels.get(0).getGameMap().mapLimit(hitBox)) return;
            position.setX(position.getX()-3);
        }
        if ( keyW ){
            hitBox.refreshHitBox(position.getX()-(width/2), position.getY()-3-(height/2), position.getX()+(width/2), position.getY()-3+(height/2));
            if (  HelloController.levels.get(0).getGameMap().mapCollision(this.hitBox)
                    || HelloController.levels.get(0).getGameMap().mapLimit(hitBox)) return;
            position.setY(position.getY()-3);
        }
        if ( keyS ){
            hitBox.refreshHitBox(position.getX()-(width/2), position.getY()+3-(height/2), position.getX()+(width/2), position.getY()+3+(height/2));
            if (HelloController.levels.get(0).getGameMap().mapCollision(this.hitBox)
                    || HelloController.levels.get(0).getGameMap().mapLimit(hitBox)) return;
            position.setY(position.getY()+3);
        }
        if ( keyD ){
            hitBox.refreshHitBox((position.getX()+3)-(width/2), position.getY()-(height/2), (position.getX()+3)+(width/2), position.getY()+(height/2));
            if (HelloController.levels.get(0).getGameMap().mapCollision(this.hitBox)
                    || HelloController.levels.get(0).getGameMap().mapLimit(hitBox)) return;
            position.setX(position.getX()+3);
        }
        hitBox.refreshHitBox(position.getX(), position.getY(), position.getX(), position.getY());
    }


    public void pressKey(KeyEvent event){
        if ( life<1 ){
            keyA = false;
            keyW = false;
            keyS = false;
            keyD = false;
            return;
        }
        switch (event.getCode()) {
            case A -> {
                keyA = true;
            }
            case W -> {
                keyW = true;
            }
            case S -> {
                keyS = true;
            }
            case D -> {
                keyD = true;
            }
        }
    }

    public void  releasedKey(KeyEvent event){
        if ( life<1 ){
            keyA = false;
            keyW = false;
            keyS = false;
            keyD = false;
            return;
        }
        switch (event.getCode()) {
            case A -> {
                keyA = false;
            }
            case W -> {
                keyW = false;
            }
            case S -> {
                keyS = false;
            }
            case D -> {
                keyD = false;
            }
        }
        movement();
    }

    public void associateNearestMapNode(){
        this.mapNodeAssociated = HelloController.levels.get(0).gameMap.associateMapNode(position.getX(), position.getY());
    }

    public boolean isMoving() {
        return keyA || keyW || keyS || keyD;
    }

    public MapNode getMapNodeAssociated() {
        return mapNodeAssociated;
    }

    public void setMapNodeAssociated(MapNode mapNodeAssociated) {
        this.mapNodeAssociated = mapNodeAssociated;
    }

    public void shoot(MouseEvent event){
        if(gun!=null ){
            if ( gun.isCouldShoot() ){
                double diffX = event.getX() - HelloController.character.getPosition().getX();
                double diffY = event.getY() - HelloController.character.getPosition().getY();
                Vector diff = new Vector(diffX, diffY);
                diff.normalize();
                diff.setMag(10);
                Bullet bullet = new Bullet(position.getX(), position.getY(), 10, 10, 1, diff, 25);
                gun.shoot();
                HelloController.levels.get(0).bullets.add(bullet);
            }
        }
    }

    public Gun getGun() {
        return gun;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }
}
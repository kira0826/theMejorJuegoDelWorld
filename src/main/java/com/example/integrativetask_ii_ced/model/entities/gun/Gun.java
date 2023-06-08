package com.example.integrativetask_ii_ced.model.entities.gun;

import com.example.integrativetask_ii_ced.model.drawing.Drawable;
import com.example.integrativetask_ii_ced.model.drawing.HelloController;
import com.example.integrativetask_ii_ced.model.entities.Avatar;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Gun extends Avatar implements Drawable, Runnable {
    public Gun(double x, double y, double width, double height, double life) {
        super(x, y, width, height, life);
    }
    private int ammo;
    private int cadence;
    private TypeGun typeGun;
    private boolean isDrop;
    private boolean couldShoot = false;
    private int countShoots = 0;
    private Image image = new Image("file:src/main/resources/images/Character/died/player-died2.png");

    public Gun(double x, double y, double width, double height, TypeGun typeGun) {
        super(x, y, width, height, 0);
        if ( typeGun == TypeGun.PISTOL ){
            this.ammo = 10;
            this.cadence = 2; // 2 shots per second
        }else{
            this.ammo = 20;
            this.cadence = 3; // 3 shot per second
        }
        this.typeGun = typeGun;
        this.isDrop = true;

        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            if ( this.getHitBox().comparePosition(HelloController.character.getHitBox())){
                this.isDrop = false;
                HelloController.character.setGun(this);
                this.couldShoot = true;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(isDrop) gc.drawImage(image, this.getPosition().getX(), this.getPosition().getY(), this.getWidth(), this.getHeight());
    }

    public void shoot(){
        Thread x = new Thread(() ->{
            couldShoot = false;
            try {
                Thread.sleep(1000/cadence);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            couldShoot = true;
        });
        x.start();

    }

    public boolean isCouldShoot() {
        return couldShoot;
    }

    public void setCouldShoot(boolean couldShoot) {
        this.couldShoot = couldShoot;
    }
}

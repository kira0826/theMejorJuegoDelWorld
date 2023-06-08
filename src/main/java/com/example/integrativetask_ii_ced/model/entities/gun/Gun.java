package com.example.integrativetask_ii_ced.model.entities.gun;

import com.example.integrativetask_ii_ced.model.drawing.Drawable;
import com.example.integrativetask_ii_ced.model.drawing.HelloController;
import com.example.integrativetask_ii_ced.model.drawing.HitBox;
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
    private Image image;

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
        image = new Image("file:src/main/resources/images/Character/gun/"+typeGun+".png");

        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            if ( this.getHitBox().comparePosition(HelloController.character.getHitBox())){
                this.isDrop = false;
                this.hitBox = new HitBox(-1000, -1000, -1000, -1000);
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
        if(isDrop) gc.drawImage(image, this.getPosition().getX(), this.getPosition().getY(), 40, 50);
    }

    public void shoot(){
        countShoots++;
        Thread x = new Thread(() ->{
            couldShoot = false;
            try {
                if ( (ammo-countShoots)<=0){
                    Thread.sleep(7000 / cadence);
                    countShoots = 0;
                } else {
                    Thread.sleep(1000 / cadence);
                }
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

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getCadence() {
        return cadence;
    }

    public void setCadence(int cadence) {
        this.cadence = cadence;
    }

    public TypeGun getTypeGun() {
        return typeGun;
    }

    public void setTypeGun(TypeGun typeGun) {
        this.typeGun = typeGun;
    }

    public boolean isDrop() {
        return isDrop;
    }

    public void setDrop(boolean drop) {
        isDrop = drop;
    }

    public int getCountShoots() {
        return countShoots;
    }

    public void setCountShoots(int countShoots) {
        this.countShoots = countShoots;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getAvailableAmmo(){
        return ammo - countShoots;
    }
}

package com.example.integrativetask_ii_ced.model.entities.gun;

import com.example.integrativetask_ii_ced.model.entities.Avatar;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Gun extends Avatar {
    public Gun(double x, double y, double width, double height, double life) {
        super(x, y, width, height, life);
    }
    private int ammo;
    private int cadence;
    private TypeGun typeGun;
    private boolean isDrop;
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
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(image, this.getPosition().getX(), this.getPosition().getY(), this.getWidth(), this.getHeight());
    }

}

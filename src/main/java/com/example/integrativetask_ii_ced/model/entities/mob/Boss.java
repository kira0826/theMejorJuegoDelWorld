package com.example.integrativetask_ii_ced.model.entities.mob;

import com.example.integrativetask_ii_ced.model.drawing.HelloController;
import com.example.integrativetask_ii_ced.model.drawing.Vector;
import com.example.integrativetask_ii_ced.model.entities.Avatar;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.Bullet;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.MobilePump;
import com.example.integrativetask_ii_ced.model.levels.FinalLevel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Boss extends Avatar implements Runnable{
    private Image idle;
    private String uri = "file:src/main/resources/images/FinalBoss/Boss.png";

    public Boss(double x, double y, double width, double height, double life){

        super(x, y, width, height, life);
        idle = new Image(uri);

    }

    @Override
    public void draw(GraphicsContext gc) {
        hitBox.refreshHitBox(position.getX()-(width/2), position.getY()-(height/2), position.getX()+(width/2), position.getY()+(height/2));
        gc.drawImage(idle, hitBox.getX0(), hitBox.getY0(), width, height);
    }

    @Override
    public void run() {
        while (HelloController.character.getLife()>0 && this.getLife()>0) {
            try {

                    double diffX = HelloController.character.getPosition().getX() - this.position.getX();
                    double diffY = HelloController.character.getPosition().getY() - this.position.getY();
                    Vector diff = new Vector(diffX, diffY);
                    diff.normalize();
                    diff.setMag(10);
                    MobilePump mobilePump = new MobilePump(getPosition().getX(),getPosition().getY(),1,200);
                    FinalLevel.mobilePumps.add(mobilePump);

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void died() {
        uri = "file:src/main/resources/images/FinalBoss/BossDied.png";
        idle = new Image(uri);
        life = -1;
    }
}
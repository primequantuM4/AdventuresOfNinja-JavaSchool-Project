package main;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class FlyingMinion extends AttackObject{
    double angle = Math.random();
    long birthTime = System.currentTimeMillis();
    GamePanel gp;

    public FlyingMinion(int x, GamePanel gp){
        super((int)(Math.random()*x), 10, 24, 24);
        this.gp = gp;
        
        try{
            BufferedImage fireImage = ImageIO.read(getClass().getResourceAsStream("/Boss/TrackingProjectile.png"));
            setImage(fireImage);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void update(){
        angle += 0.01;
        x += (int)(5*Math.sin(angle*5));
        y += (int)(5*Math.cos(angle));
        long currentTime = System.currentTimeMillis();
        int allowedTimeToLive = 3000;
        if (currentTime - birthTime > allowedTimeToLive){
            gp.removeBossAttackObject(this);
        }
    }
}

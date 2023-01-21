package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Thunder extends AttackObject{
	long birthTime = System.currentTimeMillis();
    long timeWhenCollisonAllowed;

    boolean canCollide = false;

    long timeTillAllowCollision = 1500;
    long timeTillDeath = 200;
    GamePanel gp;

    Thunder(int x, int groundY, GamePanel gp){
      super((int)(x * Math.random()), (int)(groundY/2), 30, (int) (groundY/2));
        this.gp = gp;
      try {
        BufferedImage thunder = ImageIO.read(getClass().getResourceAsStream("/Boss/lightning.png"));
        setImage(thunder);
      }catch(Exception e) {
        e.printStackTrace();
      }
    }

    public void draw(Graphics2D g){
        if (canCollide){
            super.draw(g);
        }else{
            g.setColor(Color.white);
             g.drawRect(x - halfWidth, y - halfHeight, 2*halfWidth, 2*halfHeight);
        } 
    }

    public void update(){
      long currentTime = System.currentTimeMillis();
        boolean allowCollision = (!canCollide) && currentTime - birthTime > timeTillAllowCollision;
        boolean shouldDisappear = (canCollide) && currentTime - timeWhenCollisonAllowed > timeTillDeath;

      if (allowCollision){
            canCollide = true;
            timeWhenCollisonAllowed = currentTime;
      } else if(shouldDisappear){
        gp.removeBossAttackObject(this);
      }
    
    }

    public boolean isTouching(Collidable collidable){
        if (canCollide) {
            return super.isTouching(collidable);
        }else{
            return false;
        }
    }


}

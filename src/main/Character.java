package main;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Character extends Collidable{
	int groundY;
    int maxHealth;
    int health;
    
	int downSpeed = 0;
    int horizontalSpeed = 5;
	int gravity = 1;

    long lastShootTime = 0;
	
	public Character(int[] xyPosition, int[] halfWidthHeight, int health, GamePanel gp){
        //x, y, halfWidth, halfHeight are declared in collidable
		super(xyPosition[0], xyPosition[1], halfWidthHeight[0], halfWidthHeight[1]);

        this.groundY = gp.getGroundY();
        this.health = health;
        this.maxHealth = health;
	}

    public void recieveDamage(){
        this.health -- ;
    }

    protected double getHealthPercent(){
        return (double)this.health / this.maxHealth;
    }
    public boolean isAlive(){
        return this.health > 0;
    }

    protected void drawHealthBar(Graphics2D g, int x, int y, int barWidth, int barHeight){
        double healthPercent = this.getHealthPercent();
        g.drawRect(x , y, barWidth, barHeight); //health bar frame

        if (healthPercent >= 0.8) g.setColor(Color.green);
		else if (healthPercent >= 0.4) g.setColor(Color.yellow);
		else g.setColor(Color.red);

        int healthBarWidth = (int)(healthPercent * barWidth);
        g.fillRect(x , y, healthBarWidth, barHeight); //the health bar itself
        g.setColor(Color.black);
		
    }
    
    protected void markLastShootTime(){
        this.lastShootTime = System.currentTimeMillis();;
    }
    protected boolean hasEnoughTimeGapToShoot(){
        long currentTime = System.currentTimeMillis();
        long shootInterval = 70;

	    boolean enoughTimeHasPassed =  (currentTime - lastShootTime > shootInterval);
        return enoughTimeHasPassed;
    }

    protected void applyGravity() {
        boolean onAir = y + halfHeight < groundY;
        boolean onGroundJumpingUp = y + halfHeight == groundY && downSpeed < 0;

        if (onAir || onGroundJumpingUp){
            int maxDownSpeed = 15;
            //fall speed should not be too great, so dont accelerate only if you have low speed
            if (downSpeed < maxDownSpeed) downSpeed += gravity;
            y += downSpeed;

        }

        boolean belowGround = y + halfHeight > groundY;
        if (belowGround){
            //make sure nobody sinks below the ground
            downSpeed = 0;
            y = groundY - halfHeight;
        }
	}

    protected void changeHorizontalSpeed(int newSpeed){
        this.horizontalSpeed = newSpeed;
    }
    protected void moveRight(){
        this.x += horizontalSpeed;
    }

    protected void moveLeft(){
        this.x -= horizontalSpeed;
    }

    protected void jumpIfOnGround(){
        boolean isOnGroundOrBelow = y + halfHeight >= groundY;
        if (isOnGroundOrBelow){
            this.downSpeed = -20;
        }
    }

    protected void landQuickly(){
        boolean isOnAir = y + halfHeight < groundY;
        if (isOnAir){
            this.downSpeed += 2;
        }
    }
    
    public abstract void shoot();
//    protected abstract void drawHealthBar(Graphics2D g);


}

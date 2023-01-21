package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Boss extends Character {
	BufferedImage bossImage;
	BufferedImage bossImage2;
	BufferedImage currentBoss;
	int counter;
	int BossNum = 1;
    boolean keepMovingRight;

    long lastTimeBossActed = 0;
    int initialBossActionInterval = 1000;

    GamePanel gp;

    static private int[] getPositions(){
        int initialX = 564;
        int initialY = 50;
        int[] positions = {564, 50};
        return positions;
    }

    static private int[] getSizes(){
        int halfWidth = 75;
        int halfHeight = 75;
        int size[] = {halfWidth, halfHeight};
        return size;
    }

    static private int getHealth(){
        int bossHealth = 700;
        return bossHealth;
    }

	public Boss(GamePanel gp) {
		super(getPositions(),  getSizes(), getHealth(), gp);
		getBossImage();
        this.gp = gp;
	}

    private void getBossImage() {
		try {
			bossImage = ImageIO.read(getClass().getResourceAsStream("/Boss/KnightBoss.png"));
			bossImage2 = ImageIO.read(getClass().getResourceAsStream("/Boss/KnightBoss1.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

    public void shoot() {
        int shiftInHeight = (int) ((Math.random()-0.5) * halfHeight);
		 if (hasEnoughTimeGapToShoot()){
            gp.addBossAttackObject(new FireBall(x, y + shiftInHeight));
            this.markLastShootTime();
        };
	}

    public void releaseFlyingMinions(){
        gp.addBossAttackObject(new FlyingMinion(x, gp));
    }

    public void releaseThunder(){
        gp.addBossAttackObject(new Thunder(x, groundY, gp));
    }

     

    private void makeAPhase1Act(){
        double randomNumber = Math.random();
        
        if (randomNumber > 0.5) shoot();
        else jumpIfOnGround();
    }

    private void makeAPhase2Act(){
        double randomNumber = Math.random();
        if (randomNumber > 0.5) releaseFlyingMinions();
        else jumpIfOnGround();
    }

    private void makeAPhase3Act(){
        double randomNumber = Math.random();
        if (randomNumber > 0.8) releaseThunder();
        else if (randomNumber > 0.6) shoot();
        else jumpIfOnGround();
    }

    private void makeAPhase4Act(){
        double randomNumber = Math.random();
        if (randomNumber > 0.8) releaseThunder();
        else if (randomNumber > 0.5) shoot();
        else if (randomNumber > 0.25) releaseFlyingMinions();
        else jumpIfOnGround();
    }

    private void makeAnAct(){
        //acts differ based on the health of boss
        double healthPercent = getHealthPercent();
        
        if (healthPercent > 0.75) makeAPhase1Act();
        else if(healthPercent > 0.5) makeAPhase2Act();
        else if (healthPercent > 0.25) makeAPhase3Act();
        else makeAPhase4Act();
        

    }

    public void moveBackAndFro(){
        if (x < 400) keepMovingRight = true;
        else if (x > 700) keepMovingRight = false;

        if (keepMovingRight) moveRight();
        else moveLeft();
    }
    
	private int getSmallerActionInterval(){
        double healthPercent = this.getHealthPercent();
        double newInterval =  500 + healthPercent * initialBossActionInterval;
        return (int) newInterval;
    }

	@Override
	public void update() {
        moveBackAndFro();
		applyGravity();

        int smallerActionInterval = getSmallerActionInterval();
        long currentTime = System.currentTimeMillis();
        boolean shouldMakeAnAct = currentTime - lastTimeBossActed > smallerActionInterval;
        counter ++;
        if (shouldMakeAnAct){
            makeAnAct();
            lastTimeBossActed = currentTime;
        }
        if (counter > 12) {
        	if(BossNum == 1) BossNum = 2;
        	else if(BossNum == 2) BossNum = 1;
        	counter = 0;
        }
	}
    
    @Override
	public void draw(Graphics2D g) {
        drawHealthBar(g);
        if (BossNum == 1) currentBoss = bossImage;
        else if (BossNum == 2) currentBoss = bossImage2;
        //draw boss character
        

		g.drawImage(currentBoss, x - halfWidth, y - halfHeight ,2*halfWidth, 2*halfHeight, null);
	}
    
    protected void drawHealthBar(Graphics2D g){
        int barWidth = 350;
        int barHeight = 20;
        int leftGapFromEdge = 320;
        int topGapFromEdge = groundY + 30; 
        super.drawHealthBar(g, leftGapFromEdge, topGapFromEdge, barWidth, barHeight);
    }


}

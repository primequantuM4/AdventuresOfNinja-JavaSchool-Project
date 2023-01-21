package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Character{
	BufferedImage crouchImage;
	BufferedImage standingImages[];

	long frameCounter = 0;
    long lastTimeRecievedDamage = 0;

    PressedKeyHolder pressedKeysHolder;   
    GamePanel gp;
    
    static private int[] getPositions(){
        int initialX = 10;
        int initialY = 50;
        int positions[] = {initialX, initialY};
        return positions;
    }    

    static private int[] getSizes(){
        int halfWidth = 50;
        int halfHeight = 50;
        int size[] = {halfWidth, halfHeight};
        return size;
    }

    static private int getHealth(){
        int playerHealth = 5;
        return playerHealth;
    }

	public Player(GamePanel gp) {
        super(getPositions(), getSizes(), getHealth(), gp);
		this.pressedKeysHolder = new PressedKeyHolder(gp);
        this.gp = gp;
        gp.listenToKeys(pressedKeysHolder);
		getPlayerImage();
	}
	
	public void getPlayerImage() {
		try {
			BufferedImage firstStanding = ImageIO.read(getClass().getResourceAsStream("/Player/Player.png"));
			BufferedImage secondStanding = ImageIO.read(getClass().getResourceAsStream("/Player/Player1.png"));
			BufferedImage thirdStanding = ImageIO.read(getClass().getResourceAsStream("/Player/Player2.png"));
			this.crouchImage = ImageIO.read(getClass().getResourceAsStream("/Player/NinjaCrouch.png"));

            BufferedImage[] allImages = {firstStanding, secondStanding, thirdStanding};
            this.standingImages = allImages;

		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void shoot(){

        boolean allowedToShoot = this.hasEnoughTimeGapToShoot();
        if (allowedToShoot){
            gp.addPlayerAttackObject( new PlayerBullet(x, y));
            this.markLastShootTime();
        }

    }

    private boolean hasEnoughTimeGapToRecieveDamage(){
        long currentTime = System.currentTimeMillis();
        long recieveDamageInterval = 1000;
        boolean shouldRecieveDamage =  currentTime - lastTimeRecievedDamage > recieveDamageInterval;
        return shouldRecieveDamage;
    }

	public void recieveDamage() {
		
		if (hasEnoughTimeGapToRecieveDamage()) {
			gp.soundEffect(1);
            long currentTime = System.currentTimeMillis();
            this.lastTimeRecievedDamage = currentTime;
			super.recieveDamage();
		}
	}

	public void update() {
        this.frameCounter ++;
		this.applyGravity();

        if (pressedKeysHolder.spacePress) shoot();

        if(pressedKeysHolder.leftPress) this.moveLeft();
        if(pressedKeysHolder.rightPress) this.moveRight();
        
        if(pressedKeysHolder.upPress) this.jumpIfOnGround();
        if(pressedKeysHolder.downPress) {
            this.halfHeight = (int) getSizes()[1]/2;
            this.landQuickly();
        }else{
            this.halfHeight = getSizes()[1];
        }
	}

    protected void drawHealthBar(Graphics2D g){
        //draw health bar
        int barWidth = 100;
        int barHeight = 5;
        int leftGapFromEdge = 20;
        int topGapFromEdge = 20; 
        super.drawHealthBar(g, leftGapFromEdge, topGapFromEdge, barWidth, barHeight);
    }

	public void draw(Graphics2D g) {
        drawHealthBar(g);
    
        //draw character
        int framesForOneImage = 20;
        double percentTillChange = ((double)frameCounter % framesForOneImage) / framesForOneImage;

        
        boolean shouldBlink = !hasEnoughTimeGapToRecieveDamage();
        double a = 5;
        
        if (shouldBlink){
            //dont draw image half the time to create blinking
            if (percentTillChange < 0.5) return;
        }

		BufferedImage imageToBeDrawn = null;
    
		if(pressedKeysHolder.downPress) {
			imageToBeDrawn = crouchImage; 
		}else{
            int chosenIndex = (int) (percentTillChange * standingImages.length);
            imageToBeDrawn = standingImages[chosenIndex];
        }
		
//        g.drawRect(x - halfWidth, y - halfHeight, 2*halfWidth, 2*halfHeight); //for development only
		g.drawImage(imageToBeDrawn, x - halfWidth, y - halfHeight, 2*halfWidth, 2*halfHeight, null);
	}
}


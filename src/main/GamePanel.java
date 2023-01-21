package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	ArrayList<AttackObject> playerAttackObjects = new ArrayList<AttackObject>();
	  ArrayList<AttackObject> bossAttackObjects = new ArrayList<AttackObject>();

	  int groundY = 430;
	  Sound sound = new Sound();
	  
	  Player player;
	  Boss boss;
	  Background background;
	  Thread gameThread;
	 
	  //game state trackers
	  public int currentState;
	  public boolean retry = false;
	  public int menuCommand = 0;
	  public int currentOptions = 0;
	  public int titleState = 0;
	  public int playingState = 1;
	  public int pauseState = 2;
	  public int gameOverState = 3;
	  public int playerWinsState = 4;
	  
	  private Game game;
	  public GamePanel(Game game) {
	    this.game = game;
	    setBackground(Color.black);
	    setFocusable(true);

	    player = new Player(this);
	    boss = new Boss(this);
	    background = new Background(this.getGroundY());
//	    playThis();
	    //sound = new Sound();
	    startGameThread();
	  }

	  public void addPlayerAttackObject(AttackObject attackObject){
	    playerAttackObjects.add(attackObject);
	  }
	  public void addBossAttackObject(AttackObject attackObject){
	    bossAttackObjects.add(attackObject);
	  }
	  public void removeBossAttackObject(AttackObject attackObject){
	    bossAttackObjects.remove(attackObject);
	  }
	  public int getGroundY(){
	    return groundY;
	    }
	    public void listenToKeys(KeyListener keyListener){
	        addKeyListener(keyListener);
	    }
	  
	  public void startGameThread() {
	    Thread gameThread = new Thread(this);
	    gameThread.start();
	  }
	  
	  public void run() {
		
		int drawInterval = 1000/60;
		currentState = titleState;
		playThis();

	    while(true) {
	        
		    update();
		    repaint();
	        
			try { Thread.sleep(drawInterval);}
	        catch (InterruptedException e) { e.printStackTrace(); } 
	        if (retry){
	            game.startNewGame();
	            break;
	        } 
	    }
	  }
	  public void playMusic(int i) {
		  sound.music(i);
		  sound.play();
		  sound.loop();
	  }
	  public void soundEffect(int i) {
		  sound.music(i);
		  sound.play();
	  }
	  public void stopMusic() {
		  sound.stop();
	  }
	  public void playThis() {
		  if (currentState == titleState) {
			  playMusic(2);
			  
		  }
		  else if (currentState == playingState) {
			  stopMusic();
			  playMusic(0);
		  }
	  }
	  public void update() {
	    //move all things
		if (currentState == playingState) {
			player.update();
		    boss.update();

		    for (int i = 0; i < playerAttackObjects.size(); i++){
		        playerAttackObjects.get(i).update();
		    }
		    for (int i = 0; i < bossAttackObjects.size(); i++){
		        bossAttackObjects.get(i).update();
		    }

		    //handle collision
		    for (int i = 0; i < playerAttackObjects.size(); i++){
		        if (playerAttackObjects.get(i).isTouching(boss)){
		            playerAttackObjects.remove(i);
		            boss.recieveDamage();
		        }
		    }

		    for (int i = 0; i < bossAttackObjects.size(); i++){
		        if (bossAttackObjects.get(i).isTouching(player)){
		            bossAttackObjects.remove(i);
		            player.recieveDamage();
		        }
		    }

		    if (player.isTouching(boss)){
		        player.recieveDamage();
		    }
		    if (player.isAlive() == false) {
		    	currentState = gameOverState;
		    }
		    if (boss.isAlive() == false) {
		    	currentState = playerWinsState;
		    }
		   

			
		}
		if (currentState == pauseState) {
			//Do nothing since we are paused
		}
		if (currentState == gameOverState) {
			//game is over we don't want player moving around
		}
		if (currentState == playerWinsState) {
			//player wins we don't want boss to move around after this state
		}
	      }
	  
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;
	    if (currentState == titleState) {

	       	String titleName = "Adventures of Ninja";
	       	Font title = new Font("arial_40", Font.BOLD, 30);
	       	g2.setFont(title);
	       	g2.setColor(Color.gray);
	       	g2.drawString(titleName, 252, 92);
	       	g2.setColor(Color.pink);
	       	g2.drawString(titleName, 250, 90 );
	       	BufferedImage sword = null;
	    		try {
	    			sword = ImageIO.read(getClass().getResourceAsStream("/Title/Sword.png"));
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		};
	    		g2.drawImage(sword , 240, 100, 64 *4, 64*4, null);
	       	//Start button
	       	titleName = "Start";
	       	g2.setColor(Color.white);
	       	g2.drawString(titleName, 342, 350);
	       	if (menuCommand == 0) {
	       		g2.drawString(">", 310, 350);
	       	}
	       	titleName = "Quit";
	       	g2.drawString(titleName, 342, 400);
	       	if (menuCommand == 1) {
	       		g2.drawString(">", 310, 400);
	       	}

	       }
	    
	    else {
	    	background.draw(g2);
	        player.draw(g2);
	        boss.draw(g2);
	        

	        for (int i = 0; i < playerAttackObjects.size(); i++){
	            playerAttackObjects.get(i).draw(g2);
	        }
	        for (int i = 0; i < bossAttackObjects.size(); i++){
	            bossAttackObjects.get(i).draw(g2);
	        } 
	      }
	    if (currentState == pauseState) {
	       	String text = "PAUSED";
	       	Font paused = new Font("arial_40", Font.PLAIN, 30);
	   		int x = 320;
	   		int y = 288;
	   		g2.setFont(paused);
	   		g2.setColor(Color.white);
	   		g2.drawString(text, x, y);
	       }

	       if (currentState == gameOverState) {
	       	String text = "GAME OVER";
	       	Font display = new Font("arial_40", Font.BOLD, 50);
	       	Font options = new Font("arial_40", Font.BOLD, 25);
	   		int x = 240;
	   		int y = 228;
	   		g2.setFont(display);
	   		g2.setColor(Color.black);
	   		g2.drawString(text, x, y);

	   		text = "Return to Menu";
	   		g2.setFont(options);
	   		g2.setColor(Color.black);
	   		g2.drawString(text, x +10, y + 40);
	   		if (currentOptions == 0) {
	       		g2.drawString(">", x - 20, y + 40);
	        }
	       	text = "Quit";
	       	g2.setFont(options);
	   		g2.setColor(Color.black);
	       	g2.drawString(text, x +270, y + 40);
	       	if (currentOptions == 1) {
	       		g2.drawString(">", x + 240, y + 40);
	       	}
	       }
	       if (currentState == playerWinsState) {
	          	String text = "YOU WIN";
	          	Font display = new Font("arial_40", Font.BOLD, 50);
	          	Font options = new Font("arial_40", Font.BOLD, 25);
	      		int x = 240;
	      		int y = 228;
	      		g2.setFont(display);
	      		g2.setColor(Color.black);
	      		g2.drawString(text, x, y);

	      		text = "Return to Menu";
	      		g2.setFont(options);
	      		g2.setColor(Color.black);
	      		g2.drawString(text, x +10, y + 40);
	      		if (currentOptions == 0) {
	          		g2.drawString(">", x - 20, y + 40);
	           }
	          	text = "Quit";
	          	g2.setFont(options);
	      		g2.setColor(Color.black);
	          	g2.drawString(text, x +270, y + 40);
	          	if (currentOptions == 1) {
	          		g2.drawString(">", x + 240, y + 40);
	          	}

	}
	}

}

package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PressedKeyHolder implements KeyListener{
	GamePanel gp;
	  public boolean leftPress, rightPress, upPress,downPress, spacePress;
	  
	  public PressedKeyHolder(GamePanel gp) {
		  this.gp = gp;
	  }

	  @Override
	  public void keyTyped(KeyEvent e) {}

	  @Override 
	  public void keyPressed(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    //operations to be done on title screen
	    if(gp.currentState == gp.titleState) {
	       	if (keyCode == KeyEvent.VK_UP) {
	           	gp.menuCommand --;
	           	if (gp.menuCommand < 0) {
	           		gp.menuCommand = 1;
	           	}
	           }
	       	if (keyCode == KeyEvent.VK_DOWN) {
	           	gp.menuCommand ++;
	           	if (gp.menuCommand > 1) {
	           		gp.menuCommand = 0;
	           	}

	           }
	       	if (keyCode == KeyEvent.VK_ENTER) {
	       		if (gp.menuCommand == 0) { 
	       			gp.stopMusic();
	       			gp.currentState = gp.playingState;
	       			gp.playMusic(0);
	                
	       		}
	       		else {
	       			System.exit(0);
	       		}
	       	}
	       }
	    if (gp.currentState == gp.playingState) {
	    	if(keyCode == KeyEvent.VK_A) leftPress = true;
	        if (keyCode == KeyEvent.VK_W) upPress = true;
	        if(keyCode == KeyEvent.VK_D) rightPress = true;
	        if(keyCode == KeyEvent.VK_S) downPress = true;
	        if(keyCode == KeyEvent.VK_SPACE) spacePress = true;
	        if(keyCode == KeyEvent.VK_P) {
	        	if (gp.currentState == gp.playingState) {
	        		gp.currentState = gp.pauseState;
	        	}
	        	else if (gp.currentState == gp.playingState) {
	        		gp.currentState = gp.pauseState;
	        	}
	        }
	    }
	    else if (gp.currentState == gp.pauseState) {
	    	if(keyCode == KeyEvent.VK_P) {
	    		gp.currentState = gp.playingState;
	    	}
	       	

	           }
	    if(gp.currentState == gp.gameOverState) {
	       	if (keyCode == KeyEvent.VK_LEFT) {
	           	gp.currentOptions --;
	           	if (gp.currentOptions < 0) {
	           		gp.currentOptions = 1;
	           	}
	           }
	       	if (keyCode == KeyEvent.VK_RIGHT) {
	           	gp.currentOptions ++;
	           	if (gp.currentOptions > 1) {
	           		gp.currentOptions = 0;
	           	}

	           }
	       	if (keyCode == KeyEvent.VK_ENTER) {
	       		if (gp.currentOptions == 0) {
	       			gp.stopMusic();
	       			gp.retry = true;
	       			gp.playMusic(2);
	       			gp.currentState = gp.titleState;
	       		}
	       		else {
	       			System.exit(0);
	       		}
	       	}
	       }
	    if(gp.currentState == gp.playerWinsState) {
	       	if (keyCode == KeyEvent.VK_LEFT) {
	           	gp.currentOptions --;
	           	if (gp.currentOptions < 0) {
	           		gp.currentOptions = 1;
	           	}
	           }
	       	if (keyCode == KeyEvent.VK_RIGHT) {
	           	gp.currentOptions ++;
	           	if (gp.currentOptions > 1) {
	           		gp.currentOptions = 0;
	           	}

	           }
	       	if (keyCode == KeyEvent.VK_ENTER) {
	       		if (gp.currentOptions == 0) {       			
	       			gp.retry = true;
	       			gp.stopMusic();
	       			gp.currentState = gp.playingState;
	       			gp.playMusic(2);
	       		}
	       		else {
	       			System.exit(0);
	       		}
	       	}
	       }
	   
	    
	  }
	  @Override
	  public void keyReleased(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    if(keyCode == KeyEvent.VK_A) leftPress = false;
	    if (keyCode == KeyEvent.VK_W) upPress = false;
	    if(keyCode == KeyEvent.VK_D) rightPress = false;
	    if(keyCode == KeyEvent.VK_S) downPress = false;
	    if(keyCode == KeyEvent.VK_SPACE) spacePress = false;  
	  } 
}

package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	  int groundY;
	  BufferedImage mountain;
	  BufferedImage ground1, ground2;

	  public Background(int groundY) {
	    this.groundY = groundY;
	    getBackGroundImages();
	  }

	  public void getBackGroundImages() {
	    
	    try {
	      mountain = ImageIO.read(getClass().getResourceAsStream("/backgrounds/grassbg1.gif")); 
	      ground1 = ImageIO.read(getClass().getResourceAsStream("/backgrounds/grass1.png"));
	      ground2 = ImageIO.read(getClass().getResourceAsStream("/backgrounds/grass1.png"));
	      
	    }catch(IOException e) {
	      e.printStackTrace();
	    }
	  }

	  public void draw(Graphics2D g2) {
		  
		g2.drawImage(mountain, 0, 0,768, 576, null);
		  
		g2.setColor(Color.GREEN);

		g2.fillRect( 0, groundY, 420, 320 );
		g2.fillRect( 420, groundY, 420, 320);
		g2.setColor(Color.black);
		
	    
	    g2.drawImage(ground1, 0, groundY, 420, 320, null);
	    g2.drawImage(ground2, 420, groundY, 420, 320, null);
	    
	    
	  }
}

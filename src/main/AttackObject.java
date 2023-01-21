package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class AttackObject extends Collidable {
	 protected BufferedImage image;

	    AttackObject (int x, int y, int halfWidth, int halfHeight){
	        super(x, y, halfWidth, halfHeight);
	    }
	    protected void setImage(BufferedImage image){
	        this.image = image;
	    }

	    public void draw(Graphics2D g){
	        g.drawImage(image, x - halfWidth, y-halfHeight, 2*halfWidth, 2*halfHeight, null);
	    }
	    
}

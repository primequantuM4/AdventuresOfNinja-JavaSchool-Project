package main;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class FireBall extends AttackObject{
	public FireBall(int x, int y){
        super(x, y, 24, 24);
        try{
            BufferedImage fireImage = ImageIO.read(getClass().getResourceAsStream("/Boss/fireball_left_1.png"));
            setImage(fireImage);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void update(){
        this.x -= 10;
    }
}

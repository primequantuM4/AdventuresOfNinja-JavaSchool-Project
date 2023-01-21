package main;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class PlayerBullet extends AttackObject{
	public PlayerBullet(int x, int y){
        super(x, y, 8, 8);
        try{
            BufferedImage bulletImage = ImageIO.read(getClass().getResourceAsStream("/Player/sprite_3.png"));
            setImage(bulletImage);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void update(){
        this.x += 15;
    }
}

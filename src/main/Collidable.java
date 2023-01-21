package main;

import java.awt.Graphics2D;

public abstract class Collidable {
	protected int x; 
	protected int y;

	protected int halfWidth;
	protected int halfHeight;

    public Collidable(int x, int y, int halfWidth, int halfHeight){
        this.x = x;
        this.y = y;
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public int getHalfWidth(){return this.halfWidth;}
    public int getHalfHeight(){return this.halfHeight;}

    public boolean isTouching(Collidable collidable) {
    
	  int gapInX = Math.abs(this.x - collidable.getX());
      int minimumAllowedGapInX =  this.halfWidth + collidable.getHalfWidth();

      int gapInY = Math.abs(this.y - collidable.getY());
      int minimumAllowedGapInY =  this.halfHeight + collidable.getHalfHeight();

	  boolean xTooClose = gapInX < minimumAllowedGapInX;
      boolean yTooClose = gapInY < minimumAllowedGapInY;

	return xTooClose && yTooClose;
  }

    //all collidables move and are painted
    public abstract void update();
	public abstract void draw(Graphics2D g);

}

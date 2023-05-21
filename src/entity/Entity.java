package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

	public int  worldX,  worldY;
	public int screenX, screenY;
	public int speed;
	public boolean walking = false;
	public BufferedImage[] up_still,   down_still,   left_still,   right_still;	
	public BufferedImage[] up_walking, down_walking, left_walking, right_walking;
	public String direction;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle hitBox;
	public int hitBoxDefaultX, hitBoxDefaultY;
	public boolean collisionOn = false;
}

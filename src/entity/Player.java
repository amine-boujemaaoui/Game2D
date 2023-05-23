package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	KeyHandler keyH;
	public final int screenX, screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		
		hitBox.x     = 9;  hitBox.y      = -4;
		hitBox.width = 33; hitBox.height = 32;
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		setDefaultValues();
		getImages();
	}
	public void setDefaultValues() {
		
		worldX = (int)(26.5 * gp.tileSize);
		worldY = (int)(23   * gp.tileSize);
		speed = 4;
		direction = "right";
	}
	public void getImages() {
		
		int spritesNum = 6;
		
		up_still    = new BufferedImage[spritesNum]; up_walking    = new BufferedImage[spritesNum];
		down_still  = new BufferedImage[spritesNum]; down_walking  = new BufferedImage[spritesNum];
		left_still  = new BufferedImage[spritesNum]; left_walking  = new BufferedImage[spritesNum]; 
		right_still = new BufferedImage[spritesNum]; right_walking = new BufferedImage[spritesNum];
		
		for (int i = 0; i < spritesNum; i++) up_still[i]    = setup("/player/up/still/"    + (i+1));
		for (int i = 0; i < spritesNum; i++) down_still[i]  = setup("/player/down/still/"  + (i+1));
		for (int i = 0; i < spritesNum; i++) left_still[i]  = setup("/player/left/still/"  + (i+1));
		for (int i = 0; i < spritesNum; i++) right_still[i] = setup("/player/right/still/" + (i+1));
		
		for (int i = 0; i < spritesNum; i++) up_walking[i]    = setup("/player/up/walking/"   + (i+1));
		for (int i = 0; i < spritesNum; i++) down_walking[i]  = setup("/player/down/walking/"  + (i+1));
		for (int i = 0; i < spritesNum; i++) left_walking[i]  = setup("/player/left/walking/"  + (i+1));
		for (int i = 0; i < spritesNum; i++) right_walking[i] = setup("/player/right/walking/" + (i+1));
	}
	public void pickUpObj(int index) {
		
		if(index != 999) {

		}
	}
	public void interactNPC(int index) {
		
		if(index != 999) {
			if(keyH.enterPressed) {
				gp.gameState = gp.dialogueState;
				gp.npc[index].speak();
			}
		}
		keyH.enterPressed = false;
	}
	public void update() {
		
		if (keyH.upPressed)    { direction = "up";    walking = true; }
		if (keyH.downPressed)  { direction = "down";  walking = true; }
		if (keyH.leftPressed)  { direction = "left";  walking = true; }
		if (keyH.rightPressed) { direction = "right"; walking = true; }
		
		if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) walking = false;
		
		// CHECK OBJ COLLISIONS
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObj(objIndex);
		
		// CHECK NPC COLLISION
		int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
		interactNPC(npcIndex);
		
		// CHECK COLLISION
		gp.cChecker.checkTile(this);
		if(!collisionOn && walking) {
			switch(direction) {
			case "up":    worldY -= speed; break;
			case "down":  worldY += speed; break;
			case "left":  worldX -= speed; break;
			case "right": worldX += speed; break;
			default: break;
			}
		}
		collisionOn = false;
		
		// SPRITE COUNTER FOR ANIMATION
		spriteCounter++;
		if(spriteCounter > 10 - speed) {
			spriteNum++; if(spriteNum > 6) spriteNum = 1;
			spriteCounter = 0;
		}
		
		// SPRITE COUNTER FOR SOUND
		soundCounter++;
		if(soundCounter > 26 - (int)(speed*1.75)) {
			if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)
				gp.playSE(11); 
			soundCounter = 0;
		}
		
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		if (!walking) {
			switch(direction) {
			case "up":    image = up_still[spriteNum-1];    break;
			case "down":  image = down_still[spriteNum-1];  break;
			case "left":  image = left_still[spriteNum-1];  break;
			case "right": image = right_still[spriteNum-1]; break;
			}
		} else {
			switch(direction) {
			case "up":    image = up_walking[spriteNum-1];    break;
			case "down":  image = down_walking[spriteNum-1];  break;
			case "left":  image = left_walking[spriteNum-1];  break;
			case "right": image = right_walking[spriteNum-1]; break;
			}
		}
		
		g2.drawImage(image, screenX, screenY - gp.tileSize, null);
		
		// DEBUG: PRINT PLAYER TILESIZE AND HITBOX 
		if(keyH.debug) {
			g2.setColor(new Color(0, 0, 255, 90));
			g2.fillRect(screenX , screenY - gp.tileSize, gp.tileSize, gp.tileSize*2);
			g2.setColor(new Color(0, 255, 0, 100));
			g2.fillRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);
		}
	}
}

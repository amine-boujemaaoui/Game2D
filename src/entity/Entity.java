package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Entity {

	public GamePanel gp;
	public int  worldX,  worldY;
	public int screenX, screenY;
	public int speed;
	public boolean walking = false;
	public BufferedImage[] up_still,   down_still,   left_still,   right_still;	
	public BufferedImage[] up_walking, down_walking, left_walking, right_walking;
	public String direction = "down";
	public int soundCounter = 0;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
	public int hitBoxDefaultX, hitBoxDefaultY;
	public boolean collisionOn = false;
	public int actionCounter = 0;
	public String dialogues[] = new String[20];
	public int dialogueIndex = 0;
	public String name;
	public boolean collision = false;
	public BufferedImage image1, image2, image3;
	public boolean bigObject = false;
	public boolean object = false;
	
	// STATS
	public int health,      maxHealth;
	public int stamina,     maxStamina;
	public int mana,        maxMana;
	public int inteligence;
	public int defense;
	public int strenght;
	
	public Entity(GamePanel gp) {
		
		this.gp = gp;
	}
	public void update() {
		
		setAction();
		
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkPlayer(this);
		
		// CHECK COLLISION
		if(!collisionOn && walking) {
			switch(direction) {
			case "up":    worldY -= speed; break;
			case "down":  worldY += speed; break;
			case "left":  worldX -= speed; break;
			case "right": worldX += speed; break;
			default: break;
			}
		} collisionOn = false;
		
		// SPRITE COUNTER FOR ANIMATION
		spriteCounter++;
		if(spriteCounter > 10 - speed) {
			spriteNum++; if(spriteNum > 6) spriteNum = 1;
			spriteCounter = 0;
		}
	}
	public void setAction () {
		
	}
	public BufferedImage setup(String imagePath, int width, int height) {
		
		BufferedImage image = null;
		try { image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png")); }
		catch (IOException e) { e.printStackTrace(); }
		return gp.ut.scaleImage(image, width, height);
	}
	public void speak() {
		
		switch(gp.player.direction) {
		case "up":    direction = "down";  break;
		case "down":  direction = "up";    break;
		case "left":  direction = "right"; break;
		case "right": direction = "left";  break;
		}
		
		if (dialogues[dialogueIndex] == null) dialogueIndex = 0;
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
	}
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		int offsetX, offsetY;
		     if(object)    { offsetX = gp.tileSize;    offsetY = gp.tileSize;   }
		else if(bigObject) { offsetX = gp.tileSize*3;  offsetY = gp.tileSize*4; }
		else               { offsetX = gp.tileSize;    offsetY = gp.tileSize*2; }
		
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
		
		if (worldX + offsetX > gp.player.worldX - gp.player.screenX && 
			worldX - offsetY < gp.player.worldX + gp.player.screenX &&
			worldY + offsetX > gp.player.worldY - gp.player.screenY &&
			worldY - offsetY < gp.player.worldY + gp.player.screenY) {
			if(object)         g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			else if(bigObject) g2.drawImage(image, screenX - gp.tileSize, screenY - gp.tileSize*3, gp.tileSize*3, gp.tileSize*4, null);
			else               g2.drawImage(image, screenX, screenY - gp.tileSize, gp.tileSize, gp.tileSize*2, null);
		}
		if(gp.keyH.debug) {
			g2.setColor(new Color(0, 255, 0, 100));
			g2.setStroke(new BasicStroke(1));
			g2.fillRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);
			g2.setColor(new Color(200, 200, 0));
			g2.setStroke(new BasicStroke(2));
			g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
		}
	}
}

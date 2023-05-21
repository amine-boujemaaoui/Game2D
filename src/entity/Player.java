package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX, screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		
		worldX = 20 * gp.tileSize;
		worldY = 22 * gp.tileSize;
		speed = 4;
		direction = "down";
	}
	public void getPlayerImage() {
		
		try {
			up_still = new BufferedImage[6]; down_still = new BufferedImage[6]; left_still = new BufferedImage[6]; right_still = new BufferedImage[6];
			for (int i = 0; i < 6; i++) up_still[i]    = ImageIO.read(getClass().getResourceAsStream("/player/up/still/player_up_still_" + (i+1) + ".png"));
			for (int i = 0; i < 6; i++) down_still[i]  = ImageIO.read(getClass().getResourceAsStream("/player/down/still/player_down_still_" + (i+1) + ".png"));
			for (int i = 0; i < 6; i++) left_still[i]  = ImageIO.read(getClass().getResourceAsStream("/player/left/still/player_left_still_" + (i+1) + ".png"));
			for (int i = 0; i < 6; i++) right_still[i] = ImageIO.read(getClass().getResourceAsStream("/player/right/still/player_right_still_" + (i+1) + ".png"));
			
			up_walking = new BufferedImage[6]; down_walking = new BufferedImage[6]; left_walking = new BufferedImage[6]; right_walking = new BufferedImage[6];
			for (int i = 0; i < 6; i++) up_walking[i]     = ImageIO.read(getClass().getResourceAsStream("/player/up/walking/player_up_walking_" + (i+1) + ".png"));
			for (int i = 0; i < 6; i++) down_walking [i]  = ImageIO.read(getClass().getResourceAsStream("/player/down/walking/player_down_walking_" + (i+1) + ".png"));
			for (int i = 0; i < 6; i++) left_walking [i]  = ImageIO.read(getClass().getResourceAsStream("/player/left/walking/player_left_walking_" + (i+1) + ".png"));
			for (int i = 0; i < 6; i++) right_walking [i] = ImageIO.read(getClass().getResourceAsStream("/player/right/walking/player_right_walking_" + (i+1) + ".png"));
			
		} catch (IOException e) { e.printStackTrace(); }
	}
	public void update() {
		
		if (keyH.upPressed)    { worldY -= speed; direction = "up";    walking = true; }
		if (keyH.downPressed)  { worldY += speed; direction = "down";  walking = true; }
		if (keyH.leftPressed)  { worldX -= speed; direction = "left";  walking = true; }
		if (keyH.rightPressed) { worldX += speed; direction = "right"; walking = true; }
		
		if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) walking = false;
		
		// SPRITE COUNTER FOR ANIMATION
		spriteCounter++;
		if(spriteCounter > 10 - speed) {
			spriteNum++; if(spriteNum > 6) spriteNum = 1;
			spriteCounter = 0;
		}
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		if (!walking) {
			switch(direction) {
			case "up":    image = up_still[spriteNum-1];   break;
			case "down":  image = down_still[spriteNum-1]; break;
			case "left":  image = left_still[spriteNum-1]; break;
			case "right": image = right_still[spriteNum-1];   break;
			}
		} else {
			switch(direction) {
			case "up":    image = up_walking[spriteNum-1];   break;
			case "down":  image = down_walking[spriteNum-1]; break;
			case "left":  image = left_walking[spriteNum-1]; break;
			case "right": image = right_walking[spriteNum-1];   break;
			}
		}
		
		g2.drawImage(image, screenX, screenY - gp.tileSize, gp.tileSize, gp.tileSize*2, null);
	}
}

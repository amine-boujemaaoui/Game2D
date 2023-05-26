package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	KeyHandler keyH;
	public final int screenX, screenY;
	public int caracterClass;
	public int staminaCounter = 0;
	public boolean running = false;
	public int actualSpeed = speed;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		this.keyH = keyH;
		
		type = gp.typePlayer;
		
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		
		hitBox.x     = 9;  hitBox.y      = -4;
		hitBox.width = 33; hitBox.height = 32;
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		setDefaultValues();
		getImages();
		getAttackImages();
	}
	public void setDefaultValues() {
		
		worldX = (int)(29   * gp.tileSize);
		worldY = (int)(24.5 * gp.tileSize);
		speed = 4;
		direction = "right";
		
	}
	public void setClassStats() {
		
		// maxHealth   = gp.statsValues[caracterClass][0]; health  = maxHealth - 3;
		maxHealth   = 20; health  = maxHealth;
		inteligence = gp.statsValues[caracterClass][1];
		maxStamina  = gp.statsValues[caracterClass][2]; stamina = maxStamina - 1;
		defense     = gp.statsValues[caracterClass][3];
		maxMana     = gp.statsValues[caracterClass][4]; mana    = maxMana - 5;
		strenght    = gp.statsValues[caracterClass][5];
	}
	public void getImages() {
		
		int spritesNum = 6;
		
		up_still    = new BufferedImage[spritesNum]; up_walking    = new BufferedImage[spritesNum];
		down_still  = new BufferedImage[spritesNum]; down_walking  = new BufferedImage[spritesNum];
		left_still  = new BufferedImage[spritesNum]; left_walking  = new BufferedImage[spritesNum]; 
		right_still = new BufferedImage[spritesNum]; right_walking = new BufferedImage[spritesNum];
		
		for (int i = 0; i < spritesNum; i++) up_still[i]    = setup("/player/up/still/"    + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_still[i]  = setup("/player/down/still/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_still[i]  = setup("/player/left/still/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) right_still[i] = setup("/player/right/still/" + (i+1), gp.tileSize, gp.tileSize*2);
		
		for (int i = 0; i < spritesNum; i++) up_walking[i]    = setup("/player/up/walking/"   + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_walking[i]  = setup("/player/down/walking/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_walking[i]  = setup("/player/left/walking/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) right_walking[i] = setup("/player/right/walking/" + (i+1), gp.tileSize, gp.tileSize*2);
	}
	public void getAttackImages() {
		
		int spritesNum = 4;
		
		up_attack   = new BufferedImage[spritesNum]; down_attack   = new BufferedImage[spritesNum];
		left_attack = new BufferedImage[spritesNum]; right_attack  = new BufferedImage[spritesNum];
		
		for (int i = 0; i < spritesNum; i++) up_attack[i]    = setup("/player/up/sword/"    + (i+1), gp.tileSize*3, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_attack[i]  = setup("/player/down/sword/"  + (i+1), gp.tileSize*3, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_attack[i]  = setup("/player/left/sword/"  + (i+1), gp.tileSize*3, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) right_attack[i] = setup("/player/right/sword/" + (i+1), gp.tileSize*3, gp.tileSize*2);
	}
	public void pickUpObj(int index) {
		
		if(index != 999) {
			String msg = "";
			switch(gp.obj[index].name) {
			case "Chest": msg = "to open ";   break;
			case "Key":   msg = "to pickup "; break;
			default:      msg = "NONE";       break;
			}
			if(!msg.equals("NONE")) gp.ui.showMessage("E", msg + gp.obj[index].name, gp.obj[index].worldX, gp.obj[index].worldY);
		}
	}
	public void interactNPC(int index) {
		
		if(index != 999) {
			
			gp.ui.showMessage("E", "to talk to " + gp.npc[index].name, gp.npc[index].worldX, gp.npc[index].worldY);
			if(keyH.eventPressed) {
				gp.gameState = gp.dialogueState;
				gp.npc[index].speak();
			}
		}
		else {
			if(keyH.attackPressed) attacking = true;
		}
	}
	public void interactMON(int index) {
		
		if(index != 999) {
			if(!invincible && health > 0) { health--; invincible = true; }
		}
	}
	public void update() {
		
		if(attacking) {
			attacking();
		} else {
			
			if (keyH.upPressed)    { if(keyH.shiftPressed && stamina > 0) running = true; direction = "up";    walking = true; }
			if (keyH.downPressed)  { if(keyH.shiftPressed && stamina > 0) running = true; direction = "down";  walking = true; }
			if (keyH.leftPressed)  { if(keyH.shiftPressed && stamina > 0) running = true; direction = "left";  walking = true; }
			if (keyH.rightPressed) { if(keyH.shiftPressed && stamina > 0) running = true; direction = "right"; walking = true; }
			
			if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) walking = false;
			if (!keyH.shiftPressed) running = false;
			
			// CHECK OBJ COLLISIONS
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObj(objIndex);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// CHECK NPC COLLISION
			int monIndex = gp.cChecker.checkEntity(this, gp.mon);
			interactMON(monIndex);
			
			// CHECK EVENT COLLISION
			gp.eventH.checkEvent();
			
			// CHECK COLLISION
			gp.cChecker.checkTile(this);
			if(!collisionOn && walking && !keyH.eventPressed ) {
				actualSpeed = speed;
				if(running && stamina > 0) actualSpeed = (int)(actualSpeed*1.70);
				switch(direction) {
				case "up":    worldY -= actualSpeed; break;
				case "down":  worldY += actualSpeed; break;
				case "left":  worldX -= actualSpeed; break;
				case "right": worldX += actualSpeed; break;
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
			if(soundCounter > 26 - (int)(actualSpeed*1.70)) {
				if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)
					gp.playSE(11); 
				soundCounter = 0;
			}
			
			// STAMINA COUNTER
			staminaCounter++;
			if(staminaCounter > 90) {
				if(stamina > 0 && running && walking) stamina--;
				else if((!running || !walking) && stamina < maxStamina) stamina++;
				else if(running && stamina == 0) stamina++;
				staminaCounter = 0;
			}
			
			keyH.eventPressed = false;
			keyH.enterPressed = false;
		}
		
		// INVINCIBLE COUNTER
		if(invincible) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	public void attacking() {
		if(spriteCounter == 7) gp.playSE(13);
		spriteCounter++; 
		     if(spriteCounter <  10) spriteNum = 1;
		else if(spriteCounter <  15) spriteNum = 2;
		else if(spriteCounter <  23) spriteNum = 3; 
		else if(spriteCounter <  28) spriteNum = 4;
		else { spriteNum = 1; spriteCounter = 0; attacking = false; }
		System.out.println(spriteCounter + " : " + (spriteNum-1) + " : " +up_still[spriteNum-1].getTileWidth());
	}
	@Override
	public void draw(Graphics2D g2, GamePanel gp) {
		
		BufferedImage image = null;
		int width = gp.tileSize, height = gp.tileSize*2, newScreenX = screenX, newScreenY = screenY - gp.tileSize;
		if(attacking) { newScreenX = screenX - gp.tileSize; width = gp.tileSize*3; }
		
		if (!walking) {
			switch(direction) {
			case "up":    if(attacking) image = up_attack[spriteNum-1];    else image = up_still[spriteNum-1];    break;
			case "down":  if(attacking) image = down_attack[spriteNum-1];  else image = down_still[spriteNum-1];  break;
			case "left":  if(attacking) image = left_attack[spriteNum-1];  else image = left_still[spriteNum-1];  break;
			case "right": if(attacking) image = right_attack[spriteNum-1]; else image = right_still[spriteNum-1]; break;
			}
		} else {
			switch(direction) {
			case "up":    if(attacking) image = up_attack[spriteNum-1];    else image = up_walking[spriteNum-1];    break;
			case "down":  if(attacking) image = down_attack[spriteNum-1];  else image = down_walking[spriteNum-1];  break;
			case "left":  if(attacking) image = left_attack[spriteNum-1];  else image = left_walking[spriteNum-1];  break;
			case "right": if(attacking) image = right_attack[spriteNum-1]; else image = right_walking[spriteNum-1]; break;
			}
		}
		if(invincible) { 
			if((spriteNum-1)%2 == 0) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); 
			else                     g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		
		
		g2.drawImage(image, newScreenX, newScreenY, width, height, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		// DEBUG: PRINT PLAYER TILESIZE AND HITBOX 
		if(keyH.debug) {
			g2.setColor(new Color(0, 0, 255, 90));
			g2.fillRect(newScreenX , newScreenY, width, height);
			g2.setColor(new Color(0, 255, 0, 100));
			g2.fillRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);
		}
	}
}

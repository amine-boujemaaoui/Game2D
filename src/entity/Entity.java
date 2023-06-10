package entity;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import main.GamePanel;
import projectile.PRJ;

public class Entity {

	public GamePanel gp;
	
	// POSITIONS
	public int  worldX,  worldY;
	public int screenX, screenY;
	public int hitBoxDefaultX, hitBoxDefaultY;
	public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
	public Rectangle attackHitBox = new Rectangle(0, 0, 0, 0);
	
	// IMAGES - DIALOGUES
	public BufferedImage[] up_still,   down_still,   left_still,   right_still;	
	public BufferedImage[] up_walking, down_walking, left_walking, right_walking;
	public BufferedImage[] up_attack, down_attack, left_attack, right_attack;
	public BufferedImage[] up_dying, down_dying, left_dying, right_dying;
	public BufferedImage[] ground;
	public BufferedImage item_icon;
	public String dialogues[] = new String[20];
	
	// STATUS
	public int size;
	public String name;
	public String direction = "down";
	public boolean walking = false;
	public boolean collisionOn = false;
	public int collisionV = 0;
	public int collisionH = 0;
	public boolean collision = false;
	public boolean invincible = false;
	public boolean attacking = false;
	public boolean castingSpell = false;
	public boolean alive = true;
	public boolean dying = false;
	public boolean hpBarOn = false;
	public int type, subType;
	public boolean pickupOnly = false;
	public int OBJstate = 0;
	
	// ENTITY SIZE
	public final int size1by1 = 0;
	public final int size1by2 = 1;
	public final int size3by4 = 2;
	public final int size2by2 = 3;
	
	// COUNTERS
	public int soundCounter = 0;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public int actionCounter = 0;
	public int dialogueIndex = 0;
	public int invincibleCounter = 0;
	public int dyingCounter = 0;
	public int hpBarOnCounter = 0;
	public int[] projectileCounter = {0, 0, 0}; 
	
	// STATS
	public int level;
	public int health,      maxHealth;
	public int stamina,     maxStamina;
	public int mana,        maxMana;
	public int inteligence;
	public int defense;
	public int strenght;
	public int attack;
	public int toughness;
	public int exp;
	public int nextLevelExp;
	public int speed, defaultSpeed;
	public int defaultAttackSpeed, attackSpeed;
	public int coins;
	public int spellCooldown;
	public Map<Integer, Integer> coinsByType;
	
	// EQUIPMENTS
	public Entity slotHelmet,  slotChestplate, slotLeggings, slotBoots;
	public Entity slotMele,    slotShield,     slotStaff,    slotProjectileWeapon;
	public Entity slotRing1,   slotRing2,      slotNecklace, slotBelt;
	public Entity slotPickaxe, slotAxe;
	public int attackValue, toughnessValue, attackSpeedValue, speedValue, amountValue;
	public String description = " ";
	public int durability = -1, maxDurability;
	public PRJ[] slotProjectiles = new PRJ[3];
	public int useCost;
	
	public Entity(GamePanel gp) {
		
		this.gp = gp;
	}
	public void update() {
		
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.mon);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if( this.type == gp.typeMON &&
		    contactPlayer &&
		    !gp.player.invincible &&
		    gp.player.health > 0 &&
		    alive) {
			
			damagePlayer(attack);
		}
		
		// CHECK COLLISION
		if(alive && !collisionOn && walking) {
			switch(direction) {
			case "up":    worldY -= speed; break;
			case "down":  worldY += speed; break;
			case "left":  worldX -= speed; break;
			case "right": worldX += speed; break;
			default: break;
			}
		}
		
		// SPRITE COUNTER FOR ANIMATION
		spriteCounter++;
		if(spriteCounter > 10 - speed) {
			spriteNum++; if(spriteNum > 6) spriteNum = 1;
			spriteCounter = 0;
		}
		
		// INVINCIBLE COUNTER
		if(invincible) {
			invincibleCounter++;
			if(invincibleCounter > 30) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		for(int i = 0; i < 3; i++)
			if(slotProjectiles[i] != null)
				if(slotProjectiles[i] != null && projectileCounter[i] < slotProjectiles[i].spellCooldown) {
					projectileCounter[i]++;
		}
	}
	public boolean use(Entity entity) {
		
		return false;
	}
	public void checkDrop() {
		
		
	}
	public void drop(Entity dropITM) {
		
		for(int i = 0; i < gp.itm.length; i++)
			if( gp.itm[i] == null) {
				gp.itm[i] = dropITM;
				gp.itm[i].worldX = worldX;
				gp.itm[i].worldY = worldY;
				break;
			}
		
	}
	public void setAction () {
		
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
	public int getStatValues(int i) {
		
		switch(i) {
		case 0: return attackValue;
		case 1: return attackSpeedValue;
		case 2: return speedValue;
		case 3: return toughnessValue;
		}
		return -1;
	}
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		int offsetX, offsetY, newScreenX, newScreenY;
		     if(size == size1by1) { offsetX = gp.tileSize; offsetY = gp.tileSize; 
		     						   newScreenX = screenX;  newScreenY = screenY; }
		else if(size == size3by4) { offsetX = gp.tileSize*3;  offsetY = gp.tileSize*4; 
									   newScreenX = screenX - gp.tileSize;  newScreenY = screenY - gp.tileSize*3; }
		else if(size == size2by2) { offsetX = gp.tileSize*2;  offsetY = gp.tileSize*2;
									   newScreenX = screenX - gp.tileSize/2;  newScreenY = screenY - gp.tileSize/2;}
		else              			 { offsetX = gp.tileSize;    offsetY = gp.tileSize*2; 
									   newScreenX = screenX;  newScreenY = screenY - gp.tileSize;}
		
		BufferedImage image = null;
		if(type == gp.typeOBJ) {
			
			image = ground[OBJstate];
		}
		else if( type == gp.typeARMR || type == gp.typeWPN || type == gp.typeITM) {
			image = ground[spriteNum-1];
		} 
		else if (type == gp.typePRJ) {
			
			switch(direction) {
			case "up":    image = up_attack[spriteNum-1];    break;
			case "down":  image = down_attack[spriteNum-1];  break;
			case "left":  image = left_attack[spriteNum-1];  break;
			case "right": image = right_attack[spriteNum-1]; break;
			}
		}
		else {
			
			if(dying) {
				dyingAnimation();
				switch(direction) {
				case "up":    image = up_dying[spriteNum-1];    break;
				case "down":  image = down_dying[spriteNum-1];  break;
				case "left":  image = left_dying[spriteNum-1];  break;
				case "right": image = right_dying[spriteNum-1]; break;
				}
			} else {
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
			}
		}
		
		if (worldX + offsetX > gp.player.worldX - gp.player.screenX && 
			worldX - offsetY < gp.player.worldX + gp.player.screenX &&
			worldY + offsetX > gp.player.worldY - gp.player.screenY &&
			worldY - offsetY < gp.player.worldY + gp.player.screenY) {
			
			if((type == gp.typeMON && hpBarOn && !dying && alive) || gp.keyH.debug) {
				
				double oneScale = (double)(offsetX/2)/maxHealth;
				double healthBar = oneScale * health;
				
				g2.setColor(gp.ui.helthColor);
				g2.drawImage(gp.ui.health_bar,newScreenX + offsetX/4, newScreenY, (int)healthBar, 8, null);
				g2.setColor(gp.ui.barColor);
				g2.setStroke(new BasicStroke(2));
				g2.drawRect(newScreenX + offsetX/4, newScreenY, offsetX/2, 8);
				g2.setStroke(new BasicStroke(1));
				
				hpBarOnCounter++;
				if(hpBarOnCounter > 120) { hpBarOnCounter = 0; hpBarOn = false; }
			}
			
			if(invincible) {
				hpBarOn = true;
				hpBarOnCounter = 0;
				if((spriteNum-1)%2 == 0) 
					changeAlpha(g2, 0.7f);
				else                     
					changeAlpha(g2, 0.4f);
			}
			g2.drawImage(image, newScreenX, newScreenY, null); changeAlpha(g2, 1f);
		}
		if(gp.keyH.debug) {
			g2.setColor(new Color(0, 255, 0, 100));
			g2.setStroke(new BasicStroke(1));
			g2.fillRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);
			g2.setColor(new Color(200, 200, 0));
			g2.setStroke(new BasicStroke(2));
			g2.drawRect(newScreenX, newScreenY, offsetX, offsetY);
		}
	}
	public void dyingAnimation() {
		
		dyingCounter++;
		     if(dyingCounter <  10) spriteNum = 1;
		else if(dyingCounter <  15) spriteNum = 2; 
		else if(dyingCounter <  20) spriteNum = 3;
		else if(dyingCounter <  25) spriteNum = 4;
		else { spriteNum = 5; alive = false; }
	}
	public void changeAlpha(Graphics2D g2, float alpha) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	}
	public void damagePlayer(int attack) {
		
		
		   gp.playSE(15); 
		   
		   int damage = attack - gp.player.toughness;
			if(damage < 0) damage = 0;
			
			if(slotHelmet     != null) slotHelmet.durability--;
			if(slotChestplate != null) slotChestplate.durability--;
			if(slotLeggings   != null) slotLeggings.durability--;
			if(slotBoots      != null) slotBoots.durability--;
			
		   gp.player.health -= damage; 
		   gp.player.invincible = true;
	}
	public void modifyStat(int stat, int value) {
		
		switch(stat) {
		case 1: if(health  + value > maxHealth ) health  = maxHealth;  else if(health  + value < 0) health = 0;  else health  += value; break;
		case 2: if(mana    + value > maxMana   ) mana    = maxMana;    else if(mana    + value < 0) mana = 0;    else mana    += value; break;
		case 3: if(stamina + value > maxStamina) stamina = maxStamina; else if(stamina + value < 0) stamina = 0; else stamina += value; break;
		}
	}
}

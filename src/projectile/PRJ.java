package projectile;

import entity.Entity;
import main.GamePanel;

public class PRJ extends Entity{
	
	Entity user;

	public PRJ(GamePanel gp) {
		super(gp);
		
		type = gp.typePRJ;
	}
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.health = this.maxHealth;
	}
	public void update() {
		
		if(user == gp.player ) {
			
			int monIndex = gp.cChecker.checkEntity(this, gp.mon[gp.currentMap]);
			if(monIndex != 999) {
				gp.player.damageMonster(monIndex, attackValue, true);
				alive = false;
				generateParticle(user.slotProjectiles[gp.keyH.spell], gp.mon[gp.currentMap][monIndex]);
			}
		} else {
			
			boolean playerCollision = gp.cChecker.checkPlayer(this);
			if(!gp.player.invincible && playerCollision) {
				
				damagePlayer(attackValue);
				alive = false;
				generateParticle(user.slotProjectiles[0], gp.player);
			}
		}
		
		switch(direction) {
		case "up":    worldY -= speed; break;
		case "down":  worldY += speed; break;
		case "left":  worldX -= speed; break;
		case "right": worldX += speed; break;
		default: break;
		}
		
		health--;
		if(health <= 0) alive = false;

		spriteCounter++;
		if(spriteCounter > 10 - speed) {
			spriteNum++; if(spriteNum > 4) spriteNum = 1;
			spriteCounter = 0;
		}
	}
} 

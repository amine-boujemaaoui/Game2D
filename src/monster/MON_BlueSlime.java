package monster;

import java.awt.image.BufferedImage;
import java.util.Random;

import entity.Entity;
import item.ITM_Coin_Bronze;
import item.ITM_Potion_Healing;
import item.ITM_Potion_Mana;
import main.GamePanel;
import projectile.PRJ_Waterball;

public class MON_BlueSlime extends Entity {

	public MON_BlueSlime(GamePanel gp, int worldX, int worldY) {
		super(gp);
		
		name = "Blue slime";
		speed = 1;
		type = gp.typeMON;
		size = size2by2;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		maxHealth = 10;
		health = maxHealth;
		attack = 1;
		toughness = 2;
		exp = 1;
		
		slotProjectiles[0] = new PRJ_Waterball(gp);
		
		hitBox.x = 6;      hitBox.y = 16;
		hitBox.width = 34; hitBox.height = 20;
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		getImages();
	}
	public void getImages() {
		
		int spritesNum = 6;
		up_still    = new BufferedImage[spritesNum]; up_walking    = new BufferedImage[spritesNum];
		down_still  = new BufferedImage[spritesNum]; down_walking  = new BufferedImage[spritesNum];
		left_still  = new BufferedImage[spritesNum]; left_walking  = new BufferedImage[spritesNum]; 
		right_still = new BufferedImage[spritesNum]; right_walking = new BufferedImage[spritesNum];
		up_dying    = new BufferedImage[spritesNum]; down_dying    = new BufferedImage[spritesNum];
		left_dying  = new BufferedImage[spritesNum]; right_dying   = new BufferedImage[spritesNum];
		
		for (int i = 0; i < spritesNum; i++) up_still[i]    = gp.ut.setup("/monsters/slime/blue/right/still/" + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_still[i]  = gp.ut.setup("/monsters/slime/blue/left/still/"  + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_still[i]  = gp.ut.setup("/monsters/slime/blue/left/still/"  + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) right_still[i] = gp.ut.setup("/monsters/slime/blue/right/still/" + (i+1), gp.tileSize*2, gp.tileSize*2);
		
		for (int i = 0; i < spritesNum; i++) up_walking[i]    = gp.ut.setup("/monsters/slime/blue/right/walking/" + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_walking[i]  = gp.ut.setup("/monsters/slime/blue/left/walking/"  + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_walking[i]  = gp.ut.setup("/monsters/slime/blue/left/walking/"  + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) right_walking[i] = gp.ut.setup("/monsters/slime/blue/right/walking/" + (i+1), gp.tileSize*2, gp.tileSize*2);
		
		for (int i = 0; i < spritesNum-1; i++) up_dying[i]    = gp.ut.setup("/monsters/slime/blue/right/dying/" + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum-1; i++) down_dying[i]  = gp.ut.setup("/monsters/slime/blue/left/dying/"  + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum-1; i++) left_dying[i]  = gp.ut.setup("/monsters/slime/blue/left/dying/"  + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum-1; i++) right_dying[i] = gp.ut.setup("/monsters/slime/blue/right/dying/" + (i+1), gp.tileSize*2, gp.tileSize*2);
		
		up_dying[spritesNum-1]    = gp.ut.setup("/monsters/slime/blue/right/dying/" + (spritesNum-1), gp.tileSize*2, gp.tileSize*2);
		down_dying[spritesNum-1]  = gp.ut.setup("/monsters/slime/blue/left/dying/"  + (spritesNum-1), gp.tileSize*2, gp.tileSize*2);
		left_dying[spritesNum-1]  = gp.ut.setup("/monsters/slime/blue/left/dying/"  + (spritesNum-1), gp.tileSize*2, gp.tileSize*2);
		right_dying[spritesNum-1] = gp.ut.setup("/monsters/slime/blue/right/dying/" + (spritesNum-1), gp.tileSize*2, gp.tileSize*2);
	}
	public void setAction() {
		
		int rand;
		
		actionCounter++;
		if(actionCounter == 60) {

			rand = gp.r.nextInt(100)+1;
			
			     if(rand > 90) { direction = "up";    walking = true;  }
			else if(rand > 70) { direction = "down";  walking = true;  }
			else if(rand > 60) { direction = "left";  walking = true;  }
			else if(rand > 50) { direction = "right"; walking = true;  }
			else            {                      walking = false; }
			actionCounter = 0;
		}
		
		rand = gp.r.nextInt(200)+1;
		
		if(rand > 199 && health < maxHealth && !slotProjectiles[0].alive && projectileCounter[0] == slotProjectiles[0].spellCooldown) {
			
			String oppositeDirection = "any";
			switch(gp.player.direction) {
			case "up":    oppositeDirection = "down";  break;
			case "down":  oppositeDirection = "up";    break;
			case "left":  oppositeDirection = "right"; break;
			case "right": oppositeDirection = "left";  break;
			}
			
			slotProjectiles[0].set(worldX, worldY, oppositeDirection, true, this);
			if(!gp.projectileList.contains(slotProjectiles[0]))
				gp.projectileList.add(slotProjectiles[0]);
			gp.playSE(13);
			projectileCounter[0] = 0;
		}
	}
	public void checkDrop() {
		
		int rand = gp.r.nextInt(300)+1;
		
		if(rand > 100) {
			Entity coin_bronze = new ITM_Coin_Bronze(gp);
			coin_bronze.amountValue = gp.r.nextInt(4)+1;
			drop(coin_bronze);
		}
		
		if(rand > 250) drop(new ITM_Potion_Mana(gp));
	}
}

package projectile;

import java.awt.image.BufferedImage;

import main.GamePanel;

public class PRJ_Fireball extends PRJ {

	public PRJ_Fireball(GamePanel gp) {
		super(gp);
		
		name = "Fireball";
		speed = 6;
		maxHealth = 60;
		health = maxHealth;
		attackValue = 12;
		useCost = 1;
		alive = false;
		spellCooldown = 80;
		getImages();
	}
	public void getImages() {
		
		int spritesNum = 4;
		
		up_attack = new BufferedImage[spritesNum]; 
		down_attack = new BufferedImage[spritesNum]; 
		left_attack = new BufferedImage[spritesNum]; 
		right_attack = new BufferedImage[spritesNum];
		
		for (int i = 0; i < spritesNum; i++) up_attack[i]    = gp.ut.setup("/projectile/fireball/up/"    + (i+1), gp.tileSize, gp.tileSize);
		for (int i = 0; i < spritesNum; i++) down_attack[i]  = gp.ut.setup("/projectile/fireball/down/"  + (i+1), gp.tileSize, gp.tileSize);
		for (int i = 0; i < spritesNum; i++) left_attack[i]  = gp.ut.setup("/projectile/fireball/left/"  + (i+1), gp.tileSize, gp.tileSize);
		for (int i = 0; i < spritesNum; i++) right_attack[i] = gp.ut.setup("/projectile/fireball/right/" + (i+1), gp.tileSize, gp.tileSize);
	}
}

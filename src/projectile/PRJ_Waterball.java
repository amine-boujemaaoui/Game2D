package projectile;

import java.awt.image.BufferedImage;

import main.GamePanel;

public class PRJ_Waterball extends PRJ {

	public PRJ_Waterball(GamePanel gp) {
		super(gp);
		
		name = "Fireball";
		speed = 5;
		maxHealth = 80;
		health = maxHealth;
		attackValue = 3;
		useCost = 1;
		alive = false;
		spellCooldown = 60;
		getImages();
	}
	public void getImages() {
		
		int spritesNum = 4;
		
		up_attack = new BufferedImage[spritesNum]; 
		down_attack = new BufferedImage[spritesNum]; 
		left_attack = new BufferedImage[spritesNum]; 
		right_attack = new BufferedImage[spritesNum];
		
		for (int i = 0; i < spritesNum; i++) up_attack[i]    = setup("/projectile/waterball/up/"    + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_attack[i]  = setup("/projectile/waterball/down/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_attack[i]  = setup("/projectile/waterball/left/"  + (i+1), gp.tileSize*2, gp.tileSize);
		for (int i = 0; i < spritesNum; i++) right_attack[i] = setup("/projectile/waterball/right/" + (i+1), gp.tileSize*2, gp.tileSize);
	}
}

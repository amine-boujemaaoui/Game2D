package projectile;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class PRJ_Waterball extends PRJ {

	public PRJ_Waterball(GamePanel gp) {
		super(gp);
		
		name = "Fireball";
		speed =6;
		maxHealth = 80;
		health = maxHealth;
		attackValue = 4;
		useCost = 1;
		alive = false;
		spellCooldown = 60;
		getImages();

		particleColor = new Color(0, 179, 255);
		particleSize = 6;
		particleSpeed = 3;
		particleMaxHealth = 10;

		particleOffsetX = gp.tileSize/2 - particleSize/2;
	}
	public void getImages() {
		
		int spritesNum = 4;
		
		up_attack = new BufferedImage[spritesNum]; 
		down_attack = new BufferedImage[spritesNum]; 
		left_attack = new BufferedImage[spritesNum]; 
		right_attack = new BufferedImage[spritesNum];
		
		for (int i = 0; i < spritesNum; i++) up_attack[i]    = gp.ut.setup("/projectile/waterball/up/"    + (i+1), gp.tileSize, gp.tileSize);
		for (int i = 0; i < spritesNum; i++) down_attack[i]  = gp.ut.setup("/projectile/waterball/down/"  + (i+1), gp.tileSize, gp.tileSize);
		for (int i = 0; i < spritesNum; i++) left_attack[i]  = gp.ut.setup("/projectile/waterball/left/"  + (i+1), gp.tileSize, gp.tileSize);
		for (int i = 0; i < spritesNum; i++) right_attack[i] = gp.ut.setup("/projectile/waterball/right/" + (i+1), gp.tileSize, gp.tileSize);
	}
}

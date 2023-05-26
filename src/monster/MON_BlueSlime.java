package monster;

import java.awt.image.BufferedImage;
import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_BlueSlime extends Entity {

	public MON_BlueSlime(GamePanel gp, int worldX, int worldY) {
		super(gp);
		
		name = "Blue slime";
		speed = 1;
		type = gp.typeMonster;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		maxHealth = 4;
		health = maxHealth;
		
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
		
		for (int i = 0; i < spritesNum; i++) up_still[i]    = setup("/monsters/slime/right/still/" + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_still[i]  = setup("/monsters/slime/left/still/"  + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_still[i]  = setup("/monsters/slime/left/still/"  + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) right_still[i] = setup("/monsters/slime/right/still/" + (i+1), gp.tileSize*2, gp.tileSize*2);
		
		for (int i = 0; i < spritesNum; i++) up_walking[i]    = setup("/monsters/slime/right/walking/" + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_walking[i]  = setup("/monsters/slime/left/walking/"  + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_walking[i]  = setup("/monsters/slime/left/walking/"  + (i+1), gp.tileSize*2, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) right_walking[i] = setup("/monsters/slime/right/walking/" + (i+1), gp.tileSize*2, gp.tileSize*2);
	}
	public void setAction() {
		
		actionCounter++;
		if(actionCounter == 60) {
			Random r = new Random();
			int i = r.nextInt(100)+1;
			
			     if(i > 90) { direction = "up";    walking = true;  }
			else if(i > 70) { direction = "down";  walking = true;  }
			else if(i > 60) { direction = "left";  walking = true;  }
			else if(i > 50) { direction = "right"; walking = true;  }
			else            {                      walking = false; }
			actionCounter = 0;
		}
	}
}

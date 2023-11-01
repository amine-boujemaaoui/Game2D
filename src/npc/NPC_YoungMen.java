package npc;

import java.awt.image.BufferedImage;
import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class NPC_YoungMen extends Entity {
	
	public NPC_YoungMen(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "Young men";
		speed = 2;
		
		size = size1by2;
		type = gp.typeNPC;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		hitBox.x     = 9;  hitBox.y      = -4;
		hitBox.width = 33; hitBox.height = 32;
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		getImages();
		setDialogues();
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
	public void setDialogues() {
		
		super.setDialogues("youngMen.txt");
	}
	public void getImages() {
		
		int spritesNum = 6;
		
		up_still    = new BufferedImage[spritesNum]; up_walking    = new BufferedImage[spritesNum];
		down_still  = new BufferedImage[spritesNum]; down_walking  = new BufferedImage[spritesNum];
		left_still  = new BufferedImage[spritesNum]; left_walking  = new BufferedImage[spritesNum]; 
		right_still = new BufferedImage[spritesNum]; right_walking = new BufferedImage[spritesNum];
		
		for (int i = 0; i < spritesNum; i++) up_still[i]    = gp.ut.setup("/npc/youngMen/up/still/"    + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_still[i]  = gp.ut.setup("/npc/youngMen/down/still/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_still[i]  = gp.ut.setup("/npc/youngMen/left/still/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) right_still[i] = gp.ut.setup("/npc/youngMen/right/still/" + (i+1), gp.tileSize, gp.tileSize*2);
		
		for (int i = 0; i < spritesNum; i++) up_walking[i]    = gp.ut.setup("/npc/youngMen/up/walking/"    + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_walking[i]  = gp.ut.setup("/npc/youngMen/down/walking/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_walking[i]  = gp.ut.setup("/npc/youngMen/left/walking/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) right_walking[i] = gp.ut.setup("/npc/youngMen/right/walking/" + (i+1), gp.tileSize, gp.tileSize*2);
	}
	public void speak() {
		super.speak();
 	}
	public void interact() {
		speak();
		gp.gameState = gp.dialogueState;
	}
}

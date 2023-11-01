package npc;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class NPC_Women extends Entity {

	int soundCounter = 0;
	
	public NPC_Women(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "WOMAAAAAAAAAANNNN";
		speed = 3;
		
		size = size1by2;
		type = gp.typeNPC;
		maxSpriteNumStill = 8;
		maxSpriteNumWalking = 10;
		
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

		walking = true;
		soundCounter++;
		if (soundCounter > 30 && gp.cChecker.checkPlayer(this)) { gp.playSE(20); soundCounter = 0; }
		searchPath((gp.player.worldX + gp.player.hitBox.x)/gp.tileSize, (gp.player.worldY + gp.player.hitBox.y)/gp.tileSize);
	}
	public void setDialogues() {
		
		try {
			
			InputStream is = getClass().getResourceAsStream("/dialogues/youngMen.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String line; int i = 0, nbLigne = 0; 
			while ((line = br.readLine()) != null) { 
				if(line.equals("%")) i++;
				else {
					if(nbLigne == 5) { i++; nbLigne = 0; }
					if(dialogues[i] == null) dialogues[i] = line;
					else dialogues[i] += line;
					nbLigne++;
				}
			}
			br.close();
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	public void getImages() {
		
		int spritesNumStill = maxSpriteNumStill, spritesNumWalking = maxSpriteNumWalking;
		
		up_still    = new BufferedImage[spritesNumStill]; up_walking    = new BufferedImage[spritesNumWalking];
		down_still  = new BufferedImage[spritesNumStill]; down_walking  = new BufferedImage[spritesNumWalking];
		left_still  = new BufferedImage[spritesNumStill]; left_walking  = new BufferedImage[spritesNumWalking]; 
		right_still = new BufferedImage[spritesNumStill]; right_walking = new BufferedImage[spritesNumWalking];
		
		for (int i = 0; i < spritesNumStill; i++) up_still[i]    = gp.ut.setup("/npc/women/up/still/"    + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNumStill; i++) down_still[i]  = gp.ut.setup("/npc/women/down/still/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNumStill; i++) left_still[i]  = gp.ut.setup("/npc/women/left/still/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNumStill; i++) right_still[i] = gp.ut.setup("/npc/women/right/still/" + (i+1), gp.tileSize, gp.tileSize*2);
		
		for (int i = 0; i < spritesNumWalking; i++) up_walking[i]    = gp.ut.setup("/npc/women/up/walking/"    + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNumWalking; i++) down_walking[i]  = gp.ut.setup("/npc/women/down/walking/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNumWalking; i++) left_walking[i]  = gp.ut.setup("/npc/women/left/walking/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNumWalking; i++) right_walking[i] = gp.ut.setup("/npc/women/right/walking/" + (i+1), gp.tileSize, gp.tileSize*2);
	}
	public void speak() {
		super.speak();
 	}
}

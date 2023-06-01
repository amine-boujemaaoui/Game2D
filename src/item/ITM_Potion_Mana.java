package item;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ITM_Potion_Mana extends Entity{

	public ITM_Potion_Mana(GamePanel gp) {
		
		super(gp);
		
		name = "Potion of mana";
		description = "A potion that can regenerate the players mana by 2 points.";
		size = size1by2;
		type = gp.typeITM;
		
		hitBox.x = 9;
		hitBox.width = 30;
		
		hitBoxDefaultX = hitBox.x;
		
		ground    = new BufferedImage[6];
		ground[0] = setup("/objects/potions/mana/1", gp.tileSize, gp.tileSize*2);
		ground[1] = setup("/objects/potions/mana/2", gp.tileSize, gp.tileSize*2);
		ground[2] = setup("/objects/potions/mana/3", gp.tileSize, gp.tileSize*2);
		ground[3] = setup("/objects/potions/mana/3", gp.tileSize, gp.tileSize*2);
		ground[4] = setup("/objects/potions/mana/2", gp.tileSize, gp.tileSize*2);
		ground[5] = setup("/objects/potions/mana/1", gp.tileSize, gp.tileSize*2);
		
		item_icon = setup("/objects/potions/mana/item", gp.tileSize, gp.tileSize);
	}
	public boolean use(Entity entity) {
		
		gp.playSE(17);
		gp.ui.addEventMessage("regenerated mana by 2", 
	              12f, 
	              Font.BOLD, 
	              Color.white, 
	              Color.gray, 
	              gp.player.screenX + gp.tileSize,
	              gp.player.screenY + gp.tileSize,
	              true);
		
		if(entity.mana + 2 > entity.maxMana) entity.mana = entity.maxMana;
		else entity.mana += 2;
		return true;
	}
}

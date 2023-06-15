package item.ITM.Potion;

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
		amountValue = 6;
		
		hitBox.x = 9;
		hitBox.width = 30;
		
		hitBoxDefaultX = hitBox.x;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/potions/mana/1", gp.tileSize/2, gp.tileSize);
		ground[1] = gp.ut.setup("/items/potions/mana/2", gp.tileSize/2, gp.tileSize);
		ground[2] = gp.ut.setup("/items/potions/mana/3", gp.tileSize/2, gp.tileSize);
		ground[3] = gp.ut.setup("/items/potions/mana/3", gp.tileSize/2, gp.tileSize);
		ground[4] = gp.ut.setup("/items/potions/mana/2", gp.tileSize/2, gp.tileSize);
		ground[5] = gp.ut.setup("/items/potions/mana/1", gp.tileSize/2, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/potions/mana/item", gp.tileSize, gp.tileSize);
	}
	public boolean use(Entity entity) {
		
		gp.playSE(17);
		gp.ui.addEventMessage("regenerated mana by " + amountValue, 
	              12f, 
	              Font.BOLD, 
	              Color.white, 
	              Color.gray, 
	              gp.player.screenX + gp.tileSize,
	              gp.player.screenY + gp.tileSize,
	              true);
		
		if(entity.mana + amountValue > entity.maxMana) entity.mana = entity.maxMana;
		else entity.mana += amountValue;
		return true;
	}
}

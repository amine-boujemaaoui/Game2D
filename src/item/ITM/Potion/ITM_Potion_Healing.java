package item.ITM.Potion;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ITM_Potion_Healing extends Entity{

	public ITM_Potion_Healing(GamePanel gp) {
		
		super(gp);
		
		name = "Potion of healing";
		description = "A potion that can heal by 2 points the players health.";
		size = size1by2;
		type = gp.typeITM;
		amountValue = 2;
		price = 10;
		
		hitBox.x = 9;
		hitBox.width = 30;
		
		hitBoxDefaultX = hitBox.x;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/potions/healing/1", gp.tileSize/2, gp.tileSize);
		ground[1] = gp.ut.setup("/items/potions/healing/2", gp.tileSize/2, gp.tileSize);
		ground[2] = gp.ut.setup("/items/potions/healing/3", gp.tileSize/2, gp.tileSize);
		ground[3] = gp.ut.setup("/items/potions/healing/3", gp.tileSize/2, gp.tileSize);
		ground[4] = gp.ut.setup("/items/potions/healing/2", gp.tileSize/2, gp.tileSize);
		ground[5] = gp.ut.setup("/items/potions/healing/1", gp.tileSize/2, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/potions/healing/item", gp.tileSize, gp.tileSize);
	}
	public boolean use(Entity entity) {
		
		gp.playSE(17);
		gp.ui.addEventMessage("heald by " + amountValue, 
	              12f, 
	              Font.BOLD, 
	              Color.white, 
	              Color.gray, 
	              gp.player.screenX + gp.tileSize,
	              gp.player.screenY + gp.tileSize,
	              true);
		
		if(entity.health + amountValue > entity.maxHealth) entity.health = entity.maxHealth;
		else entity.health += amountValue;
		return true;
	}
	public Entity clone() {
		return new ITM_Potion_Healing(gp);
	}
}

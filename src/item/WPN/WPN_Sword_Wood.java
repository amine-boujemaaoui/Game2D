package item.WPN;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class WPN_Sword_Wood extends Entity{

	public WPN_Sword_Wood(GamePanel gp) {
		
		super(gp);
		
		name = "Wooden Sword";
		description = "Simple wooden sword that deals additional damage and increase attack speed.";
		size = size1by1;
		type = gp.typeWPN;
		subType = gp.subType_WPN_SW;
		price = 20;
		
		attackValue = 9;
		attackSpeedValue = 4;
		maxDurability = 25;
		durability = maxDurability;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/weapon/sword/sword_wood", gp.tileSize, gp.tileSize);
		ground[1] = gp.ut.setup("/items/weapon/sword/sword_wood", gp.tileSize, gp.tileSize);
		ground[2] = gp.ut.setup("/items/weapon/sword/sword_wood", gp.tileSize, gp.tileSize);
		ground[3] = gp.ut.setup("/items/weapon/sword/sword_wood", gp.tileSize, gp.tileSize);
		ground[4] = gp.ut.setup("/items/weapon/sword/sword_wood", gp.tileSize, gp.tileSize);
		ground[5] = gp.ut.setup("/items/weapon/sword/sword_wood", gp.tileSize, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/weapon/sword/sword_wood", gp.tileSize, gp.tileSize);
	}
	public Entity clone() {
		return new WPN_Sword_Wood(gp);
	}
}
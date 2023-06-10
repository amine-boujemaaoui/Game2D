package item;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class WPN_Sword_Iron extends Entity{

	public WPN_Sword_Iron(GamePanel gp) {
		
		super(gp);
		
		name = "Iron Sword";
		description = "Simple iron sword that deals additional damage and increase attack speed.";
		size = size1by1;
		type = gp.typeWPN;
		subType = gp.subType_WPN_SW;
		
		attackValue = 12;
		attackSpeedValue = 5;
		maxDurability = 200;
		durability = maxDurability;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/weapon/sword/sword_iron", gp.tileSize, gp.tileSize);
		ground[1] = gp.ut.setup("/items/weapon/sword/sword_iron", gp.tileSize, gp.tileSize);
		ground[2] = gp.ut.setup("/items/weapon/sword/sword_iron", gp.tileSize, gp.tileSize);
		ground[3] = gp.ut.setup("/items/weapon/sword/sword_iron", gp.tileSize, gp.tileSize);
		ground[4] = gp.ut.setup("/items/weapon/sword/sword_iron", gp.tileSize, gp.tileSize);
		ground[5] = gp.ut.setup("/items/weapon/sword/sword_iron", gp.tileSize, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/weapon/sword/sword_iron", gp.tileSize, gp.tileSize);
	}
}
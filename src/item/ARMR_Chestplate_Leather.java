package item;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ARMR_Chestplate_Leather extends Entity{

	public ARMR_Chestplate_Leather(GamePanel gp) {
		
		super(gp);
		
		name = "Leather Chestplate";
		description = "Simple leather chastplate that increases defense.";
		size = size1by1;
		type = gp.typeARMR;
		subType = gp.subType_ARMR_C;
		
		toughnessValue = 1;
		maxDurability = 30;
		durability = maxDurability;
		speedValue = -1;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/armor/chestplate/chestplate_leather", gp.tileSize, gp.tileSize);
		ground[1] = gp.ut.setup("/items/armor/chestplate/chestplate_leather", gp.tileSize, gp.tileSize);
		ground[2] = gp.ut.setup("/items/armor/chestplate/chestplate_leather", gp.tileSize, gp.tileSize);
		ground[3] = gp.ut.setup("/items/armor/chestplate/chestplate_leather", gp.tileSize, gp.tileSize);
		ground[4] = gp.ut.setup("/items/armor/chestplate/chestplate_leather", gp.tileSize, gp.tileSize);
		ground[5] = gp.ut.setup("/items/armor/chestplate/chestplate_leather", gp.tileSize, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/armor/chestplate/chestplate_leather", gp.tileSize, gp.tileSize);
	}
}
package item.ARMR;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ARMR_Boots_Leather extends Entity{

	public ARMR_Boots_Leather(GamePanel gp) {
		
		super(gp);
		
		name = "Leather Boots";
		description = "Simple leather boots that increases movment speed.";
		size = size1by1;
		type = gp.typeARMR;
		subType = gp.subType_ARMR_B;
	
		speedValue = 3;
		maxDurability = 20;
		durability = maxDurability;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/armor/boots/boots_leather", gp.tileSize, gp.tileSize);
		ground[1] = gp.ut.setup("/items/armor/boots/boots_leather", gp.tileSize, gp.tileSize);
		ground[2] = gp.ut.setup("/items/armor/boots/boots_leather", gp.tileSize, gp.tileSize);
		ground[3] = gp.ut.setup("/items/armor/boots/boots_leather", gp.tileSize, gp.tileSize);
		ground[4] = gp.ut.setup("/items/armor/boots/boots_leather", gp.tileSize, gp.tileSize);
		ground[5] = gp.ut.setup("/items/armor/boots/boots_leather", gp.tileSize, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/armor/boots/boots_leather", gp.tileSize, gp.tileSize);
	}
}
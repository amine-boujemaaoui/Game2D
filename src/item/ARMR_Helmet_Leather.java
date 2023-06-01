package item;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ARMR_Helmet_Leather extends Entity{

	public ARMR_Helmet_Leather(GamePanel gp) {
		
		super(gp);
		
		name = "Leather Helmet";
		description = "Simple leather helmet that increases defense.";
		size = size1by1;
		type = gp.typeARMR;
		subType = gp.subType_ARMR_H;
		
		speedValue = -1;
		toughnessValue = 1;
		maxDurability = 25;
		durability = maxDurability;
		
		ground    = new BufferedImage[6];
		ground[0] = setup("/objects/helmet/helmet_leather", gp.tileSize, gp.tileSize);
		ground[1] = setup("/objects/helmet/helmet_leather", gp.tileSize, gp.tileSize);
		ground[2] = setup("/objects/helmet/helmet_leather", gp.tileSize, gp.tileSize);
		ground[3] = setup("/objects/helmet/helmet_leather", gp.tileSize, gp.tileSize);
		ground[4] = setup("/objects/helmet/helmet_leather", gp.tileSize, gp.tileSize);
		ground[5] = setup("/objects/helmet/helmet_leather", gp.tileSize, gp.tileSize);
		
		item_icon = setup("/objects/helmet/helmet_leather", gp.tileSize, gp.tileSize);
	}
}
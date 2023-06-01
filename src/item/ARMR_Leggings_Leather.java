package item;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ARMR_Leggings_Leather extends Entity{

	public ARMR_Leggings_Leather(GamePanel gp) {
		
		super(gp);
		
		name = "Leather Leggings";
		description = "Simple leather leggings that increases defense.";
		size = size1by1;
		type = gp.typeARMR;
		subType = gp.subType_ARMR_L;
		
		speedValue = -1;
		toughnessValue = 1;
		maxDurability = 27;
		durability = maxDurability;
		
		ground    = new BufferedImage[6];
		ground[0] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
		ground[1] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
		ground[2] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
		ground[3] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
		ground[4] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
		ground[5] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
		
		item_icon = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
	}
}
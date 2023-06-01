package item;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class WPN_Shield_Wood extends Entity{

	public WPN_Shield_Wood(GamePanel gp) {
		
		super(gp);
		
		name = "Wooden Shield";
		description = "Simple wooden shield to pare enemies attacks.";
		size = size1by1;
		type = gp.typeWPN;
		subType = gp.subType_WPN_SH;
		
		toughnessValue = 1;
		maxDurability = 30;
		durability = maxDurability;
		
		ground    = new BufferedImage[6];
		ground[0] = setup("/objects/shield/shield_wood", gp.tileSize, gp.tileSize);
		ground[1] = setup("/objects/shield/shield_wood", gp.tileSize, gp.tileSize);
		ground[2] = setup("/objects/shield/shield_wood", gp.tileSize, gp.tileSize);
		ground[3] = setup("/objects/shield/shield_wood", gp.tileSize, gp.tileSize);
		ground[4] = setup("/objects/shield/shield_wood", gp.tileSize, gp.tileSize);
		ground[5] = setup("/objects/shield/shield_wood", gp.tileSize, gp.tileSize);
		
		item_icon = setup("/objects/shield/shield_wood", gp.tileSize, gp.tileSize);
	}
}
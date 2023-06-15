package item.TOOL;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class TOOL_Pickaxe_Wood extends Entity{

	public TOOL_Pickaxe_Wood(GamePanel gp) {
		
		super(gp);
		
		name = "Wooden Pickaxe";
		description = "Simple wooden pickaxe.";
		size = size1by1;
		type = gp.typeTOOL;
		subType = gp.subType_TOOL_PKX;
		
		toughnessValue = 1;
		maxDurability = 30;
		durability = maxDurability;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/tools/pickaxe/pickaxe_wood", gp.tileSize, gp.tileSize);
		ground[1] = gp.ut.setup("/items/tools/pickaxe/pickaxe_wood", gp.tileSize, gp.tileSize);
		ground[2] = gp.ut.setup("/items/tools/pickaxe/pickaxe_wood", gp.tileSize, gp.tileSize);
		ground[3] = gp.ut.setup("/items/tools/pickaxe/pickaxe_wood", gp.tileSize, gp.tileSize);
		ground[4] = gp.ut.setup("/items/tools/pickaxe/pickaxe_wood", gp.tileSize, gp.tileSize);
		ground[5] = gp.ut.setup("/items/tools/pickaxe/pickaxe_wood", gp.tileSize, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/tools/pickaxe/pickaxe_wood", gp.tileSize, gp.tileSize);
	}
}
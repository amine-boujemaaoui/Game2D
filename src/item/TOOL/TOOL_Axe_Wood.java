package item.TOOL;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class TOOL_Axe_Wood extends Entity{

	public TOOL_Axe_Wood(GamePanel gp) {
		
		super(gp);
		
		name = "Wooden Axe";
		description = "Simple wooden Axe.";
		size = size1by1;
		type = gp.typeTOOL;
		subType = gp.subType_TOOL_AXE;
		
		attackValue = 4;
		toughnessValue = 1;
		maxDurability = 30;
		durability = maxDurability;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/tools/axe/axe_wood", gp.tileSize, gp.tileSize);
		ground[1] = gp.ut.setup("/items/tools/axe/axe_wood", gp.tileSize, gp.tileSize);
		ground[2] = gp.ut.setup("/items/tools/axe/axe_wood", gp.tileSize, gp.tileSize);
		ground[3] = gp.ut.setup("/items/tools/axe/axe_wood", gp.tileSize, gp.tileSize);
		ground[4] = gp.ut.setup("/items/tools/axe/axe_wood", gp.tileSize, gp.tileSize);
		ground[5] = gp.ut.setup("/items/tools/axe/axe_wood", gp.tileSize, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/tools/axe/axe_wood", gp.tileSize, gp.tileSize);
	}
}
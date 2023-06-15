package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{

	public OBJ_Chest(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "Chest";
		size = size1by1;
		type = gp.typeOBJ;
		subType = gp.subType_OBJ_CHEST;
		
		this.worldX = worldX;
		this.worldY = worldY;

		collision = true;
		
		hitBox.height = gp.tileSize - 16;
		
		ground    = new BufferedImage[2];
		ground[0] = gp.ut.setup("/objects/chest", gp.tileSize, gp.tileSize);
		ground[1] = gp.ut.setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
	}
}
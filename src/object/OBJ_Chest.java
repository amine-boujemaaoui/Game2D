package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{

	public OBJ_Chest(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "Chest";
		type = gp.typeObject;
		
		this.worldX = worldX;
		this.worldY = worldY;

		collision = true;
		
		down_still   = new BufferedImage[6];
		
		down_still[0] = setup("/objects/chest", gp.tileSize, gp.tileSize);
		down_still[1] = setup("/objects/chest", gp.tileSize, gp.tileSize);
		down_still[2] = setup("/objects/chest", gp.tileSize, gp.tileSize);
		down_still[3] = setup("/objects/chest", gp.tileSize, gp.tileSize);
		down_still[4] = setup("/objects/chest", gp.tileSize, gp.tileSize);
		down_still[5] = setup("/objects/chest", gp.tileSize, gp.tileSize);
	}
}
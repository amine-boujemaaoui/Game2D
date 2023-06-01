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
		
		this.worldX = worldX;
		this.worldY = worldY;

		collision = true;
		
		ground    = new BufferedImage[2];
		ground[0] = setup("/objects/chest", gp.tileSize, gp.tileSize);
		ground[1] = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
	}
}
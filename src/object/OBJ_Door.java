package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{

	public OBJ_Door(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "Door";
		type = gp.typeOBJ;
		size = size1by1;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		collision = true;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/objects/door_closed", gp.tileSize, gp.tileSize);
		ground[1] = gp.ut.setup("/objects/door_opened", gp.tileSize, gp.tileSize);
	}
}
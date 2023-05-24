package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{

	public OBJ_Door(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "Door";
		object = true;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		collision = true;
		
		down_still   = new BufferedImage[6];
		
		down_still[0] = setup("/objects/door_closed", gp.tileSize, gp.tileSize);
		down_still[1] = setup("/objects/door_closed", gp.tileSize, gp.tileSize);
		down_still[2] = setup("/objects/door_closed", gp.tileSize, gp.tileSize);
		down_still[3] = setup("/objects/door_closed", gp.tileSize, gp.tileSize);
		down_still[4] = setup("/objects/door_closed", gp.tileSize, gp.tileSize);
		down_still[5] = setup("/objects/door_closed", gp.tileSize, gp.tileSize);
	}
}
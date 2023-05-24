package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity{

	public OBJ_Boots(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "Boots";
		object = true;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		down_still   = new BufferedImage[6];
		
		down_still[0] = setup("/objects/boots", gp.tileSize, gp.tileSize);
		down_still[1] = setup("/objects/boots", gp.tileSize, gp.tileSize);
		down_still[2] = setup("/objects/boots", gp.tileSize, gp.tileSize);
		down_still[3] = setup("/objects/boots", gp.tileSize, gp.tileSize);
		down_still[4] = setup("/objects/boots", gp.tileSize, gp.tileSize);
		down_still[5] = setup("/objects/boots", gp.tileSize, gp.tileSize);
	}
}
package item;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class DRP_Heart extends Entity {
	public DRP_Heart(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "Heart";
		type = gp.typeObject;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		down_still   = new BufferedImage[6];
		
		down_still[0] = setup("/objects/heart", gp.tileSize, gp.tileSize);
		down_still[1] = setup("/objects/heart", gp.tileSize, gp.tileSize);
		down_still[2] = setup("/objects/heart", gp.tileSize, gp.tileSize);
		down_still[3] = setup("/objects/heart", gp.tileSize, gp.tileSize);
		down_still[4] = setup("/objects/heart", gp.tileSize, gp.tileSize);
		down_still[5] = setup("/objects/heart", gp.tileSize, gp.tileSize);
	}
}

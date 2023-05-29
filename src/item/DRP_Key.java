package item;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class DRP_Key extends Entity{

	public DRP_Key(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "Key";
		type = gp.typeNPC;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		hitBox.x = 9;
		hitBox.width = 30;
		
		hitBoxDefaultX = hitBox.x;
		
		down_still   = new BufferedImage[6];
		
		down_still[0] = setup("/objects/key/key_1", gp.tileSize, gp.tileSize*2);
		down_still[1] = setup("/objects/key/key_2", gp.tileSize, gp.tileSize*2);
		down_still[2] = setup("/objects/key/key_3", gp.tileSize, gp.tileSize*2);
		down_still[3] = setup("/objects/key/key_3", gp.tileSize, gp.tileSize*2);
		down_still[4] = setup("/objects/key/key_2", gp.tileSize, gp.tileSize*2);
		down_still[5] = setup("/objects/key/key_1", gp.tileSize, gp.tileSize*2);
	}
}

package item.ITM;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ITM_Key extends Entity{

	public ITM_Key(GamePanel gp) {
		
		super(gp);
		
		name = "Key";
		description = "Common key that can open a normal chest.";
		size = size1by2;
		type = gp.typeITM;
		subType = gp.subType_ITM_KEY;
		
		hitBox.x = 9;
		hitBox.width = 30;
		
		hitBoxDefaultX = hitBox.x;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/key/1", gp.tileSize/2, gp.tileSize);
		ground[1] = gp.ut.setup("/items/key/2", gp.tileSize/2, gp.tileSize);
		ground[2] = gp.ut.setup("/items/key/3", gp.tileSize/2, gp.tileSize);
		ground[3] = gp.ut.setup("/items/key/3", gp.tileSize/2, gp.tileSize);
		ground[4] = gp.ut.setup("/items/key/2", gp.tileSize/2, gp.tileSize);
		ground[5] = gp.ut.setup("/items/key/1", gp.tileSize/2, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/key/item", gp.tileSize, gp.tileSize);
	}
}

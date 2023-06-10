package item;

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
		
		hitBox.x = 9;
		hitBox.width = 30;
		
		hitBoxDefaultX = hitBox.x;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/key/1", gp.tileSize, gp.tileSize*2);
		ground[1] = gp.ut.setup("/items/key/2", gp.tileSize, gp.tileSize*2);
		ground[2] = gp.ut.setup("/items/key/3", gp.tileSize, gp.tileSize*2);
		ground[3] = gp.ut.setup("/items/key/3", gp.tileSize, gp.tileSize*2);
		ground[4] = gp.ut.setup("/items/key/2", gp.tileSize, gp.tileSize*2);
		ground[5] = gp.ut.setup("/items/key/1", gp.tileSize, gp.tileSize*2);
		
		item_icon = gp.ut.setup("/items/key/item", gp.tileSize, gp.tileSize);
	}
}

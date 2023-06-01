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
		ground[0] = setup("/objects/key/1", gp.tileSize, gp.tileSize*2);
		ground[1] = setup("/objects/key/2", gp.tileSize, gp.tileSize*2);
		ground[2] = setup("/objects/key/3", gp.tileSize, gp.tileSize*2);
		ground[3] = setup("/objects/key/3", gp.tileSize, gp.tileSize*2);
		ground[4] = setup("/objects/key/2", gp.tileSize, gp.tileSize*2);
		ground[5] = setup("/objects/key/1", gp.tileSize, gp.tileSize*2);
		
		item_icon = setup("/objects/key/item", gp.tileSize, gp.tileSize);
	}
}

package item.ITM;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ITM_Heart extends Entity {
	public ITM_Heart(GamePanel gp) {
		
		super(gp);
		
		name = "Heart";
		size = size1by1;
		type = gp.typeITM;
		
		ground    = new BufferedImage[6];	
		ground[0] = gp.ut.setup("/items/heart", gp.tileSize, gp.tileSize);
		ground[1] = gp.ut.setup("/items/heart", gp.tileSize, gp.tileSize);
		ground[2] = gp.ut.setup("/items/heart", gp.tileSize, gp.tileSize);
		ground[3] = gp.ut.setup("/items/heart", gp.tileSize, gp.tileSize);
		ground[4] = gp.ut.setup("/items/heart", gp.tileSize, gp.tileSize);
		ground[5] = gp.ut.setup("/items/heart", gp.tileSize, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/heart", gp.tileSize, gp.tileSize);
	}
}

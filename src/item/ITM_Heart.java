package item;

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
		ground[0] = setup("/objects/heart", gp.tileSize, gp.tileSize);
		ground[1] = setup("/objects/heart", gp.tileSize, gp.tileSize);
		ground[2] = setup("/objects/heart", gp.tileSize, gp.tileSize);
		ground[3] = setup("/objects/heart", gp.tileSize, gp.tileSize);
		ground[4] = setup("/objects/heart", gp.tileSize, gp.tileSize);
		ground[5] = setup("/objects/heart", gp.tileSize, gp.tileSize);
		
		item_icon = setup("/objects/heart", gp.tileSize, gp.tileSize);
	}
}

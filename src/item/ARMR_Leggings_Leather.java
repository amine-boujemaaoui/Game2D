package item;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ARMR_Leggings_Leather extends Entity{

	public ARMR_Leggings_Leather(GamePanel gp) {
		
		super(gp);
		
		name = "Leather Leggings";
		type = gp.typeObject;
		
		defenseValue = 1;
		
		down_still   = new BufferedImage[6];
		down_still[0] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
		down_still[1] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
		down_still[2] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
		down_still[3] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
		down_still[4] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
		down_still[5] = setup("/objects/leggings/leggings_leather", gp.tileSize, gp.tileSize);
	}
}
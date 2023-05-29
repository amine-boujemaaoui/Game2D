package item;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ARMR_Boots_Leather extends Entity{

	public ARMR_Boots_Leather(GamePanel gp) {
		
		super(gp);
		
		name = "Leather Boots";
		type = gp.typeObject;
	
		speedValue = 1.5;
		
		down_still   = new BufferedImage[6];
		down_still[0] = setup("/objects/boots/boots_leather", gp.tileSize, gp.tileSize);
		down_still[1] = setup("/objects/boots/boots_leather", gp.tileSize, gp.tileSize);
		down_still[2] = setup("/objects/boots/boots_leather", gp.tileSize, gp.tileSize);
		down_still[3] = setup("/objects/boots/boots_leather", gp.tileSize, gp.tileSize);
		down_still[4] = setup("/objects/boots/boots_leather", gp.tileSize, gp.tileSize);
		down_still[5] = setup("/objects/boots/boots_leather", gp.tileSize, gp.tileSize);
	}
}
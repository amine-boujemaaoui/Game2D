package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity{

	public OBJ_Shield(GamePanel gp) {
		
		super(gp);
		
		name = "Shield";
		type = gp.typeObject;
		defenseValue = 1;
		
		down_still   = new BufferedImage[1];
		down_still[0] = setup("/objects/shield", gp.tileSize, gp.tileSize);
	}
}
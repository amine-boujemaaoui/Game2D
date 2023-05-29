package item;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class WPN_Sword_Wood extends Entity{

	public WPN_Sword_Wood(GamePanel gp) {
		
		super(gp);
		
		name = "Wooden Sword";
		type = gp.typeObject;
		attackValue = 4;
		attackSpeedValue = 5;
		
		down_still   = new BufferedImage[1];
		down_still[0] = setup("/objects/sword/sword_wood", gp.tileSize, gp.tileSize);
	}
}
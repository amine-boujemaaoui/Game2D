package item;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class WPN_Shield_Wood extends Entity{

	public WPN_Shield_Wood(GamePanel gp) {
		
		super(gp);
		
		name = "Wooden Shield";
		type = gp.typeObject;
		defenseValue = 1;
		
		down_still   = new BufferedImage[1];
		down_still[0] = setup("/objects/shield/shield_wood", gp.tileSize, gp.tileSize);
	}
}
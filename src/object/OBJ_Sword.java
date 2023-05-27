package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity{

	public OBJ_Sword(GamePanel gp) {
		
		super(gp);
		
		name = "Sword";
		type = gp.typeObject;
		attackValue = 1;
		
		down_still   = new BufferedImage[1];
		down_still[0] = setup("/objects/sword", gp.tileSize, gp.tileSize);
	}
}
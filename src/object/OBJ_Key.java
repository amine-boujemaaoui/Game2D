package object;

import java.awt.image.BufferedImage;

import main.GamePanel;

public class OBJ_Key extends OBJ{
	
	BufferedImage sprite1, sprite2, sprite3;

	public OBJ_Key(GamePanel gp, int worldX, int worldY) {
		
		super(gp, "Key", worldX, worldY, "key_shadow", "key");
		
		hitBox.x = 9;
		hitBox.width = 30;
		
		hitBoxDefaultX = hitBox.x;
	}
}

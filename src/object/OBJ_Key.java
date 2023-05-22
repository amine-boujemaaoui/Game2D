package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Key extends OBJ{

	public OBJ_Key(int worldX, int worldY) {
		
		name = "Key";
		this.worldX = worldX;
		this.worldY = worldY;
		
		hitBox.x = 9;
		hitBox.width = 30;
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		try { 
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key_shadow.png"));
			imageWithoutShadow = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
		} 
		catch (IOException e) { e.printStackTrace(); }
	}
}

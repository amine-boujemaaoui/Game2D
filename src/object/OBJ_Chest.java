package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Chest extends OBJ{

	public OBJ_Chest(int worldX, int worldY) {
		
		name = "Key";
		this.worldX = worldX;
		this.worldY = worldY;
		collision = true;
		
		try { image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png")); } 
		catch (IOException e) { e.printStackTrace(); }
	}
}
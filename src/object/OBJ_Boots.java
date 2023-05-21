package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Boots extends OBJ{

	public OBJ_Boots(int worldX, int worldY) {
		
		name = "Key";
		this.worldX = worldX;
		this.worldY = worldY;
		
		
		try { image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png")); } 
		catch (IOException e) { e.printStackTrace(); }
	}
}
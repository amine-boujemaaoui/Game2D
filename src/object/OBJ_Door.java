package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Door extends OBJ{

	public OBJ_Door(int worldX, int worldY) {
		
		name = "Door";
		collision = true;
		this.worldX = worldX;
		this.worldY = worldY;
		
		try { image = ImageIO.read(getClass().getResourceAsStream("/objects/door_closed.png")); } 
		catch (IOException e) { e.printStackTrace(); }
	}
}
package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_BigTree extends OBJ{

	public OBJ_BigTree(int worldX, int worldY) {
		
		name = "Big Tree";
		bigObject = true;
		collision = true;
		this.worldX = worldX;
		this.worldY = worldY;
		
		hitBox.x     = -7; hitBox.y     = - 48*2; 
		hitBox.width = 62; hitBox.height = (int)(48*2.2);
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		try { image = ImageIO.read(getClass().getResourceAsStream("/objects/big_tree.png")); } 
		catch (IOException e) { e.printStackTrace(); }
	}
}
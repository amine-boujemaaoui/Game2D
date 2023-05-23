package object;

import main.GamePanel;

public class OBJ_BigTree extends OBJ{

	public OBJ_BigTree(GamePanel gp, int worldX, int worldY) {
		
		super(gp, "Big Tree", worldX, worldY, "big_tree", null);
		bigObject = true;
		collision = true;
		
		hitBox.x     = -7; hitBox.y     = - 48*2; 
		hitBox.width = 62; hitBox.height = (int)(48*2.2);
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
	}
}
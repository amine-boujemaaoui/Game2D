package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class OBJ_BigTree extends Entity{

	public OBJ_BigTree(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "Big tree";
		bigObject = true;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		collision = true;
		
		hitBox.x     = -7; hitBox.y     = - 24; 
		hitBox.width = 62; hitBox.height = (int)(32);
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		down_still   = new BufferedImage[6];
		
		down_still[0] = setup("/objects/big_tree", gp.tileSize*3, gp.tileSize*5);
		down_still[1] = setup("/objects/big_tree", gp.tileSize*3, gp.tileSize*5);
		down_still[2] = setup("/objects/big_tree", gp.tileSize*3, gp.tileSize*5);
		down_still[3] = setup("/objects/big_tree", gp.tileSize*3, gp.tileSize*5);
		down_still[4] = setup("/objects/big_tree", gp.tileSize*3, gp.tileSize*5);
		down_still[5] = setup("/objects/big_tree", gp.tileSize*3, gp.tileSize*5);
	}
}
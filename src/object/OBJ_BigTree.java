package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class OBJ_BigTree extends Entity{

	public OBJ_BigTree(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "Big tree";
		size = size3by4;
		type = gp.typeOBJ;
		
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		collision = true;
		
		hitBox.x     = -7; hitBox.y     = - 24; 
		hitBox.width = 62; hitBox.height = (int)(32);
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		ground    = new BufferedImage[1];
		ground[0] = gp.ut.setup("/objects/big_tree", gp.tileSize*3, gp.tileSize*4);
	}
}
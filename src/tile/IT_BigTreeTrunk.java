package tile;

import java.awt.image.BufferedImage;

import main.GamePanel;

public class IT_BigTreeTrunk extends InteractiveTile{

	public IT_BigTreeTrunk(GamePanel gp, int worldX, int worldY) {
		
		super(gp);
		
		name = "Big tree trunk";
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
		ground[0] = gp.ut.setup("/objects/big_tree_trunk", gp.tileSize*3, gp.tileSize*4);
	}
}
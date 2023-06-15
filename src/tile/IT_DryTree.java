package tile;

import java.awt.Color;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile {

	public IT_DryTree(GamePanel gp, int worldX, int worldY) {
		super(gp);
		
		name = "Dry Tree";
		size = size3by4;
		type = gp.typeOBJ;
		
		health = 12;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		afterDestroy = new IT_BigTreeTrunk(gp, worldX, worldY);
		
		collision = true;
		destructible = true;
		
		hitBox.x     = -7; hitBox.y     = - 24; 
		hitBox.width = 62; hitBox.height = (int)(32);
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		ground    = new BufferedImage[1];
		ground[0] = gp.ut.setup("/objects/big_tree_dry", gp.tileSize*3, gp.tileSize*4);
		
		// particleColor = new Color(117, 83, 56);
		particleColor = new Color(255, 0, 0);
		particleSize = 6;
		particleSpeed = 3;
		particleMaxHealth = 10;

		particleOffsetX = gp.tileSize/2 - particleSize/2;
	}
	public boolean correctToolUsed(Entity entity) {
		
		boolean correctToolUsed = false;
		
		if(entity != null && entity.type == gp.typeTOOL && entity.subType == gp.subType_TOOL_AXE) correctToolUsed = true;
		
		return correctToolUsed;
	}
}

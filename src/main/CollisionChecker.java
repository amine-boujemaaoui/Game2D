package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		
		this.gp = gp;
	}
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX   = entity.worldX + entity.hitBox.x;
		int entityTopWorldY    = entity.worldY + entity.hitBox.y;
		int entityRightWorldX  = entity.worldX + entity.hitBox.x + entity.hitBox.width;
		int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;
		
		int entityLeftCol   = entityLeftWorldX / gp.tileSize;
		int entityRightCol  = entityRightWorldX / gp.tileSize;
		int entityTopRow    = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
		
		int tileNum1 = 0, tileNum2 = 0;
		switch(entity.direction) {
		case "up":    
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize; 
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize; 
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize; 
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize; 
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			break;
		}
		if(gp.tileM.tiles[tileNum1].collision == true ||
		   gp.tileM.tiles[tileNum2].collision == true) 
	       entity.collisionOn = true;
		
	}
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		for (int i = 0; i < gp.obj.length; i++)
			if(gp.obj[i] != null) {
				// GETTING ENTITY X AND Y POSTION OF HITBOX
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				// GETTING obj[i] POSITION
				gp.obj[i].hitBox.x = gp.obj[i].worldX + gp.obj[i].hitBox.x;
				gp.obj[i].hitBox.y = gp.obj[i].worldY + gp.obj[i].hitBox.y;
				
				switch(entity.direction) {
				case "up":    entity.hitBox.y -= entity.speed; break;
				case "down":  entity.hitBox.y += entity.speed; break;
				case "left":  entity.hitBox.x -= entity.speed; break;
				case "right": entity.hitBox.x += entity.speed; break;
				}
				if(entity.hitBox.intersects(gp.obj[i].hitBox)) {
					if(gp.obj[i].collision) entity.collisionOn = true;
					if(player == true) index = i;
				}
				entity.hitBox.x = entity.hitBoxDefaultX;
				entity.hitBox.y = entity.hitBoxDefaultY;
				gp.obj[i].hitBox.x = gp.obj[i].hitBoxDefaultX;
				gp.obj[i].hitBox.y = gp.obj[i].hitBoxDefaultY;
			}
		return index;		
	}
}

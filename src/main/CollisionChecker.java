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
		
		if(entityLeftCol   < 0) entityLeftCol   = 0;
		if(entityRightCol  < 0) entityRightCol  = 0;
		if(entityTopRow    < 0) entityTopRow    = 0;
		if(entityBottomRow < 0) entityBottomRow = 0;
		
		int testSpeed = 0;
		if(entity == gp.player) testSpeed = gp.player.actualSpeed;
		else testSpeed = entity.speed;
		
		int tileNum1 = 0, tileNum2 = 0;

		switch(entity.direction) {
		case "up":    
			entityTopRow = (entityTopWorldY - testSpeed) / gp.tileSize;
			if(entityTopRow < 0) entityTopRow = 0;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + testSpeed) / gp.tileSize;
			if(entityBottomRow < 0) entityBottomRow = 0;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - testSpeed) / gp.tileSize;
			if(entityLeftCol < 0) entityLeftCol = 0;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			break;
		case "right":
			entityRightCol = (entityRightWorldX + testSpeed) / gp.tileSize;
			if(entityRightCol < 0) entityRightCol = 0;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
			break;
		}
		if(gp.tileM.tiles[tileNum1].collision == true ||
		   gp.tileM.tiles[tileNum2].collision == true) 
			   entity.collisionOn = true;
	}
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		int testSpeed = 0;
		if(player) testSpeed = gp.player.actualSpeed;
		else testSpeed = entity.speed;
		
		for (int i = 0; i < gp.obj[gp.currentMap].length; i++)
			if(gp.obj[gp.currentMap][i] != null) {

				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;

				gp.obj[gp.currentMap][i].hitBox.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].hitBox.x;
				gp.obj[gp.currentMap][i].hitBox.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].hitBox.y;

				switch(entity.direction) {
				case "up":    entity.hitBox.y -= testSpeed; break;
				case "down":  entity.hitBox.y += testSpeed; break;
				case "left":  entity.hitBox.x -= testSpeed; break;
				case "right": entity.hitBox.x += testSpeed; break;
				}
				if(entity.hitBox.intersects(gp.obj[gp.currentMap][i].hitBox)) {
					if(gp.obj[gp.currentMap][i].collision) entity.collisionOn = true;
					//if(player == true) index = i;
					index = i;
				}
				entity.hitBox.x = entity.hitBoxDefaultX;
				entity.hitBox.y = entity.hitBoxDefaultY;
				gp.obj[gp.currentMap][i].hitBox.x = gp.obj[gp.currentMap][i].hitBoxDefaultX;
				gp.obj[gp.currentMap][i].hitBox.y = gp.obj[gp.currentMap][i].hitBoxDefaultY;
			}

		return index;
	}
	public int checkEntity(Entity entity, Entity[] targets) {

		int index = 999;

		int testSpeed = 0;
		if(entity == gp.player) testSpeed = gp.player.actualSpeed;
		else testSpeed = entity.speed;

		for (int i = 0; i < targets.length; i++)
			if(targets[i] != null) {


				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;

				targets[i].hitBox.x = targets[i].worldX + targets[i].hitBox.x;
				targets[i].hitBox.y = targets[i].worldY + targets[i].hitBox.y;

				switch(entity.direction) {
				case "up":    entity.hitBox.y -= testSpeed; break;
				case "down":  entity.hitBox.y += testSpeed; break;
				case "left":  entity.hitBox.x -= testSpeed; break;
				case "right": entity.hitBox.x += testSpeed; break;
				}

				if(entity.hitBox.intersects(targets[i].hitBox)) {
					if(targets[i] != entity) { entity.collisionOn = true; index = i; }
				}

				entity.hitBox.x = entity.hitBoxDefaultX;
				entity.hitBox.y = entity.hitBoxDefaultY;

				targets[i].hitBox.x = targets[i].hitBoxDefaultX;
				targets[i].hitBox.y = targets[i].hitBoxDefaultY;
			}

		return index;
	}
	public boolean checkPlayer(Entity entity) {

		boolean contactPlayer = false;

		entity.hitBox.x = entity.worldX + entity.hitBox.x;
		entity.hitBox.y = entity.worldY + entity.hitBox.y;

		gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
		gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;

		switch(entity.direction) {
		case "up":    entity.hitBox.y -= entity.speed; break;
		case "down":  entity.hitBox.y += entity.speed; break;
		case "left":  entity.hitBox.x -= entity.speed; break;
		case "right": entity.hitBox.x += entity.speed; break;
		}

		if(entity.hitBox.intersects(gp.player.hitBox)) {
			entity.collisionOn = true;
			contactPlayer = true;
		}

		entity.hitBox.x = entity.hitBoxDefaultX;
		entity.hitBox.y = entity.hitBoxDefaultY;

		gp.player.hitBox.x = gp.player.hitBoxDefaultX;
		gp.player.hitBox.y = gp.player.hitBoxDefaultY;

		return contactPlayer;
	}
	public int checkItem(Entity entity, boolean player) {

		int index = 999;

		int testSpeed = 0;
		if(player) testSpeed = gp.player.actualSpeed;
		else testSpeed = entity.speed;

		for (int i = 0; i < gp.itm[gp.currentMap].length; i++)
			if(gp.itm[gp.currentMap][i] != null) {

				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;

				gp.itm[gp.currentMap][i].hitBox.x = gp.itm[gp.currentMap][i].worldX + gp.itm[gp.currentMap][i].hitBox.x;
				gp.itm[gp.currentMap][i].hitBox.y = gp.itm[gp.currentMap][i].worldY + gp.itm[gp.currentMap][i].hitBox.y;

				switch(entity.direction) {
				case "up":    entity.hitBox.y -= testSpeed; break;
				case "down":  entity.hitBox.y += testSpeed; break;
				case "left":  entity.hitBox.x -= testSpeed; break;
				case "right": entity.hitBox.x += testSpeed; break;
				}
				if(entity.hitBox.intersects(gp.itm[gp.currentMap][i].hitBox)) {
					if(gp.itm[gp.currentMap][i].collision) entity.collisionOn = true;
					if(player == true) index = i;
				}
				entity.hitBox.x = entity.hitBoxDefaultX;
				entity.hitBox.y = entity.hitBoxDefaultY;
				gp.itm[gp.currentMap][i].hitBox.x = gp.itm[gp.currentMap][i].hitBoxDefaultX;
				gp.itm[gp.currentMap][i].hitBox.y = gp.itm[gp.currentMap][i].hitBoxDefaultY;
			}
		
		return index;
	}
}

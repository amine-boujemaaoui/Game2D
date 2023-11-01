package main;

import java.awt.*;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][][];
	int previousEventX, previousEventY;
	public boolean canInitiateEvent = true;
	int tempMap, tempCol, tempRow;
	
	public EventHandler(GamePanel gp) {
		
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxMaps][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0, col = 0, row = 0;
		while(map < gp.maxMaps && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			eventRect[map][col][row] = new EventRect();
			
			eventRect[map][col][row].x     = 23;         eventRect[map][col][row].y      = 23;
			eventRect[map][col][row].width = 2;          eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			col++; if(col == gp.maxWorldCol) {
				col = 0; row++;
				if(row == gp.maxWorldRow) { row = 0; map++; }
			}
		}
	}
	public void checkEvent() {
		
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		
		if(distance > gp.tileSize) canInitiateEvent = true;
		
		if(canInitiateEvent) {
			if(hit(0, 29, 14, "up"))  {
				healingPool	(gp.dialogueState);
				gp.ui.showMessage("E", "to heal yourself!", 29*gp.tileSize, 14*gp.tileSize);
			}

			if(hit(0, 29, 30, "any")) {
				fallPit(gp.dialogueState);
				gp.ui.showMessage("", "FALLPIT!", 29*gp.tileSize, 30*gp.tileSize);
			} else { eventRect[0][29][30].eventDone = false; }

			if(hit(0, 22, 20, "any")) {
				fallPit(gp.dialogueState);
			} else { eventRect[0][29][30].eventDone = false; }

			if(hit(0, 16, 42, "up")) {
				teleport(1, 25, 27);
			} else { eventRect[0][16][42].eventDone = false; }

			if(hit(1, 25, 27, "down")) {
				teleport(0, 16, 42);
			} else { eventRect[1][25][27].eventDone = false; }
		}
	}
	public boolean hit(int mapNum, int col, int row, String reqDirection) {
		
		boolean hit = false;

		if (mapNum == gp.currentMap) {
			gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
			gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;
			eventRect[mapNum][col][row].x = col*gp.tileSize + eventRect[mapNum][col][row].x;
			eventRect[mapNum][col][row].y = row*gp.tileSize + eventRect[mapNum][col][row].y;

			if(gp.player.hitBox.intersects(eventRect[mapNum][col][row]) && !eventRect[mapNum][col][row].eventDone) {
				if(gp.player.direction.equals(reqDirection) || reqDirection.contentEquals("any")) {
					hit = true;
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}

			gp.player.hitBox.x = gp.player.hitBoxDefaultX;
			gp.player.hitBox.y = gp.player.hitBoxDefaultY;
			eventRect[mapNum][col][row].x = eventRect[mapNum][col][row].eventRectDefaultX;
			eventRect[mapNum][col][row].y = eventRect[mapNum][col][row].eventRectDefaultY;
		}

		return hit;
	}
	public void healingPool(int gameState) {
		
		if(gp.keyH.eventPressed) {
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.ui.currentDialogue = "You healed from drinking!";
			gp.player.health = gp.player.maxHealth;
			gp.player.stamina = gp.player.maxStamina;
			gp.aSetter.setMON();
		}
	}
	public void fallPit(int gameState) {
		if(gp.player.health > 0 && gp.player.running) {
			gp.gameState = gameState;
			gp.playSE(15);
			gp.ui.currentDialogue = "You fell into a pit!";
			gp.player.health--;
			canInitiateEvent = false;
		}
	}
	public void teleport(int mapNum, int col, int row) {

		gp.gameState = gp.transitionState;
		tempCol = col;
		tempRow = row;
		tempMap = mapNum;


	}
}

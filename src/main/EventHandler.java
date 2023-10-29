package main;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][];
	int previousEventX, previousEventY;
	public boolean canInitiateEvent = true;
	
	public EventHandler(GamePanel gp) {
		
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0, row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			eventRect[col][row] = new EventRect();
			
			eventRect[col][row].x     = 23;         eventRect[col][row].y      = 23;
			eventRect[col][row].width = 2;          eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			
			col++; if(col == gp.maxWorldCol) { col = 0; row++;}
		}
		
	}
	public void checkEvent() {
		
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		
		if(distance > gp.tileSize) canInitiateEvent = true;
		
		if(canInitiateEvent) {
			if(hit(29, 14, "up"))  { healingPool(29, 14, gp.dialogueState); gp.ui.showMessage("E", "to heal yourself!", 29*gp.tileSize, 14*gp.tileSize); }
			if(hit(29, 30, "any")) { fallPit(29, 30, gp.dialogueState); gp.ui.showMessage("", "FALLPIT!", 29*gp.tileSize, 30*gp.tileSize);} else { eventRect[29][30].eventDone = false; }
			if(hit(22, 20, "any")) { fallPit(29, 30, gp.dialogueState); } else { eventRect[29][30].eventDone = false; }
		}
	}
	public boolean hit(int col, int row, String reqDirection) {
		
		boolean hit = false;
		
		gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
		gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;
		eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;
		
		if(gp.player.hitBox.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
			if(gp.player.direction.equals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
				previousEventX = gp.player.worldX; 
				previousEventY = gp.player.worldY; 
			}
		}
		
		gp.player.hitBox.x = gp.player.hitBoxDefaultX;
		gp.player.hitBox.y = gp.player.hitBoxDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}
	public void healingPool(int col, int row,int gameState) {
		
		if(gp.keyH.eventPressed) {
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.ui.currentDialogue = "You healed from drinking!";
			gp.player.health = gp.player.maxHealth;
			gp.player.stamina = gp.player.maxStamina;
			gp.aSetter.setMON();
		}
	}
	public void fallPit(int col, int row,int gameState) {
		if(gp.player.health > 0 && gp.player.running) {
			gp.gameState = gameState;
			gp.playSE(15);
			gp.ui.currentDialogue = "You fell into a pit!";
			gp.player.health--;
			canInitiateEvent = false;
		}
	}
}

package tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tiles;
	public int mapTileNum[][][];
	public Tile[][] mapTiles;
	boolean drwPath = false;
	Color pathColor = new Color(255, 0, 255, 20);
	
	public TileManager(GamePanel gp) {

		this.gp = gp;
		tiles = new Tile[150]; // 10 for the number of different type of tiles
		mapTileNum = new int[gp.maxMaps][250][250];
		getTileImage();
		loadMap("/maps/world01.txt",0, 100);
		loadMap("/maps/house01.txt",1, 50);
	}
	public void loadMap(String mapPath, int mapNum, int mapSize) {

		if (gp.currentMap == mapNum) {
			gp.maxWorldCol = mapSize;
			gp.maxWorldRow = mapSize;
		}

		try {
			InputStream is = getClass().getResourceAsStream(mapPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0, row = 0;
			while(col < mapSize && row < mapSize) {

				String numbers[] = br.readLine().split(" ");
				
				while (col < mapSize) {
					
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[mapNum][col][row] = num; col++;
				}
				if (col == mapSize) { col = 0; row++; }
			}
			br.close();
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	public void getTileImage() {
		
		for (int i = 0;   i < 10;  i++) setup(i, "/tiles/00"+ i, false, false);
		for (int i = 10;  i < 100; i++) setup(i, "/tiles/0" + i, false, false);
		for (int i = 100; i < 135; i++) setup(i, "/tiles/"  + i, false, false);
		
		// COLLISIONS
		for (int i = 18; i < 32; i++)
			tiles[i].collision  = true; // WATER
		tiles[32].collision = true; 	// WALL
		tiles[33].collision = true; 	// HOUSE
        tiles[35].collision = true; 	// TABLE
		for (int i = 103; i < 135; i++)
			tiles[i].collision  = true; // NEW WALL
	}
	public void setup(int index, String imagePath, boolean collision, boolean tall) {
			
			tiles[index] = new Tile(); 
			if(tall)
				tiles[index].image = gp.ut.setup(imagePath, gp.tileSize, gp.tileSize*2);
			else
				tiles[index].image = gp.ut.setup(imagePath, gp.tileSize, gp.tileSize);
			tiles[index].collision = collision;
			tiles[index].tall = tall;
	}
	public void draw(Graphics2D g2) {
		
		int worldCol = 0, worldRow = 0;
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int worldX =  worldCol * gp.tileSize;
			int worldY =  worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if (worldX + gp.tileSize*2 > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize*2 < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize*2 > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize*2 < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tiles[mapTileNum[gp.currentMap][worldCol][worldRow]].image, screenX, screenY, null);
				
				// DEBUG: PRINT TILE SIZE
				if(gp.keyH.debug) {
					g2.setStroke(new BasicStroke(2));
					g2.setColor(Color.black);
					g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
					g2.setStroke(new BasicStroke(1));
					// DEBUG: PRINT TILE HITBOX 
					if(tiles[mapTileNum[gp.currentMap][worldCol][worldRow]].collision) {
						g2.setColor(new Color(255, 0, 0, 90));
						g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
					}

					g2.setColor(Color.black);
					g2.setFont(gp.ui.debugFont.deriveFont(10f));

					// DEBUG: PRINT TILE NUM
					g2.drawString("num: "+mapTileNum[gp.currentMap][worldCol][worldRow], screenX+5, screenY+15);

					// DEBUG: PRINT TILE COORDINATES
					g2.drawString("x: "+worldCol, screenX+5, screenY+30);
					g2.drawString("y: "+worldRow, screenX+5, screenY+45);

					g2.setFont(gp.ui.pixelFont);
				}

				// DEBUG: PRINT PATH
				if (drwPath) {
					// DEBUG: PRINT ENTITY PATH
					g2.setColor(pathColor);
					for (int i = 0; i < gp.pathF.pathList.size(); i++) {

						worldX  = gp.pathF.pathList.get(i).col * gp.tileSize;
						worldY  = gp.pathF.pathList.get(i).row * gp.tileSize;
						screenX = worldX - gp.player.worldX + gp.player.screenX;
						screenY = worldY - gp.player.worldY + gp.player.screenY;

						g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
					}

					// DEBUG: PRINT PATH NODES
					g2.setColor(Color.red);
					for (int i = 0; i < gp.pathF.openList.size(); i++) {

						worldX  = gp.pathF.openList.get(i).col * gp.tileSize;
						worldY  = gp.pathF.openList.get(i).row * gp.tileSize;
						screenX = worldX - gp.player.worldX + gp.player.screenX;
						screenY = worldY - gp.player.worldY + gp.player.screenY;

						g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
					}
				}
			}
			
			worldCol++;
			if (worldCol == gp.maxWorldCol) { worldCol = 0; worldRow++; }
		}
	}
}

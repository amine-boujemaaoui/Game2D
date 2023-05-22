package tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tiles;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {

		this.gp = gp;
		tiles = new Tile[50]; // 10 for the number of different type of tiles
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/map.txt");
	}
	public void loadMap(String mapPath) {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(mapPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0, row = 0;
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

				String numbers[] = br.readLine().split(" ");
				
				while (col < gp.maxWorldCol) {
					
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num; col++;
				}
				if (col == gp.maxWorldCol) { col = 0; row++; }
			}
			br.close();
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	public void getTileImage() {
		
		try {
			for (int i = 0; i < 10; i++)  { tiles[i] = new Tile(); tiles[i].image = ImageIO.read(getClass().getResourceAsStream("/tiles/00"+ i +".png")); }
			for (int i = 10; i < 38; i++) { tiles[i] = new Tile(); tiles[i].image = ImageIO.read(getClass().getResourceAsStream("/tiles/0" + i +".png")); }
			
			// COLLISIONS
			for (int i = 18; i < 32; i++) 
			tiles[i].collision  = true; // WATER
            tiles[32].collision = true; // WALL
            tiles[35].collision = true; // TABLE
            tiles[16].collision = true; // TREE
			
		} catch (IOException e) { e.printStackTrace(); }
	}
	public void draw(Graphics2D g2) {
		
		int worldCol = 0, worldRow = 0;
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			// pos on map * tile size - pos of player on world - offset of screen
			int worldX =  worldCol * gp.tileSize;
			int worldY =  worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tiles[mapTileNum[worldCol][worldRow]].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
				
				// DEBUG: PRINT TILE SIZE
				if(gp.keyH.debug) {
					g2.setStroke(new BasicStroke(2));
					g2.setColor(Color.black);
					g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
					g2.setStroke(new BasicStroke(1));
					// DEBUG: PRINT TILE HITBOX 
					if(tiles[mapTileNum[worldCol][worldRow]].collision) {
						g2.setColor(new Color(255, 0, 0, 90));
						g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
					}
				}
			}
			
			worldCol++;
			if (worldCol == gp.maxWorldCol) { worldCol = 0; worldRow++;}
		}
	}
}

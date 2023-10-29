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
	public Tile[][] mapTiles;
	
	public TileManager(GamePanel gp) {

		this.gp = gp;
		tiles = new Tile[150]; // 10 for the number of different type of tiles
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
		
		for (int i = 0;   i < 10;  i++) setup(i, "/tiles/00"+ i, false, false);
		for (int i = 10;  i < 100; i++) setup(i, "/tiles/0" + i, false, false);
		for (int i = 100; i < 135; i++) setup(i, "/tiles/"  + i, false, false);
		
		// COLLISIONS
		for (int i = 18; i < 32; i++) 
		tiles[i].collision  = true; // WATER
        tiles[32].collision = true; // WALL
        tiles[35].collision = true; // TABLE
        //tiles[16].collision = true; // TREE
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
				g2.drawImage(tiles[mapTileNum[worldCol][worldRow]].image, screenX, screenY, null);
				
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
			if (worldCol == gp.maxWorldCol) { worldCol = 0; worldRow++; }
		}
	}
}

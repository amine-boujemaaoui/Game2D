package tile;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	Tile[] tiles;
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {

		this.gp = gp;
		tiles = new Tile[38]; // 10 for the number of different type of tiles
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/worldmap.txt");
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
		} catch (IOException e) { e.printStackTrace(); }
	}
	public void draw(Graphics2D g2) {
		
		int worldCol = 0, worldRow = 0;
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			// pos on map * tile size - pos of player on world - offset of screen
			int worldX =  worldCol * gp.tileSize;
			int worldY =  worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldRow * gp.tileSize - gp.player.worldY + gp.player.screenY;
			
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
				g2.drawImage(tiles[mapTileNum[worldCol][worldRow]].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			
			worldCol++;
			if (worldCol == gp.maxWorldCol) { worldCol = 0; worldRow++;}
		}
	}
}

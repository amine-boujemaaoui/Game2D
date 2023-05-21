package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	Tile[] tiles;
	
	public TileManager(GamePanel gp) {

		this.gp = gp;
		tiles = new Tile[10]; // 10 for the number of different type of tiles
		getTileImage();
	}
	public void getTileImage() {
		
		try {
			tiles[0] = new Tile(); tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cobblestone_wall.png"));
			tiles[1] = new Tile(); tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
		} catch (IOException e) { e.printStackTrace(); }
	}
	public void draw(Graphics2D g2) {
		
		g2.drawImage(tiles[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
		g2.drawImage(tiles[1].image, gp.tileSize, gp.tileSize, gp.tileSize, gp.tileSize, null);
		g2.drawImage(tiles[0].image, gp.tileSize, 0, gp.tileSize, gp.tileSize, null);
		g2.drawImage(tiles[1].image, 0, gp.tileSize, gp.tileSize, gp.tileSize, null);
	}
}

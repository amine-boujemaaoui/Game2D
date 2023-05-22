package object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class OBJ {
	
	public BufferedImage image, imageWithoutShadow;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
	public int hitBoxDefaultX = 0, hitBoxDefaultY = 0;
	public boolean bigObject = false;
	
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(bigObject)
			g2.drawImage(image, screenX - gp.tileSize, screenY - gp.tileSize*3, gp.tileSize*3, gp.tileSize*4, null);
		
		else if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			     worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			     worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			     worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			
			// DEBUG: PRINT TILE OBJ
			if(gp.keyH.debug) {
				g2.setColor(new Color(0, 255, 0, 100));
				g2.setStroke(new BasicStroke(1));
				g2.fillRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);
				g2.setColor(new Color(200, 200, 0));
				g2.setStroke(new BasicStroke(2));
				g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
			}
		}
	}
}

package object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ extends Entity{
	
	public BufferedImage image, uiIcon;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
	public int hitBoxDefaultX = 0, hitBoxDefaultY = 0;
	public boolean bigObject = false;
	public boolean mediumObject = false;
	
	protected OBJ(GamePanel gp, String name, int worldX, int worldY, String imageName, String uiIconName) {
		
		super(gp);
		
		this.name = name;
		this.worldX = worldX;
		this.worldY = worldY;
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		try {
			if(imageName != null) {
				image       = ImageIO.read(getClass().getResourceAsStream("/objects/" + imageName + ".png"));
				gp.ut.scaleImage(image, gp.tileSize,  gp.tileSize);
			}
			if(uiIconName != null) {
				uiIcon = ImageIO.read(getClass().getResourceAsStream("/objects/" + uiIconName + ".png"));
				gp.ut.scaleImage(uiIcon, gp.tileSize,  gp.tileSize);
			}
		} 
		catch (IOException e) { e.printStackTrace(); }
	}	
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(bigObject)
			g2.drawImage(image, screenX - gp.tileSize, screenY - gp.tileSize*3, gp.tileSize*3, gp.tileSize*4, null);
		else if(mediumObject)
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

package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {

	GamePanel gp;
	Font arial_20;
	Color shadow = new Color(0, 0, 0, 150);
	BufferedImage keyImage, ui_dialogue;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public UI(GamePanel gp) {
		
		 this.gp = gp;
		 arial_20 = new Font("Arial", Font.BOLD, 20);
		 keyImage = new OBJ_Key(0, 0).imageWithoutShadow;
	}
	public void showMessage(String message) {
		this.message = message; messageOn = true;
	}
	public void draw(Graphics2D g2) {
		
		g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
		g2.setFont(arial_20);
		g2.setColor(shadow);      g2.drawString("X" + gp.player.keyAmount, gp.tileSize + 4,     gp.tileSize + 24);
		g2.setColor(Color.white); g2.drawString("X" + gp.player.keyAmount, gp.tileSize + 4 - 1, gp.tileSize + 24 - 2);
		
		if(messageOn) {
			g2.setFont(g2.getFont().deriveFont(20f));
			g2.setColor(shadow);      g2.drawString(message, gp.player.screenX + gp.tileSize,     gp.player.screenY + gp.tileSize);
			g2.setColor(Color.white); g2.drawString(message, gp.player.screenX + gp.tileSize - 1, gp.player.screenY + gp.tileSize - 2);
			
			messageCounter++;
			if(messageCounter > 120) { messageCounter = 0; messageOn = false; }
		}
	}
}

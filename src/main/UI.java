package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font arial_20;
	Color shadow = new Color(0, 0, 0, 200);
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	BufferedImage dialogueWindow;
	public String currentDialogue = "";
	
	public UI(GamePanel gp) {
		
		 this.gp = gp;
		 arial_20 = new Font("Arial", Font.BOLD, 20);
	}
	public void showMessage(String message) {
		this.message = message; messageOn = true;
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;

		if(gp.gameState == gp.playState    ) {                         }
		if(gp.gameState == gp.pauseState   ) { drawPauseScreen(g2);    }
		if(gp.gameState == gp.dialogueState) { drawDialogueScreen(g2); }
	}
	public void drawPauseScreen(Graphics2D g2) {
		
		String text = "PAUSE";
		g2.setFont(g2.getFont().deriveFont(60f));
		g2.setColor(shadow);
		g2.drawString(text, gp.screenWidth/2 + 3, gp.screenHeight/2 - getXforCenteredText(text)/2 + 4);
		g2.setColor(Color.white);
		g2.drawString(text, gp.screenWidth/2, gp.screenHeight/2 - getXforCenteredText(text)/2);
	}
	public void drawDialogueScreen(Graphics2D g2) {
		
		int x = gp.screenWidth/2 - gp.tileSize * 10, y =  gp.screenHeight - gp.tileSize*6;
		int width =  gp.tileSize * 20, height =  gp.tileSize * 5;
		
		try {

			dialogueWindow = ImageIO.read(getClass().getResourceAsStream("/ui/ui_dialogue.png"));
			gp.ut.scaleImage(dialogueWindow, width,  height);
		} 
		catch (IOException e) { e.printStackTrace(); }
		
		g2.drawImage(dialogueWindow, x, y, width, height, null);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f));
		g2.setColor(Color.black);
		x += gp.tileSize; y += (int)(gp.tileSize*1.5);
	
		for(String line : currentDialogue.split("%")) {
			g2.drawString(line, x, y); 
			y += 50; 
		}
	}
	public void drawSubWindow(int x, int y, int width, int height) {

		g2.setColor(shadow);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.white);
		g2.drawRoundRect(x + 4, y + 4, width - 8, height - 8 , 25, 25);
	}
	public int getXforCenteredText(String text) {
		
		return (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
	}
}

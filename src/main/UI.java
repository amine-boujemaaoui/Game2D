package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font arial_20;
	Color shadow = new Color(0, 0, 0);
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public UI(GamePanel gp) {
		
		 this.gp = gp;
		 arial_20 = new Font("Arial", Font.BOLD, 20);
	}
	public void showMessage(String message) {
		this.message = message; messageOn = true;
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;

		if(gp.gameState == gp.pauseState) drawPauseScreen(g2);
	}
	public void drawPauseScreen(Graphics2D g2) {
		
		String text = "PAUSE";
		g2.setFont(g2.getFont().deriveFont(60f));
		g2.setColor(shadow);
		g2.drawString(text, gp.screenWidth/2 + 3, gp.screenHeight/2 - getXforCenteredText(text)/2 + 4);
		g2.setColor(Color.white);
		g2.drawString(text, gp.screenWidth/2, gp.screenHeight/2 - getXforCenteredText(text)/2);
	}
	public int getXforCenteredText(String text) {
		
		return (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
	}
}

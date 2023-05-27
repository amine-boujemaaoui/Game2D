package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font pixelFont;
	public Color shadow = new Color(0, 0, 0, 200);
	public Color titleScreenColor = new Color(59, 143, 202);
	public Color barColor = new Color(24, 20, 37);
	public Color helthColor = new Color(255, 0, 68);
	public boolean messageOn = false;
	public String message = "";
	public String showKey = "";
	public int x, y;
	int messageCounter = 0;
	BufferedImage dialogueWindow, titleScreen, statsWindow, equipmentWindow;
	BufferedImage E, SPACE;
	BufferedImage heart_full,   heart_half,   heart_empty;
	BufferedImage mana_full,    mana_half,    mana_empty;
	BufferedImage stamina_full, stamina_half, stamina_empty;
	public BufferedImage health_bar;
	public String currentDialogue = "";
	public int selectedOption = 0;
	public int subStateScreen = 0;
	public String titleScreenOptions[] = {"NEW GAME", "LOAD GAME", "QUIT"};
	
	public UI(GamePanel gp) {
		
		 this.gp = gp;

		 InputStream is = getClass().getResourceAsStream("/fonts/Minecraft.TTF");
		 try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 try {
			int iconSize = (int)(gp.tileSize*0.75);
			
			titleScreen = ImageIO.read(getClass().getResourceAsStream("/ui/ui_titleScreen.png"));
			gp.ut.scaleImage(titleScreen, gp.screenHeight, gp.screenHeight);
			statsWindow = ImageIO.read(getClass().getResourceAsStream("/ui/ui_stats.png"));
			gp.ut.scaleImage(statsWindow, gp.tileSize*14, gp.tileSize*14);
			dialogueWindow = ImageIO.read(getClass().getResourceAsStream("/ui/ui_dialogue.png"));
			gp.ut.scaleImage(dialogueWindow, gp.tileSize*20, gp.tileSize*5);
			equipmentWindow = ImageIO.read(getClass().getResourceAsStream("/ui/ui_equipment.png"));
			gp.ut.scaleImage(equipmentWindow, gp.tileSize*15, (int)(10.5*gp.tileSize));
			
			E = ImageIO.read(getClass().getResourceAsStream("/ui/keys/E.png"));
			gp.ut.scaleImage(E, gp.tileSize, gp.tileSize);
			SPACE = ImageIO.read(getClass().getResourceAsStream("/ui/keys/SPACE.png"));
			gp.ut.scaleImage(SPACE, 35*gp.scale, 14*gp.scale);
			
			heart_full = ImageIO.read(getClass().getResourceAsStream("/ui/icons/heart/full.png"));
			gp.ut.scaleImage(heart_full, iconSize, iconSize);
			heart_half = ImageIO.read(getClass().getResourceAsStream("/ui/icons/heart/half.png"));
			gp.ut.scaleImage(heart_half, iconSize, iconSize);
			heart_empty = ImageIO.read(getClass().getResourceAsStream("/ui/icons/heart/empty.png"));
			gp.ut.scaleImage(heart_empty, iconSize, iconSize);
			
			mana_full = ImageIO.read(getClass().getResourceAsStream("/ui/icons/mana/full.png"));
			gp.ut.scaleImage(mana_full, iconSize, iconSize);
			mana_half = ImageIO.read(getClass().getResourceAsStream("/ui/icons/mana/half.png"));
			gp.ut.scaleImage(mana_half, iconSize, iconSize);
			mana_empty = ImageIO.read(getClass().getResourceAsStream("/ui/icons/mana/empty.png"));
			gp.ut.scaleImage(mana_empty, iconSize, iconSize);
			
			stamina_full = ImageIO.read(getClass().getResourceAsStream("/ui/icons/stamina/full.png"));
			gp.ut.scaleImage(stamina_full, iconSize, iconSize);
			stamina_half = ImageIO.read(getClass().getResourceAsStream("/ui/icons/stamina/half.png"));
			gp.ut.scaleImage(stamina_half, iconSize, iconSize);
			stamina_empty = ImageIO.read(getClass().getResourceAsStream("/ui/icons/stamina/empty.png"));
			gp.ut.scaleImage(stamina_empty, iconSize, iconSize);
			
			health_bar = ImageIO.read(getClass().getResourceAsStream("/ui/icons/health_bar.png"));
			gp.ut.scaleImage(health_bar, 4, 4);
			
		} catch (IOException e) { e.printStackTrace(); }
	}
	public void showMessage(String key, String text, int x, int y) {
		messageOn = true;
		this.message = text;
		this.showKey = key; 
		this.x = x; this.y = y;
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(pixelFont);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		 
		if(messageOn) drawIndication(showKey, message, x, y);

		if(gp.gameState == gp.titleScreenState    ) { drawTitleScreen(g2); }
		if(gp.gameState == gp.playState           ) { drawGUI(g2); }
		if(gp.gameState == gp.pauseState          ) { drawPauseScreen(g2); }
		if(gp.gameState == gp.dialogueState       ) { drawDialogueScreen(g2); drawGUI(g2); }
		if(gp.gameState == gp.equipmentWindowState) { drawEquipmentWindow(g2); }
	}
	public void drawTitleScreen(Graphics2D g2) {
		String title;
		switch(subStateScreen) {
		case 0: 
			
			title = "Game2D";
			g2.setColor(titleScreenColor);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			g2.drawImage(titleScreen, 0, 0, gp.screenHeight, gp.screenHeight, null);
			g2.setFont(g2.getFont().deriveFont(120f));
			drawTextShadow(title, Color.gray, Color.white, getXforCenteredText(title) + gp.screenHeight/3, gp.tileSize*4);
			
			g2.setFont(g2.getFont().deriveFont(50f));
			y = gp.screenHeight/2;
			
			for(int i = 0; i < titleScreenOptions.length; i++) {
				x = getXforCenteredText(titleScreenOptions[i]) + gp.screenHeight/3;
				y += (int)(gp.tileSize*1.5);
				drawTextShadow(titleScreenOptions[i], Color.gray, Color.white, x, y);
				if(selectedOption == i) drawTextShadow(">", Color.gray, Color.white, x - gp.tileSize, y);
			}
			break;
		case 1:
			
			title = "Choose your class";
			g2.setFont(g2.getFont().deriveFont(70f));
			g2.setColor(titleScreenColor);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			g2.drawImage(statsWindow,  gp.tileSize*2, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*14, gp.tileSize*14, null);
			drawTextShadow(title, Color.gray, Color.white, getXforCenteredText(title) + (int)(gp.screenHeight/2.5), gp.tileSize*4);
			
			g2.setFont(g2.getFont().deriveFont(32f));
			x = (int)(gp.tileSize*6.5);
			y = (int)(gp.screenHeight/2-gp.tileSize*3.6);
			
			for(int i = 0; i < gp.statsTitles.length; i++) {
				if(selectedOption < gp.classSelectionOptions.length) { drawTextShadow(gp.statsTitles[i] + " : " + gp.statsValues[selectedOption][i], Color.gray, Color.white, x, y); }
				y += (int)(gp.tileSize*1.5);
			}
			
			g2.setFont(g2.getFont().deriveFont(50f));
			y = gp.screenHeight/3;
			
			for(int i = 0; i < gp.classSelectionOptions.length; i++) {
				x = getXforCenteredText(gp.classSelectionOptions[i]) + (int)(gp.screenHeight/2.5);
				y += (int)(gp.tileSize*1.5);
				drawTextShadow(gp.classSelectionOptions[i], Color.gray, Color.white, x, y);
				if(selectedOption == i) drawTextShadow(">", Color.gray, Color.white, x - gp.tileSize, y);
			}
			
			String back = "BACK";
			x = getXforCenteredText(back) + (int)(gp.screenHeight/2.5);
			y += (int)(gp.tileSize*1.5) * 2;
			drawTextShadow(back, Color.gray, Color.white, x, y);
			if(selectedOption == gp.classSelectionOptions.length) drawTextShadow(">", Color.gray, Color.white, x - gp.tileSize, y);
			break;
		case 2: break;
		}
		
	}
	public void drawPauseScreen(Graphics2D g2) {
		
		String text = "PAUSE";
		g2.setColor(shadow); g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setFont(g2.getFont().deriveFont(60f));
		drawTextShadow(text, Color.gray, Color.white, getXforCenteredText(text), gp.screenHeight/2 - gp.tileSize*2);
	}
	public void drawDialogueScreen(Graphics2D g2) {
		
		int x = gp.screenWidth/2 - gp.tileSize * 10, y =  gp.screenHeight - gp.tileSize*6;
		int width =  gp.tileSize * 20, height =  gp.tileSize * 5;
		
		g2.drawImage(dialogueWindow, x, y, width, height, null);
		g2.drawImage(SPACE, x + width - gp.tileSize*2, y + height - (int)(gp.tileSize/1.5), 35*gp.scale, 14*gp.scale, null);

		x += gp.tileSize; y += (int)(gp.tileSize*1.30);
	
		g2.setFont(g2.getFont().deriveFont(24f));
		for(String line : currentDialogue.split("%")) {
			//drawTextShadow(line, shadow, Color.black, x, y);
			g2.drawString(line, x, y);
			y += 30; 
		}
	}
	public void drawEquipmentWindow(Graphics2D g2) {

		int x = 0, y = gp.tileSize, statValue;
				
		g2.drawImage(equipmentWindow, x, y, 15*gp.tileSize, (int)(10.5*gp.tileSize), null);
		
		x += (int)(gp.tileSize*3.5);
		y += (int)(gp.tileSize*2.5);
		
		g2.setFont(g2.getFont().deriveFont(26f));
		for(int i = 0; i < gp.statsTitles.length; i++) {
			statValue = gp.player.getClassStats(i);
			if(statValue != -1) drawTextShadow(gp.statsTitles[i] + " : " + statValue, Color.gray, Color.white, x, y);
			y += (int)(gp.tileSize*1.15);
		}
		
		if(gp.player.slotMele != null) g2.drawImage(gp.player.slotMele.down_still[0], (int)(gp.tileSize*7.75), (int)(gp.tileSize*4.25), gp.tileSize, gp.tileSize, null);
		if(gp.player.slotShield != null) g2.drawImage(gp.player.slotShield.down_still[0], (int)(gp.tileSize*7.75), (int)(gp.tileSize*5.25), gp.tileSize, gp.tileSize, null);
	}
	public void drawGUI(Graphics2D g2) {
		
		int i;
		int iconSize = (int)(gp.tileSize*0.75);
		int x = gp.tileSize*2;
		int y = gp.screenHeight - gp.tileSize*4;
		
		// BLANK BARS
		for(i = 0; i < gp.player.maxHealth/2;  i++) { g2.drawImage(heart_empty,   x, y, iconSize, iconSize, null); x += iconSize - 6;  } x = gp.tileSize*2; y += iconSize + 2;
		for(i = 0; i < gp.player.maxStamina/2; i++) { g2.drawImage(stamina_empty, x, y, iconSize, iconSize, null); x += iconSize - 14; } x = gp.tileSize*2; y += iconSize + 2;
		if(gp.player.maxMana > 0)
		for(i = 0; i < gp.player.maxMana/2;    i++) { g2.drawImage(mana_empty,    x, y, iconSize, iconSize, null); x += iconSize - 10;  } 
		
		// FILL BARS
		x = gp.tileSize*2;
		y = gp.screenHeight - gp.tileSize*4;
		i = 0;
		while(i < gp.player.health) {
			g2.drawImage(heart_half,   x, y, iconSize, iconSize, null); i++;
			if(i < gp.player.health) g2.drawImage(heart_full,   x, y, iconSize, iconSize, null);
			i++; x += iconSize - 6;
		}
		
		x = gp.tileSize*2;
		y += iconSize + 2;
		i = 0;
		while(i < gp.player.stamina) {
			g2.drawImage(stamina_half,   x, y, iconSize, iconSize, null); i++;
			if(i < gp.player.stamina) g2.drawImage(stamina_full,   x, y, iconSize, iconSize, null);
			i++; x += iconSize - 14;
		}
		
		if(gp.player.maxMana > 0) {
			x = gp.tileSize*2;
			y += iconSize + 2;
			i = 0;
			while(i < gp.player.mana) {
				g2.drawImage(mana_half,   x, y, iconSize, iconSize, null); i++;
				if(i < gp.player.mana) g2.drawImage(mana_full,   x, y, iconSize, iconSize, null);
				i++; x += iconSize - 10;
			}
		}

	}
	public void drawSubWindow(int x, int y, int width, int height) {

		g2.setColor(shadow);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.white);
		g2.drawRoundRect(x + 4, y + 4, width - 8, height - 8 , 25, 25);
	}
	public void drawTextShadow(String text, Color shadow, Color color, int x, int y) {
		
		g2.setColor(shadow);
		g2.drawString(text, x + 3, y + 4);
		g2.setColor(color);
		g2.drawString(text, x, y);
	}
	public void drawIndication(String key, String text, int x, int y) {
		
		int screenX = x - gp.player.worldX + gp.player.screenX;
		int screenY = y - gp.player.worldY + gp.player.screenY;
		
		g2.setFont(g2.getFont().deriveFont(18f));
		g2.drawImage(E, screenX + 50, screenY + 10, gp.tileSize/3, gp.tileSize/3, null);
		
		g2.setColor(Color.black);  g2.drawString(text, screenX + 72 + 1, screenY + 22 + 1);
		g2.setColor(Color.white); g2.drawString(text, screenX + 72,     screenY + 22);
		messageOn = false;
	}
	public int getXforCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return (gp.screenWidth/2 - length/2);
	}
}

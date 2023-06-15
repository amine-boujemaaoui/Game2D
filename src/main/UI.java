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
import java.util.ArrayList;
import javax.imageio.ImageIO;

import entity.Entity;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font pixelFont;
	public Color shadow = new Color(0, 0, 0, 200);
	public Color titleScreenColor = new Color(59, 143, 202);
	public Color barColor = new Color(24, 20, 37);
	public Color helthColor = new Color(255, 0, 68);
	public Color equipmentBGC = new Color(234, 212, 170);
	public Color equipmentEquipedBGC = new Color(58, 68, 102);
	public Color evtColM_damage = helthColor;
	public Color evtColS_damage = new Color(38, 0, 10);
	public Color evtColM_exp = new Color(99, 199, 77);
	public Color evtColS_exp = new Color(17, 38, 20);
	public boolean messageOn = false;
	public String showKey = "";
	public int x, y;
	BufferedImage dialogueWindow, titleScreen, statsWindow, equipmentWindow, expBarOutline, inventoryWindow, itemInfosWindow, inventoryCursor;
	BufferedImage E, SPACE;
	BufferedImage heart_full,   heart_half,   heart_empty;
	BufferedImage mana_full,    mana_half,    mana_empty;
	BufferedImage stamina_full, stamina_half, stamina_empty;
	public BufferedImage exp_bar, health_bar, plus, minus;
	public String currentDialogue = "";
	public int selectedOption = 0;
	public int subStateScreen = 0;
    public String indicationText;
	ArrayList<EventMessage> eventMessages = new ArrayList<EventMessage>();
	public String titleScreenOptions[] = {"NEW GAME", "LOAD GAME", "QUIT"};
	public int slotCol = 0, slotRow = 0;
	
	public UI(GamePanel gp) {
		
		this.gp = gp;

		InputStream is = getClass().getResourceAsStream("/fonts/Minecraft.TTF");
		try { pixelFont = Font.createFont(Font.TRUETYPE_FONT, is); } 
		catch (Exception e) { e.printStackTrace(); }
		 
		int iconSize = (int)(gp.tileSize*0.75);
			
		// WINDOWS
		titleScreen     = gp.ut.setup("/ui/ui_titleScreen",      gp.tileSize*18,           gp.tileSize*18         );
		statsWindow     = gp.ut.setup("/ui/ui_stats",            gp.tileSize*14,           gp.tileSize*14         );
		dialogueWindow  = gp.ut.setup("/ui/ui_dialogue",         gp.tileSize*20,           gp.tileSize*5          );
		equipmentWindow = gp.ut.setup("/ui/ui_equipment",        gp.tileSize*15,           gp.tileSize*16         );
		expBarOutline   = gp.ut.setup("/ui/ui_expBarOutline",    iconSize*6,               8*3                    );
		inventoryWindow = gp.ut.setup("/ui/ui_inventory",        gp.tileSize*9,            gp.tileSize*12         );
		itemInfosWindow = gp.ut.setup("/ui/ui_item_infos",       gp.tileSize*10,           gp.tileSize*10         );
		inventoryCursor = gp.ut.setup("/ui/ui_inventory_cursor", (int)(gp.tileSize*1.5),   (int)(gp.tileSize*1.5) );
		
		// ICONS
		plus          = gp.ut.setup("/ui/icons/plus",          gp.tileSize/2, gp.tileSize/2 );
		minus         = gp.ut.setup("/ui/icons/minus",         gp.tileSize/2, gp.tileSize/2 );
		heart_full    = gp.ut.setup("/ui/icons/heart/full",    iconSize,      iconSize      );
		heart_half    = gp.ut.setup("/ui/icons/heart/half",    iconSize,      iconSize      );
		heart_empty   = gp.ut.setup("/ui/icons/heart/empty",   iconSize,      iconSize      );
		mana_full     = gp.ut.setup("/ui/icons/mana/full",     iconSize,      iconSize      );
		mana_half     = gp.ut.setup("/ui/icons/mana/half",     iconSize,      iconSize      );
		mana_empty    = gp.ut.setup("/ui/icons/mana/empty",    iconSize,      iconSize      );
		stamina_full  = gp.ut.setup("/ui/icons/stamina/full",  iconSize,      iconSize      );
		stamina_half  = gp.ut.setup("/ui/icons/stamina/half",  iconSize,      iconSize      );
		stamina_empty = gp.ut.setup("/ui/icons/stamina/empty", iconSize,      iconSize      );
		health_bar    = gp.ut.setup("/ui/icons/health_bar",    4,             4             );
		exp_bar       = gp.ut.setup("/ui/icons/exp_bar",       4,             4             );
	
		// KEYS
		E     = gp.ut.setup("/ui/keys/E",     gp.tileSize/3, gp.tileSize/3 );
		SPACE = gp.ut.setup("/ui/keys/SPACE", 2*gp.tileSize, 2*gp.tileSize/3 );
			
	}
	public void showMessage(String key, String text, int x, int y) {
		messageOn = true;
		this.indicationText = text;
		this.showKey = key; 
		this.x = x; this.y = y;
	}
	public void addEventMessage(String text, float fontSize, int fontType, Color textColor, Color shadowColor, int x, int y, boolean fixedPosition) {
		
		eventMessages.add(new EventMessage(text, fontSize, fontType, textColor, shadowColor, x, y, fixedPosition));
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(pixelFont);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		 
		if(messageOn) drawIndication(showKey, indicationText, x, y);

		if(gp.gameState == gp.titleScreenState    ) { drawTitleScreen(g2); }
		if(gp.gameState == gp.pauseState          ) { drawPauseScreen(g2); }
		if(gp.gameState == gp.dialogueState       ) { drawDialogueScreen(g2); }
		if(gp.gameState == gp.equipmentWindowState) { drawEquipmentWindow(g2); drawInventory(g2); }
		if(gp.gameState == gp.playState           ) { drawGUI(g2); drawEventMessages(g2); }
	}
	public void drawTitleScreen(Graphics2D g2) {
		String title;
		switch(subStateScreen) {
		case 0: 
			
			title = "Game2D";
			g2.setColor(titleScreenColor);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			g2.drawImage(titleScreen, 0, 0, null);
			g2.setFont(g2.getFont().deriveFont(120f));
			
			drawTextShadow(title, Color.gray, Color.white, getXforCenteredText(title) + gp.screenHeight/3, gp.tileSize*4);
			
			y = gp.screenHeight/2;
			g2.setFont(g2.getFont().deriveFont(50f));
			
			for(int i = 0; i < titleScreenOptions.length; i++) {
				x = getXforCenteredText(titleScreenOptions[i]) + gp.screenHeight/3;
				y += (int)(gp.tileSize*1.5);
					
				drawTextShadow(titleScreenOptions[i], Color.gray, Color.white, x, y);
				if(selectedOption == i) drawTextShadow(">", Color.gray, Color.white, x - gp.tileSize, y);
			}
			break;
		case 1:
			
			title = "Choose your class";
			g2.setColor(titleScreenColor);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			g2.drawImage(statsWindow,  gp.tileSize*2, gp.screenHeight/2-gp.tileSize*7, null);
			
			g2.setFont(g2.getFont().deriveFont(70f));
			drawTextShadow(title, Color.gray, Color.white, getXforCenteredText(title) + (int)(gp.screenHeight/2.5), gp.tileSize*4);
			
			x = (int)(gp.tileSize*6.5);
			y = (int)(gp.screenHeight/2-gp.tileSize*3.6);
			
			g2.setFont(g2.getFont().deriveFont(32f));
			
			for(int i = 0; i < gp.statsTitles.length; i++) {
				if(selectedOption < gp.classSelectionOptions.length) { drawTextShadow(gp.statsTitles[i] + " : " + gp.statsValues[selectedOption][i], Color.gray, Color.white, x, y); }
				y += (int)(gp.tileSize*1.5);
			}
			
			y = gp.screenHeight/3;
			
			g2.setFont(g2.getFont().deriveFont(50f));
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
		
		g2.drawImage(dialogueWindow, x, y, null);
		g2.drawImage(SPACE, x + width - gp.tileSize*2, y + height - (int)(gp.tileSize/1.5), null);

		width -= (int)(gp.tileSize*2.1); height -=(int)(gp.tileSize*2.1);
		x += gp.tileSize; y += (int)(gp.tileSize*0.95);
		g2.setFont(g2.getFont().deriveFont(24f));
		gp.ut.drawMultilineString(g2, currentDialogue, x, y, width, height);
	}
	public void drawEquipmentWindow(Graphics2D g2) {

		int x = 0, y = gp.tileSize, statValue;
		String print;
				
		g2.drawImage(equipmentWindow, x, y, null);
		
		x += (int)(gp.tileSize*3.5);
		y += (int)(gp.tileSize*2.5);
		
		g2.setFont(g2.getFont().deriveFont(26f)); g2.setColor(Color.white);
		
		for(int i = 0; i < gp.statsTitles.length; i++) {
			statValue = gp.player.getClassStats(i);
			print = gp.statsTitles[i] + " : " + statValue;
			if(gp.statsTitles[i] == "Health" || gp.statsTitles[i] == "Mana" || gp.statsTitles[i] == "Sta")
				if(gp.player.getClassStats(i+6) > 0)
					print += "/" + gp.player.getClassStats(i+6);
				else print = gp.statsTitles[i] + " : X";
			if(statValue != -1) drawTextShadow(print, Color.gray, Color.white, x, y);
			y += (int)(gp.tileSize*1.1);
		}
		
		int tempY = y;
		y += (int)(gp.tileSize*1.4);
		for(int i = 0; i < gp.playerStatsTitles.length; i++) {
			statValue = gp.player.getPlayerStats(i);
			if(statValue != -1) drawTextShadow(gp.playerStatsTitles[i] + " : " + statValue, Color.gray, Color.white, x, y);
			y += (int)(gp.tileSize*1);
		}
		
		y = tempY;
		y += (int)(gp.tileSize*1.375);
		x += (int)(gp.tileSize*7.5);
		for(int coinType: gp.ut.coinsType) {
			drawTextShadow(gp.player.coinsByType.get(coinType) + "", Color.gray, Color.white, x, y);
			y += (int)(gp.tileSize*1.1);
		}
		
		// WEAPONS SLOTS
		g2.setColor(equipmentBGC);
		x = (int)(gp.tileSize*7.75); y = (int)(gp.tileSize*4.25);
		if(gp.player.slotMele != null) {
			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			g2.drawImage(gp.player.slotMele.item_icon,   x, y, null); 
		}
		y = (int)(y+gp.tileSize*1.1);
		if(gp.player.slotShield != null) { 
			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			g2.drawImage(gp.player.slotShield.item_icon, x, y, null); 
		}
		y = (int)(y+gp.tileSize*1.1); 
		if(gp.player.slotStaff != null) { 
			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			g2.drawImage(gp.player.slotStaff.item_icon,  x, y, null);
		}
		y = (int)(y+gp.tileSize*1.1);
		if(gp.player.slotProjectileWeapon != null) { 
			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			g2.drawImage(gp.player.slotProjectileWeapon.item_icon,  x, y, null);
		}
		y = (int)(y+gp.tileSize*1.1);
		
		// ARMOR SLOTS
		x = (int)(gp.tileSize*9.26); y = (int)(gp.tileSize*3.875);
		if(gp.player.slotHelmet  != null) { 
			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			g2.drawImage(gp.player.slotHelmet.item_icon,  x, y, null);
		}
		y = (int)(y+gp.tileSize*1.1);
		if(gp.player.slotChestplate  != null) { 
			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			g2.drawImage(gp.player.slotChestplate.item_icon,  x, y, null);
		}
		y = (int)(y+gp.tileSize*1.1);
		if(gp.player.slotLeggings  != null) { 
			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			g2.drawImage(gp.player.slotLeggings.item_icon,  x, y, null);
		}
		y = (int)(y+gp.tileSize*1.1);
		if(gp.player.slotBoots  != null) { 
			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			g2.drawImage(gp.player.slotBoots.item_icon,  x, y, null);
		}
		
		// TOOLS SLOTS
		x = (int)(gp.tileSize*8.67);
		y = (int)(gp.tileSize*8.35);
		if(gp.player.slotPickaxe  != null) { 
			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			g2.drawImage(gp.player.slotPickaxe.item_icon,  x, y, null);
		}
		x += (int)(gp.tileSize*1.15);
		if(gp.player.slotAxe  != null) { 
			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			g2.drawImage(gp.player.slotAxe.item_icon,  x, y, null);
		}
	}
	public void drawInventory(Graphics2D g2) {
		
		int inventoryItemsSize = (int)(gp.tileSize*1.5);
		
		// INVENTORY WINDOW
		int x = gp.screenWidth - gp.tileSize - gp.tileSize*9, y = gp.tileSize;
		g2.drawImage(inventoryWindow, x, y, null);
		
		// CURSOR
		x += inventoryItemsSize; y += inventoryItemsSize;
		
		final int slotStartX = x; int slotX = slotStartX;
		final int slotStartY = y; int slotY = slotStartY;
		
		int cursorWidth = inventoryItemsSize;
		int cursorHeight = inventoryItemsSize;
		int cursorX = slotStartX + cursorWidth*slotCol;
		int cursorY = slotStartY + cursorHeight*slotRow;
		
		// INVENTORY ITEMS
		Entity item;
		for(int i = 0; i < gp.player.inventory.size(); i++) {
			item = gp.player.inventory.get(i);
			
			if( item == gp.player.slotMele       || 
			    item == gp.player.slotShield     ||
			    item == gp.player.slotHelmet     ||
			    item == gp.player.slotChestplate ||
			    item == gp.player.slotLeggings   ||
			    item == gp.player.slotBoots      ||
			    item == gp.player.slotAxe        ||
			    item == gp.player.slotPickaxe    ){
				
				g2.setColor(equipmentEquipedBGC); 
				g2.fillRect(slotX + 9, slotY + 9, inventoryItemsSize - 18, inventoryItemsSize - 18);
				g2.setColor(barColor);  
				g2.setStroke(new BasicStroke(3)); 
				g2.drawRect(slotX + 9, slotY + 9, inventoryItemsSize - 18, inventoryItemsSize - 18);
				g2.setStroke(new BasicStroke(1));
			}
			
			g2.drawImage(gp.player.inventory.get(i).item_icon,  slotX + 9, slotY + 9, inventoryItemsSize - 18, inventoryItemsSize - 18, null);
			slotX += inventoryItemsSize;

			if(i == 3 || i == 7 || i == 11|| i == 15 || i == 19 ) { slotY += inventoryItemsSize; slotX = slotStartX; }
		}
		
		
		// ITEM INFOS WINDOW
		if(getItemIndexInventory() < gp.player.inventory.size()) {
			
			item = gp.player.inventory.get(getItemIndexInventory());
			
			// DRAW ITEM INFOS WINDOW
			x = gp.screenWidth - gp.tileSize*17; y = (int)(gp.tileSize*1.5);
			g2.drawImage(itemInfosWindow, x, y, null);
			
			// ITEM INFOS CURRENT ITEM
			x += (int)(gp.tileSize*1.875); y += (int)(gp.tileSize*1.875);
			g2.drawImage(item.item_icon, x, y, null); 
			x += (int)(gp.tileSize*1.75); y += (int)(gp.tileSize/3);
			g2.setFont(g2.getFont().deriveFont(16f));
			drawTextShadow(item.name, Color.gray, Color.white, x, y);
			
			if(item.durability >= 0) {
				// CURRENT ITEM DURABILITY - TEXT
				x -= (int)(gp.tileSize*0.05); y += (int)(gp.tileSize/1.75);
				g2.setFont(g2.getFont().deriveFont(10f));
				g2.setColor(Color.gray);  g2.drawString("durability " + item.durability + "/" + item.maxDurability, x + 1, y + 1);
				g2.setColor(Color.white); g2.drawString("durability " + item.durability + "/" + item.maxDurability, x, y);
				
				float oneScale = (float)gp.tileSize*2/item.maxDurability;
				float durabilityBar = (float)oneScale * item.durability;
				
				// CURRENT ITEM DURABILITY - BAR 
				y += (int)(gp.tileSize/6);
				g2.setStroke(new BasicStroke(3));
				g2.drawImage(exp_bar, x, y, (int)durabilityBar, gp.tileSize/4, null);
				g2.setColor(barColor);
				g2.drawRect(x, y, gp.tileSize*2, gp.tileSize/4);
				g2.setStroke(new BasicStroke(1));
			}
			// SKIP DURABILITY BAR
			else y += (int)(gp.tileSize/1.75) + (int)(gp.tileSize/6);
			
			// DRAW DESCRIPTION
			y += (int)(gp.tileSize); 
			int tempY = y;
			g2.setFont(g2.getFont().deriveFont(14f));
			g2.setColor(Color.gray);
			gp.ut.drawMultilineString(g2, item.description, x + 1, y + 1, gp.tileSize*3, gp.tileSize*4);
			g2.setColor(Color.white);
			gp.ut.drawMultilineString(g2, item.description, x, y, gp.tileSize*3, gp.tileSize*4);
			
			
			y = tempY + 4; x = gp.screenWidth - gp.tileSize*17 + (int)(gp.tileSize*1.875);
			g2.setFont(g2.getFont().deriveFont(20f));
			
			
			Entity selectedItem = gp.player.inventory.get(getItemIndexInventory());
			
			if(selectedItem.type == gp.typeITM) {
				g2.setColor(equipmentEquipedBGC);
				g2.fillRect(x - 10, tempY, gp.tileSize + 10, gp.tileSize*4);
			} 
			else {
				
				for(int i = 0; i < gp.playerStatsTitles.length; i++) {
					
					if(selectedItem.getStatValues(i) < 0) g2.drawImage(minus, x, y, null);
					else if((i != 0 && i != 1) && selectedItem.getStatValues(i) > 0) g2.drawImage(plus,  x, y, null);
					drawTextShadow(selectedItem.getStatValues(i) + "", Color.gray, Color.white, x + 30, y + 20);
					y += (gp.tileSize);
				}
			}
		}
		
		// DRAW CURSOR
		g2.drawImage(inventoryCursor, cursorX, cursorY, null);
	}
	public void drawGUI(Graphics2D g2) {
		
		int i;
		int iconSize = (int)(gp.tileSize*0.75);
		int x = gp.tileSize*2;
		int y = gp.screenHeight - gp.tileSize*4;
		
		// BLANK BARS
		for(i = 0; i < gp.player.maxHealth/2;  i++) { g2.drawImage(heart_empty,   x, y, null); x += iconSize - 6;  } x = gp.tileSize*2; y += iconSize + 2;
		for(i = 0; i < gp.player.maxStamina/2; i++) { g2.drawImage(stamina_empty, x, y, null); x += iconSize - 14; } x = gp.tileSize*2; y += iconSize + 2;
		if(gp.player.maxMana > 0)
		for(i = 0; i < gp.player.maxMana/2;    i++) { g2.drawImage(mana_empty,    x, y, null); x += iconSize - 10;  } 
		
		// FILL BARS
		x = gp.tileSize*2;
		y = gp.screenHeight - gp.tileSize*4;
		i = 0;
		while(i < gp.player.health) {
			g2.drawImage(heart_half,   x, y, null); i++;
			if(i < gp.player.health) g2.drawImage(heart_full,   x, y, null);
			i++; x += iconSize - 6;
		}
		
		x = gp.tileSize*2;
		y += iconSize + 2;
		i = 0;
		while(i < gp.player.stamina) {
			g2.drawImage(stamina_half,   x, y, iconSize, iconSize, null); i++;
			if(i < gp.player.stamina) g2.drawImage(stamina_full,   x, y, null);
			i++; x += iconSize - 14;
		}
		
		if(gp.player.maxMana > 0) {
			x = gp.tileSize*2;
			y += iconSize + 4;
			i = 0;
			while(i < gp.player.mana) {
				g2.drawImage(mana_half,   x, y, iconSize, iconSize, null); i++;
				if(i < gp.player.mana) g2.drawImage(mana_full,   x, y, null);
				i++; x += iconSize - 10;
			}
		}
		
		double oneScale = iconSize*6/gp.player.nextLevelExp;
		double expBar = oneScale * gp.player.exp;
		
		x = gp.tileSize*2;
		y += iconSize + 2;
		
		g2.drawImage(exp_bar,       x, y, (int)expBar, 8*3, null);
		g2.drawImage(expBarOutline, x, y, null);

	}
	public void drawEventMessages(Graphics2D g2) {
		
		int screenX, screenY, offset = 10;
		EventMessage e; 
		
		if(eventMessages.size() > 0) {
			for(int i = 0; i < eventMessages.size(); i++) {
				
				e = eventMessages.get(i);
				
				if(!e.fixedPosition) {
					screenX = e.x - gp.player.worldX + gp.player.screenX;
					screenY = e.y - gp.player.worldY + gp.player.screenY;
				}
				else { screenX = e.x; screenY = e.y; }
				g2.setFont(g2.getFont().deriveFont(e.fontType, e.fontSize));
				drawTextShadow(e.message, e.shadowColor, e.textColor, screenX, screenY - (eventMessages.size()-i)*offset);
				
				e.messageCounter++;
				if(e.messageCounter > 120) eventMessages.remove(i);
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
		g2.drawString(text, x + 2, y + 3);
		g2.setColor(color);
		g2.drawString(text, x, y);
	}
	public void drawIndication(String key, String text, int x, int y) {
		
		int screenX = x - gp.player.worldX + gp.player.screenX;
		int screenY = y - gp.player.worldY + gp.player.screenY;
		
		g2.setFont(g2.getFont().deriveFont(18f));
		g2.drawImage(E, screenX + 50, screenY + 10, null);
		
		g2.setColor(Color.black);  g2.drawString(text, screenX + 72 + 1, screenY + 22 + 1);
		g2.setColor(Color.white); g2.drawString(text, screenX + 72,     screenY + 22);
		messageOn = false;
	}
	public int getXforCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return (gp.screenWidth/2 - length/2);
	}
	public int getItemIndexInventory() {
		
		return slotCol + slotRow * gp.player.maxInventoryCol;
	}
}

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
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.xml.stream.events.StartDocument;
import java.util.Map;

import entity.Entity;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	public Font pixelFont;
	public Font debugFont = new Font("Arial", Font.BOLD, 24);
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
	BufferedImage dialogueWindow, titleScreen, statsWindow, equipmentWindow, expBarOutline, inventoryWindow, itemInfosWindow, inventoryCursor, price_coins;
	BufferedImage E, SPACE;
	public Map<String, BufferedImage> keys = new HashMap<>();
	BufferedImage heart_full,   heart_half,   heart_empty;
	BufferedImage mana_full,    mana_half,    mana_empty;
	BufferedImage stamina_full, stamina_half, stamina_empty;
	public BufferedImage exp_bar, health_bar, plus, minus;
	public String currentDialogue = "";
	public int selectedOption = 0;
	public int subStateScreen = 0;
	public int volumeBar = 0;
    public String indicationText;
	ArrayList<EventMessage> eventMessages = new ArrayList<EventMessage>();
	public String titleScreenOptions[] = {"NEW GAME", "LOAD GAME", "QUIT"};
	public String settingsOptions[] = {"FULL SCREEN", "MUSIC", "SOUND", "CONTROLS", "BACK", "QUIT"};
	public String controlsOptions[] = {"UP", "DOWN", "LEFT", "RIGHT", "ATTACK", "TOOL", "SPELL", "DASH", "INVENTORY", "BACK"};
	public String controlsKeys[] = {"W", "S", "A", "D", "H", "N", "J-K-L", "G", "I", ""};
	public int playerSlotCol = 0, playerSlotRow = 0;
	public int npcSlotCol = 0, npcSlotRow = 0;
	public int counter = 0;
	boolean transition = false;
	public Entity npc;
	public boolean trade = false;
	public Entity selectedItem;
	
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
		inventoryCursor = gp.ut.setup("/ui/ui_inventory_cursor", (int)(gp.tileSize*1.5),   	(int)(gp.tileSize*1.5) );
		price_coins     = gp.ut.setup("/ui/ui_price_coins",     (int)(gp.tileSize*4.5),   		(int)(gp.tileSize*1.80) );
		
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
		keys.put("E", 		gp.ut.setup("/ui/keys/E",       gp.tileSize/3, gp.tileSize/3 ));
		keys.put("SPACE",	gp.ut.setup("/ui/keys/SPACE", 2*gp.tileSize, 2*gp.tileSize/3 ));

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

		if(gp.gameState == gp.titleScreenState    ) { drawTitleScreen(); }
		if(gp.gameState == gp.pauseState          ) { drawPauseScreen(); }
		if(gp.gameState == gp.dialogueState       ) { drawDialogueScreen(); }
		if(gp.gameState == gp.equipmentWindowState) { drawEquipmentWindow(); drawInventory(gp.player, true); }
		if(gp.gameState == gp.playState           ) { drawGUI(); drawEventMessages(); }
		if(gp.gameState == gp.settingsState       ) { drawSettingsScreen(); }
		if(gp.gameState == gp.gameOverState       ) { drawGameOverScreen(); }
		if(gp.gameState == gp.transitionState     ) { drawTransition(); }
		if(gp.gameState == gp.tradeState          ) { drawTradeScreen(); }
	}
	public void drawTitleScreen() {
		String title;
		switch(subStateScreen) {
		case 0: 
			
			title = "Bonobo Quest";
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
	public void drawPauseScreen() {
		
		String text = "PAUSE";
		g2.setColor(shadow); g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setFont(g2.getFont().deriveFont(60f));
		drawTextShadow(text, Color.gray, Color.white, getXforCenteredText(text), gp.screenHeight/2 - gp.tileSize*2);
	}
	public void drawDialogueScreen() {
		
		int x = gp.screenWidth/2 - gp.tileSize * 10, y =  gp.screenHeight - gp.tileSize*6;
		int width =  gp.tileSize * 20, height =  gp.tileSize * 5;
		
		g2.drawImage(dialogueWindow, x, y, null);
		g2.drawImage(SPACE, x + width - gp.tileSize*2, y + height - (int)(gp.tileSize/1.5), null);

		width -= (int)(gp.tileSize*2.1); height -=(int)(gp.tileSize*2.1);
		x += gp.tileSize; y += (int)(gp.tileSize*0.95);
		g2.setFont(g2.getFont().deriveFont(24f));
		g2.setColor(Color.black);
		gp.ut.drawMultilineString(g2, currentDialogue, x, y, width, height);
	}
	public void drawEquipmentWindow() {

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
	public void drawInventory(Entity entity, boolean cursor) {

		int x = 0, y = 0, slotCol = 0, slotRow = 0;

		int inventoryItemsSize = (int)(gp.tileSize*1.5);

		// INVENTORY WINDOW
		if (entity == gp.player) {
			x = gp.screenWidth - gp.tileSize - gp.tileSize*9;
			y = gp.tileSize;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		} else {
			x = gp.tileSize;
			y = gp.tileSize;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}

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
		for(int i = 0; i < entity.inventory.size(); i++) {
			item = entity.inventory.get(i);

			if(entity == gp.player) {
				if( item == entity.slotMele       ||
						item == entity.slotShield     ||
						item == entity.slotHelmet     ||
						item == entity.slotChestplate ||
						item == entity.slotLeggings   ||
						item == entity.slotBoots      ||
						item == entity.slotAxe        ||
						item == entity.slotPickaxe    ){

					g2.setColor(equipmentEquipedBGC);
					g2.fillRect(slotX + 9, slotY + 9, inventoryItemsSize - 18, inventoryItemsSize - 18);
					g2.setColor(barColor);
					g2.setStroke(new BasicStroke(3));
					g2.drawRect(slotX + 9, slotY + 9, inventoryItemsSize - 18, inventoryItemsSize - 18);
					g2.setStroke(new BasicStroke(1));
				}
			}
			
			g2.drawImage(entity.inventory.get(i).item_icon,  slotX + 9, slotY + 9, inventoryItemsSize - 18, inventoryItemsSize - 18, null);
			slotX += inventoryItemsSize;

			if(i == 3 || i == 7 || i == 11|| i == 15 || i == 19 ) { slotY += inventoryItemsSize; slotX = slotStartX; }
		}
		
		
		// ITEM INFOS WINDOW
		if(cursor) {
			if(getItemIndexInventory(entity) < entity.inventory.size()) {

				item = entity.inventory.get(getItemIndexInventory(entity));

				// DRAW ITEM INFOS WINDOW
				if (entity == gp.player) 	x = gp.screenWidth - gp.tileSize * 17;
				else 						x = itemInfosWindow.getWidth() - gp.tileSize;
				y = (int)(gp.tileSize*1.5);
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

				y = tempY + 4;
				if (entity == gp.player) 	x = gp.screenWidth - gp.tileSize * 17;
				else 						x = itemInfosWindow.getWidth() - gp.tileSize;
				x += (int)(gp.tileSize*1.875);
				g2.setFont(g2.getFont().deriveFont(20f));

				selectedItem = entity.inventory.get(getItemIndexInventory(entity));

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
	}
	public void drawGUI() {
		
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
	public void drawEventMessages() {
		
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

		if(key != "") g2.drawImage(keys.get(key), screenX + 50, screenY + 10, null);

		g2.setColor(Color.black);  g2.drawString(text, screenX + 72 + 1, screenY + 22 + 1);
		g2.setColor(Color.white); g2.drawString(text, screenX + 72,     screenY + 22);
		messageOn = false;
	}
	public int getXforCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return (gp.screenWidth/2 - length/2);
	}
	public int getItemIndexInventory(Entity entity) {
		int slotCol = 0, slotRow = 0;
		if(entity == gp.player) {
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		else {
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		return slotCol + slotRow * entity.maxInventoryCol;
	}
	public void drawSettingsScreen(){

		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32f));

		int frameX = gp.tileSize*9;
		int frameY = gp.tileSize*2;
		int frameWidth = gp.screenWidth - frameX*2;
		int frameHeight = gp.screenHeight - frameY*2;
		if (subStateScreen == 2)    drawSubWindow(frameX, frameY - 80, frameWidth, frameHeight + 160);
		else 						drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		switch (subStateScreen) {
			case 0: settings_title();
				break;
			case 1: drawNotificationPopUp(frameX, frameY, frameWidth, frameHeight, "Information", "Les modification prendront effet apres le redemarrage du jeu", true);
				break;
			case 2: drawControlsSetting(frameX, frameY);
				break;
			case 3:
				break;
			case 4: gp.gameState = gp.playState;
				break;
			case 5: drawNotificationPopUp(frameX, frameY, frameWidth, frameHeight, "Information", "Voulez vous vraiment retourner au menu principale ?", false);
				break;
				
		}

		gp.config.saveConfig();
	}
	public void settings_title() {

		String title = "Settings";
		g2.setFont(g2.getFont().deriveFont(60f));
		drawTextShadow(title, Color.gray, Color.white, getXforCenteredText(title), gp.tileSize*4);

		int space = 30;
		int x = gp.tileSize*9 + gp.tileSize;
		int y = gp.tileSize*2 + gp.tileSize*5;
		int width = gp.screenWidth - x*2;
		int height = gp.screenHeight - y*2;

		g2.setFont(g2.getFont().deriveFont(32f));
		g2.setColor(Color.gray);
		for (int i = 0, j = 0 ; i < settingsOptions.length; i++, j+=2) {
			if (i == settingsOptions.length-2) j+=2;
			if (i == selectedOption) g2.setColor(Color.white);
			else g2.setColor(Color.gray);
			g2.drawString(settingsOptions[i], x + space, y + space*j);
		}

		g2.setColor(Color.white);
		if (selectedOption < settingsOptions.length-2) g2.drawString(">", x, y + selectedOption*space*2);
		else g2.drawString(">", x, y + selectedOption*space*2+space*2);

		if(gp.fullScreen) {
			g2.setColor(Color.gray);
			g2.fillRect(x + (int)(width/1.5), y - 24, 24, 24);
			g2.setColor(Color.white);
		}

		g2.drawRect(x + (int)(width/1.5), y - 24,           24,   24);

		g2.setColor(Color.gray);
		g2.fillRect(x + (int)(width/1.5), y - 24 + space*2, 24*gp.music.volumeIndicator, 24);
		g2.setColor(Color.white);
		g2.drawRect(x + (int)(width/1.5), y - 24 + space*2, 24*8, 24);

		g2.setColor(Color.gray);
		g2.fillRect(x + (int)(width/1.5), y - 24 + space*4, 24*gp.se.volumeIndicator, 24);
		g2.setColor(Color.white);
		g2.drawRect(x + (int)(width/1.5), y - 24 + space*4, 24*8, 24);


	}
	public void drawNotificationPopUp(int frameX, int frameY, int frameW, int frameH, String title, String text, boolean back) {

		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32f));
		drawSubWindow(frameX, frameY, frameW, frameH);

		g2.setFont(g2.getFont().deriveFont(60f));
		drawTextShadow(title, Color.gray, Color.white, getXforCenteredText(title), frameY + gp.tileSize*4);

		g2.setFont(g2.getFont().deriveFont(32f));
		gp.ut.drawMultilineString(g2, text, frameX + gp.tileSize*2, frameY + gp.tileSize*6, frameW - gp.tileSize*4, frameH - gp.tileSize*8);

		if(back) g2.drawString("> BACK", frameX + gp.tileSize*2, frameY + gp.tileSize*12);
		else {
			int middle = gp.screenWidth/2 -gp.tileSize;
			g2.drawString("YES", middle + gp.tileSize*2, frameY + gp.tileSize*12);
			g2.drawString("NO",  middle - gp.tileSize*2, frameY + gp.tileSize*12);
			int x = middle - 40;
			if (selectedOption == 1) x += gp.tileSize*2;
			else 					 x -= gp.tileSize*2;
			g2.drawString(">", x, frameY + gp.tileSize*12);
		}
	}
	public void selectOption() {

		switch (subStateScreen) {
			case 0:
				switch (selectedOption) {
				case 0:
					if (gp.fullScreen) gp.fullScreen = false;
					else gp.fullScreen = true;
					gp.ui.subStateScreen = 1;
					gp.ui.selectedOption = 0;
					break;
				case 3:
					gp.ui.subStateScreen = 2;
					gp.ui.selectedOption = 0;
					break;
				case 4:
					gp.gameState = gp.playState;
					break;
				case 5:
					gp.ui.subStateScreen = 5;
					gp.ui.selectedOption = 0;
					break;
				} break;
			case 1:
				gp.ui.subStateScreen = 0;
				gp.ui.selectedOption = 0;
				break;
			case 2:
				if(selectedOption == controlsOptions.length-1) {
					gp.ui.subStateScreen = 0;
					gp.ui.selectedOption = 0;
				}
				break;
			case 5:
				if(selectedOption == 1) {
					gp.ui.subStateScreen = 0;
					gp.ui.selectedOption = 0;
					gp.gameState = gp.titleScreenState;
				} else {
					gp.ui.subStateScreen = 0;
					gp.ui.selectedOption = 0;
				}
				break;
		}
	}
	public void backOption() {

	}
	public void drawControlsSetting(int frameX, int frameY) {

		String title = "Controls";
		g2.setFont(g2.getFont().deriveFont(60f));
		drawTextShadow(title, Color.gray, Color.white, getXforCenteredText(title), gp.tileSize*4 - 80);

		int space = 50;
		int x = gp.tileSize*9 + gp.tileSize + space;
		int y = gp.tileSize*5;
		int width = gp.screenWidth - x*2;
		int height = gp.screenHeight - y*2;
		int frameW = gp.screenWidth - gp.tileSize*18;
		int j = 0;

		g2.setFont(g2.getFont().deriveFont(32f));
		for(int i = 0; i < controlsOptions.length; i++) {
			if (i == controlsOptions.length-1)  j = i+1;
			else j = i;
			if(selectedOption == i) {
				g2.setColor(Color.white);
				g2.drawString(">", x - space, y + j*space);
			} else g2.setColor(Color.gray);

			g2.drawString(controlsOptions[i], x, y + j*space);
			g2.drawString(controlsKeys[i], x + frameW/2, y + j*space);
		}
	}
	public void drawGameOverScreen() {

		String text = "GAME OVER";
		g2.setColor(shadow); g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setFont(g2.getFont().deriveFont(60f));
		drawTextShadow(text, Color.gray, Color.white, getXforCenteredText(text), gp.screenHeight/2 - gp.tileSize*2);

		String options[] = {"Continue", "Exit"};
		int x, y = gp.screenHeight/2 + gp.tileSize*2, space = 40;

		g2.setFont(g2.getFont().deriveFont(32f));
		for (int i = 0; i < options.length; i++) {
			x = getXforCenteredText(options[i]);
			if (selectedOption == i) {
				g2.setColor(Color.white);
				g2.drawString(">", x - space, y + space*i);
			}
			else g2.setColor(Color.gray);
			g2.drawString(options[i], x, y + space*i);
		}
	}
	public void drawTransition() {


		if (counter < 25 && !transition) {
			counter++;
			if (counter == 25) {
				transition = true;
				gp.currentMap = gp.eventH.tempMap;
				gp.player.worldX = gp.eventH.tempCol * gp.tileSize;
				gp.player.worldY = gp.eventH.tempRow * gp.tileSize;
				gp.eventH.previousEventX = gp.player.worldX;
				gp.eventH.previousEventY = gp.player.worldY;
			}
			g2.setColor(new Color(0, 0, 0, counter*9));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		}
		else {
			if (counter == 0) {
				counter = -1;
				transition = false;
				gp.gameState = gp.playState;
			} else {
				counter--;
				g2.setColor(new Color(0, 0, 0, counter*9));
				g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			}
		}
	}
	public void drawTradeScreen() {

		switch (subStateScreen) {
		case 0: trade_select(); break;
		case 1: trade_buy();  	break;
		case 2: trade_sell();   break;
		}
		gp.keyH.enterPressed = false;
	}
	public void trade_select() {

		drawDialogueScreen();
		int space = 40;
		int x = gp.screenWidth - gp.tileSize*11;
		int y = gp.screenHeight - gp.tileSize*9;

		drawSubWindow(x, y, gp.tileSize*4, gp.tileSize*3);

		x += gp.tileSize;
		y += gp.tileSize*0.85;

		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(24f));

		String options[] = {"Buy", "Sell", "Leave"};
		for (int i = 0; i < options.length; i++) {
			if (selectedOption == i) {
				g2.setColor(Color.white);
				g2.drawString(">", x - space/2, y + space*i);
			}
			else g2.setColor(Color.gray);
			g2.drawString(options[i], x, y + space*i);
		}
	}
	public void trade_sell() {

		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, true);

		// DRAW NPC INVENTORY
		drawInventory(npc, false);

		x = itemInfosWindow.getWidth() + (int) (gp.tileSize * 6.25);
		y = itemInfosWindow.getHeight() + (int) (gp.tileSize * 0.25);
		g2.drawImage(price_coins, x, y, null);
		g2.setFont(g2.getFont().deriveFont(20f));
		g2.setColor(Color.black);

		String priceText = selectedItem.price + "";
		if(selectedItem.price < 0) priceText = "Untradeable !";
		g2.drawString("price: " + priceText, x + 30, y + 25);
		g2.drawString("coins: " + gp.player.coins, x + 30, y + 70);

		if (trade) {
			int itemIndex = getItemIndexInventory(gp.player);
			if (itemIndex < gp.player.inventory.size() && !gp.player.isEquipedItem(gp.player.inventory.get(itemIndex)) && gp.player.inventory.get(itemIndex).price >= 0) {
				gp.player.coins += gp.player.inventory.get(itemIndex).price;
				gp.player.inventory.remove(gp.player.inventory.get(itemIndex));

			}
			trade = false;
		}
		gp.player.coinsByType = gp.ut.calculerPieces(gp.player.coins);
	}
	public void trade_buy() {

		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, false);

		// DRAW NPC INVENTORY
		drawInventory(npc, true);

		x = itemInfosWindow.getWidth() + (int) (gp.tileSize * 0.25);
		y = itemInfosWindow.getHeight() + (int) (gp.tileSize * 0.25);
		g2.drawImage(price_coins, x, y, null);
		g2.setFont(g2.getFont().deriveFont(20f));
		g2.setColor(Color.black);

		String priceText = selectedItem.price + "";
		if(selectedItem.price < 0) priceText = "Untradeable !";
		g2.drawString("price: " + priceText, x + 30, y + 25);
		g2.drawString("coins: " + gp.player.coins, x + 30, y + 70);

		if (trade) {

			int itemIndex = getItemIndexInventory(npc);
			if (itemIndex < npc.inventory.size()) {
				if (npc.inventory.get(itemIndex).price > gp.player.coins) {
					subStateScreen = 0;
					currentDialogue = "You don't have enough coins !";
					gp.gameState = gp.dialogueState;
				} else if (gp.player.inventory.size() == gp.player.maxInventorySize) {
					subStateScreen = 0;
					currentDialogue = "Your inventory is full !";
					gp.gameState = gp.dialogueState;
				} else {
					gp.player.coins -= npc.inventory.get(itemIndex).price;
					gp.player.inventory.add(npc.inventory.get(itemIndex).clone());
				}
			}
			trade = false;
		}
		gp.player.coinsByType = gp.ut.calculerPieces(gp.player.coins);
	}
}

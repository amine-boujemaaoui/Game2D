package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.ArrayList;

import item.ARMR.ARMR_Boots_Leather;
import item.ARMR.ARMR_Chestplate_Leather;
import item.ARMR.ARMR_Helmet_Leather;
import item.ARMR.ARMR_Leggings_Leather;
import item.ITM.Potion.ITM_Potion_Healing;
import item.ITM.Potion.ITM_Potion_Mana;
import item.TOOL.TOOL_Axe_Wood;
import item.TOOL.TOOL_Pickaxe_Wood;
import item.WPN.WPN_Shield_Wood;
import item.WPN.WPN_Sword_Iron;
import item.WPN.WPN_Sword_Wood;
import main.GamePanel;
import main.KeyHandler;
import projectile.PRJ_Fireball;
import projectile.PRJ_Waterball;

public class Player extends Entity {
	
	Random r = new Random();
	KeyHandler keyH;
	public final int screenX, screenY;
	public int caracterClass = -1;
	public int staminaCounter = 0;
	public int manaCounter = 0;
	public int dashCounter = 0;
	public boolean canDash = true;
	public boolean running = false;
	public int actualSpeed = speed;
	public boolean attackCanceled = false;
	public boolean hit = false;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		this.keyH = keyH;
		
		size = size1by2;
		type = gp.typePLY;
		
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		
		hitBox.x     = 9;  hitBox.y      = -4;
		hitBox.width = 33; hitBox.height = 32;
		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		attackHitBox.x = 32;	 attackHitBox.y = 32;
		attackHitBox.width = 34; attackHitBox.height = 26;
		
		setDefaultValues();
		getImages();
		getAttackImages();
		setItems();
	}
	public void setDefaultValues() {
		
		worldX = (int)(18   * gp.tileSize);
		worldY = (int)(40   * gp.tileSize);
		defaultSpeed = 3; speed = defaultSpeed;
		direction = "right";
		level = 0; 
		exp = 0; 
		nextLevelExp = 2;
		defaultAttackSpeed = 7; attackSpeed = defaultAttackSpeed;
		
		coins = 0;
		coinsByType = gp.ut.calculerPieces(coins);
		
		setAttack();
		setToughness();
		setMovementSpeed();
		setAttackSpeed();
	}
	public void retry() {

		worldX = (int)(29   * gp.tileSize);
		worldY = (int)(24.5 * gp.tileSize);

		health = maxHealth;
		mana = maxMana;
		stamina = maxStamina;

		running = false;
		canDash = true;
		dashCounter = 0;
		staminaCounter = 0;
		manaCounter = 0;

		coins -= 200;
		exp -= 100;
		if(exp < 0) exp = 0;
		coinsByType = gp.ut.calculerPieces(coins);
	}
	public void setAttack() {
		
		double totalMeleValue = 1;
		if(slotMele != null) totalMeleValue += slotMele.attackValue;
		
		attack = (int)(strenght * totalMeleValue/10);
	}
	public void setToughness() {
		
		double totalToughnessValue = 1;
		if(slotHelmet     != null) totalToughnessValue += slotHelmet.toughnessValue;
		if(slotChestplate != null) totalToughnessValue += slotChestplate.toughnessValue;
		if(slotLeggings   != null) totalToughnessValue += slotLeggings.toughnessValue;
		if(slotBoots      != null) totalToughnessValue += slotBoots.toughnessValue;

		toughness = (int)(defense * totalToughnessValue/10);
	}
	public void setAttackSpeed() {
		
		int totalAttackSpeedValue = defaultAttackSpeed;
		if(slotMele != null) totalAttackSpeedValue = slotMele.attackSpeedValue;
		
		attackSpeed = totalAttackSpeedValue;
	}
	public void setMovementSpeed() {
		
		speed = defaultSpeed;
		
		if(slotHelmet     != null)   speed += slotHelmet.speedValue;
		if(slotChestplate != null)   speed += slotChestplate.speedValue;
		if(slotLeggings   != null)   speed += slotLeggings.speedValue;
		if(slotBoots      != null) {
			
		 if(slotHelmet     == null ||
		    slotChestplate == null ||
		    slotLeggings   == null  ) speed += slotBoots.speedValue;
		 
		 else speed += slotBoots.speedValue/2;
		}
		
	}
	public void setClassStats() {
		
		maxHealth   = gp.statsValues[caracterClass][0]; health  = maxHealth;
		maxMana     = gp.statsValues[caracterClass][1]; mana    = maxMana;
		maxStamina  = gp.statsValues[caracterClass][2]; stamina = maxStamina;
		defense     = gp.statsValues[caracterClass][3];
		strenght    = gp.statsValues[caracterClass][4];
		inteligence = gp.statsValues[caracterClass][5];
		
		if(caracterClass == 3 || caracterClass == 4) {
			slotProjectiles[0] = new PRJ_Fireball(gp); 
			slotProjectiles[1] = new PRJ_Waterball(gp);
		}
		
		setAttack();
		setToughness();
	}
	public int getClassStats(int i) {
		
		switch(i) {
		case 0: return health;
		case 1: return mana;
		case 2: return stamina;
		case 3: return defense;
		case 4: return strenght;
		case 5: return inteligence;
		case 6: return maxHealth;
		case 7: return maxMana;
		case 8: return maxStamina;
		}
		return -1;
	}
	public int getPlayerStats(int i) {
		
		switch(i) {
		case 0: return attack;
		case 1: return attackSpeed;
		case 2: return speed;
		case 3: return toughness;
		}
		return -1;
	}
	public void getImages() {
		
		int spritesNum = 6;
		
		up_still    = new BufferedImage[spritesNum]; up_walking    = new BufferedImage[spritesNum];
		down_still  = new BufferedImage[spritesNum]; down_walking  = new BufferedImage[spritesNum];
		left_still  = new BufferedImage[spritesNum]; left_walking  = new BufferedImage[spritesNum]; 
		right_still = new BufferedImage[spritesNum]; right_walking = new BufferedImage[spritesNum];
		
		for (int i = 0; i < spritesNum; i++) up_still[i]    = gp.ut.setup("/player/up/still/"    + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_still[i]  = gp.ut.setup("/player/down/still/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_still[i]  = gp.ut.setup("/player/left/still/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) right_still[i] = gp.ut.setup("/player/right/still/" + (i+1), gp.tileSize, gp.tileSize*2);
		
		for (int i = 0; i < spritesNum; i++) up_walking[i]    = gp.ut.setup("/player/up/walking/"   + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) down_walking[i]  = gp.ut.setup("/player/down/walking/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) left_walking[i]  = gp.ut.setup("/player/left/walking/"  + (i+1), gp.tileSize, gp.tileSize*2);
		for (int i = 0; i < spritesNum; i++) right_walking[i] = gp.ut.setup("/player/right/walking/" + (i+1), gp.tileSize, gp.tileSize*2);
	}
	public void getAttackImages() {
		
		int spritesNum = 4;
		
		up_attack   = new BufferedImage[spritesNum+2]; down_attack   = new BufferedImage[spritesNum+2];
		left_attack = new BufferedImage[spritesNum+2]; right_attack  = new BufferedImage[spritesNum+2];
		
		
		if(slotMele != null) {
			
			for (int i = 0; i < spritesNum; i++) up_attack[i]    = gp.ut.setup("/player/up/sword/"    + (i+1), gp.tileSize*3, gp.tileSize*2);
			for (int i = 0; i < spritesNum; i++) down_attack[i]  = gp.ut.setup("/player/down/sword/"  + (i+1), gp.tileSize*3, gp.tileSize*2);
			for (int i = 0; i < spritesNum; i++) left_attack[i]  = gp.ut.setup("/player/left/sword/"  + (i+1), gp.tileSize*3, gp.tileSize*2);
			for (int i = 0; i < spritesNum; i++) right_attack[i] = gp.ut.setup("/player/right/sword/" + (i+1), gp.tileSize*3, gp.tileSize*2);
			
			up_attack[spritesNum]      = gp.ut.setup("/player/up/sword/"    + (spritesNum-1), gp.tileSize*3, gp.tileSize*2);
			down_attack[spritesNum]    = gp.ut.setup("/player/down/sword/"  + (spritesNum-1), gp.tileSize*3, gp.tileSize*2);
			left_attack[spritesNum]    = gp.ut.setup("/player/left/sword/"  + (spritesNum-1), gp.tileSize*3, gp.tileSize*2);
			right_attack[spritesNum]   = gp.ut.setup("/player/right/sword/" + (spritesNum-1), gp.tileSize*3, gp.tileSize*2);
			up_attack[spritesNum+1]    = gp.ut.setup("/player/up/sword/"    + (spritesNum-2), gp.tileSize*3, gp.tileSize*2);
			down_attack[spritesNum+1]  = gp.ut.setup("/player/down/sword/"  + (spritesNum-2), gp.tileSize*3, gp.tileSize*2);
			left_attack[spritesNum+1]  = gp.ut.setup("/player/left/sword/"  + (spritesNum-2), gp.tileSize*3, gp.tileSize*2);
			right_attack[spritesNum+1] = gp.ut.setup("/player/right/sword/" + (spritesNum-2), gp.tileSize*3, gp.tileSize*2);
		}
		else {
			
			for (int i = 0; i < spritesNum; i++) up_attack[i]    = gp.ut.setup("/player/up/fist/"    + (i+1), gp.tileSize*3, gp.tileSize*2);
			for (int i = 0; i < spritesNum; i++) down_attack[i]  = gp.ut.setup("/player/down/fist/"  + (i+1), gp.tileSize*3, gp.tileSize*2);
			for (int i = 0; i < spritesNum; i++) left_attack[i]  = gp.ut.setup("/player/left/fist/"  + (i+1), gp.tileSize*3, gp.tileSize*2);
			for (int i = 0; i < spritesNum; i++) right_attack[i] = gp.ut.setup("/player/right/fist/" + (i+1), gp.tileSize*3, gp.tileSize*2);
			
			up_attack[spritesNum]      = gp.ut.setup("/player/up/fist/"    + (spritesNum-1), gp.tileSize*3, gp.tileSize*2);
			down_attack[spritesNum]    = gp.ut.setup("/player/down/fist/"  + (spritesNum-1), gp.tileSize*3, gp.tileSize*2);
			left_attack[spritesNum]    = gp.ut.setup("/player/left/fist/"  + (spritesNum-1), gp.tileSize*3, gp.tileSize*2);
			right_attack[spritesNum]   = gp.ut.setup("/player/right/fist/" + (spritesNum-1), gp.tileSize*3, gp.tileSize*2);
			up_attack[spritesNum+1]    = gp.ut.setup("/player/up/fist/"    + (spritesNum-2), gp.tileSize*3, gp.tileSize*2);
			down_attack[spritesNum+1]  = gp.ut.setup("/player/down/fist/"  + (spritesNum-2), gp.tileSize*3, gp.tileSize*2);
			left_attack[spritesNum+1]  = gp.ut.setup("/player/left/fist/"  + (spritesNum-2), gp.tileSize*3, gp.tileSize*2);
			right_attack[spritesNum+1] = gp.ut.setup("/player/right/fist/" + (spritesNum-2), gp.tileSize*3, gp.tileSize*2);
		}
	}
	public void setItems() {
		
		inventory.add(new WPN_Sword_Wood(gp));

		coins = 300;
		coinsByType = gp.ut.calculerPieces(coins);
	}
	public void pickUpItm(int index) {
		
		if(index != 999) {
			
			//PICKUP ONLY 
			if(gp.itm[gp.currentMap][index].pickupOnly) {
				gp.itm[gp.currentMap][index].use(this);
				gp.itm[gp.currentMap][index] = null;
			}
			
			// INVENTORY
			else {
				
				if(inventory.size() != maxInventorySize) {
					
					inventory.add(gp.itm[gp.currentMap][index]);
					gp.playSE(7);
					
					gp.ui.addEventMessage("picked up " + gp.itm[gp.currentMap][index].name,
							12f, 
							Font.BOLD, 
							Color.white, 
							Color.gray, 
							//screenX + gp.tileSize,
							//screenY + gp.tileSize,
								gp.tileSize*2,
							 gp.screenHeight - gp.tileSize*4,
							true);
					
					gp.itm[gp.currentMap][index] = null;
				}
			}
		}
	}
	public void interactNPC(int index) {
		
		if(index != 999) {
			attackCanceled = true;
			gp.ui.showMessage("E", "to talk to " + gp.npc[gp.currentMap][index].name, gp.npc[gp.currentMap][index].worldX, gp.npc[gp.currentMap][index].worldY);
			if(keyH.eventPressed) {
				gp.npc[gp.currentMap][index].interact();
				//gp.gameState = gp.dialogueState;
				//gp.npc[gp.currentMap][index].speak();
			}
		} 
	}
	public void interactMON(int index) {
		
		if(index != 999) {
			if(!gp.mon[gp.currentMap][index].dying && !invincible && health > 0) {
				
				gp.playSE(15); 
				
				int damage = gp.mon[gp.currentMap][index].attack - toughness;
				if(damage < 0) damage = 0;
				
				if(slotHelmet     != null) slotHelmet.durability--;
				if(slotChestplate != null) slotChestplate.durability--;
				if(slotLeggings   != null) slotLeggings.durability--;
				if(slotBoots      != null) slotBoots.durability--;

				modifyStat("health", -damage);
				invincible = true;
			}
		}
	}
	public void interactOBJ(int index) {
		
		if(index != 999) {
			
		}
	}
	public void interactIT(int index) {
		
		if(index != 999) {
			if(gp.it[gp.currentMap][index].destructible && gp.it[gp.currentMap][index].correctToolUsed(slotAxe)) {
				
				generateParticle(gp.it[gp.currentMap][index], gp.it[gp.currentMap][index]);

				gp.it[gp.currentMap][index].modifyStat("health", -slotAxe.attackValue);
				if(gp.it[gp.currentMap][index].health <= 0) gp.it[gp.currentMap][index] = gp.it[gp.currentMap][index].afterDestroy;
			}
		}
	}
	public void damageMonster(int index, int attack, boolean spell) {
		
		if(index != 999) {
			
		
		if(!gp.mon[gp.currentMap][index].invincible) {
				
				gp.playSE(14);
				
				int damage = attack - gp.mon[gp.currentMap][index].toughness;
				if(damage < 0) damage = 0;
				
				int offsetX = 4;
				
				gp.ui.addEventMessage("-" + damage, 
						              24f, 
						              Font.BOLD, 
						              gp.ui.evtColM_damage, 
						              gp.ui.evtColS_damage, 
						              gp.mon[gp.currentMap][index].worldX + offsetX + r.nextInt(-8, 9),
						              gp.mon[gp.currentMap][index].worldY + r.nextInt(-8, 9),
						              false);
			
				if (gp.mon[gp.currentMap][index].health - damage <= 0) gp.mon[gp.currentMap][index].health = 0;
				else gp.mon[gp.currentMap][index].modifyStat("health", -damage);
				
				gp.mon[gp.currentMap][index].invincible = true;
				if(!spell) {
					if(slotMele != null) {
						slotMele.durability--;
						if(slotMele.durability <= 0) { 
							gp.playSE(16); 
							gp.player.inventory.remove(slotMele); 
							slotMele = null; 
							setAttack(); setAttackSpeed(); 
						}
					}
				}
			}
			if(gp.mon[gp.currentMap][index].health <= 0) gp.mon[gp.currentMap][index].dying = true;
		}
	}
	public void checkLevel() {
		
		if(exp >= nextLevelExp) { 
			gp.playSE(10);
			String leveledUp = "Leveld UP!";
			gp.ui.addEventMessage(leveledUp, 
			           32f, 
			           Font.BOLD, 
			           gp.ui.evtColM_exp, 
			           gp.ui.evtColS_exp, 
			           gp.ui.getXforCenteredText(leveledUp) - 10, 
			           gp.screenHeight/2 - (int)(gp.tileSize*1.5),
			           true);
			
			level++; 
			exp = exp-nextLevelExp; 
			nextLevelExp *= 2;
			if(level%2 == 0) {
				maxHealth += 2;
				maxStamina += 2;
				if(maxMana > 0) maxMana += 2;
				attack++;
				defense++;
				strenght++;
				
				setAttack();
				setToughness();
			}
		}
	}
	public void attacking() {
		
		spriteCounter++; 
		if(spriteCounter < attackSpeed * 1) {
			
			spriteNum = 1;
	     
	     	int currentWorldX = worldX,       currentWorldY = worldY;
			int hitBoxWidth   = hitBox.width, hitBoxHeight  = hitBox.height;
			
			switch(direction) {
			case "up":    worldY -= attackHitBox.height; break;
			case "down":  worldY += attackHitBox.height; break;
			case "left":  worldX -= attackHitBox.width;  break;
			case "right": worldX += attackHitBox.width;  break;
			default: break;
			}
			
			hitBox.width  = attackHitBox.width;
			hitBox.height = attackHitBox.height;
			
			int monIndex = gp.cChecker.checkEntity(this, gp.mon[gp.currentMap]);
			if(monIndex != 999 && stamina > 0) { damageMonster(monIndex, attack, false); hit = true; }
			
			worldX = currentWorldX;     worldY = currentWorldY;
			hitBox.width = hitBoxWidth; hitBox.height = hitBoxHeight;
		}
		else if(spriteCounter < attackSpeed * 2) spriteNum = 2; 
		else if(spriteCounter < attackSpeed * 3) spriteNum = 3;
		else if(spriteCounter < attackSpeed * 4) spriteNum = 4;
		else {
			spriteNum = 1; spriteCounter = 0; attacking = false; 
			if(hit) stamina--; hit = false; 
		}	
	}
	public void useTool() {
		
		spriteCounter++; 
		if(spriteCounter < attackSpeed * 1) {
			
			spriteNum = 1;
	     
	     	int currentWorldX = worldX,       currentWorldY = worldY;
			int hitBoxWidth   = hitBox.width, hitBoxHeight  = hitBox.height;
			
			switch(direction) {
			case "up":    worldY -= attackHitBox.height; break;
			case "down":  worldY += attackHitBox.height; break;
			case "left":  worldX -= attackHitBox.width;  break;
			case "right": worldX += attackHitBox.width;  break;
			default: break;
			}
			
			hitBox.width  = attackHitBox.width;
			hitBox.height = attackHitBox.height;
			
			int itIndex = gp.cChecker.checkEntity(this, gp.it[gp.currentMap]);
			if(itIndex != 999 && stamina > 0 && !hit) { interactIT(itIndex); hit = true; }
			
			worldX = currentWorldX;     worldY = currentWorldY;
			hitBox.width = hitBoxWidth; hitBox.height = hitBoxHeight;
		}
		else if(spriteCounter < attackSpeed * 2) spriteNum = 2; 
		else if(spriteCounter < attackSpeed * 3) spriteNum = 3;
		else if(spriteCounter < attackSpeed * 4) spriteNum = 4;
		else {
			spriteNum = 1; spriteCounter = 0; usingTool = false; 
			if(hit) stamina--; hit = false; 
		}
	}
	public void castingSpell() {
		
		
		spriteCounter++; 
		     if(spriteCounter < attackSpeed * 1) spriteNum = 1;
		else if(spriteCounter < attackSpeed * 2) spriteNum = 2;
		else if(spriteCounter < attackSpeed * 3) spriteNum = 3;
		else if(spriteCounter < attackSpeed * 4) spriteNum = 4;
		else { spriteNum = 1; spriteCounter = 0; castingSpell = false; } 
	}
	public void selectItem() {
		
		boolean changed = false;
		int itemIndex = gp.ui.getItemIndexInventory(this);
		
		if(itemIndex < inventory.size()) {
			
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == gp.typeWPN) {
				
					 if(selectedItem.subType == gp.subType_WPN_SW) { if(selectedItem != slotMele)   slotMele   = selectedItem; else slotMele   = null; changed = true; }
				else if(selectedItem.subType == gp.subType_WPN_SH) { if(selectedItem != slotShield) slotShield = selectedItem; else slotShield = null; changed = true; }
			    if(changed) { gp.playSE(19); setAttack(); setAttackSpeed(); getAttackImages(); }
			} 
			else if(selectedItem.type == gp.typeARMR) {
				
					if(selectedItem.subType == gp.subType_ARMR_H) { if(selectedItem != slotHelmet)     slotHelmet     = selectedItem; else slotHelmet     = null; changed = true; }
			   else if(selectedItem.subType == gp.subType_ARMR_C) { if(selectedItem != slotChestplate) slotChestplate = selectedItem; else slotChestplate = null; changed = true; }
			   else if(selectedItem.subType == gp.subType_ARMR_L) { if(selectedItem != slotLeggings)   slotLeggings   = selectedItem; else slotLeggings   = null; changed = true; }
			   else if(selectedItem.subType == gp.subType_ARMR_B) { if(selectedItem != slotBoots)      slotBoots      = selectedItem; else slotBoots      = null; changed = true; }
			   if(changed) { gp.playSE(18); setToughness(); setMovementSpeed(); }
			}	
			else if(selectedItem.type == gp.typeITM) {
				
				if(selectedItem.use(this)) inventory.remove(selectedItem);
			}
			else if(selectedItem.type == gp.typeTOOL) {
				     if(selectedItem.subType == gp.subType_TOOL_AXE) { if(selectedItem != slotAxe    ) slotAxe     = selectedItem; else slotAxe     = null; changed = true; }
				else if(selectedItem.subType == gp.subType_TOOL_PKX) { if(selectedItem != slotPickaxe) slotPickaxe = selectedItem; else slotPickaxe = null; changed = true; }
				if(changed) { gp.playSE(19); }
			}
		}
	}
	public boolean isEquipedItem(Entity selectedItem) {
		if( selectedItem != slotHelmet           &&
			selectedItem != slotChestplate       &&
			selectedItem != slotLeggings         &&
			selectedItem != slotBoots            &&
			selectedItem != slotMele             &&
			selectedItem != slotShield           &&
			selectedItem != slotStaff            &&
			selectedItem != slotProjectileWeapon &&
			selectedItem != slotRing1            &&
			selectedItem != slotRing2            &&
			selectedItem != slotNecklace         &&
			selectedItem != slotBelt             &&
			selectedItem != slotPickaxe          &&
			selectedItem != slotAxe              )
			return false;
		return true;
	}
	public void dropItem() {
		
		int itemIndex = gp.ui.getItemIndexInventory(this);
		
		if(itemIndex < inventory.size()) {
			
			Entity selectedItem = inventory.get(itemIndex);
			
			if( isEquipedItem(selectedItem) ) {
				for(int i = 0; i < gp.itm[gp.currentMap].length; i++) {
					if(gp.itm[gp.currentMap][i] == null) {
						gp.itm[gp.currentMap][i] = selectedItem;
						
						selectedItem.worldX = worldX + gp.r.nextInt(-8, 9);
						selectedItem.worldY = worldY + gp.r.nextInt(-8, 9);
						
						switch(direction) {
						case "up":    selectedItem.worldY -= 12 + selectedItem.hitBox.height; break;
						case "down":  selectedItem.worldY += 12 + hitBox.height; break;
						case "left":  selectedItem.worldX -= 12 + selectedItem.hitBox.width;  break;
						case "right": selectedItem.worldX += 12 + hitBox.width; break;
						default: break;
						}
						
						inventory.remove(selectedItem);
						break;
					}
				}
			}
				
		}
	}
	public void update() {
		
		if(attacking) {
			attacking();
		}
		else if(castingSpell) {
			castingSpell();
		}
		else if(usingTool) {
			useTool();
		}
		else {

			// CHECK ITM COLLISIONS
			int itmIndex = gp.cChecker.checkItem(this, true);
			pickUpItm(itmIndex);
			
			// CHECK OBJ COLLISIONS
			int objIndex = gp.cChecker.checkObject(this, true);
			interactOBJ(objIndex);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc[gp.currentMap]);
			interactNPC(npcIndex);
			
			// CHECK NPC COLLISION
			int monIndex = gp.cChecker.checkEntity(this, gp.mon[gp.currentMap]);
			interactMON(monIndex);
			
			// CHECK IT TILES COLLISION
			int itIndex = gp.cChecker.checkEntity(this, gp.it[gp.currentMap]);
			//interactIT(itIndex);
			
			// CHECK EVENT COLLISION
			gp.eventH.checkEvent();
			
			// CHECK COLLISION
			actualSpeed = speed;
			if((caracterClass != 3 && caracterClass != 4) && dashCounter < 20 && !canDash) actualSpeed *= 2;
			if(running && stamina > 0) actualSpeed = (int)(actualSpeed*1.70);
			gp.cChecker.checkTile(this);
			if(!collisionOn && walking && !keyH.eventPressed) {
				switch(direction) {
				case "up":    worldY -= actualSpeed; break;
				case "down":  worldY += actualSpeed; break;
				case "left":  worldX -= actualSpeed; break;
				case "right": worldX += actualSpeed; break;
				default: break;
				}
			}

			if(keyH.dashPressed && (caracterClass != 3 && caracterClass != 4)) {
				actualSpeed = 10;
				boolean tempWalking = walking;
				walking = false;
				String tempDirection = direction;
				if (keyH.upPressed   ) direction = "up";
				if (keyH.downPressed ) direction = "down";
				if (keyH.leftPressed ) direction = "left";
				if (keyH.rightPressed) direction = "right";
				gp.cChecker.checkTile(this);
				gp.cChecker.checkEntity(this, gp.npc[gp.currentMap]);
				gp.cChecker.checkEntity(this, gp.mon[gp.currentMap]);
				     if (keyH.upPressed    && dashCounter < 10 && !collisionOn && stamina > 1) { worldY -= actualSpeed; if(canDash)stamina -= 2; canDash = false; }
				else if (keyH.downPressed  && dashCounter < 10 && !collisionOn && stamina > 1) { worldY += actualSpeed; if(canDash)stamina -= 2; canDash = false; }
				else if (keyH.leftPressed  && dashCounter < 10 && !collisionOn && stamina > 1) { worldX -= actualSpeed; if(canDash)stamina -= 2; canDash = false; }
				else if (keyH.rightPressed && dashCounter < 10 && !collisionOn && stamina > 1) { worldX += actualSpeed; if(canDash)stamina -= 2; canDash = false; }

				direction = tempDirection;
				walking = tempWalking;
			} else {

				if (keyH.upPressed    && !keyH.downPressed ) { if(keyH.shiftPressed && stamina > 0) running = true; direction = "up";    walking = true; }
				if (keyH.downPressed  && !keyH.upPressed   ) { if(keyH.shiftPressed && stamina > 0) running = true; direction = "down";  walking = true; }
				if (keyH.leftPressed  && !keyH.rightPressed) { if(keyH.shiftPressed && stamina > 0) running = true; direction = "left";  walking = true; }
				if (keyH.rightPressed && !keyH.leftPressed ) { if(keyH.shiftPressed && stamina > 0) running = true; direction = "right"; walking = true; }

				if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) walking = false;
				if (!keyH.shiftPressed) running = false;
			}


			if(keyH.attackPressed && !attackCanceled) {
				attacking = true; 
				gp.playSE(13);
				spriteCounter = 0;
			}
			
			if(keyH.toolPressed && !attackCanceled) {
				usingTool = true; 
				gp.playSE(13);
				spriteCounter = 0;
			}
			
			attackCanceled = false;
			collisionOn = false;
			
			// SPRITE COUNTER FOR ANIMATION
			spriteCounter++;
			if(spriteCounter > 10 - speed) {
				spriteNum++; if(spriteNum > 6) spriteNum = 1;
				spriteCounter = 0;
			}
			
			// SPRITE COUNTER FOR SOUND
			soundCounter++;
			if(soundCounter > 26 - (int)(actualSpeed*1.70)) {
				if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)
					gp.playSE(11); 
				soundCounter = 0;
			}

			// STAMINA COUNTER
			staminaCounter++;
			if(staminaCounter > 90) {
				if(stamina > 0 && running && walking) stamina--;
				else if((!running || !walking) && stamina < maxStamina) stamina++;
				else if(running && stamina == 0) stamina++;
				staminaCounter = 0;
			}

			// MANA COUNTER
			manaCounter++;
			if(manaCounter > 380 && (caracterClass == 3 || caracterClass == 4)) {
				if (mana < maxMana) mana++;
				manaCounter = 0;
			}
			
			keyH.eventPressed = false;
			keyH.enterPressed = false;
		}
		
		
		if (keyH.spellPressed && (caracterClass == 3 || caracterClass == 4) && slotProjectiles[keyH.spell] != null && !slotProjectiles[keyH.spell].alive && projectileCounter[keyH.spell] == slotProjectiles[keyH.spell].spellCooldown) {
			if(mana >= slotProjectiles[keyH.spell].useCost) {
				castingSpell = true;



				slotProjectiles[keyH.spell].set(worldX, worldY, direction, true, this);
				if(!gp.projectileList.contains(slotProjectiles[keyH.spell]))
					gp.projectileList.add(slotProjectiles[keyH.spell]);
				gp.playSE(13);
				mana -= slotProjectiles[keyH.spell].useCost;
				projectileCounter[keyH.spell] = 0;
			}
		}
		
		// INVINCIBLE COUNTER
		if(invincible) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		// DASH COUNTER
		if((caracterClass != 3 && caracterClass != 4) && !canDash) {
			dashCounter++;
			if(dashCounter > 120) {
				canDash = true;
				dashCounter = 0;
			}
		}
		
		// PROJECTILE COUNTER
		for(int i = 0; i < 3; i++) 
			if(slotProjectiles[i] != null) 
				if((caracterClass == 3 || caracterClass == 4) && projectileCounter[i] < slotProjectiles[i].spellCooldown) 
					projectileCounter[i]++;

		if (health <= 0) {
			gp.playSE(12);
			gp.ui.selectedOption = -1;
			gp.gameState = gp.gameOverState;
		}
	}
	@Override
	public void draw(Graphics2D g2, GamePanel gp) {
		
		BufferedImage image = null;
		int width = gp.tileSize, height = gp.tileSize*2, newScreenX = screenX, newScreenY = screenY - gp.tileSize;
		if(attacking || castingSpell || usingTool) { newScreenX = screenX - gp.tileSize; width = gp.tileSize*3; }
		
		if (!walking) {
			switch(direction) {
			case "up":    if(attacking || castingSpell || usingTool) image = up_attack[spriteNum-1];    else image = up_still[spriteNum-1];    break;
			case "down":  if(attacking || castingSpell || usingTool) image = down_attack[spriteNum-1];  else image = down_still[spriteNum-1];  break;
			case "left":  if(attacking || castingSpell || usingTool) image = left_attack[spriteNum-1];  else image = left_still[spriteNum-1];  break;
			case "right": if(attacking || castingSpell || usingTool) image = right_attack[spriteNum-1]; else image = right_still[spriteNum-1]; break;
			}
		} else {
			switch(direction) {
			case "up":    if(attacking || castingSpell || usingTool) image = up_attack[spriteNum-1];    else image = up_walking[spriteNum-1];    break;
			case "down":  if(attacking || castingSpell || usingTool) image = down_attack[spriteNum-1];  else image = down_walking[spriteNum-1];  break;
			case "left":  if(attacking || castingSpell || usingTool) image = left_attack[spriteNum-1];  else image = left_walking[spriteNum-1];  break;
			case "right": if(attacking || castingSpell || usingTool) image = right_attack[spriteNum-1]; else image = right_walking[spriteNum-1]; break;
			}
		}
		if(invincible) { 
			if((spriteNum-1)%2 == 0) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); 
			else                     g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		g2.drawImage(image, newScreenX, newScreenY, width, height, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		// DEBUG: PRINT PLAYER TILESIZE AND HITBOX 
		if(keyH.debug) {
			g2.setColor(new Color(0, 0, 255, 90));
			g2.fillRect(newScreenX , newScreenY, width, height);
			g2.setColor(new Color(0, 255, 0, 100));
			g2.fillRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);
		}
	}
}

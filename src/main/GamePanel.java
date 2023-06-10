package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	public Random r = new Random();
	
	// SCREEN SETTINGS
	final int originalTileSize = 16;
	public final int scale = 3;
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 32;
	public final int maxScreenRow = 18;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	final int FPS = 60;
	public int drawFPS;

	// HANDLERS
	public EventHandler eventH = new EventHandler(this);
	public KeyHandler keyH = new KeyHandler(this);
	public UtilityTool ut = new UtilityTool();
	public TileManager tileM= new TileManager(this);
	public CollisionChecker cChecker= new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public Sound music = new Sound();
	public Sound se = new Sound();
	public UI ui = new UI(this);
	Thread gameThread;
	
	//PLAYER AND OBJ
	public Player player = new Player(this, keyH);
	public ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public Entity obj[] = new Entity[20];
	public Entity npc[] = new Entity[10];
	public Entity mon[] = new Entity[20];
	public Entity itm[] = new Entity[40];
	
	// WORLD SETTINGS
	public final int maxWorldCol = 61;
	public final int maxWorldRow = 57;
	
	// GAME STATES
	public int gameState;
	public final int titleScreenState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int equipmentWindowState = 4;
	
	// TYPES
	public final int typePLY = 0;
	public final int typeOBJ = 1;
	public final int typeNPC = 2;
	public final int typeMON = 3;
	public final int typeITM = 4;
	
	public final int typeARMR = 5;
	public final int subType_ARMR_H = 6;
	public final int subType_ARMR_C = 7;
	public final int subType_ARMR_L = 8;
	public final int subType_ARMR_B = 9;
	
	public final int typeWPN = 10;
	public final int subType_WPN_SH = 11;
	public final int subType_WPN_SW = 12;
	
	public final int typePRJ = 13;
	
	public int setSpeedCounter = 0;
	public boolean startSetSpeedCounter = false;
	
	public String classSelectionOptions[] = {"KNIGHT", "THIEF", "ARCHER", "WIZARD", "SORCELER"};
	public String playerStatsTitles[] = {"Attack", "Atk Speed", "Speed", "Toughness"};
	public String statsTitles[] = {"Health", "Int", "Sta", "Def", "Mana", "Str"};
	public int statsValues[][] = {{ 8,        3,     6,     8,     0,      8   },
								  { 4,        5,     8,     2,     0,      6   },
								  { 4,        4,     4,     4,     0,      6   },
								  { 6,        8,     4,     2,    10,      2   },
								  { 6,        8,     4,    10,     4,      2   }};

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	public void setupGame() {
		
		aSetter.setITM();
		aSetter.setNPC();
		aSetter.setOBJ();
		aSetter.setMON();
		//playMusic(0);
		gameState = titleScreenState;
	}
	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS, delta = 0;
		long lastTime = System.nanoTime(), currentTime, timer = 0;
		drawFPS = 0;

		while (gameThread != null) {
			currentTime = System.nanoTime(); 
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta > 1) {
				update(); repaint();
				delta--; drawFPS++;
			}
			if ( timer >= 1000000000 ) { drawFPS = 0; timer = 0; }
		}
	}
	public void update() {
		
		if(gameState == playState)  {
			
			// PLAYER
			player.update();
			
			// OBJs
			for (int i = 0; i < obj.length; i++) 
				if(obj[i] != null) obj[i].update();
			
			// NPCs
			for (int i = 0; i < npc.length; i++) 
				if(npc[i] != null) npc[i].update();
			
			// ITMs
			for (int i = 0; i < itm.length; i++) 
				if(itm[i] != null) itm[i].update();
			
			// MONs
			for (int i = 0; i < mon.length; i++) {
				if(mon[i] != null) {
					if(mon[i].alive) mon[i].update();
					else {
						player.exp += mon[i].exp;
						ui.addEventMessage("+" + mon[i].exp, 
								           24f, 
								           Font.BOLD, 
								           ui.evtColM_exp, 
								           ui.evtColS_exp, 
								           mon[i].worldX + 24 + r.nextInt(-8, 9), 
								           mon[i].worldY + 24 + r.nextInt(-8, 9),
								           false);
						
						player.checkLevel();
						mon[i].checkDrop();
						mon[i] = null;
					}
				} 
			}
			
			// PRJs
			for (int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					if(projectileList.get(i).alive) projectileList.get(i).update();
					else projectileList.remove(i);
				} 
			}
		}
		if(gameState == pauseState) {
			
		}
		if(gameState == equipmentWindowState) {
			if(keyH.enterPressed && setSpeedCounter == 0)  { playSE(4); player.selectItem(); startSetSpeedCounter = true; }
			 
			if(setSpeedCounter >= 20) { setSpeedCounter = 0; startSetSpeedCounter = false; } 
			else if (startSetSpeedCounter) setSpeedCounter++;
		}
	}
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if(gameState == titleScreenState) {
			
			ui.draw(g2);
		} else {

			tileM.draw(g2);
			
			entityList.add(player);
			
			for (int i = 0; i < obj.length; i++) 
				if(obj[i] != null) entityList.add(obj[i]);
			
			for (int i = 0; i < itm.length; i++) 
				if(itm[i] != null) entityList.add(itm[i]);
			
			for (int i = 0; i < npc.length; i++) 
				if(npc[i] != null) entityList.add(npc[i]);
			
			for (int i = 0; i < mon.length; i++)
				if(mon[i] != null) entityList.add(mon[i]);
			
			for (int i = 0; i < projectileList.size(); i++)
				if(projectileList.get(i) != null) entityList.add(projectileList.get(i));
				
			Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) {
					return Integer.compare(e1.worldY, e2.worldY);
			}});
			
			for (int i = 0; i < entityList.size(); i++)
				entityList.get(i).draw(g2, this);
			
			entityList.clear();
			
			ui.draw(g2);
			
			if (keyH.debug) {
				g2.setColor(Color.black);
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
				g2.drawString("X: " + (player.worldX/tileSize) + ", Y: " + (player.worldY/tileSize), tileSize, tileSize);
				g2.drawString("FPS: " + drawFPS, tileSize, tileSize + 24); 
				g2.drawString("Invincible: " + player.invincible, tileSize, tileSize + 48);
				g2.drawString("Speed: " + player.speed, tileSize, tileSize + 48 + 24);
			}	
		}
		g2.dispose();
	}
	public void playMusic(int i) {
		music.setVolume(-30f);
		music.setFile(i);
		music.play();
		music.loop();
		music.setVolume(-15f);
	}
	public void stopMusic() {
		
		music.stop();
	}
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
	}
}
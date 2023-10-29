package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.InteractiveTile;
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
	int screenWidthFull = screenWidth;
	int screenHeightFull = screenHeight;
	public boolean fullScreen = true;
	BufferedImage fullScreenImage;
	public Graphics2D g2;
	final int FPS = 60;
	public int drawFPS;
	public int debugFPS;

	// WORLD SETTINGS
	// public int maxWorldCol = 61;
	// public int maxWorldRow = 57;
	public int maxWorldCol = 200;
	public int maxWorldRow = 200;
	public int maxMaps = 2;
	public int currentMap = 0;

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
	Config config = new Config(this);
	Thread gameThread;
	
	//PLAYER AND OBJ
	public Player player = new Player(this, keyH);
	public ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	public Entity obj[][] = new Entity[maxMaps][30];
	public Entity npc[][] = new Entity[maxMaps][10];
	public Entity mon[][] = new Entity[maxMaps][99];
	public Entity itm[][] = new Entity[maxMaps][99];
	public InteractiveTile it[][] = new InteractiveTile[maxMaps][40];
	
	// GAME STATES
	public int gameState;
	public final int titleScreenState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int equipmentWindowState = 4;
	public final int settingsState = 5;
	public final int gameOverState = 6;
	
	// TYPES
	public final int typePLY = 0;

	public final int typeOBJ = 1;
	public final int subType_OBJ_CHEST = 17;

	public final int typeNPC = 2;
	public final int typeMON = 3;

	public final int typeITM = 4;
	public final int subType_ITM_KEY = 18;
	
	public final int typeARMR = 5;
	public final int subType_ARMR_H = 6;
	public final int subType_ARMR_C = 7;
	public final int subType_ARMR_L = 8;
	public final int subType_ARMR_B = 9;
	
	public final int typeWPN = 10;
	public final int subType_WPN_SH = 11;
	public final int subType_WPN_SW = 12;
	
	public final int typePRJ = 13;
	
	public final int typeTOOL = 14;
	public final int subType_TOOL_PKX = 15;
	public final int subType_TOOL_AXE = 16;
	
	public int setSpeedCounter = 0;
	public boolean startSetSpeedCounter = false;
	
	public String classSelectionOptions[] = {"KNIGHT", "THIEF", "ARCHER", "WIZARD", "SORCELER"};
	public String playerStatsTitles[] = {"Attack", "Atk Speed", "Speed", "Toughness"};
	public String statsTitles[] = {"Health", "Mana", "Sta", "Def", "Str", "Int"};
	public int statsValues[][] = {{ 8,        0,      20,    8,     8,     8   },
								  { 4,        0,       8,    2,     4,     6   },
								  { 4,        0,       4,    4,     4,     6   },
								  { 6,        8,       4,    2,     1,     2   },
								  { 6,        0,       4,    10,    2,     2   }};

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
		aSetter.setIT();
		playMusic(0);
		gameState = titleScreenState;

		fullScreenImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) fullScreenImage.getGraphics();

		if(fullScreen) setFullScreen();
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
		debugFPS = 0;

		while (gameThread != null) {
			currentTime = System.nanoTime(); 
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta > 1) {
				update();
				// repaint();
				drawFullScreenImage();
				drawToScereen();
				delta--; drawFPS++;
			}
			if ( timer >= 1000000000 ) { debugFPS = drawFPS; drawFPS = 0; timer = 0; }
		}
	}
	public void update() {

		if(gameState == titleScreenState) { }
		if(gameState == playState)  {
			
			// PLAYER
			player.update();
			
			// OBJs
			for (int i = 0; i < obj[currentMap].length; i++)
				if(obj[currentMap][i] != null) obj[currentMap][i].update();
			
			// NPCs
			for (int i = 0; i < npc[currentMap].length; i++)
				if(npc[currentMap][i] != null) npc[currentMap][i].update();
			
			// ITMs
			for (int i = 0; i < itm[currentMap].length; i++)
				if(itm[currentMap][i] != null) itm[currentMap][i].update();
			
			// TILEs
			for (int i = 0; i < it[currentMap].length; i++)
				if(it[currentMap][i] != null) it[currentMap][i].update();
			
			// MONs
			for (int i = 0; i < mon[currentMap].length; i++) {
				if(mon[currentMap][i] != null) {
					if(mon[currentMap][i].alive) mon[currentMap][i].update();
					else {
						player.exp += mon[currentMap][i].exp;
						ui.addEventMessage("+" + mon[currentMap][i].exp,
								           24f, 
								           Font.BOLD, 
								           ui.evtColM_exp, 
								           ui.evtColS_exp, 
								           mon[currentMap][i].worldX + 24 + r.nextInt(-8, 9),
								           mon[currentMap][i].worldY + 24 + r.nextInt(-8, 9),
								           false);
						
						player.checkLevel();
						mon[currentMap][i].checkDrop();
						mon[currentMap][i] = null;
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
			
			// Particles
			for (int i = 0; i < particleList.size(); i++) {
				if(particleList.get(i) != null) {
					if(particleList.get(i).alive) particleList.get(i).update();
					else particleList.remove(i);
				} 
			}
		}
		if(gameState == pauseState) { }
		if(gameState == equipmentWindowState) {
			     if((keyH.enterPressed || keyH.spacePressed) && setSpeedCounter == 0)  { playSE(4); player.selectItem(); startSetSpeedCounter = true; }
			else if(keyH.backSpacePressed && setSpeedCounter == 0) { playSE(7); player.dropItem(); startSetSpeedCounter = true; }
			if(setSpeedCounter >= 20) { setSpeedCounter = 0; startSetSpeedCounter = false; }
			if (startSetSpeedCounter) setSpeedCounter++;
		}
		if (gameState == settingsState) {
			if((keyH.enterPressed || keyH.spacePressed) && setSpeedCounter == 0) { playSE(4); ui.selectOption(); startSetSpeedCounter = true; }
			if(setSpeedCounter >= 20) { setSpeedCounter = 0; startSetSpeedCounter = false; }
			if (startSetSpeedCounter) setSpeedCounter++;
		}
	}
	public void drawFullScreenImage() {

		if(gameState == titleScreenState) { }
		else {

			tileM.draw(g2);

			entityList.add(player);

			for (int i = 0; i < it[currentMap].length; i++)
				if(it[currentMap][i] != null) entityList.add(it[currentMap][i]);

			for (int i = 0; i < obj[currentMap].length; i++)
				if(obj[currentMap][i] != null) entityList.add(obj[currentMap][i]);

			for (int i = 0; i < itm[currentMap].length; i++)
				if(itm[currentMap][i] != null) entityList.add(itm[currentMap][i]);

			for (int i = 0; i < npc[currentMap].length; i++)
				if(npc[currentMap][i] != null) entityList.add(npc[currentMap][i]);

			for (int i = 0; i < mon[currentMap].length; i++)
				if(mon[currentMap][i] != null) entityList.add(mon[currentMap][i]);

			Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) {
					return Integer.compare(e1.worldY, e2.worldY);
				}
			});

			for (int i = 0; i < entityList.size(); i++)
				entityList.get(i).draw(g2, this);

			entityList.clear();

			for (int i = 0; i < projectileList.size(); i++)
				if(projectileList.get(i) != null) entityList.add(projectileList.get(i));

			for (int i = 0; i < particleList.size(); i++)
				if(particleList.get(i) != null) particleList.get(i).draw(g2, this);

			if (keyH.debug) {
				g2.setColor(Color.black);
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
				g2.drawString("X: " + (player.worldX/tileSize) + ", Y: " + (player.worldY/tileSize), tileSize, tileSize);
				g2.drawString("FPS: " + debugFPS, tileSize, tileSize + 24);
				g2.drawString("Invincible: " + player.invincible, tileSize, tileSize + 48);
				g2.drawString("Speed: " + player.speed, tileSize, tileSize + 48 + 24);
			}
		}
		ui.draw(g2);
	}
	public void drawToScereen() {

		Graphics g = this.getGraphics();
		g.drawImage(fullScreenImage, 0, 0, screenWidthFull, screenHeightFull, null);
		g.dispose();
	}
	public void setFullScreen() {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		screenWidthFull = Main.window.getWidth();
		screenHeightFull = Main.window.getHeight();

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
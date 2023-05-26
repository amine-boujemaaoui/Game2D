package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	// SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3;
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
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	public Entity mon[] = new Entity[20];
	
	// WORLD SETTINGS
	public final int maxWorldCol = 61;
	public final int maxWorldRow = 57;
	
	// GAME STATES
	public int gameState;
	public final int titleScreenState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	
	// ENTIRY TYPES
	public final int typePlayer = 0;
	public final int typeObject = 1;
	public final int typeBigObject = 2;
	public final int typeMonster = 3;
	public final int typeNPC = 4;
	
	public String classSelectionOptions[] = {"KNIGHT", "THIEF", "ARCHER", "WIZARD", "SORCELER"};
	public String statsTitles[] = {"Health", "Int", "Sta", "Def", "Mana", "Str"};
	public int statsValues[][] = {{ 8,        4,     8,     8,     0,      8   },
								  { 4,        4,     6,     2,     0,      6   },
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
		
		aSetter.setNPC();
		aSetter.setOBJ();
		aSetter.setMON();
		playMusic(0);
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
			// DRAW FPS
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
			
			// MONs
			for (int i = 0; i < mon.length; i++) 
				if(mon[i] != null) mon[i].update();
		}
		if(gameState == pauseState) {
			
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
			
			for (int i = 0; i < npc.length; i++) 
				if(npc[i] != null) entityList.add(npc[i]);
			
			for (int i = 0; i < mon.length; i++) 
				if(mon[i] != null) entityList.add(mon[i]);
				
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
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
				g2.setColor(Color.black);
				// DEBUG: DRAW WORLD POSITION
				g2.drawString("X: " + (player.worldX/tileSize) + ", Y: " + (player.worldY/tileSize), tileSize, tileSize);
				// DEBUG: DRAW FPS
				g2.drawString("FPS: " + drawFPS, tileSize, tileSize + 24); 
				
				g2.setColor(Color.black);
				g2.drawString("Invincible: " + player.invincible, tileSize, tileSize + 48);
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
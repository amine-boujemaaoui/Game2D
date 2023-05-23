package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import entity.Entity;
import tile.TileManager;
import object.OBJ;

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
	public OBJ obj[] = new OBJ[10];
	public Entity npc[] = new Entity[10];
	
	// WORLD SETTINGS
	public final int maxWorldCol = 61;
	public final int maxWorldRow = 57;
	
	// GAME STATES
	public int gameState;
	public final int playState = 0;
	public final int pauseState = 1;

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	public void setupGame() {
		
		aSetter.setNPCs();
		aSetter.setObjects();
		playMusic(0);
		gameState = playState;
	}
	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		
		// --- FPS
		double drawInterval = 1000000000/FPS, delta = 0;
		long lastTime = System.nanoTime(), currentTime, timer = 0;
		drawFPS = 0;
		// -------

		while (gameThread != null) {
			
			// --- FPS
			currentTime = System.nanoTime(); 
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			// -------
			
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
			
			// NPCs
			for (int i = 0; i < npc.length; i++) 
				if(npc[i] != null) npc[i].update();
			
		}
		if(gameState == pauseState) {}
		
	}
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
//		long drawStart = System.nanoTime();

		tileM.draw(g2);
		
		for (int i = 0; i < obj.length; i++) 
			if(obj[i] != null) obj[i].draw(g2, this);
		
		for (int i = 0; i < npc.length; i++) 
			if(npc[i] != null) npc[i].draw(g2, this);
			
		player.draw(g2);
		
		ui.draw(g2);
		
		if (keyH.debug) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
			g2.setColor(Color.black);
			// DEBUG: DRAW WORLD POSITION
			g2.drawString("X: " + (player.worldX/tileSize) + ", Y: " + (player.worldY/tileSize), tileSize, tileSize);
			// DEBUG: DRAW FPS
			g2.drawString("FPS: " + drawFPS, tileSize, tileSize + 24); 
		}	
		
//		long drawEnd = System.nanoTime();
//		long timePassed = drawEnd - drawStart;
		
//		System.out.println("TIME PASSED : " + timePassed);
		
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
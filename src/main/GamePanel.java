package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
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
	public KeyHandler keyH = new KeyHandler();
	TileManager tileM= new TileManager(this);
	Thread gameThread;
	public CollisionChecker cChecker= new CollisionChecker(this);
	public Player player = new Player(this, keyH);
	public AssetSetter aSetter = new AssetSetter(this);
	public OBJ obj[] = new OBJ[10];
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	public void setupGame() {
		
		aSetter.setObjects();
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
		player.update();
		
	}
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		tileM.draw(g2);
		
		for (int i = 0; i < obj.length; i++) 
			if(obj[i] != null) obj[i].draw(g2, this);
			
		player.draw(g2);
		
		if (keyH.debug) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
			g2.setColor(Color.black);
			// DEBUG: DRAW WORLD POSITION
			g2.drawString("X: " + (player.worldX/tileSize) + ", Y: " + (player.worldY/tileSize), tileSize, tileSize);
			// DEBUG: DRAW FPS
			g2.drawString("FPS: " + drawFPS, tileSize, tileSize + 24);
		}
		
		g2.dispose();
	}
}
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	// SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3;
	public final int tileSize = originalTileSize * scale;
	final int maxScreenCol = 32;
	final int maxScreenRow = 18;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	final int FPS = 60;
	
	// HANDLERS
	KeyHandler keyH = new KeyHandler();
	TileManager tileM= new TileManager(this);
	Player player = new Player(this, keyH);
	Thread gameThread;

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
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
		int drawFPS = 0;
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
			if ( timer >= 1000000000 ) { System.out.println("FPS " + drawFPS); drawFPS = 0; timer = 0; }
		}
	}
	public void update() {
		
		player.update();
		
	}
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		tileM.draw(g2);
		player.draw(g2);
		
		g2.dispose();
	}
}
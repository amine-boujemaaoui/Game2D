package main;

import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel{

	// SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3;
	final int tileSize = originalTileSize * scale;
	final int maxScreenCol = 32;
	final int maxScreenRow = 18;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
	}
}

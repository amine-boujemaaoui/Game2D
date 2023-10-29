package main;

import javax.swing.JFrame;

public class Main {

	public static JFrame window;

	public static void main(String[] args) {
		
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Game2D");
		// window.setUndecorated(true);

		GamePanel gp = new GamePanel();
		window.add(gp);
		gp.config.loadConfig();
		if (gp.fullScreen) window.setUndecorated(true);
		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		gp.setupGame();
		gp.startGameThread();
	}
	
}

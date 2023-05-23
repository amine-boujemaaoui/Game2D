package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Game2D");
		
		GamePanel gp = new GamePanel();
		window.add(gp);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gp.setupGame();
		gp.startGameThread();
		
//		String currentDialogue = "Wesh la team. \nBien ou quoi?";
//		
//		System.out.println("AAAAAAAAA" + currentDialogue);
//		
//		for(String line : currentDialogue.split("\n")) {
//			System.out.println("BBBBBBBB" + line);
//			//g2.drawString(line, x, y); 
//			//y += 50; 
//		}
	}
}
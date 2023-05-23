package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	GamePanel gp;
	public boolean debug = false;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	
	public KeyHandler(GamePanel gp) {
		
		this.gp = gp;
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) upPressed    = true;
		if (code == KeyEvent.VK_A) leftPressed  = true;
		if (code == KeyEvent.VK_S) downPressed  = true;
		if (code == KeyEvent.VK_D) rightPressed = true;
		if (code == KeyEvent.VK_P) {
				 if (gp.gameState == gp.playState ) { gp.gameState = gp.pauseState; gp.playSE(4); gp.stopMusic();  }
			else if (gp.gameState == gp.pauseState) { gp.gameState = gp.playState;  gp.playSE(5); gp.playMusic(0); }
		}
		if (code == KeyEvent.VK_T) debug = !debug;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) upPressed    = false;
		if (code == KeyEvent.VK_A) leftPressed  = false;
		if (code == KeyEvent.VK_S) downPressed  = false;
		if (code == KeyEvent.VK_D) rightPressed = false;
	}
}
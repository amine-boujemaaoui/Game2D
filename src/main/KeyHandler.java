package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	GamePanel gp;
	public boolean debug = false;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, eventPressed, spacePressed, shiftPressed;
	
	public KeyHandler(GamePanel gp) {
		
		this.gp = gp;
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		if(gp.gameState == gp.titleScreenState) {
			switch(gp.ui.subStateScreen) {
			case 0:
				if (code == KeyEvent.VK_W) {
					gp.playSE(5);
					gp.ui.selectedOption--; 
					if (gp.ui.selectedOption < 0) gp.ui.selectedOption = gp.ui.titleScreenOptions.length-1;
				}
				if (code == KeyEvent.VK_S) {
					gp.playSE(5);
					gp.ui.selectedOption++;
					if (gp.ui.selectedOption > gp.ui.titleScreenOptions.length-1) gp.ui.selectedOption = 0;
				}
				if (code == KeyEvent.VK_ENTER) {
					gp.playSE(4);
					switch(gp.ui.selectedOption) {
					case 0: gp.ui.subStateScreen = 1; gp.ui.selectedOption = 0; break;
					case 1: /* TODO LOAD GAME */ break;
					case 2: System.exit(0);
					}
				}
				break;
			case 1: 
				if (code == KeyEvent.VK_W) {
					gp.playSE(5);
					gp.ui.selectedOption--; 
					if (gp.ui.selectedOption < 0) gp.ui.selectedOption = gp.classSelectionOptions.length;
				}
				if (code == KeyEvent.VK_S) {
					gp.playSE(5);
					gp.ui.selectedOption++;
					if (gp.ui.selectedOption > gp.classSelectionOptions.length) gp.ui.selectedOption = 0;
				}
				if (code == KeyEvent.VK_ENTER) {
					gp.playSE(4);
					if(gp.ui.selectedOption == gp.classSelectionOptions.length) { gp.ui.subStateScreen = 0; gp.ui.selectedOption = 0; }
					else { gp.player.caracterClass = gp.ui.selectedOption; gp.player.setClassStats(); gp.gameState = gp.playState; }
				}
				break;
			case 2: break;	
			}
		}
		if(gp.gameState == gp.playState) {
			if (code == KeyEvent.VK_ENTER) enterPressed = true;
			if (code == KeyEvent.VK_SHIFT) shiftPressed = true;
			if (code == KeyEvent.VK_W)     upPressed    = true;
			if (code == KeyEvent.VK_A)     leftPressed  = true;
			if (code == KeyEvent.VK_S)     downPressed  = true;
			if (code == KeyEvent.VK_D)     rightPressed = true;
			if (code == KeyEvent.VK_E)     eventPressed = true;
			if (code == KeyEvent.VK_T)     debug = !debug;
			if (code == KeyEvent.VK_P) {   gp.gameState = gp.pauseState; gp.playSE(4); gp.stopMusic(); }
		}
		else if(gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_P) { gp.gameState = gp.playState; gp.playSE(5); gp.playMusic(0); }
		}
		else if(gp.gameState == gp.dialogueState) {
			if (code == KeyEvent.VK_SPACE) { spacePressed = true; gp.gameState = gp.playState; gp.playSE(4); }
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		// if(gp.gameState == gp.playState) {
			if (code == KeyEvent.VK_ENTER) enterPressed = false;
			if (code == KeyEvent.VK_SHIFT) shiftPressed = false;
			if (code == KeyEvent.VK_W)     upPressed    = false;
			if (code == KeyEvent.VK_A)     leftPressed  = false;
			if (code == KeyEvent.VK_S)     downPressed  = false;
			if (code == KeyEvent.VK_D)     rightPressed = false;
			if (code == KeyEvent.VK_E)     eventPressed = false;
		// }
	}
}
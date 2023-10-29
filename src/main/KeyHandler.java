package main;

import entity.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ScheduledExecutorService;

public class KeyHandler implements KeyListener {
	
	GamePanel gp;
	public boolean debug = false;
	public boolean 	upPressed, 
				 	downPressed, 
				 	leftPressed, 
				 	rightPressed, 
				 	enterPressed, 
				 	eventPressed, 
				 	spacePressed, 
				 	shiftPressed,
				 	attackPressed,
				 	toolPressed,
				 	equipmentWindowPressed,
				 	dashPressed,
				 	backSpacePressed,
				 	spellPressed,
					settingsPressed;
	public int spell = 0;
	
	public KeyHandler(GamePanel gp) {
		
		this.gp = gp;
	}
	@Override
	public void keyTyped(KeyEvent e) { }
	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		if(gp.gameState == gp.titleScreenState) titleScreenState(code);
		else if(gp.gameState == gp.playState) playState(code);
		else if(gp.gameState == gp.pauseState) pauseState(code);
		else if(gp.gameState == gp.dialogueState) dialogueState(code);
		else if (gp.gameState == gp.equipmentWindowState) equipmentWindowState(code);
		else if (gp.gameState == gp.settingsState)  settingsState(code);
		else if (gp.gameState == gp.gameOverState) gameOverState(code);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
	    if (code == KeyEvent.VK_I)          equipmentWindowPressed = false;
	    if (code == KeyEvent.VK_BACK_SPACE) backSpacePressed = false;
		if (code == KeyEvent.VK_ENTER)      enterPressed  = false;
		if (code == KeyEvent.VK_SHIFT)      shiftPressed  = false;
		if (code == KeyEvent.VK_SPACE)      spacePressed  = false; 
		if (code == KeyEvent.VK_W)          upPressed     = false;
		if (code == KeyEvent.VK_A)          leftPressed   = false;
		if (code == KeyEvent.VK_S)          downPressed   = false;
		if (code == KeyEvent.VK_D)          rightPressed  = false;
		if (code == KeyEvent.VK_E)          eventPressed  = false;
		if (code == KeyEvent.VK_H)          attackPressed = false;
		if (code == KeyEvent.VK_N)          toolPressed   = false;
		if (code == KeyEvent.VK_J)          spellPressed  = false;
		if (code == KeyEvent.VK_K)          spellPressed  = false;
		if (code == KeyEvent.VK_L)          spellPressed  = false;
		if (code == KeyEvent.VK_G)          dashPressed   = false;
	}
	public void titleScreenState(int code) {
		
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
				if(gp.ui.selectedOption == gp.classSelectionOptions.length) {
					gp.ui.subStateScreen = 0;
					gp.ui.selectedOption = 0;
				} else {
					gp.player.caracterClass = gp.ui.selectedOption; 
					gp.player.setClassStats(); 
					gp.gameState = gp.playState; 
				}
			}
			break;
		case 2: break;
		}
	}
	public void playState(int code) {
		
		if (code == KeyEvent.VK_ENTER)  enterPressed    = true;
		if (code == KeyEvent.VK_SHIFT)  shiftPressed    = true;
		if (code == KeyEvent.VK_W)      upPressed       = true;
		if (code == KeyEvent.VK_A)      leftPressed     = true;
		if (code == KeyEvent.VK_S)      downPressed     = true;
		if (code == KeyEvent.VK_D)      rightPressed    = true;
		if (code == KeyEvent.VK_H)      attackPressed   = true;
		if (code == KeyEvent.VK_N)      toolPressed     = true;
		if (code == KeyEvent.VK_E)      eventPressed    = true;
		if (code == KeyEvent.VK_G)      dashPressed     = true;

		if (code == KeyEvent.VK_J)   {  spellPressed    = true; spell = 0; }
		if (code == KeyEvent.VK_K)   {  spellPressed    = true; spell = 1; }
		if (code == KeyEvent.VK_L)   {  spellPressed    = true; spell = 2; }
		if (code == KeyEvent.VK_T) debug = !debug;
		if (code == KeyEvent.VK_I) {   
			 gp.gameState = gp.equipmentWindowState; gp.playSE(1); 
		}
		if (code == KeyEvent.VK_P) {   	 gp.gameState = gp.pauseState; gp.playSE(4); gp.stopMusic(); 
		}
		if (code == KeyEvent.VK_ESCAPE) {

			gp.gameState = gp.settingsState;
			gp.playSE(5);
			gp.stopMusic();
			gp.ui.subStateScreen = 0;
			gp.ui.selectedOption = 0;
		}
	}
	public void pauseState(int code) {
		
		if (code == KeyEvent.VK_P) { 
			gp.gameState = gp.playState; 
			gp.playSE(5); 
			//gp.playMusic(0); 
		}
	}
	public void dialogueState(int code) {
		
		if (code == KeyEvent.VK_SPACE) { 
			spacePressed = true; 
			gp.gameState = gp.playState; 
			gp.playSE(4); }
	}
	public void equipmentWindowState(int code) {
		
		if (code == KeyEvent.VK_I || code == KeyEvent.VK_ESCAPE) { 
			gp.gameState = gp.playState; gp.playSE(5); }
		if (code == KeyEvent.VK_W) { gp.playSE(5); gp.ui.slotRow--; if(gp.ui.slotRow < 0) gp.ui.slotRow = 5; }
		if (code == KeyEvent.VK_S) { gp.playSE(5); gp.ui.slotRow++; if(gp.ui.slotRow > 5) gp.ui.slotRow = 0; }
		if (code == KeyEvent.VK_A) { gp.playSE(5); gp.ui.slotCol--; if(gp.ui.slotCol < 0) gp.ui.slotCol = 3; }
		if (code == KeyEvent.VK_D) { gp.playSE(5); gp.ui.slotCol++; if(gp.ui.slotCol > 3) gp.ui.slotCol = 0; }
		if (code == KeyEvent.VK_BACK_SPACE) backSpacePressed  = true;
		if (code == KeyEvent.VK_ENTER)      enterPressed      = true; 
		if (code == KeyEvent.VK_SPACE)      spacePressed      = true; 
	}
	public void settingsState(int code) {

		int max = gp.ui.settingsOptions.length - 1;
		switch (gp.ui.subStateScreen) {
			case 0: max = gp.ui.settingsOptions.length - 1; break;
			case 1: break;
			case 2: max = gp.ui.controlsOptions.length - 1; break;
			case 5: max = 1; break;
		}

		if (code == KeyEvent.VK_ESCAPE) {

			gp.config.saveConfig();
			gp.gameState = gp.playState;
			gp.playSE(5);
			gp.playMusic(0);
		}
		if (code == KeyEvent.VK_W) {
			gp.playSE(5);
			gp.ui.selectedOption--;
			if (gp.ui.selectedOption < 0) gp.ui.selectedOption = max;
		}
		if (code == KeyEvent.VK_S) {
			gp.playSE(5);
			gp.ui.selectedOption++;
			if (gp.ui.selectedOption > max) gp.ui.selectedOption = 0;
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.subStateScreen == 0) {
				if (gp.ui.selectedOption == 1 && gp.music.volumeIndicator > 0) {
					gp.music.volumeIndicator--;
					gp.music.checkVolume();
					gp.playSE(5);
				} else if (gp.ui.selectedOption == 2 && gp.se.volumeIndicator > 0) {
					gp.se.volumeIndicator--;
					gp.playSE(5);
				}
			}
			if (gp.ui.subStateScreen == 5) {
				gp.ui.selectedOption--;
				if (gp.ui.selectedOption < 0) gp.ui.selectedOption = 1;
			}
		}
		if (code == KeyEvent.VK_D) {
			if (gp.ui.subStateScreen == 0 ) {
				if (gp.ui.selectedOption == 1 && gp.music.volumeIndicator < 8) {
					gp.music.volumeIndicator++;
					gp.music.checkVolume();
					gp.playSE(5);
				} else if (gp.ui.selectedOption == 2 && gp.se.volumeIndicator < 8) {
					gp.se.volumeIndicator++;
					gp.playSE(5);
				}
			}
			if (gp.ui.subStateScreen == 5) {
				gp.ui.selectedOption++;
				if (gp.ui.selectedOption > 1) gp.ui.selectedOption = 0;
			}
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
	}
	public void gameOverState(int code) {

		if (code == KeyEvent.VK_ENTER) {
			gp.playSE(4);
			switch (gp.ui.selectedOption) {
				case 0:
					gp.player.retry();
					gp.gameState = gp.playState;
					break;
				case 1:
					gp.ui.subStateScreen = 0;
					gp.ui.selectedOption = 0;
					gp.gameState = gp.titleScreenState;
					gp.player = new Player(gp, gp.keyH);
					gp.ui.eventMessages.clear();
					gp.setupGame();
					break;
			}
		}
		if (code == KeyEvent.VK_W) {
			gp.playSE(5);
			gp.ui.selectedOption--;
			if (gp.ui.selectedOption < 0) gp.ui.selectedOption = 1;
		}
		if (code == KeyEvent.VK_S) {
			gp.playSE(5);
			gp.ui.selectedOption++;
			if (gp.ui.selectedOption > 1) gp.ui.selectedOption = 0;
		}
	}
}
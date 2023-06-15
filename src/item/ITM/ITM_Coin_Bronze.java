package item.ITM;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ITM_Coin_Bronze extends Entity{

	public ITM_Coin_Bronze(GamePanel gp) {
		
		super(gp);
		
		name = "Coin";
		description = "Bronze coin [value 1]";
		size = size1by2;
		type = gp.typeITM;
		pickupOnly = true;
		amountValue = 1;
		
		hitBox.x = 9;
		hitBox.width = 30;
		
		hitBoxDefaultX = hitBox.x;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/coins/bronze/1", gp.tileSize/2, gp.tileSize);
		ground[1] = gp.ut.setup("/items/coins/bronze/2", gp.tileSize/2, gp.tileSize);
		ground[2] = gp.ut.setup("/items/coins/bronze/3", gp.tileSize/2, gp.tileSize);
		ground[3] = gp.ut.setup("/items/coins/bronze/4", gp.tileSize/2, gp.tileSize);
		ground[4] = gp.ut.setup("/items/coins/bronze/5", gp.tileSize/2, gp.tileSize);
		ground[5] = gp.ut.setup("/items/coins/bronze/6", gp.tileSize/2, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/coins/bronze/item", gp.tileSize, gp.tileSize);
	}
	public boolean use(Entity entity) {
		
		gp.playSE(6);
		gp.ui.addEventMessage("Money + " + amountValue, 
	              12f, 
	              Font.BOLD, 
	              Color.white, 
	              Color.gray, 
	              gp.player.screenX + gp.tileSize,
	              gp.player.screenY + gp.tileSize,
	              true);
		
		gp.player.coins += amountValue;
		gp.player.coinsByType = gp.ut.calculerPieces(gp.player.coins);
		return true;
	}
}

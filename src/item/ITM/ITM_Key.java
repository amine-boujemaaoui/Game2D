package item.ITM;

import java.awt.*;
import java.awt.image.BufferedImage;

import entity.Entity;
import item.WPN.WPN_Sword_Iron;
import main.GamePanel;

public class ITM_Key extends Entity{

	public ITM_Key(GamePanel gp) {
		
		super(gp);
		
		name = "Key";
		description = "Common key that can open a normal chest.";
		size = size1by2;
		type = gp.typeITM;
		subType = gp.subType_ITM_KEY;
		price = 20;
		
		hitBox.x = 9;
		hitBox.width = 30;
		
		hitBoxDefaultX = hitBox.x;
		
		ground    = new BufferedImage[6];
		ground[0] = gp.ut.setup("/items/key/1", gp.tileSize/2, gp.tileSize);
		ground[1] = gp.ut.setup("/items/key/2", gp.tileSize/2, gp.tileSize);
		ground[2] = gp.ut.setup("/items/key/3", gp.tileSize/2, gp.tileSize);
		ground[3] = gp.ut.setup("/items/key/3", gp.tileSize/2, gp.tileSize);
		ground[4] = gp.ut.setup("/items/key/2", gp.tileSize/2, gp.tileSize);
		ground[5] = gp.ut.setup("/items/key/1", gp.tileSize/2, gp.tileSize);
		
		item_icon = gp.ut.setup("/items/key/item", gp.tileSize, gp.tileSize);
	}
	public boolean use(Entity entity) {

		int objIndex = gp.cChecker.checkObject(gp.player, true);

		if (objIndex != 999 && gp.obj[gp.currentMap][objIndex].subType == gp.subType_OBJ_CHEST && gp.obj[gp.currentMap][objIndex].OBJstate == 0) {

			gp.obj[gp.currentMap][objIndex].OBJstate = 1;

			if(inventory.size() != maxInventorySize) {

				Entity itm = new WPN_Sword_Iron(gp);

				gp.player.inventory.add(itm);
				gp.playSE(7);

				gp.ui.addEventMessage("picked up " + itm.name + " in " + gp.obj[gp.currentMap][objIndex].name,
						12f,
						Font.BOLD,
						Color.white,
						Color.gray,
						//screenX + gp.tileSize,
						//screenY + gp.tileSize,
						gp.tileSize*2,
						gp.screenHeight - gp.tileSize*4,
						true);
			}

			return true;
		}
		return false;
	}
	public Entity clone() {
		return new ITM_Key(gp);
	}
}

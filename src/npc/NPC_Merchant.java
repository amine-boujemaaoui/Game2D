package npc;

import java.awt.image.BufferedImage;
import entity.Entity;
import item.ARMR.ARMR_Boots_Leather;
import item.ARMR.ARMR_Helmet_Leather;
import item.ITM.ITM_Key;
import item.ITM.Potion.ITM_Potion_Healing;
import item.ITM.Potion.ITM_Potion_Mana;
import item.TOOL.TOOL_Axe_Wood;
import item.TOOL.TOOL_Pickaxe_Wood;
import item.WPN.WPN_Sword_Wood;
import main.GamePanel;

public class NPC_Merchant extends Entity {

    public NPC_Merchant(GamePanel gp, int worldX, int worldY) {

        super(gp);

        name = "Merchant";
        speed = 2;

        size = size1by2;
        type = gp.typeNPC;
        trading = true;

        this.worldX = worldX;
        this.worldY = worldY;

        hitBox.x     = 9;  hitBox.y      = -4;
        hitBox.width = 33; hitBox.height = 32;

        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        getImages();
        setDialogues();
        setItems();
    }
    public void setAction() {

    }
    public void setDialogues() {

        super.setDialogues("merchant.txt");
    }
    public void getImages() {

        int spritesNum = 6;

        up_still    = new BufferedImage[spritesNum]; up_walking    = new BufferedImage[spritesNum];
        down_still  = new BufferedImage[spritesNum]; down_walking  = new BufferedImage[spritesNum];
        left_still  = new BufferedImage[spritesNum]; left_walking  = new BufferedImage[spritesNum];
        right_still = new BufferedImage[spritesNum]; right_walking = new BufferedImage[spritesNum];

        for (int i = 0; i < spritesNum; i++) up_still[i]    = gp.ut.setup("/npc/youngMen/up/still/"    + (i+1), gp.tileSize, gp.tileSize*2);
        for (int i = 0; i < spritesNum; i++) down_still[i]  = gp.ut.setup("/npc/youngMen/down/still/"  + (i+1), gp.tileSize, gp.tileSize*2);
        for (int i = 0; i < spritesNum; i++) left_still[i]  = gp.ut.setup("/npc/youngMen/left/still/"  + (i+1), gp.tileSize, gp.tileSize*2);
        for (int i = 0; i < spritesNum; i++) right_still[i] = gp.ut.setup("/npc/youngMen/right/still/" + (i+1), gp.tileSize, gp.tileSize*2);

        for (int i = 0; i < spritesNum; i++) up_walking[i]    = gp.ut.setup("/npc/youngMen/up/walking/"    + (i+1), gp.tileSize, gp.tileSize*2);
        for (int i = 0; i < spritesNum; i++) down_walking[i]  = gp.ut.setup("/npc/youngMen/down/walking/"  + (i+1), gp.tileSize, gp.tileSize*2);
        for (int i = 0; i < spritesNum; i++) left_walking[i]  = gp.ut.setup("/npc/youngMen/left/walking/"  + (i+1), gp.tileSize, gp.tileSize*2);
        for (int i = 0; i < spritesNum; i++) right_walking[i] = gp.ut.setup("/npc/youngMen/right/walking/" + (i+1), gp.tileSize, gp.tileSize*2);
    }
    public void speak() {
        super.speak();
    }
    public void setItems() {

        int i = 0;
        Entity[] items = new Entity[20];

        items[i] = new WPN_Sword_Wood(gp);
        items[i].price = 10; i++;

        items[i] = new TOOL_Axe_Wood(gp);
        items[i].price = 20; i++;

        items[i] = new TOOL_Pickaxe_Wood(gp);
        items[i].price = 15; i++;

        items[i] = new ARMR_Helmet_Leather(gp);
        items[i].price = 30; i++;

        items[i] = new ARMR_Boots_Leather(gp);
        items[i].price = 25; i++;

        items[i] = new ITM_Potion_Mana(gp);
        items[i].price = 10; i++;

        items[i] = new ITM_Potion_Healing(gp);
        items[i].price = 12; i++;

        items[i] = new ITM_Key(gp);
        items[i].price = 40; i++;

        for (int j = 0; j < i; j++) inventory.add(items[j]);
    }
    public void interact() {
        speak();
        gp.gameState = gp.tradeState;
        gp.ui.subStateScreen = 0;
        gp.ui.npc = this;
    }
}


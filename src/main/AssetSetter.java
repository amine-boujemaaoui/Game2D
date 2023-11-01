  package main;

import npc.NPC_Merchant;
import object.*;
import tile.IT_DryTree;
import entity.*;
import item.ARMR.ARMR_Boots_Leather;
import item.ITM.ITM_Coin_Bronze;
import item.ITM.ITM_Key;
import item.ITM.Potion.ITM_Potion_Healing;
import item.ITM.Potion.ITM_Potion_Mana;
import monster.MON_BlueSlime;
import monster.MON_RedSlime;
import npc.NPC_Women;
import npc.NPC_YoungMen;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		
		this.gp = gp;
	}
	public void setITM() {
		
		int i = 0;
		gp.itm[0][i] = new ITM_Key            (gp); gp.itm[0][i].worldX = (int)(18.5*gp.tileSize);	gp.itm[0][i].worldY =      (34  *gp.tileSize); i++;
		gp.itm[0][i] = new ITM_Potion_Healing (gp); gp.itm[0][i].worldX =      (29  *gp.tileSize);	gp.itm[0][i].worldY =      (34  *gp.tileSize); i++;
		gp.itm[0][i] = new ITM_Potion_Healing (gp); gp.itm[0][i].worldX =      (29  *gp.tileSize);	gp.itm[0][i].worldY =      (35  *gp.tileSize); i++;
		gp.itm[0][i] = new ITM_Potion_Mana    (gp); gp.itm[0][i].worldX =      (29  *gp.tileSize);	gp.itm[0][i].worldY =      (36  *gp.tileSize); i++;
		gp.itm[0][i] = new ARMR_Boots_Leather (gp); gp.itm[0][i].worldX = (int)(43.5*gp.tileSize);	gp.itm[0][i].worldY = (int)(11.5*gp.tileSize); i++;
		gp.itm[0][i] = new ITM_Coin_Bronze    (gp); gp.itm[0][i].worldX = 	   (29  *gp.tileSize);	gp.itm[0][i].worldY =      (37  *gp.tileSize); i++;
		gp.itm[0][i] = new ITM_Coin_Bronze    (gp); gp.itm[0][i].worldX =      (29  *gp.tileSize);	gp.itm[0][i].worldY =      (38  *gp.tileSize); i++;
		gp.itm[0][i] = new ITM_Coin_Bronze    (gp); gp.itm[0][i].worldX =      (29  *gp.tileSize);	gp.itm[0][i].worldY =      (26  *gp.tileSize); i++;
	}
	public void setOBJ() {
		
		int i = 0;
		gp.obj[0][i] = new OBJ_Chest   (gp, (36  *gp.tileSize),      (31   *gp.tileSize)); i++;
		gp.obj[0][i] = new OBJ_Door    (gp, (18  *gp.tileSize),      (14   *gp.tileSize)); i++;
		gp.obj[0][i] = new OBJ_BigTree (gp, (36  *gp.tileSize), (int)(16.25*gp.tileSize)); i++;
		gp.obj[0][i] = new OBJ_BigTree (gp, (24  *gp.tileSize), (int)(16.25*gp.tileSize)); i++;
		gp.obj[0][i] = new OBJ_BigTree (gp, (17  *gp.tileSize), (int)(19.25*gp.tileSize)); i++;
		gp.obj[0][i] = new OBJ_BigTree (gp, (18  *gp.tileSize), (int)(21.25*gp.tileSize)); i++;
		gp.obj[0][i] = new OBJ_BigTree (gp, (21  *gp.tileSize), (int)(19.25*gp.tileSize)); i++;
		gp.obj[0][i] = new OBJ_BigTree (gp, (23  *gp.tileSize), (int)(20.25*gp.tileSize)); i++;
		gp.obj[0][i] = new OBJ_BigTree (gp, (25  *gp.tileSize), (int)(20.25*gp.tileSize)); i++;
	}
	public void setNPC() {
		
		int i = 0;
		gp.npc[0][i] = new NPC_YoungMen(gp, (32  *gp.tileSize), (37   *gp.tileSize)); i++;
		gp.npc[0][i] = new NPC_Women   (gp, (32  *gp.tileSize), (35   *gp.tileSize)); i++;

		gp.npc[1][i] = new NPC_Merchant(gp, (25  *gp.tileSize), (25   *gp.tileSize)); i++;
	}
	public void setMON() {

		int i = 0;
		gp.mon[0][i] = new MON_RedSlime  (gp, (28  *gp.tileSize), (40   *gp.tileSize)); i++;
		gp.mon[0][i] = new MON_RedSlime  (gp, (29  *gp.tileSize), (41   *gp.tileSize)); i++;
		gp.mon[0][i] = new MON_RedSlime  (gp, (30  *gp.tileSize), (42   *gp.tileSize)); i++;
		gp.mon[0][i] = new MON_RedSlime  (gp, (31  *gp.tileSize), (43   *gp.tileSize)); i++;
		gp.mon[0][i] = new MON_RedSlime  (gp, (29  *gp.tileSize), (40   *gp.tileSize)); i++;
		/*
		gp.mon[0][i] = new MON_RedSlime  (gp, (30  *gp.tileSize), (41   *gp.tileSize)); i++;
		gp.mon[0][i] = new MON_RedSlime  (gp, (31  *gp.tileSize), (42   *gp.tileSize)); i++;
		gp.mon[0][i] = new MON_RedSlime  (gp, (32  *gp.tileSize), (43   *gp.tileSize)); i++;

		gp.mon[1][i] = new MON_RedSlime  (gp, (30  *gp.tileSize), (30   *gp.tileSize)); i++;
		gp.mon[1][i] = new MON_BlueSlime (gp, (34  *gp.tileSize), (34   *gp.tileSize)); i++;

		 */
	}
	public void setIT() {
		
		int i = 0;
		gp.it[0][i] = new IT_DryTree (gp, (36  *gp.tileSize), (26   *gp.tileSize)); i++;
		gp.it[0][i] = new IT_DryTree (gp, (40  *gp.tileSize), (26   *gp.tileSize)); i++;
		gp.it[0][i] = new IT_DryTree (gp, (39  *gp.tileSize), (30   *gp.tileSize)); i++;
	}
}
  package main;

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
		gp.itm[i] = new ITM_Key     (gp); gp.itm[i].worldX = (int)(18.5*gp.tileSize); gp.itm[i].worldY = (int)(34   *gp.tileSize); i++;
		gp.itm[i] = new ITM_Key     (gp); gp.itm[i].worldX = (int)(19.5*gp.tileSize); gp.itm[i].worldY = (int)(33   *gp.tileSize); i++;
		gp.itm[i] = new ITM_Key     (gp); gp.itm[i].worldX = (int)(18.5*gp.tileSize); gp.itm[i].worldY = (int)(35   *gp.tileSize); i++;
		gp.itm[i] = new ITM_Key     (gp); gp.itm[i].worldX = (int)(18.5*gp.tileSize); gp.itm[i].worldY = (int)(32   *gp.tileSize); i++;
		gp.itm[i] = new ITM_Key     (gp); gp.itm[i].worldX = (int)(19.5*gp.tileSize); gp.itm[i].worldY = (int)(34   *gp.tileSize); i++;
		gp.itm[i] = new ITM_Key     (gp); gp.itm[i].worldX = (int)(17.5*gp.tileSize); gp.itm[i].worldY = (int)(31   *gp.tileSize); i++;
		gp.itm[i] = new ITM_Key     (gp); gp.itm[i].worldX = (int)(16.5*gp.tileSize); gp.itm[i].worldY = (int)(30   *gp.tileSize); i++;
		gp.itm[i] = new ITM_Key     (gp); gp.itm[i].worldX = (int)(17.5*gp.tileSize); gp.itm[i].worldY = (int)(34   *gp.tileSize); i++;
		gp.itm[i] = new ITM_Potion_Healing (gp); gp.itm[i].worldX = (int)(29  *gp.tileSize); gp.itm[i].worldY = (int)(34   *gp.tileSize); i++;
		gp.itm[i] = new ITM_Potion_Healing (gp); gp.itm[i].worldX = (int)(29  *gp.tileSize); gp.itm[i].worldY = (int)(35   *gp.tileSize); i++;
		gp.itm[i] = new ITM_Potion_Mana    (gp); gp.itm[i].worldX = (int)(29  *gp.tileSize); gp.itm[i].worldY = (int)(36   *gp.tileSize); i++;
		gp.itm[i] = new ARMR_Boots_Leather (gp); gp.itm[i].worldX = (int)(43.5*gp.tileSize); gp.itm[i].worldY = (int)(11.5 *gp.tileSize); i++;
		gp.itm[i] = new ITM_Coin_Bronze (gp); gp.itm[i].worldX = (int)(29*gp.tileSize); gp.itm[i].worldY = (int)(37 *gp.tileSize); i++;
		gp.itm[i] = new ITM_Coin_Bronze (gp); gp.itm[i].worldX = (int)(29*gp.tileSize); gp.itm[i].worldY = (int)(38 *gp.tileSize); i++;
		gp.itm[i] = new ITM_Coin_Bronze (gp); gp.itm[i].worldX = (int)(29*gp.tileSize); gp.itm[i].worldY = (int)(26 *gp.tileSize); i++;
	}
	public void setOBJ() {
		
		int i = 0;
		gp.obj[i] = new OBJ_Chest   (gp, (int)(36  *gp.tileSize), (int)(31   *gp.tileSize)); i++;
		gp.obj[i] = new OBJ_Door    (gp, (int)(18  *gp.tileSize), (int)(14   *gp.tileSize)); i++;
		gp.obj[i] = new OBJ_BigTree (gp, (int)(23  *gp.tileSize), (int)(29.25*gp.tileSize)); i++;
		gp.obj[i] = new OBJ_BigTree (gp, (int)(36  *gp.tileSize), (int)(16.25*gp.tileSize)); i++;
		gp.obj[i] = new OBJ_BigTree (gp, (int)(24  *gp.tileSize), (int)(16.25*gp.tileSize)); i++;
		gp.obj[i] = new OBJ_BigTree (gp, (int)(17  *gp.tileSize), (int)(19.25*gp.tileSize)); i++;
		gp.obj[i] = new OBJ_BigTree (gp, (int)(18  *gp.tileSize), (int)(21.25*gp.tileSize)); i++;
		gp.obj[i] = new OBJ_BigTree (gp, (int)(21  *gp.tileSize), (int)(19.25*gp.tileSize)); i++;
		gp.obj[i] = new OBJ_BigTree (gp, (int)(23  *gp.tileSize), (int)(20.25*gp.tileSize)); i++;
		gp.obj[i] = new OBJ_BigTree (gp, (int)(25  *gp.tileSize), (int)(20.25*gp.tileSize)); i++;
		
		//gp.obj[i] = new OBJ_BigTree (gp, (int)(29  *gp.tileSize), (int)(23.25*gp.tileSize)); i++;
	}
	public void setNPC() {
		
		int i = 0;
		gp.npc[i] = new NPC_YoungMen(gp, (int)(34  *gp.tileSize), (int)(23   *gp.tileSize)); i++;
		gp.npc[i] = new NPC_YoungMen(gp, (int)(31  *gp.tileSize), (int)(23   *gp.tileSize)); i++;
		// gp.npc[i] = new NPC_Women(gp, (int)(32  *gp.tileSize), (int)(23   *gp.tileSize)); i++;
	}
	public void setMON() {
		
		int i = 0;
		gp.mon[i] = new MON_BlueSlime (gp, (int)(28  *gp.tileSize), (int)(40   *gp.tileSize)); i++;
		gp.mon[i] = new MON_BlueSlime (gp, (int)(29  *gp.tileSize), (int)(41   *gp.tileSize)); i++;
		gp.mon[i] = new MON_BlueSlime (gp, (int)(30  *gp.tileSize), (int)(42   *gp.tileSize)); i++;
		gp.mon[i] = new MON_BlueSlime (gp, (int)(31  *gp.tileSize), (int)(43   *gp.tileSize)); i++;
		gp.mon[i] = new MON_RedSlime  (gp, (int)(29  *gp.tileSize), (int)(40   *gp.tileSize)); i++;
		gp.mon[i] = new MON_RedSlime  (gp, (int)(30  *gp.tileSize), (int)(41   *gp.tileSize)); i++;
		gp.mon[i] = new MON_RedSlime  (gp, (int)(31  *gp.tileSize), (int)(42   *gp.tileSize)); i++;
		gp.mon[i] = new MON_RedSlime  (gp, (int)(32  *gp.tileSize), (int)(43   *gp.tileSize)); i++;
	}
	public void setIT() {
		
		int i = 0;
		gp.it[i] = new IT_DryTree (gp, (int)(36  *gp.tileSize), (int)(26   *gp.tileSize)); i++;
		gp.it[i] = new IT_DryTree (gp, (int)(40  *gp.tileSize), (int)(26   *gp.tileSize)); i++;
		gp.it[i] = new IT_DryTree (gp, (int)(39  *gp.tileSize), (int)(30   *gp.tileSize)); i++;
	}
}
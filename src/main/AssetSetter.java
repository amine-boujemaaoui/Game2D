package main;

import object.*;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		
		this.gp = gp;
	}
	public void setObjects() {
		
		int i = 0;
		gp.obj[i] = new OBJ_Key     ((int)(29  *gp.tileSize), (int)(25   *gp.tileSize)); i++;
		gp.obj[i] = new OBJ_Key     ((int)(18.5*gp.tileSize), (int)(34   *gp.tileSize)); i++;
		gp.obj[i] = new OBJ_Chest   ((int)(36  *gp.tileSize), (int)(31   *gp.tileSize)); i++;
		gp.obj[i] = new OBJ_Boots   ((int)(43.5*gp.tileSize), (int)(11.5 *gp.tileSize)); i++;
		gp.obj[i] = new OBJ_Door    ((int)(18  *gp.tileSize), (int)(14   *gp.tileSize)); i++;
		gp.obj[i] = new OBJ_BigTree ((int)(29  *gp.tileSize), (int)(23.25*gp.tileSize)); i++;
	}
}
package main;

import object.*;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		
		this.gp = gp;
	}
	public void setObjects() {
		
		int i = 0;
		gp.obj[i] = new OBJ_Key  (22*gp.tileSize, 21*gp.tileSize); i++;
		gp.obj[i] = new OBJ_Key  (23*gp.tileSize, 38*gp.tileSize); i++;
		gp.obj[i] = new OBJ_Chest(30*gp.tileSize, 29*gp.tileSize); i++;
		gp.obj[i] = new OBJ_Boots(37*gp.tileSize,  9*gp.tileSize); i++;
		gp.obj[i] = new OBJ_Door (12*gp.tileSize, 12*gp.tileSize); i++;
	}
}
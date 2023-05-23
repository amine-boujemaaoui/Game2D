package object;

import main.GamePanel;

public class OBJ_Chest extends OBJ{

	public OBJ_Chest(GamePanel gp, int worldX, int worldY) {
		
		super(gp, "Chest", worldX, worldY, "chest", null);

		collision = true;
	}
}
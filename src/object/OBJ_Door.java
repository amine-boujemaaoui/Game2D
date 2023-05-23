package object;

import main.GamePanel;

public class OBJ_Door extends OBJ{

	public OBJ_Door(GamePanel gp, int worldX, int worldY) {
		
		super(gp, "Door", worldX, worldY, "door_closed", null);
		
		collision = true;
	}
}
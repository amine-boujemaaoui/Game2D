package tile;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
	
	public boolean destructible = false;
	public InteractiveTile afterDestroy = null;

	public InteractiveTile(GamePanel gp) {
		super(gp);
	}
	public void update() {
		
	}
	public boolean correctToolUsed(Entity entity) {
		
		boolean correctToolUsed = false;
		
		return correctToolUsed;
	}
}

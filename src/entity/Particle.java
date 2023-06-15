package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Particle extends Entity {
	
	Entity generator;
	BufferedImage icon;
	Color color;
	int size;
	int speed;
	int xd, yd;
	int offsetX, offsetY;

	public Particle(GamePanel gp, Entity generator, String imagePath, Color color, int size, int speed, int maxHealth, int xd, int yd, int offsetX, int offsetY) {
		super(gp);
		this.generator = generator;
		this.color = color;
		this.size = size;
		this.xd = xd;
		this.yd = yd;
		this.speed = speed;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		
		health = maxHealth;
		worldX = generator.worldX;
		worldY = generator.worldY;

		if(imagePath != null)
			this.icon = gp.ut.setup(imagePath, size, size);
	}
	public void update() {

		health--;
		if(health <= 0) alive = false;

		worldX += xd*speed;
		worldY += yd*speed;
	}
	public void draw(Graphics2D g2, GamePanel gp) {

		int screenX = worldX - gp.player.worldX + gp.player.screenX + offsetX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY + offsetY;

		g2.setColor(color);
		if(icon == null) g2.fillRect(screenX, screenY, size, size);
		else g2.drawImage(icon, screenX, screenY, null);
	}
}

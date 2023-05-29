package main;

import java.awt.Color;

public class EventMessage {
	public String message;
	public Color textColor, shadowColor;
	public float fontSize;
	public int fontType;
	public int messageCounter = 0;
	public int x, y;
	boolean fixedPosition;
	
	public EventMessage(String message, float fontSize, int fontType, Color textColor, Color shadowColor, int x, int y, boolean fixedPosition) {
		super();
		this.message = message;
		this.textColor = textColor;
		this.shadowColor = shadowColor;
		this.fontType = fontType;
		this.fontSize = fontSize;
		this.x = x; this.y = y;
		this.fixedPosition = fixedPosition;
	}
}

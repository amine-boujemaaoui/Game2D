package main;

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class UtilityTool {
	
	public final int[] coinsType = {1, 50, 250, 1000};

	public BufferedImage setup(String imagePath, int width, int height) {
		
		BufferedImage image = null;
		try { image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png")); }
		catch (IOException e) { e.printStackTrace(); }
		
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(image, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}
	public void drawMultilineString(Graphics2D g2, String text, int x, int y, int width, int height) {
		
		AttributedString attributedString = new AttributedString(text);
		attributedString.addAttribute(TextAttribute.FONT, g2.getFont());
		attributedString.addAttribute(TextAttribute.FOREGROUND, g2.getColor());
		
		AttributedCharacterIterator characterIterator = attributedString.getIterator();
		FontRenderContext fontRenderContext = g2.getFontRenderContext();
		LineBreakMeasurer measurer = new LineBreakMeasurer(characterIterator, fontRenderContext);
		
		while (measurer.getPosition() < characterIterator.getEndIndex()) {
			TextLayout textLayout = measurer.nextLayout(width);
			y += textLayout.getAscent();
			textLayout.draw(g2, x, y);
			y += textLayout.getDescent() + textLayout.getLeading();
		}
	}
    public Map<Integer, Integer> calculerPieces(int montant) {
    	
        Map<Integer, Integer> nombrePieces = new HashMap<>();
        
        for (int i = coinsType.length - 1; i >= 0; i--) {
        	
            int typePiece = coinsType[i];
            int nombre = montant / typePiece;
            montant %= typePiece;
            nombrePieces.put(typePiece, nombre);
        }
        
        return nombrePieces;
    }
}

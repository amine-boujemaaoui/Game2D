package main;

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Map;

public class UtilityTool {
	
	public final int[] coinsType = {1, 10, 50, 250};

	public BufferedImage scaleImage(BufferedImage original,  int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
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

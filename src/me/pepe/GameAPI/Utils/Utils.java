package me.pepe.GameAPI.Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Random;

public class Utils {
	public static Random random = new Random();
	public static BufferedImage copyTexture(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    int y = (int) (rect.y + ((rect.height - metrics.getHeight()) / 2) + (metrics.getAscent() * 1.1));
	    g.setFont(font);
	    g.drawString(text, x, y);
	}
	public static Color getColorForPixel(BufferedImage image, int x, int y) {
		ColorModel cm = image.getColorModel();
		Raster raster = image.getRaster();
		Object pixel = raster.getDataElements(x, y, null);
		Color color;
		if (cm.hasAlpha()) {
			color = new Color(cm.getRed(pixel), cm.getGreen(pixel), cm.getBlue(pixel), cm.getAlpha(pixel));
		} else {
			color = new Color(cm.getRed(pixel), cm.getGreen(pixel), cm.getBlue(pixel), 255);
		}
		return color;
	}
	public static Color getColorForPixel(ColorModel cm, Raster raster, int x, int y) {
		Object pixel = raster.getDataElements(x, y, null);
		Color color;
		if (cm.hasAlpha()) {
			color = new Color(cm.getRed(pixel), cm.getGreen(pixel), cm.getBlue(pixel), cm.getAlpha(pixel));
		} else {
			color = new Color(cm.getRed(pixel), cm.getGreen(pixel), cm.getBlue(pixel), 255);
		}
		return color;
	}
}

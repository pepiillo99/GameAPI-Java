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
import java.util.HashMap;
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
		return getColorForPixel(cm, raster, x, y);
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
	public static BufferedImage passColor(Color... colors) {
		BufferedImage buf = new BufferedImage((colors.length-1) * (255), 255, 1);
		Graphics g = buf.getGraphics();
		for (int colorLenght = 0; colorLenght<colors.length; colorLenght++) {
			Color c1 = colors[colorLenght];
			if (colorLenght + 1 >= colors.length) {
				break;
			}
			Color c2 = colors[colorLenght+1];
			double r = colors[colorLenght].getRed(), gg = colors[colorLenght].getGreen() , b = colors[colorLenght].getBlue();
			HashMap<String, Double> map = new HashMap<String, Double>();
			map.put("r", (double) Math.abs(c1.getRed() - c2.getRed()) / 255);
			map.put("g", (double) Math.abs(c1.getGreen() - c2.getGreen()) / 255);
			map.put("b", (double) Math.abs(c1.getBlue() - c2.getBlue()) / 255);
			HashMap<String, Boolean> sum = new HashMap<String, Boolean>();
			sum.put("r", c1.getRed() < c2.getRed());
			sum.put("g", c1.getGreen() < c2.getGreen());
			sum.put("b", c1.getBlue() < c2.getBlue());
			for (int i = 0; i < 255; i++) {
				g.setColor(new Color((int) r, (int) gg, (int) b));
				g.drawLine(i + (colorLenght * (255)), 0, i + (colorLenght * (255)), 500);
				if (c2.getRed() != (int) r) {
					if (sum.get("r")) {
						r += map.get("r");
					} else {
						r -= map.get("r");
					}
				}
				if (c2.getGreen() != (int) gg) {
					if (sum.get("g")) {
						gg += map.get("g");
					} else {
						gg -= map.get("g");
					}
				}
				if (c2.getBlue() != (int) b) {
					if (sum.get("b")) {
						b += map.get("b");
					} else {
						b -= map.get("b");
					}
				}
			}
		}
		return buf;
	}
}

package me.pepe.GameAPI.TextureManager.Texture;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.pepe.GameAPI.TextureManager.TextureDistance;

public abstract class TextureChanger {
	private TextureDistance dist;
	private BufferedImage texture;
	public TextureChanger(TextureDistance dist) {
		this.dist = dist;
	}
	public void load(Texture texture) {
		if (texture.hasTextureDistance(dist) && texture.isTextureDistanceLoaded(dist)) {
			this.texture = texture.getTexture(dist);
			loadTexture();
		}
	}
	public abstract void loadTexture();
	public boolean isLoaded() {
		return texture != null;
	}
	public void unload() {
		texture = null;
	}
	public void changeColor(Color mainColor, Color replaceColor) {
	    int RGB_MASK = 0x00ffffff;
	    int ALPHA_MASK = 0xff000000;
		if (texture != null) {
		    int oldRGB = mainColor.getRed() << 16 | mainColor.getGreen() << 8 | mainColor.getBlue();
		    int toggleRGB = oldRGB ^ (replaceColor.getRed() << 16 | replaceColor.getGreen() << 8 | replaceColor.getBlue());
		    int w = texture.getWidth();
		    int h = texture.getHeight();

		    int[] rgb = texture.getRGB(0, 0, w, h, null, 0, w);
		    for (int i = 0; i < rgb.length; i++) {
		        if ((rgb[i] & RGB_MASK) == oldRGB) {
		            rgb[i] ^= toggleRGB;
		        }
		    }
		    texture.setRGB(0, 0, w, h, rgb, 0, w);
		}
	}
	public void changeImageAlpha(float alpha) {
		  BufferedImage reImage = new BufferedImage(texture.getWidth(), texture.getHeight(), texture.getType());
	      Graphics2D g = reImage.createGraphics();
	      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	      g.drawImage(texture, 0, 0, texture.getWidth(), texture.getHeight(), null);
	      g.dispose();
	      texture = reImage;
	}
}

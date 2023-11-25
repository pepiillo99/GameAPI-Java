package me.pepe.GameAPI.Game.Objects.ScreenObjects.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import me.pepe.GameAPI.Screen.Screen;
import me.pepe.GameAPI.TextureManager.TextureDistance;
import me.pepe.GameAPI.TextureManager.Texture.Texture;
import me.pepe.GameAPI.Utils.Utils;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;

public abstract class ImageButton extends Button {
	private Texture texture;
	private BufferedImage directedTexture;
	private String text;
	public ImageButton(Texture texture, InteligentPosition intPos, Screen screen, InteligentDimension intDim) {
		this(null, texture, intPos, screen, intDim);
	}
	public ImageButton(String text, Texture texture, InteligentPosition intPos, Screen screen, InteligentDimension intDim) {
		super(intPos, screen, intDim);
		this.texture = texture;
		this.text = text;
	}
	public ImageButton(BufferedImage directedTexture, InteligentPosition intPos, Screen screen, InteligentDimension intDim) {
		this(null, directedTexture, intPos, screen, intDim);
	}
	public ImageButton(String text, BufferedImage directedTexture, InteligentPosition intPos, Screen screen, InteligentDimension intDim) {
		super(intPos, screen, intDim);
		this.directedTexture = directedTexture;
		this.text = text;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public BufferedImage getDirectedTexture() {
		return directedTexture;
	}
	public void setDirectedTexture(BufferedImage directedTexture) {
		this.directedTexture = directedTexture;
	}
	public String getText() {
		return text;
	}
	@Override
	public void render(Graphics g) {
		if (isShow()) {
			if (directedTexture != null) {
				g.drawImage(directedTexture, (int) getX() + (!isOver() ? 3 : -3), (int) getY() + (!isOver() ? 3 : -3), (int) getDimension().getX() + (isOver() ? 6 : -6), (int) getDimension().getY() + (isOver() ? 6 : -6), null, null);
				g.setColor(new Color(0, 0, 0, isOver() ? 255 : 200));
				if (text != null) {
					Utils.drawCenteredString(g, text, new Rectangle((int) getX() - (!isOver() ? 0 : 1), (int) getY() - (!isOver() ? 17 : 19), (int) getDimension().getX(), (int) getDimension().getY()), /*Dymos.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, isOver() ? 50 : 45)*/ new Font("Aria", Font.PLAIN, isOver() ? 50 : 45));
				}
			} else {
				if (texture.isTextureDistanceLoaded(TextureDistance.LOD)) {
					BufferedImage bud = texture.getTexture(TextureDistance.LOD);
					/**
					if (!isOver() && texture.hasChangedTexture("unnOver")) {
						bud = texture.getTextureChanged("unnOver").getTexture();
					}
					*/
					g.drawImage(bud, (int) getX() + (!isOver() ? 3 : -3), (int) getY() + (!isOver() ? 3 : -3), (int) getDimension().getX() + (isOver() ? 6 : -6), (int) getDimension().getY() + (isOver() ? 6 : -6), null, null);
					g.setColor(new Color(0, 0, 0, isOver() ? 255 : 200));
					if (text != null) {
						Utils.drawCenteredString(g, text, new Rectangle((int) getX() - (!isOver() ? 0 : 1), (int) getY() - (!isOver() ? 17 : 19), (int) getDimension().getX(), (int) getDimension().getY()), /*Dymos.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, isOver() ? 50 : 45)*/ new Font("Aria", Font.PLAIN, isOver() ? 50 : 45));
					}
				} else {
					g.setColor(Utils.random.nextBoolean() ? Color.CYAN : Color.PINK);
					g.fillRect((int) getX() + (!isOver() ? 3 : -3), (int) getY() + (!isOver() ? 3 : -3), (int) getDimension().getX() + (isOver() ? 6 : -6), (int) getDimension().getY() + (isOver() ? 6 : -6));
				}
			}
		}
	}
}

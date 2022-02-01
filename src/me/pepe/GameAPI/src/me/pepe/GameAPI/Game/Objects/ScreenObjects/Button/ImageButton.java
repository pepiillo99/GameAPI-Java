package me.pepe.GameAPI.Game.Objects.ScreenObjects.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.TextureManager.TextureDistance;
import me.pepe.GameAPI.TextureManager.Texture.Texture;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;
import me.pepe.GameAPI.Utils.RenderLimits;
import me.pepe.GameAPI.Utils.Utils;

public abstract class ImageButton extends Button {
	private Texture texture;
	private String text;
	public ImageButton(String name, Texture texture, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		this(null, name, texture, gameLocation, game, dimension, null);
	}
	public ImageButton(String name, Texture texture, GameLocation gameLocation, Game game, ObjectDimension dimension, RenderLimits limits) {
		this(null, name, texture, gameLocation, game, dimension, limits);
	}
	public ImageButton(String text, String name, Texture texture, GameLocation gameLocation, Game game, ObjectDimension dimension, RenderLimits limits) {
		super(name, gameLocation, game, dimension, limits);
		this.texture = texture;
		this.text = text;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public String getText() {
		return text;
	}
	@Override
	public void render(Graphics g) {
		if (isShow()) {
			if (texture.isTextureDistanceLoaded(TextureDistance.LOD)) {
				BufferedImage bud = texture.getTexture(TextureDistance.LOD);
				/**
				if (!isOver() && texture.hasChangedTexture("unnOver")) {
					bud = texture.getTextureChanged("unnOver").getTexture();
				}
				*/
				g.drawImage(bud, getActualX() + (!isOver() ? 3 : -3), getActualY() + (!isOver() ? 3 : -3), getActualDimensionX() + (isOver() ? 6 : -6), getActualDimensionY() + (isOver() ? 6 : -6), null, null);
				g.setColor(new Color(0, 0, 0, isOver() ? 255 : 200));
				if (text != null) {
					Utils.drawCenteredString(g, text, new Rectangle(getActualX() - (!isOver() ? 0 : 1), getActualY() - (!isOver() ? 17 : 19), getActualDimensionX(), getActualDimensionY()), /*Dymos.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, isOver() ? 50 : 45)*/ new Font("Aria", Font.PLAIN, isOver() ? 50 : 45));
				}
			} else {
				g.setColor(Utils.random.nextBoolean() ? Color.CYAN : Color.PINK);
				g.fillRect(getActualX() + (!isOver() ? 3 : -3), getActualY() + (!isOver() ? 3 : -3), getActualDimensionX() + (isOver() ? 6 : -6), getActualDimensionY() + (isOver() ? 6 : -6));
			}
		}
	}
}

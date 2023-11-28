package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Color;
import java.awt.Graphics;

import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Screen.Screen;
import me.pepe.GameAPI.TextureManager.Texture.Texture;
import me.pepe.GameAPI.Utils.Utils;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;

public class ImageObject extends GameObject {
	private Texture texture;
	public ImageObject(Texture texture, InteligentPosition intPos, Screen Screen, InteligentDimension intDim) {
		super(intPos, Screen, intDim);
		this.texture = texture;
	}
	@Override
	public void tick() {}
	@Override
	public void render(Graphics g) {
		if (texture.isTextureDistanceLoaded(texture.getMaxTextureDistance())) {
			g.drawImage(texture.getTexture(texture.getMaxTextureDistance()), (int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY(), null, null);
		} else {
			g.setColor(Utils.random.nextBoolean() ? Color.PINK : Color.GREEN);
			g.fillRect((int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY());
		}
	}
}

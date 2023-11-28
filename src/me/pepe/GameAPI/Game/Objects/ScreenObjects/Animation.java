package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Color;
import java.awt.Graphics;

import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Screen.Screen;
import me.pepe.GameAPI.TextureManager.Texture.Texture;
import me.pepe.GameAPI.Utils.Utils;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;

public abstract class Animation extends GameObject {
	private Texture[] textures;
	private long durationPerFotogram = 0;
	private int pos = 0;
	private boolean bucle = false;
	private boolean finish = false;
	private long timer = 0;
	public Animation(String id, InteligentPosition intPos, Screen screen, InteligentDimension intDim, Texture[] textures, long durationPerFotogram) {
		super(id, intPos, screen, intDim);
		this.textures = textures;
		this.durationPerFotogram = durationPerFotogram;
	}
	public Animation(InteligentPosition intPos, Screen screen, InteligentDimension intDim, Texture[] textures, long durationPerFotogram) {
		this(null, intPos, screen, intDim, textures, durationPerFotogram);
	}
	@Override
	public void tick() {}
	@Override
	public void render(Graphics g) {
		if (!finish) {
			if (textures[pos] != null && textures[pos].isTextureDistanceLoaded(textures[pos].getMaxTextureDistance())) {
				g.drawImage(textures[pos].getTexture(textures[pos].getMaxTextureDistance()), (int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY(), null, null);
			} else {
				g.setColor(Utils.random.nextBoolean() ? Color.PINK : Color.GREEN);
				g.fillRect((int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY());
			}
		}
	}
	@Override
	public void renderTick() {
		if (timer - System.currentTimeMillis() <= 0) {
			timer = durationPerFotogram + System.currentTimeMillis();
			pos++;
			if (pos >= textures.length) {
				if (bucle) {
					pos = 0;
				} else {
					finish = true;
					onFinish();
				}
			}
			needRender = true;
		}
	}
	public void setBucle(boolean bucle) {
		this.bucle = bucle;
	}
	public abstract void onFinish();
}

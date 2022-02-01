package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;
import me.pepe.GameAPI.Utils.RenderLimits;

public abstract class SelectBox extends GameObject {
	private boolean selected = false;
	private boolean over = false;
	private RenderLimits limits;
	public SelectBox(GameLocation gameLocation, Game game, int size) {
		super(gameLocation, game, new ObjectDimension(size, size));
	}
	public SelectBox(GameLocation gameLocation, Game game, int size, RenderLimits limits) {
		super(gameLocation, game, new ObjectDimension(size, size));
		this.limits = limits;
	}
	@Override
	public void tick() {
		boolean newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(), getActualX(), getActualY(), getActualDimensionX(), getActualDimensionY());
		if (!over && newover) {
			onOver();
		}
		over = newover;
	}
	@Override
	public void render(Graphics g) {
		g.setColor(selected ? Color.GRAY : Color.DARK_GRAY);
		g.fillRect(getActualX(), getActualY(), getActualDimensionX(), getActualDimensionY());
	}
	@Override
	public int getCursor() {
		if (over) {
			return Cursor.HAND_CURSOR;
		}
		return Cursor.DEFAULT_CURSOR;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isOver() {
		return over;
	}
	public int getActualX() {
		return (int) (x * (limits != null ? limits.getSizeX() : getGame().getWindows().getActualXToPaint()) / 100) + (limits != null ? limits.getX() : 0);
	}
	public int getActualY() {
		return (int) (y * (limits != null ? limits.getSizeY() : getGame().getWindows().getActualYToPaint()) / 100) + (limits != null ? limits.getY() : 0);
	}
	public int getActualDimensionX() {
		int actualX = (int) (limits != null ? limits.getSizeX() : getGame().getWindows().getActualXToPaint());
		int actualY = (int) (limits != null ? limits.getSizeY() : getGame().getWindows().getActualYToPaint());
		return (int) (getDimension().getX() * (actualY > actualX ? actualX : actualY) / 100);
	}
	public int getActualDimensionY() {
		int actualX = (int) (limits != null ? limits.getSizeX() : getGame().getWindows().getActualXToPaint());
		int actualY = (int) (limits != null ? limits.getSizeY() : getGame().getWindows().getActualYToPaint());
		return (int) (getDimension().getY() * (actualY > actualX ? actualX : actualY) / 100);
	}
	public void select(boolean selected) {
		this.selected = selected;
		onSelect(selected);
	}
	public abstract void onSelect(boolean selected);
	public abstract void onOver();
}

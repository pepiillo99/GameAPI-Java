package me.pepe.GameAPI.Game.Objects.ScreenObjects.Button;

import java.awt.Cursor;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;
import me.pepe.GameAPI.Utils.RenderLimits;

public abstract class Button extends GameObject {
	private String name = "button";
	private boolean over = false;
	private boolean show = true;
	private RenderLimits limits;
	public Button(String name, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		super(gameLocation, game, dimension);
		this.name = name;
	}
	public Button(String name, GameLocation gameLocation, Game game, ObjectDimension dimension, RenderLimits limits) {
		super(gameLocation, game, dimension);
		this.name = name;
		this.limits = limits;
	}
	public String getName() {
		return name;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	@Override
	public void tick() {
		boolean newover = false;
		if (isOnMenu()) {
			newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(getMenu()), (int) (getActualX() + getMenu().getStartRender().getX()), (int) (getActualY() + getMenu().getStartRender().getY()), getActualDimensionX(), getActualDimensionY());
		} else {
			newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(), getActualX(), getActualY(), getActualDimensionX(), getActualDimensionY());
		}
		if (!over && newover) {
			onOver();
		}
		over = newover;
	}
	@Override
	public int getCursor() {
		if (over) {
			return Cursor.HAND_CURSOR;
		}
		return Cursor.DEFAULT_CURSOR;
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
	public abstract void onClick();
	public abstract void onOver();
}
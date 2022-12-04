package me.pepe.GameAPI.Game.Objects.ScreenObjects.Button;

import java.awt.Cursor;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;

public abstract class Button extends GameObject {
	private boolean over = false;
	private boolean show = true;
	public Button(InteligentPosition intPos, Game game, InteligentDimension intDim) {
		super(intPos, game, intDim);
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
			if (hasInteligence()) {
				newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(getMenu()), (int) (getX() + getMenu().getStartRender().getX()), (int) (getY() + getMenu().getStartRender().getY()), (int) getDimension().getX(), (int) getDimension().getY());
			}
		} else {
			if (hasInteligence()) {
				newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(), (int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY());
			}
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
	public abstract void onClick();
	public abstract void onOver();
}
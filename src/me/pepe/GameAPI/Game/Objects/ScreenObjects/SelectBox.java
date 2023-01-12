package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;

public abstract class SelectBox extends GameObject {
	private boolean selected = false;
	private boolean over = false;
	public SelectBox(InteligentPosition intPos, Game game, InteligentDimension intDim) {
		this("", intPos, game, intDim);
	}
	public SelectBox(String id, InteligentPosition intPos, Game game, InteligentDimension intDim) {
		super(id, intPos, game, intDim);
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
	public void render(Graphics g) {
		g.setColor(selected ? Color.GRAY : Color.DARK_GRAY);
		if (hasInteligence()) {
			g.fillRect((int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY());
		}
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
	public void select(boolean selected) {
		this.selected = selected;
		onSelect(selected);
	}
	public abstract void onSelect(boolean selected);
	public abstract void onOver();
}

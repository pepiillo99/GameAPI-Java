package me.pepe.GameAPI.Game.Objects;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Menu;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;

public abstract class GameObject {
	private Game game;
	protected double x;
	protected double y;
	private int id;
	private int velX;
	private int velY;
	private boolean windowsPassable = true;
	private ObjectDimension dimension;
	private Rectangle hitBox;
	private boolean move = true;
	private Menu menu;
	public GameObject(GameLocation gameLocation, Game game, ObjectDimension dimension) {
		this.game = game;
		this.x = gameLocation.getX();
		this.y = gameLocation.getY();
		this.id = game.getNextObjectID();
		this.dimension = dimension;
		hitBox = new Rectangle((int) x, (int) y, (int) dimension.getX(), (int) dimension.getY());
	}
	protected Game getGame() {
		return game;
	}
	public int getID() {
		return id;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getVelX() {
		return velX;
	}
	public int getVelY() {
		return velY;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public void setVelY(int velY) {
		this.velY = velY;
	}
	protected void setMove(boolean move) {
		this.move = move;
	}
	protected boolean isMove() {
		return move;
	}
	public ObjectDimension getDimension() {
		return dimension;
	}
	public Rectangle getHitBox() {
		return hitBox;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public Menu getMenu() {
		return menu;
	}
	public boolean isOnMenu() {
		return menu != null;
	}
	public boolean isCollision(GameObject object) {
		return object.getHitBox().intersects(hitBox);
	}
	public void internalTick() {
		if (move) {
			x += velX;
			y += velY;
			if (!windowsPassable) {
				x = clamp((int) x, 0, game.getWindows().getXToPaint() - (int) dimension.getX());
				y = clamp((int) y, 0, game.getWindows().getYToPaint() - (int) dimension.getY());
			}
			hitBox.setBounds((int) x, (int) y, (int) dimension.getX(), (int) dimension.getY());
		}
		tick();
	}
	protected void setWindowsPassable(boolean windowsPassable) {
		this.windowsPassable = windowsPassable;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	public void onScreen() {
		
	}
	public void onQuitScreen() {
		
	}
	private int clamp(int var, int min, int max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}
	public int getCursor() {
		return Cursor.DEFAULT_CURSOR;
	}
}

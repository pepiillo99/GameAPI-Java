package me.pepe.GameAPI.Screen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Menu;
import me.pepe.GameAPI.TextureManager.Animation;
import me.pepe.GameAPI.Utils.FirstRender;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Windows.KeyInput;
import me.pepe.GameAPI.Windows.MouseInput;
import me.pepe.GameAPI.Windows.Windows;

public abstract class Screen {
	private Windows windows;
	private Game game;
	private MouseInput mouseInput;
	private GameLocation mouseLoc = new GameLocation(0, 0);
	private KeyInput keyInput;
	private List<GameObject> objects = new ArrayList<GameObject>();
	private List<Animation> animations = new ArrayList<Animation>();
	private boolean loaded = false;
	private int cursor = Cursor.DEFAULT_CURSOR;
	private FirstRender fRender = FirstRender.BUILD;
	public Screen(Windows windows, Game game) {
		this.windows = windows;
		this.game = game;
	}
	public Screen(Windows windows, Game game, MouseInput mouseInput, KeyInput keyInput) {
		this.windows = windows;
		this.game = game;
		this.mouseInput = mouseInput;
		this.keyInput = keyInput;
	}
	public boolean isLoaded() {
		return loaded;
	}
	public void setLoaded() {
		loaded = true;
	}
	public boolean informed = false;
	public void buildLevel(Graphics g) {
		if (!informed) {
			System.out.println(System.currentTimeMillis() - getWindows().start + "ms en renderizar");
			informed = true;
		}
		if (fRender.equals(FirstRender.BUILD)) {
			paintLevel(g);
			ArrayList<GameObject> objects_copy = (ArrayList<GameObject>) ((ArrayList<GameObject>) objects).clone();
			for (GameObject object : objects_copy) {
				object.render(g);
			}
			ArrayList<Animation> animation_copy = (ArrayList<Animation>) ((ArrayList<Animation>) animations).clone();
			for (Animation anim : animation_copy) {
				anim.render(g);
			}
		} else {
			ArrayList<GameObject> objects_copy = (ArrayList<GameObject>) ((ArrayList<GameObject>) objects).clone();
			for (GameObject object : objects_copy) {
				object.render(g);
			}
			ArrayList<Animation> animation_copy = (ArrayList<Animation>) ((ArrayList<Animation>) animations).clone();
			for (Animation anim : animation_copy) {
				anim.render(g);
			}
			paintLevel(g);
		}
		g.setColor(getFPSTPSColor(getGame().getTPS(), getGame().getFPS()));
		g.setFont(new Font("Aria", Font.PLAIN, 10));
		// https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
		g.drawString("FPS: " + getGame().getFPS() + " TPS: " + getGame().getTPS(), 0, 10);
	}
	public void tick() {
		if (getMouseInput() != null) {
			getMouseInput().tick();
			mouseLoc = new GameLocation(getMouseInput().getX(), getMouseInput().getY());
		}
		if (getKeyInput() != null) {
			getKeyInput().tick();
		}
		int cursor = this.cursor;
		ArrayList<GameObject> objects_copy = (ArrayList<GameObject>) ((ArrayList<GameObject>) objects).clone();
		for (GameObject object : objects_copy) {
			object.tick();
			if (this.cursor == Cursor.DEFAULT_CURSOR && object.getCursor() != Cursor.DEFAULT_CURSOR) {
				cursor = object.getCursor();
			}
			if (object instanceof Menu) {
				Menu menu = (Menu) object;
				ArrayList<GameObject> objects_menu_copy = (ArrayList<GameObject>) ((ArrayList<GameObject>) menu.getGameObjects()).clone();
				for (GameObject menu_object : objects_menu_copy) {
					menu_object.tick();
					if (this.cursor == Cursor.DEFAULT_CURSOR && menu_object.getCursor() != Cursor.DEFAULT_CURSOR) {
						cursor = menu_object.getCursor();
					}
				}
			}
		}
		getWindows().setCursor(cursor);
		internalTick();
	}
	public abstract void internalTick();
	protected abstract void paintLevel(Graphics g);
	protected Windows getWindows() {
		return windows;
	}
	protected Game getGame() {
		return game;
	}
	public Color getBackground() {
		return new Color(0, 0, 0);
	}
	public MouseInput getMouseInput() {
		return mouseInput;
	}
	public KeyInput getKeyInput() {
		return keyInput;
	}
	protected void setMouseInput(MouseInput mouseInput) {
		this.mouseInput = mouseInput;
	}
	protected void setKeyInput(KeyInput keyInput) {
		this.keyInput = keyInput;
	}
	public List<GameObject> getGameObjects() {
		return objects;
	}
	public void addGameObject(GameObject gameObject) {
		if (!objects.contains(gameObject)) {
			objects.add(gameObject);
		}
	}
	public void removeGameObject(GameObject gameObject) {
		if (objects.contains(gameObject)) {
			objects.remove(gameObject);
		}
	}
	public void addAnimation(Animation anim) {
		if (!animations.contains(anim)) {
			animations.add(anim);
		}
	}
	public void removeAnimation(Animation anim) {
		if (animations.contains(anim)) {
			animations.remove(anim);
		}
	}
	public void restartAnimations() {
		animations.clear();
	}
	public boolean isTouch(GameLocation mouse, GameObject object) {
		return isTouch(mouse, (int) object.getX(), (int) object.getY(), (int) object.getDimension().getX(), (int) object.getDimension().getY());
	}
	public boolean isTouch(GameLocation mouse, int x, int y, int dimX, int dimY) {
		if (mouse.getX() >= x && mouse.getX() <= x + dimX && mouse.getY() >= y && mouse.getY() <= y + dimY) {
			return true;
		}	
		return false;
	}
	private Color getFPSTPSColor(int tps, int fps) {
		double correctFPS = ((getGame().getFPS() != 0 ? getGame().getFPS() : 0.1) * 100) / getGame().getMaxFPS();
		double correctTPS = ((getGame().getTPS() != 0 ? getGame().getTPS() : 0.1) * 100) / getGame().getMaxTPS();
		int totalCorrect = (int) (((correctFPS + correctTPS) * 100) / 200);
		int totalCorrectPorcent = ((255 * totalCorrect) / 100);
		if (totalCorrectPorcent < 0) { // el porcentage puede superar el 100% si los fps o tps son mayores que el maximo
			totalCorrectPorcent = 0;
		} else if (totalCorrectPorcent > 255) { // lo mismo que los fps pero con los tps xd
			totalCorrectPorcent = 255;
		}
		return new Color(255 - totalCorrectPorcent, totalCorrectPorcent, 0, 200);
	}
	public GameLocation getMouseLocation() {
		return mouseLoc;
	}
	public GameLocation getMouseLocation(Menu menu) {
		return new GameLocation(mouseLoc.getX() - menu.getActualX(), mouseLoc.getY() - menu.getActualY());
	}
	public void restartObjects(Class<? extends GameObject> clase) {
		Iterator<GameObject> iterator = objects.iterator();
		while (iterator.hasNext()) {
			GameObject object = iterator.next();
			if (object.getClass().getSuperclass() == clase) {
				iterator.remove();
			}
		}
	}
	public int getCursor() {
		return cursor;
	}
	public void setCursor(int cursor) {
		this.cursor = cursor;
	}
	public void onOpen() {
		
	}
	public void onQuit() {
		
	}
	public void setFirstRender(FirstRender fRender) {
		this.fRender = fRender;
	}
}

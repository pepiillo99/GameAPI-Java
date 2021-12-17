package me.pepe.GameAPI.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.TextureManager.Animation;
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
	public Screen(Windows windows, Game game) {
		this.windows = windows;
		this.game = game;
	}
	public Screen(Windows windows, MouseInput mouseInput, KeyInput keyInput) {
		this.windows = windows;
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
		paintLevel(g);
		ArrayList<GameObject> objects_copy = (ArrayList<GameObject>) ((ArrayList<GameObject>) objects).clone();
		for (GameObject object : objects_copy) {
			object.render(g);
		}
		ArrayList<Animation> animation_copy = (ArrayList<Animation>) ((ArrayList<Animation>) animations).clone();
		for (Animation anim : animation_copy) {
			anim.render(g);
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
		ArrayList<GameObject> objects_copy = (ArrayList<GameObject>) ((ArrayList<GameObject>) objects).clone();
		for (GameObject object : objects_copy) {
			object.tick();
		}
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
		if (mouse.getX() >= object.getX() && mouse.getX() <= object.getX() + object.getDimension().getX() && mouse.getY() >= object.getY() && mouse.getY() <= object.getY() + object.getDimension().getY()) {
			return true;
		}	
		return false;
	}
	private Color getFPSTPSColor(int tps, int fps) { // vivan las matematicas :)
		double correctFPS = ((getGame().getFPS() != 0 ? getGame().getFPS() : 0.1) * 100) / getGame().getMaxFPS();
		double correctTPS = ((getGame().getTPS() != 0 ? getGame().getTPS() : 0.1) * 100) / getGame().getMaxTPS();
		int totalCorrect = (int) (((correctFPS + correctTPS) * 100) / 200);
		int totalCorrectPorcent = ((255 * totalCorrect) / 100);
		//System.out.println("totalCorrectPorcent: " + totalCorrectPorcent);
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
	public void restartObjects(Class<? extends GameObject> clase) {
		Iterator<GameObject> iterator = objects.iterator();
		while (iterator.hasNext()) {
			GameObject object = iterator.next();
			if (object.getClass().getSuperclass() == clase) {
				iterator.remove();
			}
		}
	}
}

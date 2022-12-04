package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.TextureManager.Animation;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;

public abstract class Menu extends GameObject {
	private boolean show = true;
	private boolean over = false;
	private HashMap<String, GameObject> objects = new HashMap<String, GameObject>();
	private List<Animation> animations = new ArrayList<Animation>();
	private GameLocation startRender = new GameLocation(0, 0);
	private int maxMoveX, minMoveX, maxMoveY, minMoveY = 0;
	private boolean clicked = false;
	public Menu(InteligentPosition intPos, Game game, InteligentDimension intDim) {
		super(intPos, game, intDim);
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
		over = newover;
	}
	@Override
	public void internalTick() {
		tick();
		for (GameObject go : getGameObjects()) {
			go.internalTick();
		}
	}
	@Override
	public void render(Graphics g) {
		if (show) {
			if (hasInteligence()) {
				g.drawImage(internalBuild((int) getDimension().getX(), (int) getDimension().getY()), (int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY(), null);
			}
		}
	}
	@Override
	public void onScreen() {
		for (GameObject go : getGameObjects()) {
			go.onScreen();
		}
	}
	@Override
	public void onQuitScreen() {
		for (GameObject go : getGameObjects()) {
			go.onQuitScreen();
		}
	}
	public GameLocation getStartRender() {
		return startRender;
	}
	public void setStartRender(GameLocation startRender) {
		double x = startRender.getX();
		double y = startRender.getY();
		if (startRender.getX() < maxMoveX) {
			x = maxMoveX;
		} else if (startRender.getX() > minMoveX) {
			x = minMoveX;
		}
		if (startRender.getY() < maxMoveY) {
			y = maxMoveY;
		} else if (startRender.getY() > minMoveY) {
			y = minMoveY;
		}
		this.startRender = new GameLocation(x, y);
	}
	public void setMoveLimits(int maxMoveX, int minMoveX, int maxMoveY, int minMoveY) {
		this.maxMoveX = maxMoveX;
		this.minMoveX = minMoveX;
		this.maxMoveY = maxMoveY;
		this.minMoveY = minMoveY;
	}
	private BufferedImage internalBuild(int width, int height) {
		BufferedImage image = new BufferedImage(width <= 0 ? 1 : width, height <= 0 ? 1 : height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		build(g);
		for (GameObject object : getGameObjectClonned()) {
			object.internalRender(g);
		}
		ArrayList<Animation> animation_copy = (ArrayList<Animation>) ((ArrayList<Animation>) animations).clone();
		for (Animation anim : animation_copy) {
			anim.render(g);
		}
		g.dispose();
		BufferedImage finalImage = new BufferedImage(width <= 0 ? 1 : width, height <= 0 ? 1 : height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2 = finalImage.createGraphics();
		g2.fillRect(0, 0, width-1, height-1);
		g2.drawImage(image, 0 + ((int) startRender.getX()), 0 + ((int) startRender.getY()), width <= 0 ? 1 : width, height <= 0 ? 1 : height, null);
		g.dispose();
		return finalImage;
	}
	public abstract void build(Graphics g);
	public GameObject getGameObject(String id) {
		if (objects.containsKey(id)) {
			return objects.get(id);
		}
		return null;
	}
	public Collection<GameObject> getGameObjects() {
		return objects.values();
	}
	public ArrayList<GameObject> getGameObjectClonned() {
		return new ArrayList<GameObject>(getGameObjects());
	}
	public void addGameObject(GameObject gameObject) {
		if (!objects.containsKey(gameObject.getID())) {
			gameObject.setMenu(this);
			objects.put(gameObject.getID(), gameObject);
		} else {
			throw new IllegalArgumentException("Ya hay un objeto con el nombre " + gameObject.getID() + " en el menu " + getID());
		}
	}
	public void removeGameObject(GameObject gameObject) {
		if (objects.containsKey(gameObject.getID())) {
			objects.remove(gameObject.getID());
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
	public boolean isOver() {
		return over;
	}
	public boolean isClicked() {
		return clicked;
	}
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	public void registerClick(int x, int y) {
		onClick(x, y);
	}
	public abstract void onClick(int x, int y);
}

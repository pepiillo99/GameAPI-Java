package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.TextureManager.Animation;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;
import me.pepe.GameAPI.Utils.RenderLimits;

public abstract class Menu extends GameObject {
	private boolean show = true;
	private boolean over = false;
	private RenderLimits limits;
	private RenderLimits menuRenderLimits;
	private List<GameObject> objects = new ArrayList<GameObject>();
	private List<Animation> animations = new ArrayList<Animation>();
	private GameLocation startRender = new GameLocation(0, 0);
	private int maxMoveX, minMoveX, maxMoveY, minMoveY = 0;
	private boolean clicked = false;
	public Menu(GameLocation gameLocation, Game game, ObjectDimension dimension) {
		this(gameLocation, game, dimension, null);
	}
	public Menu(GameLocation gameLocation, Game game, ObjectDimension dimension, RenderLimits limits) {
		super(gameLocation, game, dimension);
		this.limits = limits;
		this.menuRenderLimits = new RenderLimits() {
			@Override
			protected int calcX(int x, int y) {
				return 0;
			}
			@Override
			protected int calcY(int x, int y) {
				return 0;
			}
			@Override
			protected int calcSizeX(int x, int y) {
				return getActualDimensionX();
			}
			@Override
			protected int calcSizeY(int x, int y) {
				return getActualDimensionY();
			}			
		};
	}
	@Override
	public void tick() {
		menuRenderLimits.calc(getActualDimensionX(), getActualDimensionY());
		boolean newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(), getActualX(), getActualY(), getActualDimensionX(), getActualDimensionY());
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
			g.drawImage(internalBuild(getActualDimensionX(), getActualDimensionY()), getActualX(), getActualY(), getActualDimensionX(), getActualDimensionY(), null);
		}
	}
	public RenderLimits getMenuRenderLimits() {
		return menuRenderLimits;
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
		ArrayList<GameObject> objects_copy = (ArrayList<GameObject>) ((ArrayList<GameObject>) objects).clone();
		for (GameObject object : objects_copy) {
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
		return (int) (getDimension().getY() * (actualY > actualX ? actualX : actualY) / 100) - 1;
	}
	public List<GameObject> getGameObjects() {
		return objects;
	}
	public void addGameObject(GameObject gameObject) {
		if (!objects.contains(gameObject)) {
			gameObject.setMenu(this);
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

package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Screen.Screen;
import me.pepe.GameAPI.Utils.DOMUtils;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.Margin;
import me.pepe.GameAPI.Utils.Borders.HorizontalBorders;
import me.pepe.GameAPI.Utils.Borders.VerticalBorders;
import me.pepe.GameAPI.Utils.InteligentDimensions.ExtendInteligentDimension;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;
import me.pepe.GameAPI.Utils.InteligentPositions.ReferenceBordersInteligentPosition;

public abstract class Menu extends GameObject {
	/*
	 * El menu deberá de bajar automaticamente si un objeto de su interior sale de las dimensiones del menu o si sale de la pantalla
	 */
	private boolean show = true;
	private boolean over = false;
	private HashMap<String, GameObject> objects = new HashMap<String, GameObject>();
	private List<Animation> animations = new ArrayList<Animation>();
	private GameLocation startRender = new GameLocation(0, 0);
	private int maxMoveX, maxMoveY = 0;
	private boolean clicked = false;
	private Margin margin = new Margin();
	private boolean paintBackground = true;
	public Menu(InteligentPosition intPos, Screen screen, InteligentDimension intDim) {
		this("", intPos, screen, intDim);
	}
	public Menu(String id, InteligentPosition intPos, Screen screen, InteligentDimension intDim) {
		super(id, intPos, screen, intDim);
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
	// hacer posibilidad de que el min/max move sea automatico o manual
	@Override
	public void render(Graphics g) {
		if (show) {
			if (hasInteligence()) {
				g.drawImage(internalBuild((int) getDimension().getX(), (int) getDimension().getY()), (int) getX(), (int) getY(), null);
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
	@Override
	public boolean needRender() {
		if (needRender) {
			return true;
		} else {
			for (GameObject gm : getGameObjectClonned()) {
				if (gm.needRender()) {
					return true;
				}
			}
			return false;
		}
	}
	@Override
	public void renderTick() {
		for (GameObject gm : getGameObjectClonned()) {
			gm.renderTick();
		}
	}
	public GameLocation getStartRender() {
		return startRender;
	}
	public int calcMaxMoveX() {
		int result = 0;
		for (GameObject object : getGameObjects()) {
			int endX = (int) (object.getX() + object.getDimension().getX() - getDimension().getX());
			if (result < endX) {
				result = endX;
			}
		}
		return result;
	}
	public int calcMaxMoveY() {
		int result = 0;
		for (GameObject object : getGameObjects()) {
			int endY = (int) (object.getY() + object.getDimension().getY() - getDimension().getY());
			if (result < endY) {
				result = endY;
			}
		}
		return result;
	}
	public void setStartRender(GameLocation startRender) {
		double x = startRender.getX();
		double y = startRender.getY();
		if (maxMoveX < 0) {
			if (startRender.getX() < maxMoveX) {
				x = maxMoveX;
			} else if (startRender.getX() > 0) {
				x = 0;
			}
		} else {
			if (startRender.getX() > maxMoveX) {
				x = maxMoveX;
			} else if (startRender.getX() < 0) {
				x = 0;
			}
		}
		if (maxMoveY < 0) {
			if (startRender.getY() < maxMoveY) {
				y = maxMoveY;
			} else if (startRender.getY() > 0) {
				y = 0;
			}
		} else {
			if (startRender.getY() > maxMoveY) {
				y = maxMoveY;
			} else if (startRender.getY() < 0) {
				y = 0;
			}
		}
		System.out.println("StartRender: " + x + " - " + y);
		this.startRender = new GameLocation(x, y);
		needRender = true;
	}
	public void setMoveLimits(int maxMoveX, int maxMoveY) {
		this.maxMoveX = maxMoveX;
		this.maxMoveY = maxMoveY;
	}
	private BufferedImage internalBuild(int width, int height) {
		BufferedImage image = new BufferedImage(width <= 0 ? 1 : width + Math.abs((int) startRender.getX()), height <= 0 ? 1 : height + Math.abs((int) startRender.getY()), BufferedImage.TYPE_4BYTE_ABGR);
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
		BufferedImage finalImage = new BufferedImage(width <= 0 ? 1 : width + Math.abs((int) startRender.getX()), height <= 0 ? 1 : height + Math.abs((int) startRender.getY()), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2 = finalImage.createGraphics();
		if (paintBackground) {
			g2.fillRect(0, 0, width-1, height-1);
		}
		g2.drawImage(image, 0 + ((int) startRender.getX()), 0 + ((int) startRender.getY()), null);
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
			if (gameObject.getInteligentDimension() instanceof ExtendInteligentDimension) {
				ExtendInteligentDimension extendInteligentDimension = (ExtendInteligentDimension) gameObject.getInteligentDimension();
				extendInteligentDimension.setGameObject(gameObject);
				extendInteligentDimension.setMenu(this);
			}
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
	public int getLastObjectWeidht() {
		int result = 0;
		for (GameObject object : getGameObjectClonned()) {
			boolean valid = true;
			if (object.hasInteligence() && object.getInteligentPosition() instanceof ReferenceBordersInteligentPosition) {
				ReferenceBordersInteligentPosition inteligentPosition = (ReferenceBordersInteligentPosition) object.getInteligentPosition();
				if (inteligentPosition.getVerticalBorder() == VerticalBorders.RIGHT) {
					valid = false;
				}
			}
			if (valid) {
				int sum = (int) (object.getX() + object.getDimension().getX());
				if (result < sum) {
					result = sum;
				}
			}
		}
		return result;
	}
	public int getLastObjectHeight() {
		int result = 0;
		for (GameObject object : getGameObjectClonned()) {
			boolean valid = true;
			if (object.hasInteligence() && object.getInteligentPosition() instanceof ReferenceBordersInteligentPosition) {
				ReferenceBordersInteligentPosition inteligentPosition = (ReferenceBordersInteligentPosition) object.getInteligentPosition();
				if (inteligentPosition.getHorizontalBorder() == HorizontalBorders.DOWN) {
					valid = false;
				}
			}
			if (valid) {
				int sum = (int) (object.getY() + object.getDimension().getY());
				if (result < sum) {
					result = sum;
				}
			}
		}
		return result;
	}
	public abstract void onClick(int x, int y);
	public Margin getMargin() {
		return margin;
	}
	public void setMargin(Margin margin) {
		this.margin = margin;
	}
	public void loadFromXML(String filePath) {
		File file = new File(this.getClass().getClassLoader().getResource(filePath).getPath().replace("%c3%b3", "ó").replace("bin", "resources"));
		System.out.println("Menu loaded from file " + file.getAbsolutePath());
		Document doc = DOMUtils.abrirDOM(file);
		Node node = doc.getFirstChild();
		for (Node object : DOMUtils.getChildrens(DOMUtils.getChild(node, "objects"))) {
			GameObject.build(this, this, getScreen(), object);
		}
	}
	public boolean isPaintBackground() {
		return paintBackground;
	}
	public void setPaintBackground(boolean paintBackgroun) {
		this.paintBackground = paintBackgroun;
	}
}

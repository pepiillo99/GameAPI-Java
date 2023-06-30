package me.pepe.GameAPI.Screen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Menu;
import me.pepe.GameAPI.TextureManager.Animation;
import me.pepe.GameAPI.Utils.DOMUtils;
import me.pepe.GameAPI.Utils.FirstRender;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.InteligentDimensions.ExtendInteligentDimension;
import me.pepe.GameAPI.Windows.KeyInput;
import me.pepe.GameAPI.Windows.MouseInput;
import me.pepe.GameAPI.Windows.Windows;
import me.pepe.GameAPI.Windows.Buttons.MouseButtons;

public abstract class Screen {
	private Windows windows;
	private Game game;
	private MouseInput mouseInput;
	private GameLocation mouseLoc = new GameLocation(0, 0);
	private KeyInput keyInput;
	private HashMap<String, GameObject> objects = new HashMap<String, GameObject>();
	private List<Animation> animations = new ArrayList<Animation>();
	private boolean loaded = false;
	private int cursor = Cursor.DEFAULT_CURSOR;
	private FirstRender fRender = FirstRender.BUILD;
	private Color background = Color.BLACK;
	public Screen(Windows windows, Game game) {
		this.windows = windows;
		this.game = game;
		this.keyInput = new KeyInput(this) {
			@Override
			public void tick() {}
			@Override
			public void onKeyPressed(int key) {}
			@Override
			public void onKeyReleased(int key) {}		
		};
		this.mouseInput = new MouseInput(this) {
			@Override
			public void tick() {
				
			}
			@Override
			public void onClick(MouseButtons mouseButton) {
				
			}
			@Override
			public void onWheelMoved(MouseButtons mouseButton) {
				
			}
			@Override
			public void onButtonPressed(MouseButtons mouseButton) {
				
			}
			@Override
			public void onButtonReleased(MouseButtons mouseButton) {
				
			}			
		};
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
			for (GameObject object : getGameObjectClonned()) {
				object.internalRender(g);
			}
			ArrayList<Animation> animation_copy = (ArrayList<Animation>) ((ArrayList<Animation>) animations).clone();
			for (Animation anim : animation_copy) {
				anim.render(g);
			}
		} else {
			for (GameObject object : getGameObjectClonned()) {
				object.internalRender(g);
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
		if (game.isShowInfo()) {
			g.drawString("FPS: " + getGame().getFPS() + " TPS: " + getGame().getTPS(), 0, 10);
		}
	}
	public void tick() {
		if (getMouseInput() != null) {
			getMouseInput().tick();
			if (mouseLoc == null) {
				mouseLoc = new GameLocation(getMouseInput().getX(), getMouseInput().getY());
			} else {
				mouseLoc.setX(getMouseInput().getX());
				mouseLoc.setY(getMouseInput().getY());
			}
		}
		if (getKeyInput() != null) {
			getKeyInput().tick();
		}
		int cursor = this.cursor;
		for (GameObject object : getGameObjectClonned()) {
			object.internalTick();
			if (this.cursor == Cursor.DEFAULT_CURSOR && object.getCursor() != Cursor.DEFAULT_CURSOR) {
				cursor = object.getCursor();
			}
			if (object instanceof Menu) {
				Menu menu = (Menu) object;
				for (GameObject menu_object : menu.getGameObjectClonned()) {
					menu_object.internalTick();
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
	public Windows getWindows() {
		return windows;
	}
	protected Game getGame() {
		return game;
	}
	public Color getBackground() {
		return background;
	}
	public void setBackground(Color color) {
		this.background = color;
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
				extendInteligentDimension.setScreen(this);
			}
			objects.put(gameObject.getID(), gameObject);
		} else {
			throw new IllegalArgumentException("Ya hay un objeto con el nombre " + gameObject.getID() + " en la pantalla " + getClass().getSimpleName());
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
	public void restartObjects() {
		objects.clear();
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
		int menuXLoc = 0;
		int menuYLoc = 0;
		Menu lastMenu = menu;
		while (lastMenu.isOnMenu()) {
			lastMenu = lastMenu.getMenu();
			menuXLoc += lastMenu.getX();
			menuYLoc += lastMenu.getY();
		}
		return new GameLocation(mouseLoc.getX() - menu.getX() - menuXLoc, mouseLoc.getY() - menu.getY() - menuYLoc);
	}
	public void restartObjects(Class<? extends GameObject> clase) {
		Iterator<GameObject> iterator = getGameObjectClonned().iterator();
		while (iterator.hasNext()) {
			GameObject object = iterator.next();
			if (object.getClass().getSuperclass() == clase || object.getClass() == clase) {
				removeGameObject(object);
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
	public void loadFromXML(String filePath) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
		File tempFile;
		try {
			tempFile = File.createTempFile("temp", null);
			// Copia el contenido del flujo de entrada al archivo temporal
			try (OutputStream outputStream = new FileOutputStream(tempFile)) {
			    byte[] buffer = new byte[8192];
			    int bytesRead;
			    while ((bytesRead = inputStream.read(buffer)) != -1) {
			        outputStream.write(buffer, 0, bytesRead);
			    }
			}
			loadFromXML(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadFromXML(File file) {
		long startLoad = System.currentTimeMillis();
		System.out.println("Screen loaded from file " + file.getAbsolutePath());
		Document doc = DOMUtils.abrirDOM(file);
		Node node = doc.getFirstChild();
		for (Node object : DOMUtils.getChildrens(DOMUtils.getChild(node, "objects"))) {
			GameObject.build(this, this, getGame(), object);
		}
		onLoadXML();
		System.out.println("Screen loaded in " + (System.currentTimeMillis() - startLoad) + "ms");
	}
	public void onLoadXML() {
		
	}
}

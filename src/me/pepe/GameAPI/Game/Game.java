package me.pepe.GameAPI.Game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.Collection;
import java.util.HashMap;

import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Screen.Screen;
import me.pepe.GameAPI.Screen.ScreenManager;
import me.pepe.GameAPI.Windows.Windows;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 3210817684040472217L;
	private String screen = "";
	private boolean running = false;
	private Thread thread;
	private Windows windows;
	private ScreenManager screenManager;
	private int objectID = 0;
	private HashMap<Integer, GameObject> objects = new HashMap<Integer, GameObject>();
	private int tps = 1;
	private int fps = 1;
	private int maxTPS = 20;
	private int maxFPS = 144;
	public Game(String windowsName, int wx, int wy, Image icon) {
		screenManager = new ScreenManager(this);
		windows = new Windows(windowsName, wx, wy, icon, this);
	}
	long start = 0;
	public void start() {
		windows.setVisible();
		start = System.currentTimeMillis();
		//System.out.println(System.currentTimeMillis() - start + "ms en ver la ventanaaaa");
		createBufferStrategy(3);
		//System.out.println(System.currentTimeMillis() - start + "ms en crear el buffer");
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	public void stop() {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void tick() {
		for (GameObject object : getObjects()) {
			object.internalTick();
		}
		if (getScreen() != null) {
			getScreen().tick();
		}
	}
	private boolean screenLoading = false;
	private boolean screenLoaded = false;
	private void render() {
		//System.out.println(System.currentTimeMillis() - start + "ms en el primer render");
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(getScreen().getBackground());
		g.fillRect(0, 0, getWindows().getXToPaint(), getWindows().getYToPaint());
		//System.out.println(System.currentTimeMillis() - start + "ms en dibujar el rectangulo de fondo");
		if (screenLoaded) {
			getScreen().buildLevel(g);
			//System.out.println(System.currentTimeMillis() - start + "ms en dibujar level");
			for (GameObject object : getObjects()) {
				object.render(g);
			}
		} else {
			if (!screenLoading) {
				screenLoading = true;
				new Thread() {
					@Override
					public void run() {
						int amount = 0;
						while (amount < 5) {
							getScreen().buildLevel(g);
							//System.out.println(System.currentTimeMillis() - start + "ms en dibujar level en el thread");
							for (GameObject object : getObjects()) {
								object.render(g);
							}
							try {
								sleep(5);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							amount++;
						}
						screenLoaded = true;
					}
				}.start();
			}
		}
		//System.out.println(System.currentTimeMillis() - start + "ms en dibujar todo");
		g.dispose();
		//System.out.println(System.currentTimeMillis() - start + "ms en dispose");
		bs.show();
		//System.out.println(System.currentTimeMillis() - start + "ms en el fin del primer render");
	}
	public Screen getScreen() {
		return screenManager.getScreen(screen);
	}
	public void setScreen(String screen) {
		if (screenManager.getScreen(screen) != null) {
			if (getScreen() != null) {
				this.removeMouseListener(getScreen().getMouseInput());
				this.removeMouseWheelListener(getScreen().getMouseInput());
				this.removeMouseMotionListener(getScreen().getMouseInput());
				this.removeKeyListener(getScreen().getKeyInput());
			}
			this.screen = screen;
			this.addMouseListener(getScreen().getMouseInput());
			this.addMouseMotionListener(getScreen().getMouseInput());
			this.addMouseWheelListener(getScreen().getMouseInput());
			this.addKeyListener(getScreen().getKeyInput());
		} else {
			System.out.println("No se pudo cambiar la screen porque es nulla");
		}
	}
	public Windows getWindows() {
		return windows;
	}
	public boolean informed = false;
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsTicks = 1000000000 / maxTPS;
		double deltaTicks = 0;
		double nsFPS = 1000000000 / maxFPS;
		double deltaFPS = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int tick = 0;
		while(running) {
			long now = System.nanoTime();
			deltaTicks += (now - lastTime) / nsTicks;
			deltaFPS += (now - lastTime) / nsFPS;
			lastTime = now;
			while(deltaTicks >= 1) {
				if (running) {
					tick++;
					tick();
					deltaTicks--;
				}
			}
			if (maxFPS != 0) {
				while(deltaFPS >= 1) {
					if (running) {
						if (getScreen() != null) {
							render();
								if (!informed) {
									System.out.println(System.currentTimeMillis() - getWindows().start + "ms en renderizar2");
									System.out.println(System.currentTimeMillis() - start + "ms en renderizar3");
									informed = true;
								}
						}
						frames++;
						deltaFPS--;
					}
				}
			} else {
				if (running) {
					if (getScreen() != null) {
						render();
						if (!informed) {
							System.out.println(System.currentTimeMillis() - getWindows().start + "ms en renderizar2");
							System.out.println(System.currentTimeMillis() - start + "ms en renderizar3");
							informed = true;
						}
					}
					frames++;
				}
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				System.out.println("Ticks: " + tick);
				fps = frames;
				tps = tick;
				tick = 0;
				frames = 0;
			}
		}
		stop();
	}
	public int getNextObjectID() {
		return objectID++;
	}
	public Collection<GameObject> getObjects() {
		return objects.values();
	}
	public GameObject getObject(int id) {
		if (objects.containsKey(id)) {
			return objects.get(id);
		} else {
			return null;
		}
	}
	public void addObject(GameObject object) {
		objects.put(object.getID(), object);
	}
	public void removeObject(int id) {
		objects.remove(id);
	}
	public void removeObject(GameObject object) {
		objects.remove(object.getID());
	}
	public int getTPS() {
		return tps;
	}
	public int getFPS() {
		return fps;
	}
	public int getMaxFPS() {
		return (int) maxFPS;
	}
	public int getMaxTPS() {
		return (int) maxTPS;
	}
	public ScreenManager getScreenManager() {
		return screenManager;
	}
}

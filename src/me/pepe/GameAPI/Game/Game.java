package me.pepe.GameAPI.Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Screen.Screen;
import me.pepe.GameAPI.Screen.ScreenManager;
import me.pepe.GameAPI.Utils.RenderOption;
import me.pepe.GameAPI.Windows.Windows;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 3210817684040472217L;
	private String screen = "";
	private boolean running = false;
	private Thread thread;
	private Windows windows;
	private ScreenManager screenManager;
	private int objectID = 0;
	private int tps = 1;
	private int fps = 1;
	private boolean showInfo = true;
	private int maxTPS = 20;
	private int maxFPS = 144;
	public Game(String windowsName, int wx, int wy, Image icon) {
		screenManager = new ScreenManager(this);
		windows = new Windows(windowsName, wx, wy, icon, this);
		setFocusTraversalKeysEnabled(false);
	}
	private long start = 0;
	public void start() {
		windows.setVisible();
		requestFocus();
		start = System.currentTimeMillis();
		//System.out.println(System.currentTimeMillis() - start + "ms en ver la ventanaaaa");
		createBufferStrategy(3);
		//System.out.println(System.currentTimeMillis() - start + "ms en crear el buffer");
		thread = new Thread(this);
		if (getScreen() != null) {
			getScreen().onOpen();
			for (GameObject object : getScreen().getGameObjects()) {
				object.onScreen();
			}	
		}	
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
		if (getScreen() != null) {
			getScreen().tick();
		}
	}
	private boolean screenLoading = false;
	private boolean screenLoaded = false;
	private BufferedImage lastRender;
	private int objectRendered = 0;
	private int lastObjectRendered = 0;
	private boolean render() {
		//System.out.println(System.currentTimeMillis() - start + "ms en el primer render");
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return false;
		}
		try {
			boolean rendered = false;
			Graphics bsg = bs.getDrawGraphics();
			if (lastRender == null || (lastRender.getWidth() != getWindows().getX() || lastRender.getHeight() != getWindows().getY())) {
				lastRender = new BufferedImage(getWindows().getX(), getWindows().getY(), BufferedImage.TYPE_INT_ARGB);
				getScreen().setScreenNeedRender(true);
			}
			Graphics g = lastRender.getGraphics();
			//System.out.println(System.currentTimeMillis() - start + "ms en dibujar el rectangulo de fondo");
			if (screenLoaded) {
				getScreen().internalRenderTick();
				if (getScreen().needRender()) {
					if (getScreen().screenNeedRender()) {
						g.setColor(getScreen().getBackground());
						g.fillRect(0, 0, getWindows().getX(), getWindows().getY());
					}
					objectRendered+= getScreen().buildLevel(g);
					rendered = true;
				}
				//System.out.println(System.currentTimeMillis() - start + "ms en dibujar level");
			} else {
				if (!screenLoading) {
					screenLoading = true;
					rendered = true;
					new Thread() {
						@Override
						public void run() {
							int amount = 0;
							while (amount < 5) {
								g.setColor(getScreen().getBackground());
								g.fillRect(0, 0, getWindows().getX(), getWindows().getY());
								objectRendered += getScreen().buildLevel(g);
								if (getScreen().getRenderOption().equals(RenderOption.WHEN_NEED)) {
									getScreen().setScreenNeedRender(true);
								}
								//System.out.println(System.currentTimeMillis() - start + "ms en dibujar level en el thread");
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
			if (isShowInfo()) {
				g.setColor(Color.BLACK);
				g.setFont(new Font("Aria", Font.PLAIN, 10));
				g.fillRect(0, 0, 250, 13);
				g.setColor(getFPSTPSColor(getTPS(), getFPS()));
				g.drawString("FPS: " + getFPS() + " TPS: " + getTPS() + (getScreen().getRenderOption().equals(RenderOption.WHEN_NEED) ? " OBJECTS RENDERED: " + lastObjectRendered : ""), 0, 10);
			}
			bsg.drawImage(lastRender, 0, 0, getWindows().getX(), getWindows().getY(), null);
			//System.out.println(System.currentTimeMillis() - start + "ms en dibujar todo");
			bsg.dispose();
			//System.out.println(System.currentTimeMillis() - start + "ms en dispose");
			bs.show();
			//System.out.println(System.currentTimeMillis() - start + "ms en el fin del primer render");
			return rendered;
		} catch (IllegalStateException ex) {
			System.out.println("error al renderizar");
		}
		return false;
	}
	private Color getFPSTPSColor(int tps, int fps) {
		double correctFPS = getScreen().getRenderOption().equals(RenderOption.PER_FRAME) ? ((getFPS() != 0 ? getFPS() : 0.1) * 100) / getMaxFPS() : 0;
		double correctTPS = ((getTPS() != 0 ? getTPS() : 0.1) * 100) / getMaxTPS();
		int totalCorrect = (int) (((getScreen().getRenderOption().equals(RenderOption.PER_FRAME) ? correctFPS + correctTPS : correctTPS) * 100) / (getScreen().getRenderOption().equals(RenderOption.PER_FRAME) ? 200 : 100));
		int totalCorrectPorcent = ((255 * totalCorrect) / 100);
		if (totalCorrectPorcent < 0) { // el porcentage puede superar el 100% si los fps o tps son mayores que el maximo
			totalCorrectPorcent = 0;
		} else if (totalCorrectPorcent > 255) { // lo mismo que los fps pero con los tps xd
			totalCorrectPorcent = 255;
		}
		return new Color(255 - totalCorrectPorcent, totalCorrectPorcent, 0, 200);
	}
	public Screen getScreen() {
		return screenManager.getScreen(screen);
	}
	public void setScreen(String screen) {
		if (screenManager.getScreen(screen) != null) {
			if (getScreen() != null) {
				getScreen().onQuit();
				for (GameObject object : getScreen().getGameObjects()) {
					object.onQuitScreen();
				}
				this.removeMouseListener(getScreen().getMouseInput());
				this.removeMouseWheelListener(getScreen().getMouseInput());
				this.removeMouseMotionListener(getScreen().getMouseInput());
				this.removeKeyListener(getScreen().getKeyInput());
				if (getScreen().getMouseInput() != null) {
					getScreen().getMouseInput().restart();
				}
			}
			this.screen = screen;
			if (running) {
				getScreen().onOpen();
				for (GameObject object : getScreen().getGameObjects()) {
					object.onScreen();
				}
			}
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
		double nsFPS = 1000000000 / (maxFPS != 0 ? maxFPS : 1);
		double deltaFPS = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int tick = 0;
		while(running) {
			long now = System.nanoTime();
			deltaTicks += (now - lastTime) / nsTicks;
			deltaFPS += (now - lastTime) / nsFPS;
			lastTime = now;
			if(deltaTicks >= 1) {
				if (running) {
					tick++;
					tick();
					deltaTicks--;
				}
			}
			if (maxFPS != 0) {
				if(deltaFPS >= 1) {
					if (running) {
						if (getScreen() != null) {
							if (!informed) {
								System.out.println(System.currentTimeMillis() - getWindows().start + "ms en renderizar2");
								System.out.println(System.currentTimeMillis() - start + "ms en renderizar3");
								informed = true;
							}
							if (render()) {
								frames++;
							}
							deltaFPS--;
						} else {
							frames++;
							deltaFPS--;
						}
					}
				}
			} else {
				if (running) {
					if (getScreen() != null) {
						if (!informed) {
							System.out.println(System.currentTimeMillis() - getWindows().start + "ms en renderizar2");
							System.out.println(System.currentTimeMillis() - start + "ms en renderizar3");
							informed = true;
						}
						if (render()) {
							frames++;
						}
					} else {
						frames++;
					}
				}
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				//System.out.println("Ticks: " + tick);
				fps = frames;
				tps = tick;
				lastObjectRendered = objectRendered;
				objectRendered = 0;
				tick = 0;
				frames = 0;
				if (fps > maxFPS) {
					// cuando el sistema se laguea se reinician los FPS a recuperar una vez ya estén recuperados ;)
					deltaFPS = 0;
				}
			}
		}
		stop();
	}
	public boolean isShowInfo() {
		return showInfo;
	}
	public void setShowInfo(boolean showInfo) {
		this.showInfo = showInfo;
	}
	public int getNextObjectID() {
		return objectID++;
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
	public void setMaxFPS(int maxFPS) {
		this.maxFPS = maxFPS;
	}
	public int getMaxTPS() {
		return (int) maxTPS;
	}
	public void setMaxTPS(int maxTPS) {
		this.maxTPS = maxTPS;
	}
	public ScreenManager getScreenManager() {
		return screenManager;
	}
}

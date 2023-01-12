package me.pepe.GameAPI.Game.Objects;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Node;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.LoadingBar;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Menu;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.SelectBox;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.TextBox;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Button.TextButton;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Button.TextButtonStyles.TextButtonStyle1;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Button.TextButtonStyles.TextButtonStyle2;
import me.pepe.GameAPI.Screen.Screen;
import me.pepe.GameAPI.Utils.DOMUtils;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;
import me.pepe.GameAPI.Utils.InteligentDimensions.ExtendInteligentDimension;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;

public abstract class GameObject {
	private String id;
	private Game game;
	protected double x;
	protected double y;
	private double velX;
	private double velY;
	private boolean windowsPassable = true;
	private ObjectDimension dimension;
	private Rectangle hitBox;
	private boolean move = true;
	private Menu menu;
	private InteligentPosition intPos;
	private int intPostOffSetX = 0;
	private int intPostOffSetY = 0;	
	private InteligentDimension intDim;
	public GameObject(GameLocation gameLocation, Game game, ObjectDimension dimension) {
		this(null, gameLocation, game, dimension);
	}
	public GameObject(InteligentPosition intPos, Game game, InteligentDimension intDim) {
		this(null, intPos, game, intDim);
	}
	public GameObject(String id, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		this.game = game;
		this.x = gameLocation.getX();
		this.y = gameLocation.getY();
		if (id == null || id.isEmpty()) {
			this.id = this.getClass().getSuperclass().getSimpleName() + "-" + game.getNextObjectID();
		}
		this.dimension = dimension;
		hitBox = new Rectangle((int) x, (int) y, (int) dimension.getX(), (int) dimension.getY());
	}
	public GameObject(String id, InteligentPosition intPos, Game game, InteligentDimension intDim) {
		this.game = game;
		if (id == null || id.isEmpty()) {
			this.id = this.getClass().getSuperclass().getSimpleName() + "-" + game.getNextObjectID();
		} else {
			this.id = id;
		}
		this.intPos = intPos;
		this.intDim = intDim;
		calcInteligence();
	}
	protected Game getGame() {
		return game;
	}
	public String getID() {
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
	public double getVelX() {
		return velX;
	}
	public double getVelY() {
		return velY;
	}
	public InteligentPosition getInteligentPosition() {
		return intPos;
	}
	public void setInteligentPosition(InteligentPosition intPos) {
		this.intPos = intPos;
	}
	public InteligentDimension getInteligentDimension() {
		return intDim;
	}
	public void setInteligentDimension(InteligentDimension intDim) {
		if (intDim instanceof ExtendInteligentDimension) {
			((ExtendInteligentDimension) intDim).setGameObject(this);
			if (isOnMenu()) {
				((ExtendInteligentDimension) intDim).setMenu(menu);
			}
		}
		this.intDim = intDim;
	}
	public boolean hasInteligence() {
		return intPos != null && intDim != null;
	}
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public void setVelY(double velY) {
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
	public void setDimension(ObjectDimension dimension) {
		this.dimension = dimension;
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
	public boolean isCollision(Rectangle object) {
		return object.intersects(hitBox);
	}
	public void internalTick() {
		if (move) {
			if (canMoveTo(x+velX, y+velY)) {
				x += velX;
				y += velY;
				if (!windowsPassable) {
					x = clamp((int) x, 0, game.getWindows().getXToPaint() - (int) dimension.getX());
					y = clamp((int) y, 0, game.getWindows().getYToPaint() - (int) dimension.getY());
				}
				hitBox.setBounds((int) x, (int) y, (int) dimension.getX(), (int) dimension.getY());
			}
		}
		tick();
	}
	protected void setWindowsPassable(boolean windowsPassable) {
		this.windowsPassable = windowsPassable;
	}
	public abstract void tick();
	public void internalRender(Graphics g) {
		if (hasInteligence()) {
			calcInteligence();
		}
		render(g);
	}
	public abstract void render(Graphics g);
	public void onScreen() {}
	public void onQuitScreen() {}
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
	public boolean canMoveTo(double x, double y) {
		return canMoveTo(new GameLocation(x, y));
	}
	public boolean canMoveTo(GameLocation gameLocation) {
		return true;
	}
	public Rectangle simulateHitboxMove(double x, double y) {
		return new Rectangle((int) x, (int) y, (int) dimension.getX(), (int) dimension.getY());
	}
	public int getIntPostOffSetX() {
		return intPostOffSetX;
	}
	/*
	 * Para que el objeto está centrado habrá que ponerle -50% (porque se empieza a pintar desde el 0,0)
	 */
	public void setIntPostOffSetX(int intPostOffSetX) {
		this.intPostOffSetX = intPostOffSetX;
	}
	public int getIntPostOffSetY() {
		return intPostOffSetY;
	}
	public void setIntPostOffSetY(int intPostOffSetY) {
		this.intPostOffSetY = intPostOffSetY;
	}
	private void calcInteligence() {
		if (hasInteligence()) {
			x = intPos.calculateX(intDim, intPostOffSetX);
			y = intPos.calculateY(intDim, intPostOffSetY);
			dimension = new ObjectDimension(intDim.calcWeidht(), intDim.calcHeight());
			if (hitBox != null) {
				hitBox.setBounds((int) x, (int) y, (int) dimension.getX(), (int) dimension.getY());
			} else {
				hitBox = new Rectangle((int) x, (int) y, (int) dimension.getX(), (int) dimension.getY());
			}
		}
	}
	public static GameObject build(Object clase, Object menuOrScreen, Game game, Node node) {
		Menu menu = null;
		Screen screen = null;
		if (menuOrScreen instanceof Menu) {
			menu = (Menu) menuOrScreen;
		} else if (menuOrScreen instanceof Screen) {
			screen = (Screen) menuOrScreen;
		}
		if (menu != null || screen != null) {
			InteligentPosition position = InteligentPosition.build(menuOrScreen, DOMUtils.getChild(node, "position"));
			InteligentDimension dimension = InteligentDimension.build(DOMUtils.getChild(node, "dimension"));
			if (position != null && dimension != null) {
				try {
					Node idNode = DOMUtils.getChild(node, "id");
					String id = idNode != null ? idNode.getTextContent() : "";
					if (node.getNodeName().equals("TextBox")) {
						boolean ocult = Boolean.valueOf(DOMUtils.getChild(node, "ocult").getTextContent());
						String initialText = DOMUtils.getChild(node, "initialText").getTextContent();
						String placeholder = DOMUtils.getChild(node, "placeholder").getTextContent();
						Node methodsNode = DOMUtils.getChild(node, "methods");
						String onFocusMethod = DOMUtils.getChild(methodsNode, "onFocus").getTextContent();
						TextBox textBox = new TextBox(id, initialText, position, game, dimension, ocult) {
							@Override
							public void onFocus() {
								if (!onFocusMethod.isEmpty()) {
									//execMethod(menuOrScreen, onFocusMethod, new Class<?>[] {int.class}, new Object[] {1});
									execMethod(clase, onFocusMethod);
								}
							}
						};
						textBox.setPlaceholder(placeholder);
						System.out.println(textBox.getID());
						if (menu != null) {
							menu.addGameObject(textBox);
						} else if (screen != null) {
							screen.addGameObject(textBox);
						}
					} else if (node.getNodeName().equals("SelectBox")) {
						boolean selected = Boolean.valueOf(DOMUtils.getChild(node, "selected").getTextContent());
						Node methodsNode = DOMUtils.getChild(node, "methods");
						String onOverMethod = DOMUtils.getChild(methodsNode, "onOver").getTextContent();
						String onSelectMethod = DOMUtils.getChild(methodsNode, "onSelect").getTextContent();
						SelectBox selectBox = new SelectBox(id, position, game, dimension) {
							@Override
							public void onSelect(boolean selected) {
								execMethod(clase, onSelectMethod, new Class<?>[] {boolean.class}, new Object[] {selected});
							}
							@Override
							public void onOver() {
								execMethod(clase, onOverMethod);
							}							
						};
						selectBox.setSelected(selected);
						if (menu != null) {
							menu.addGameObject(selectBox);
						} else if (screen != null) {
							screen.addGameObject(selectBox);
						}
					} else if (node.getNodeName().equals("LoadingBar")) {
						boolean showPorcent = Boolean.valueOf(DOMUtils.getChild(node, "showPorcent").getTextContent());
						String textExtra = DOMUtils.getChild(node, "extraText").getTextContent();
						int porcent = Integer.valueOf(DOMUtils.getChild(node, "porcent").getTextContent());
						Color porcentColor = DOMUtils.getColor(DOMUtils.getChild(node, "porcentColor"));
						Color backgroundColor = DOMUtils.getColor(DOMUtils.getChild(node, "backgroundColor"));
						Color completeColor = DOMUtils.getColor(DOMUtils.getChild(node, "completeColor"));
						Node methodsNode = DOMUtils.getChild(node, "methods");
						String onOverMethod = DOMUtils.getChild(methodsNode, "onOver").getTextContent();
						LoadingBar lb = new LoadingBar(id, porcent, showPorcent, position, game, dimension) {
							@Override
							public void onOver() {
								execMethod(clase, onOverMethod);
							}
						};
						lb.setTextExtra(textExtra);
						lb.setPorcentColor(porcentColor);
						lb.setBackgroundColor(backgroundColor);
						lb.setCompleteColor(completeColor);
						if (menu != null) {
							menu.addGameObject(lb);
						} else if (screen != null) {
							screen.addGameObject(lb);
						}
					} else if (node.getNodeName().equals("TextButton")) {
						String text = DOMUtils.getChild(node, "text").getTextContent();
						Color letterColor = DOMUtils.getColor(DOMUtils.getChild(node, "letterColor"));
						Color boxColor = DOMUtils.getColor(DOMUtils.getChild(node, "boxColor"));
						Node methodsNode = DOMUtils.getChild(node, "methods");
						String onOverMethod = DOMUtils.getChild(methodsNode, "onOver").getTextContent();
						String onClickMethod = DOMUtils.getChild(methodsNode, "onClick").getTextContent();
						TextButton tb = new TextButton(id, text, position, game, dimension) {
							@Override
							public void onClick() {
								execMethod(clase, onClickMethod);
							}
							@Override
							public void onOver() {
								execMethod(clase, onOverMethod);
							}							
						};
						tb.setLetterColor(letterColor);
						tb.setBoxColor(boxColor);
						if (menu != null) {
							menu.addGameObject(tb);
						} else if (screen != null) {
							screen.addGameObject(tb);
						}
					} else if (node.getNodeName().equals("TextButtonStyle1")) {
						String text = DOMUtils.getChild(node, "text").getTextContent();
						Color letterColor = DOMUtils.getColor(DOMUtils.getChild(node, "letterColor"));
						Color boxColor = DOMUtils.getColor(DOMUtils.getChild(node, "boxColor"));
						Color toBoxColor = DOMUtils.getColor(DOMUtils.getChild(node, "toBoxColor"));
						Color toLetterColor = DOMUtils.getColor(DOMUtils.getChild(node, "toLetterColor"));
						Node methodsNode = DOMUtils.getChild(node, "methods");
						String onOverMethod = DOMUtils.getChild(methodsNode, "onOver").getTextContent();
						String onClickMethod = DOMUtils.getChild(methodsNode, "onClick").getTextContent();
						TextButtonStyle1 tb = new TextButtonStyle1(id, text, position, game, dimension) {
							@Override
							public void onClick() {
								execMethod(clase, onClickMethod);
							}
							@Override
							public void onOver() {
								execMethod(clase, onOverMethod);
							}							
						};
						tb.setLetterColor(letterColor);
						tb.setBoxColor(boxColor);
						tb.setToBoxColor(toBoxColor);
						tb.setToLetterColor(toLetterColor);
						if (menu != null) {
							menu.addGameObject(tb);
						} else if (screen != null) {
							screen.addGameObject(tb);
						}
					} else if (node.getNodeName().equals("TextButtonStyle2")) {
						String text = DOMUtils.getChild(node, "text").getTextContent();
						Color letterColor = DOMUtils.getColor(DOMUtils.getChild(node, "letterColor"));
						Color boxColor = DOMUtils.getColor(DOMUtils.getChild(node, "boxColor"));
						Node methodsNode = DOMUtils.getChild(node, "methods");
						String onOverMethod = DOMUtils.getChild(methodsNode, "onOver").getTextContent();
						String onClickMethod = DOMUtils.getChild(methodsNode, "onClick").getTextContent();
						TextButtonStyle2 tb = new TextButtonStyle2(id, text, position, game, dimension) {
							@Override
							public void onClick() {
								execMethod(clase, onClickMethod);
							}
							@Override
							public void onOver() {
								execMethod(clase, onOverMethod);
							}							
						};
						tb.setLetterColor(letterColor);
						tb.setBoxColor(boxColor);
						if (menu != null) {
							menu.addGameObject(tb);
						} else if (screen != null) {
							screen.addGameObject(tb);
						}
					} else if (node.getNodeName().equals("Menu")) {
						Node methodsNode = DOMUtils.getChild(node, "methods");
						String buildMethod = DOMUtils.getChild(methodsNode, "build").getTextContent();
						String onClickMethod = DOMUtils.getChild(methodsNode, "onClick").getTextContent();
						Node menuObjectsNode = DOMUtils.getChild(node, "objects");
						Menu buildMenu = new Menu(id, position, game, dimension) {
							@Override
							public void build(Graphics g) {
								execMethod(clase, buildMethod, new Class<?>[] {Graphics.class}, new Object[] {g});
							}
							@Override
							public void onClick(int x, int y) {
								execMethod(clase, onClickMethod, new Class<?>[] {int.class, int.class}, new Object[] {x, y});
							}
						};
						System.out.println(menuObjectsNode == null);
						System.out.println("Cargando " + DOMUtils.getChildrens(menuObjectsNode).size() + " objetos dentro del menu...");
						for (Node menuObject : DOMUtils.getChildrens(menuObjectsNode)) {
							build(clase, buildMenu, game, menuObject);
						}
						if (menu != null) {
							menu.addGameObject(buildMenu);
						} else if (screen != null) {
							screen.addGameObject(buildMenu);
						}
					} else {
						System.err.println("Error al coger el tipo de elemento...");
						System.err.println(DOMUtils.getXML(node));
					}
				} catch (NumberFormatException ex) {
					System.err.println("Error al montar el objeto...");
					System.err.println(DOMUtils.getXML(node));
				}
			} else {
				System.err.println("Error al coger la posición o dimensión del objeto...");
				System.err.println(DOMUtils.getXML(node));
			}
		} else {
			throw new IllegalArgumentException("El objeto pasado no es ni un menu ni un screen...");
		}
		return null;
	}
	private static void execMethod(Object clase, String methodName) {
		execMethod(clase, methodName, new Class<?>[0], new Object[0]);
	}
	private static void execMethod(Object clase, String methodName, Class<?>[] vars, Object[] args) {
		try {
			try {
				clase.getClass().getDeclaredMethod(methodName, vars).invoke(clase, args);
			} catch (IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			System.err.println("No se encontró el método " + methodName + " en la clase " + clase.getClass().getSimpleName());
			System.err.print("Compruebe que el siguiente codigo: 'public void " + methodName + "(");
			for (int i = 0; i < vars.length; i++) {
				System.err.print((i != 0 ? ", " : "") + vars[i].getSimpleName() + " var" + i);
			}
			System.err.println(") {/*...*/}'");
			e.printStackTrace();
		} catch (SecurityException | IllegalAccessException e) {
			System.err.println("El método " + methodName + " en la clase " + clase.getClass().getSimpleName() + " probablemente sea privado, coloquelo como público.");
			e.printStackTrace();
		}
	}
}
package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;
import me.pepe.GameAPI.Utils.RenderLimits;

public abstract class TextBox extends GameObject {
	private JTextField text;
	private boolean over = false;
	private RenderLimits limits;
	public TextBox(String test, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		this(test, gameLocation, game, dimension, null, false);
	}
	public TextBox(String test, GameLocation gameLocation, Game game, ObjectDimension dimension, RenderLimits limits) {
		this(test, gameLocation, game, dimension, limits, false);
	}
	public TextBox(String test, GameLocation gameLocation, Game game, ObjectDimension dimension, RenderLimits limits, boolean ocult) {
		super(gameLocation, game, dimension);
		this.limits = limits;
		if (ocult) {
			text = new JPasswordField(test);
			text.setBounds((int) getX(), (int) getY(), (int) getActualDimensionX(), (int) getActualDimensionY());
		} else {
			text = new JTextField(test);
			text.setBounds((int) getX(), (int) getY(), (int) getActualDimensionX(), (int) getActualDimensionY());
		}
		text.setFocusTraversalKeysEnabled(false);
	}
	@Override
	public void tick() {
		text.setBounds((int) getActualX(), (int) getActualY(), (int) getActualDimensionX(), (int) getActualDimensionY());
		boolean newover = false;
		if (isOnMenu()) {
			if (hasInteligence()) {
				newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(getMenu()), (int) (getX() + getMenu().getStartRender().getX()), (int) (getY() + getMenu().getStartRender().getY()), (int) getDimension().getX(), (int) getDimension().getY());
			} else {
				newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(getMenu()), (int) (getActualX() + getMenu().getStartRender().getX()), (int) (getActualY() + getMenu().getStartRender().getY()), getActualDimensionX(), getActualDimensionY());
			}
		} else {
			if (hasInteligence()) {
				newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(), (int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY());
			} else {
				newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(), getActualX(), getActualY(), getActualDimensionX(), getActualDimensionY());
			}
		}
		over = newover;
		text.setFocusable(text.hasFocus());
	}
	@Override
	public int getCursor() {
		if (over) {
			return Cursor.TEXT_CURSOR;
		}
		return Cursor.DEFAULT_CURSOR;
	}
	@Override
	public void render(Graphics g) {
		if (text.getSize().getWidth() == getActualDimensionX() && text.getSize().getHeight() == getActualDimensionY()) {
			g.drawImage(createImage(getActualDimensionX(), getActualDimensionY()), getActualX(), getActualY(), getActualDimensionX(), getActualDimensionY(), null);
		}
	}
	@Override
	public void onScreen() {
		getGame().getWindows().getFrame().add(text);
	}
	@Override
	public void onQuitScreen() {
		getGame().getWindows().getFrame().remove(text);
	}
	public boolean isOver() {
		return over;
	}
	public void requestFocus() {
		text.setFocusable(true);
		text.requestFocus();
		GameLocation mosLoc = getGame().getScreen().getMouseLocation();
		int x = 0;
		if (hasInteligence()) {
			x = (int) (getX());
		} else {
			x = (int) (getActualX());
		}
		if (isOnMenu()) {
			mosLoc = getGame().getScreen().getMouseLocation(getMenu());
			x += getMenu().getStartRender().getX();
		}
		int diffX = (int) (mosLoc.getX() - x);
		FontMetrics metrics = text.getFontMetrics(text.getFont());
		int textLenght = text.getText().length();
		for (int i = 1; i <= textLenght; i++) {
			int width = metrics.stringWidth(text.getText().substring(0, i));
			if (width >= diffX || width + 2 >= diffX || width - 2 >= diffX) {
				text.setCaretPosition(i);
				break;
			} else if (i == textLenght) {
				text.setCaretPosition(textLenght);
			}
		}
		onFocus();
	}
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
		return (int) (getDimension().getY() * (actualY > actualX ? actualX : actualY) / 100);
	}
	private BufferedImage createImage (int width, int height) {
		BufferedImage image = new BufferedImage(width <= 0 ? 1 : width, height <= 0 ? 1 : height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		text.paint(g);
		g.dispose();
		return image;
	}
	public String getText() {
		return text.getText();
	}
	public void setText(String text) {
		this.text.setText(text);
	}
	public abstract void onFocus();
	public void setKeyListener(KeyListener keyListener) {
		text.addKeyListener(keyListener);
	}
}
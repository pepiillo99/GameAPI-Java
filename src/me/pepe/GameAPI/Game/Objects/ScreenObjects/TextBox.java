package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;
import me.pepe.GameAPI.Utils.RenderLimits;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;
import me.pepe.GameAPI.Utils.InteligentResize.InteligentResize;

public abstract class TextBox extends GameObject {
	private String text = "";
	private String placeholder = "placeholder";
	private boolean ocult = false;
	private Font font = new Font("Arial", Font.PLAIN, 20);
	private int caret = 0;
	private boolean focused = false;
	private boolean needFocus = false;
	private int focusDiffX = 0;
	private boolean over = false;
	private boolean drawLine = false;
	private long timeToDrawLine = 0;
	private RenderLimits limits;
	public TextBox(String text, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		this(text, gameLocation, game, dimension, null, false);
	}
	public TextBox(String text, GameLocation gameLocation, Game game, ObjectDimension dimension, RenderLimits limits) {
		this(text, gameLocation, game, dimension, limits, false);
	}
	public TextBox(String text, GameLocation gameLocation, Game game, ObjectDimension dimension, RenderLimits limits, boolean ocult) {
		super(gameLocation, game, dimension);
		this.limits = limits;
		this.text = text;
		this.ocult = ocult;
	}
	public TextBox(String text, InteligentPosition intPos, Game game, InteligentResize intRes) {
		this(text, intPos, game, intRes, false);
	}
	public TextBox(String text, InteligentPosition intPos, Game game, InteligentResize intRes, boolean ocult) {
		super(intPos, game, intRes);
		if (intPos.hasRenderLimits()) {
			this.limits = intPos.getRenderLimits();
		}
		this.text = text;
		this.ocult = ocult;
	}
	@Override
	public void tick() {
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
		if (hasInteligence()) {
			g.drawImage(createImage((int) getDimension().getX(), (int) getDimension().getY()), (int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY(), null);
		} else {
			g.drawImage(createImage(getActualDimensionX(), getActualDimensionY()), getActualX(), getActualY(), getActualDimensionX(), getActualDimensionY(), null);
		}	
	}
	public boolean isFocused() {
		return focused;
	}
	public void write(char c) {
		this.text += c;
		caret++;
	}
	public int getCaretPosition() {
		return caret;
	}
	public void setCaretPosition(int pos) {
		if (pos < 0) {
			pos = 0;
		}
		if (pos > text.length()) {
			pos = text.length();
		}
		caret = pos;
	}
	public boolean isOver() {
		return over;
	}
	public void onPress(int key) {
		if (key == 37) {
			setCaretPosition(getCaretPosition() - 1);
		} else if (key == 39) {
			setCaretPosition(getCaretPosition() + 1);
		} else if (key == 8) {
			if (!text.isEmpty()) {
				text = text.substring(0, caret-1) + text.substring(caret, text.length());
				caret--;	
			}
		} else if (key == 127) {
			if (caret != text.length()) {
				text = text.substring(0, caret) + text.substring(caret+1, text.length());
			}		
		}
	}
	public void requestFocus() {
		needFocus = true;
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
		focusDiffX = (int) (mosLoc.getX() - x);
	}
	public void unFocus() {
		focused = false;
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
		FontMetrics fontMetrics = g.getFontMetrics(font);
		if (needFocus) {
			int textLenght = getText().length();
			for (int i = 1; i <= textLenght; i++) {
				int widthh = fontMetrics.stringWidth(getText().substring(0, i));
				if (widthh >= focusDiffX || widthh + 2 >= focusDiffX || widthh - 2 >= focusDiffX) {
					caret = i;
					break;
				} else if (i == textLenght) {
					caret = textLenght;
				}
			}
			focused = true;
			needFocus = false;
			onFocus();
		}
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		int form = fontMetrics.getHeight() - 6;
		//System.out.println(fontMetrics.getHeight() + " - " + height + " - " + (form));
		String text = getText();
		if (ocult) {
			text = "";
			for (int i = 0; i < getText().length(); i++) {
				text += "*";
			}
		}
		g.setFont(font);
		if (!text.isEmpty()) {
			g.setColor(Color.BLACK);
			g.drawString(text, 2, form);
		} else {
			g.setColor(Color.LIGHT_GRAY);
			g.drawString(placeholder, 2, form);
		}
		if (focused) {
			if (drawLine) {
				int posLine = fontMetrics.stringWidth(text.substring(0, caret));
				g.setColor(Color.BLACK);
				g.drawLine(posLine+1, 0, posLine+1, height);
			}
			if (timeToDrawLine - System.currentTimeMillis() <= 0) {
				drawLine = !drawLine;
				timeToDrawLine = System.currentTimeMillis() + 500;
			}
		}
		g.dispose();
		return image;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public abstract void onFocus();
}
package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;

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
	private int textLimit = -1;
	private boolean onlyNumbers = false;
	private TextBox nextTextBox;
	public TextBox(String text, InteligentPosition intPos, Game game, InteligentDimension intDim) {
		this(null, text, intPos, game, intDim, false);
	}
	public TextBox(String text, InteligentPosition intPos, Game game, InteligentDimension intDim, boolean ocult) {
		this(null, text, intPos, game, intDim, ocult);
	}
	public TextBox(String id, String text, InteligentPosition intPos, Game game, InteligentDimension intDim, boolean ocult) {
		super(id, intPos, game, intDim);
		this.text = text;
		this.ocult = ocult;
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
		}	
	}
	public boolean isFocused() {
		return focused;
	}
	public void write(char c) {
		if (onlyNumbers ) {
			try {
				Integer.valueOf(text + c);
			} catch (NumberFormatException ex) {
				return;
			}
		}
		if (textLimit != -1) {
			if (textLimit > text.length()) {
				if (caret == text.length()) {
					this.text += c;
				} else {
					this.text = getPartText(0, caret) + c + getPartText(caret, text.length());
				}
				caret++;
			}
		} else {
			if (caret == text.length()) {
				this.text += c;
			} else {
				this.text = getPartText(0, caret) + c + getPartText(caret, text.length());
			}
			caret++;
		}
	}
	private String getPartText(int init, int fin) {
		String sol = "";
		for (int i = init; i < fin; i++) {
			sol = sol + text.charAt(i);
		}
		return sol;
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
		if (key == KeyEvent.VK_LEFT) {
			setCaretPosition(getCaretPosition() - 1);
		} else if (key == KeyEvent.VK_RIGHT) {
			setCaretPosition(getCaretPosition() + 1);
		} else if (key == 8) { // boton de borrar
			if (!text.isEmpty() && caret != 0) {
				text = text.substring(0, caret-1) + text.substring(caret, text.length());
				caret--;	
			}
		} else if (key == 127) { // suprimir
			if (caret != text.length()) {
				text = text.substring(0, caret) + text.substring(caret+1, text.length());
			}		
		} else if (key == KeyEvent.VK_TAB) {
			unFocus();
			if (nextTextBox != null) {
				nextTextBox.requestFocus();
			}
		}
	}
	public void requestFocus() {
		needFocus = true;
		GameLocation mosLoc = getGame().getScreen().getMouseLocation();
		int x = 0;
		if (hasInteligence()) {
			x = (int) (getX());
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
	private BufferedImage createImage(int width, int height) {
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
		int form = fontMetrics.getHeight() - 8;
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
				g.drawLine(posLine+2, 0, posLine+2, height);
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
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public TextBox getNextTextBox() {
		return nextTextBox;
	}
	public void setNextTextBox(TextBox nextTextBox) {
		this.nextTextBox = nextTextBox;
	}
	public boolean isOnlyNumbers() {
		return onlyNumbers;
	}
	public void setOnlyNumbers(boolean on) {
		this.onlyNumbers = on;
	}
	public void setTextLimit(int textLimit) {
		this.textLimit = textLimit;
	}
	public int getTextLimit() {
		return textLimit;
	}
	public abstract void onFocus();
}
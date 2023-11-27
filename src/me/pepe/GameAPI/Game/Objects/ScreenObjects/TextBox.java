package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Screen.Screen;
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
	private int accent = 0; // 0 sin accento, 1 ú, 2 ù, 3 ü, 4 û
	public TextBox(String text, InteligentPosition intPos, Screen Screen, InteligentDimension intDim) {
		this(null, text, intPos, Screen, intDim, false);
	}
	public TextBox(String text, InteligentPosition intPos, Screen Screen, InteligentDimension intDim, boolean ocult) {
		this(null, text, intPos, Screen, intDim, ocult);
	}
	public TextBox(String id, String text, InteligentPosition intPos, Screen Screen, InteligentDimension intDim, boolean ocult) {
		super(id, intPos, Screen, intDim);
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
		System.out.println(Character.isLowerCase(c) + " " + c);
		if (accent != 0 && Character.toLowerCase(c) != addAccent(Character.toLowerCase(c), accent)) {
			c = Character.isUpperCase(c) || Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK) ? Character.toUpperCase(addAccent(Character.toLowerCase(c), accent)) : addAccent(Character.toLowerCase(c), accent);
			accent = 0;
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
    private static char addAccent(char c, int accent) {
    	// āăąēîïĩíĝġńñšŝśûůŷ 
        switch (c) {
            case 'a':
                return accent == 1 ? 'á' : accent == 2 ? 'à' : accent == 3 ? 'ä' : accent == 4 ? 'â' : c;
            case 'e':
                return accent == 1 ? 'é' : accent == 2 ? 'è' : accent == 3 ? 'ë' : accent == 4 ? 'ê' : c;
            case 'i':
                return accent == 1 ? 'í' : accent == 2 ? 'ì' : accent == 3 ? 'ï' : accent == 4 ? 'î' : c;
            case 'o':
                return accent == 1 ? 'ó' : accent == 2 ? 'ò' : accent == 3 ? 'ö' : accent == 4 ? 'ô' : c;
            case 'u':
                return accent == 1 ? 'ú' : accent == 2 ? 'ù' : accent == 3 ? 'ü' : accent == 4 ? 'û' : c;
            default:
                return c;
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
		} else if (key == 128 || key == 129) {
			if (accent != 0) {
				if (accent == 1) {
					write('´');
				} else if (accent == 2) {
					write('`');
				} else if (accent == 3) {
					write('¨');
				} else if (accent == 4) {
					write('^');
				}
				if (key == 129) {
					if (getScreen().getKeyInput().checkIsPressed(KeyEvent.VK_SHIFT)) {
						write('¨');
					} else {
						write('´');
					}
				} else if (key == 128) {
					if (getScreen().getKeyInput().checkIsPressed(KeyEvent.VK_SHIFT)) {
						write('^');
					} else {
						write('`');
					}
				}
				accent = 0;
			} else {
				if (key == 129) {
					if (getScreen().getKeyInput().checkIsPressed(KeyEvent.VK_SHIFT)) {
						accent = 3;
					} else {
						accent = 1;
					}
				} else if (key == 128) {
					if (getScreen().getKeyInput().checkIsPressed(KeyEvent.VK_SHIFT)) {
						accent = 4;
					} else {
						accent = 2;
					}
				}
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
		int form = fontMetrics.getHeight() - 4;
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
	public int getAccent() {
		return accent;
	}
	public void setAccent(int accent) {
		this.accent= accent;
	}
}
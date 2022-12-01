package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.RenderUtils;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;
import me.pepe.GameAPI.Utils.InteligentPositions.PixelInteligentPosition;
import me.pepe.GameAPI.Utils.InteligentResize.InteligentResize;
import me.pepe.GameAPI.Utils.InteligentResize.PixelInteligentResize;

public class LoadingBar extends GameObject {
	private int porcent = 0;
	private boolean over = false;
	private boolean showPorcent = true;
	private Color backgroundColor = Color.BLACK;
	private Color porcentColor = Color.WHITE;
	private Color completeColor = Color.GREEN;
	private String textExtra = "";
	public LoadingBar(int porcent, InteligentPosition intPos, Game game, InteligentResize intRes) {
		this(porcent, true, intPos, game, intRes);
	}
	public LoadingBar(int porcent, boolean showPorcent, InteligentPosition intPos, Game game, InteligentResize intRes) {
		super(intPos, game, intRes);
		this.porcent = porcent;
		this.showPorcent = showPorcent;
	}
	@Override
	public void tick() {
		boolean newover = false;
		if (isOnMenu()) {
			if (hasInteligence()) {
				newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(getMenu()), (int) (getX() + getMenu().getStartRender().getX()), (int) (getY() + getMenu().getStartRender().getY()), (int) getDimension().getX(), (int) getDimension().getY());
			} else {
				newover = false;
			}
		} else {
			if (hasInteligence()) {
				newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(), (int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY());
			} else {
				newover = false;
			}
		}
		if (!over && newover) {
			onOver();
		}
		over = newover;
	}

	@Override
	public void render(Graphics g) {
		if (hasInteligence()) {
			g.setColor(backgroundColor);
			g.fillRect((int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY());
			g.drawRect((int) getX()-3, (int) getY() -3, (int) getDimension().getX() + 5, (int) getDimension().getY() + 5);
			RenderUtils.fillRect(g, porcent != 100 ? porcentColor : completeColor, new PixelInteligentPosition((int) getX(), (int) getY()), new PixelInteligentResize((int) getDimension().getY(), (int) (getDimension().getX() * (porcent)) / 100));
			if (showPorcent) {
				g.setColor(porcent != 100 ? porcentColor : completeColor);
				g.setFont(new Font("Aria", Font.BOLD, 11));
				if (textExtra.isEmpty()) {
					g.drawString(porcent + "%", (int) getX(), (int) getY() - 4);
				} else {
					g.drawString(porcent + "% - " + textExtra, (int) getX(), (int) getY() - 4);
				}
			}
		}
	}
	public void onOver() {}
	public boolean isOver() {
		return over;
	}
	public int getPorcent() {
		return porcent;
	}
	public void setPorcent(int porcent) {
		if (porcent > 100) {
			porcent = 100;
		} else if (porcent < 0) {
			porcent = 0;
		}
		this.porcent = porcent;
	}
	public boolean isShowPorcent() {
		return showPorcent;
	}
	public void setShowPorcent(boolean showPorcent) {
		this.showPorcent = showPorcent;
	}
	public Color getPorcentColor() {
		return porcentColor;
	}
	public void setPorcentColor(Color porcentColor) {
		this.porcentColor = porcentColor;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public Color getCompleteColor() {
		return completeColor;
	}
	public void setCompleteColor(Color completeColor) {
		this.completeColor = completeColor;
	}
	public String getTextExtra() {
		return textExtra;
	}
	public void setTextExtra(String textExtra) {
		this.textExtra = textExtra;
	}
	public int calcPorcent(int x, int maxX) {
		return (100 * x) / maxX;
	}
}
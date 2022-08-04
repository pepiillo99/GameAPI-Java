package me.pepe.GameAPI.Game.Objects.ScreenObjects.Button.TextButtonStyles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Button.TextButton;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;
import me.pepe.GameAPI.Utils.RenderLimits;
import me.pepe.GameAPI.Utils.RenderUtils;
import me.pepe.GameAPI.Utils.Utils;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;
import me.pepe.GameAPI.Utils.InteligentPositions.PixelInteligentPosition;
import me.pepe.GameAPI.Utils.InteligentResize.InteligentResize;
import me.pepe.GameAPI.Utils.InteligentResize.PixelInteligentResize;

public abstract class TextButtonStyle2 extends TextButton {
	private long transDuration = 250;
	private long transTime = 0;
	private long lastRender = 0;
	public TextButtonStyle2(String text, String name, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		this(text, name, gameLocation, game, dimension, null);
	}
	public TextButtonStyle2(String text, String name, GameLocation gameLocation, Game game, ObjectDimension dimension, RenderLimits limits) {
		super(text, name, gameLocation, game, dimension, limits);
		setBoxColor(Color.CYAN);
		setLetterColor(Color.WHITE);
	}
	public TextButtonStyle2(String text, String name, InteligentPosition intPos, Game game, InteligentResize intRes) {
		super(text, name, intPos, game, intRes);
		setBoxColor(Color.CYAN);
		setLetterColor(Color.WHITE);
	}
	@Override
	public void render(Graphics g) {
		if (isShow()) {
			if (hasInteligence()) {
				g.setColor(getBoxColor());
				g.drawRect((int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY());
				if (transDuration != transTime) {
					int transPorcent = (int) Math.abs(((transTime * 100) / transDuration) - 100);
					RenderUtils.fillRect(g, getBoxColor(), new PixelInteligentPosition((int) (getX() + (getDimension().getX() / 2)), (int) getY()), new PixelInteligentResize((int) getDimension().getY(), (int) (getDimension().getX() * (transPorcent)) / 100), -50, 0);
				}
				g.setColor(getLetterColor());
				Utils.drawCenteredString(g, getText(), new Rectangle((int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY()), /*Dymos.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, isOver() ? 50 : 45)*/new Font("Aria", Font.BOLD, (int)(getDimension().getY()/1.35)));
			}
			if (isOver()) {
				if (transTime > 0) {
					transTime -= System.currentTimeMillis() - lastRender;
					if (transTime < 0) {
						transTime = 0;
					}
				}
			} else {
				if (transTime != transDuration) {
					transTime += System.currentTimeMillis() - lastRender;
					if (transTime > transDuration) {
						transTime = transDuration;
					}
				}
			}
			lastRender = System.currentTimeMillis();
		}
	}
}

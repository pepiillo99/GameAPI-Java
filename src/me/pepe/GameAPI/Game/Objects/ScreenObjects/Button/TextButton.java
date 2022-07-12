package me.pepe.GameAPI.Game.Objects.ScreenObjects.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;
import me.pepe.GameAPI.Utils.RenderLimits;
import me.pepe.GameAPI.Utils.Utils;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;
import me.pepe.GameAPI.Utils.InteligentResize.InteligentResize;

public abstract class TextButton extends Button {
	private String text = "";
	private int[] letterColor = new int[] {255, 0, 0};
	private int[] boxColor = new int[] {125, 125, 125};
	public TextButton(String text, String name, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		this(text, name, gameLocation, game, dimension, null);
	}
	public TextButton(String text, String name, GameLocation gameLocation, Game game, ObjectDimension dimension, RenderLimits limits) {
		super(name, gameLocation, game, dimension, limits);
		this.text = text;
	}
	public TextButton(String text, String name, InteligentPosition intPos, Game game, InteligentResize intRes) {
		super(name, intPos, game, intRes);
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public void render(Graphics g) {
		if (isShow()) {
			if (hasInteligence()) {
				g.setColor(new Color(boxColor[0], boxColor[1], boxColor[2], isOver() ? 255 : 150));
				g.fillRect((int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY());
				g.setColor(new Color(letterColor[0], letterColor[1], letterColor[2], isOver() ? 255 : 150));
				Utils.drawCenteredString(g, text, new Rectangle((int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY()), /*Dymos.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, isOver() ? 50 : 45)*/new Font("Aria", Font.BOLD, (int)(getDimension().getY()/1.35)));
			} else {
				g.setColor(new Color(boxColor[0], boxColor[1], boxColor[2], isOver() ? 255 : 150));
				g.fillRect(getActualX(), getActualY(), getActualDimensionX(), getActualDimensionY());
				g.setColor(new Color(letterColor[0], letterColor[1], letterColor[2], isOver() ? 255 : 150));
				Utils.drawCenteredString(g, text, new Rectangle(getActualX(), getActualY(), getActualDimensionX(), getActualDimensionY()), /*Dymos.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, isOver() ? 50 : 45)*/new Font("Aria", Font.BOLD, (int)(getActualDimensionY()/1.35)));
			}
		}
	}
	public void setLetterColor(Color color) {
		letterColor = new int[] {color.getRed(), color.getGreen(), color.getBlue()};
	}
	public void setBoxColor(Color color) {
		boxColor = new int[] {color.getRed(), color.getGreen(), color.getBlue()};
	}
	public Color getBoxColor() {
		return new Color(boxColor[0], boxColor[1], boxColor[2]);
	}
	public Color getLetterColor() {
		return new Color(letterColor[0], letterColor[1], letterColor[2]);
	}
}
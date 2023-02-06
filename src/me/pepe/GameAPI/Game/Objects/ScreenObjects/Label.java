package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.LabelAligment;
import me.pepe.GameAPI.Utils.LabelPart;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;

public class Label extends GameObject {
	private String text;
	private Color color = Color.BLACK;
	private Font font;
	private LabelAligment aligment = LabelAligment.LEFT;
	private List<LabelPart> parts = new ArrayList<LabelPart>();
	private Color background;
	private boolean underlined = false;
	private Color underlineColor = Color.BLACK;
	public Label(String text, InteligentPosition intPos, Game game, InteligentDimension intDim) {
		this(text, Color.BLACK, new Font("Aria", Font.BOLD, 10), null, intPos, game, intDim);
	}
	public Label(String text, Color color, Font font, InteligentPosition intPos, Game game, InteligentDimension intDim) {
		this(text, color, font, null, intPos, game, intDim);
	}
	public Label(String text, Color color, Font font, String id, InteligentPosition intPos, Game game, InteligentDimension intDim) {
		super(id, intPos, game, intDim);
		this.text = text;
		this.color = color;
		this.font = font;
	}
	@Override
	public void tick() {
	}
	@Override
	public void render(Graphics g) {
		if (hasInteligence()) {
			int calcY = (int) (getY() + g.getFontMetrics().getHeight());
			//int calcY = (int) (getY() + getDimension().getY()); // down
			//int calcY = (int) (getY() + ((getDimension().getY()/2) + (g.getFontMetrics().getHeight()/2))); // center
			if (parts.isEmpty()) {
				g.setColor(color);
				g.setFont(font);
 				if (aligment.equals(LabelAligment.LEFT)) {
					g.drawString(text, (int) getX(), (int) getY());
					if (underlined) {
						g.setColor(underlineColor);
						g.drawLine((int) getX(), (int) getY(), (int) getX() + g.getFontMetrics().stringWidth(text), calcY);
					}
				} else if (aligment.equals(LabelAligment.RIGHT)) {
					int calcX = (int) (getX() + (getDimension().getX() - g.getFontMetrics().stringWidth(text)));
					g.drawString(text, calcX, calcY);
					if (underlined) {
						g.setColor(underlineColor);
						g.drawLine(calcX, calcY, calcX + g.getFontMetrics().stringWidth(text), calcY);
					}
				} else if (aligment.equals(LabelAligment.CENTER)) {
					int calcX = (int) (getX() + ((getDimension().getX()/2) - (g.getFontMetrics().stringWidth(text) / 2)));
					g.drawString(text, calcX, calcY);
					if (underlined) {
						g.setColor(underlineColor);
						g.drawLine(calcX, calcY, calcX + g.getFontMetrics().stringWidth(text), calcY);
					}
				}
			} else {
				int xPos = 0;
				if (aligment.equals(LabelAligment.RIGHT)) {
					xPos = (int) getDimension().getX();
					for (int i = parts.size()-1; i >= 0; i--) {
						LabelPart lp = parts.get(i);
						g.setColor(lp.getColor());
						g.setFont(lp.getFont());
						xPos -= g.getFontMetrics().stringWidth(lp.getText());
						g.drawString(lp.getText(), (int) getX() + xPos, calcY);
					}
				} else if (aligment.equals(LabelAligment.LEFT)) {
					for (LabelPart lp : parts) {
						g.setColor(lp.getColor());
						g.setFont(lp.getFont());
						g.drawString(lp.getText(), (int) getX() + xPos, calcY);
						xPos += g.getFontMetrics().stringWidth(lp.getText());
					}
				} else if (aligment.equals(LabelAligment.CENTER)) {
					int xTotal = 0;
					for (LabelPart lp : parts) {
						xTotal += g.getFontMetrics(lp.getFont()).stringWidth(lp.getText());
					}
					for (LabelPart lp : parts) {
						g.setColor(lp.getColor());
						g.setFont(lp.getFont());
						g.drawString(lp.getText(), (int) (getX() + xPos + (getDimension().getX()/2) - (xTotal/2)), calcY);
						xPos += g.getFontMetrics().stringWidth(lp.getText());
					}
				}
			}
		}
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public List<LabelPart> getLabelParts() {
		return parts;
	}
	public void setLabelParts(List<LabelPart> parts) {
		this.parts = parts;
	}
	public LabelAligment getAligment() {
		return aligment;
	}
	public void setAligment(LabelAligment aligment) {
		this.aligment = aligment;
	}
}
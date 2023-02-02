package me.pepe.GameAPI.Utils;

import java.awt.Color;
import java.awt.Font;

public class LabelPart {
	private String text;
	private Color color;
	private Font font;
	public LabelPart(String text, Color color, Font font) {
		this.text = text;
		this.color = color;
		this.font = font;
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
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
}
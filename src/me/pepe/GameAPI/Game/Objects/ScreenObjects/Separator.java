package me.pepe.GameAPI.Game.Objects.ScreenObjects;

import java.awt.Color;
import java.awt.Graphics;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;
import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;

public class Separator extends GameObject {
	private Color color;
	public Separator(InteligentPosition intPos, Game game, InteligentDimension intDim) {
		this("", Color.DARK_GRAY, intPos, game, intDim);
	}
	public Separator(Color color, InteligentPosition intPos, Game game, InteligentDimension intDim) {
		this("", color, intPos, game, intDim);
	}
	public Separator(String id, Color color, InteligentPosition intPos, Game game, InteligentDimension intDim) {
		super(id, intPos, game, intDim);
		this.color = color;
	}
	@Override
	public void tick() {}
	@Override
	public void render(Graphics g) {
		if (hasInteligence()) {
			g.setColor(color);
			g.fillRect((int) getX(), (int) getY(), (int) getDimension().getX(), 2);
		}
	}
}
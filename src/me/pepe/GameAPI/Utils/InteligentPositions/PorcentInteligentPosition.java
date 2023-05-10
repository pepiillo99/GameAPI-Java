package me.pepe.GameAPI.Utils.InteligentPositions;

import me.pepe.GameAPI.Game.Objects.ScreenObjects.Menu;
import me.pepe.GameAPI.Windows.Windows;

public class PorcentInteligentPosition extends InteligentPosition {
	private Windows windows;
	private Menu menu;
	private double x;
	private double y;
	public PorcentInteligentPosition(Windows windows, double x, double y) {
		this.windows = windows;
		this.x = x;
		this.y = y;
	}
	public PorcentInteligentPosition(Menu menu, double x, double y) {
		this.menu = menu;
		this.x = x;
		this.y = y;
	}
	public boolean isMenu() {
		return menu != null;
	}
	public boolean isWindows() {
		return windows != null;
	}
	public double getPorcentX() {
		return x;
	}
	public double getPorcentY() {
		return y;
	}
	public void setPorcentX(double x) {
		this.x = x;
	}
	public void setPorcentY(double y) {
		this.y = y;
	}
	@Override
	protected int calcX() {
		return (isWindows() ? (int) (x * windows.getX() / 100) : (int) (x * menu.getDimension().getX() / 100));
	}
	@Override
	protected int calcY() {
		return (isWindows() ? (int) (y * windows.getY() / 100) : (int) (y * menu.getDimension().getY() / 100));
	}
}
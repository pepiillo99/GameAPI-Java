package me.pepe.GameAPI.Utils;

public class SubTextureCoords {
	private String name;
	private int x;
	private int y;
	private int cx;
	private int cy;
	public SubTextureCoords(String name, int x, int y, int cx, int cy) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.cx = cx;
		this.cy = cy;
	}
	public String getName() {
		return name;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getCutX() {
		return cx;
	}
	public int getCutY() {
		return cy;
	}
}

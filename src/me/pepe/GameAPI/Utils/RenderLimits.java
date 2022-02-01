package me.pepe.GameAPI.Utils;

public abstract class RenderLimits {
	private int x = 0;
	private int y = 0;
	private int sizeX = 0;
	private int sizeY = 0;	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getSizeX() {
		return sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}
	public void calc(int x, int y) {
		this.x = calcX(x, y);
		this.y = calcY(x, y);
		this.sizeX = calcSizeX(x, y);
		this.sizeY = calcSizeY(x, y);
	}
	protected abstract int calcX(int x, int y);
	protected abstract int calcY(int x, int y);
	protected abstract int calcSizeX(int x, int y);
	protected abstract int calcSizeY(int x, int y);
}

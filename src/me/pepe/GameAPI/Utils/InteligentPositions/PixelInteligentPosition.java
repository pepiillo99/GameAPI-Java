package me.pepe.GameAPI.Utils.InteligentPositions;

public class PixelInteligentPosition extends InteligentPosition {
	private int x;
	private int y;
	public PixelInteligentPosition(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	protected int calcX() {
		return x;
	}
	@Override
	protected int calcY() {
		return y;
	}
}
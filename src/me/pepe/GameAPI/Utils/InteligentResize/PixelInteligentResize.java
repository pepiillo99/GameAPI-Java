package me.pepe.GameAPI.Utils.InteligentResize;

public class PixelInteligentResize extends InteligentResize {
	private int weidht;
	private int height;
	public PixelInteligentResize(int weidht, int height) {
		this.weidht = weidht;
		this.height = height;
	}
	public int getWeidht() {
		return weidht;
	}
	public int getHeight() {
		return height;
	}
	@Override
	public int calcWeidht() {
		return weidht;
	}
	@Override
	public int calcHeight() {
		return height;
	}
}

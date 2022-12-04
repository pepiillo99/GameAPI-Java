package me.pepe.GameAPI.Utils.InteligentDimensions;

public class PixelInteligentDimension extends InteligentDimension {
	private int weidht;
	private int height;
	public PixelInteligentDimension(int weidht, int height) {
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

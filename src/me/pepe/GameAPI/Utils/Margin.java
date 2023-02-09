package me.pepe.GameAPI.Utils;

public class Margin {
	private int top = 0;
	private int buttom = 0;
	private int left = 0;
	private int right = 0;
	public Margin() {}
	public Margin(int top, int buttom, int left, int right) {
		this.top = top;
		this.buttom = buttom;
		this.left = left;
		this.right = right;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getButtom() {
		return buttom;
	}
	public void setButtom(int buttom) {
		this.buttom = buttom;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}	
}
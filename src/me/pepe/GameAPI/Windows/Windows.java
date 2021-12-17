package me.pepe.GameAPI.Windows;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFrame;

public class Windows {
	private JFrame frame;
	private String title;
	private int x;
	private int y;
	public long start = 0;
	public Windows(String title, int x, int y, Image icon, Canvas canvas) {
		this.x = x;
		this.y = y;
		this.title = title;
		start = System.currentTimeMillis();
		System.setProperty("sun.awt.noerasebackground", "true");
		frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(x, y));
		frame.setSize(new Dimension(x, y));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(false);
		if (icon != null) {
			frame.setIconImage(icon);
		}
		System.out.println("Ha tardadp " + (System.currentTimeMillis() - start) + "ms en ejecutar la ventana");
		frame.add(canvas);
		System.out.println("Ha tardadp " + (System.currentTimeMillis() - start) + "ms en añadir el canvas la ventana");
	}
	public String getTitle() {
		return title;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getXToPaint() {
		return x - 15;
	}
	public int getYToPaint() {
		return y - 35;
	}
	public Dimension getSize() {
		return frame.getSize();
	}
	public void setResizable(boolean resizable) {
		frame.setResizable(resizable);
		if (!resizable) {
			frame.setMinimumSize(new Dimension(x, y));
			frame.setMaximumSize(new Dimension(x, y));
		} else {
			frame.setMinimumSize(new Dimension(50, 50));
			frame.setMaximumSize(new Dimension(1000, 1000));
		}
	}
	public void setVisible() {
		frame.setVisible(true);
		System.out.println("Ha tardadp " + (System.currentTimeMillis() - start) + "ms en ver la ventana");
	}
}

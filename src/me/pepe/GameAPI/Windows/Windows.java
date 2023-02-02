package me.pepe.GameAPI.Windows;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Menu;
import java.awt.Toolkit;

import javax.swing.JFrame;

import me.pepe.GameAPI.Game.Game;

public class Windows {
	private JFrame frame;
	private Game game;
	private String title;
	private int x;
	private int y;
	private Dimension minSize = new Dimension(500, 500);
	public long start = 0;
	private boolean canFullScrenable = false;
	public Windows(String title, int x, int y, Image icon, Game game) {
		this.game = game;
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
		frame.add(game);
		System.out.println("Ha tardadp " + (System.currentTimeMillis() - start) + "ms en a�adir el canvas la ventana");
	}
	public String getTitle() {
		return title;
	}
	public int getX() {
		return frame.getWidth();
	}
	public int getY() {
		return frame.getHeight();
	}
	public int getXToPaint() {
		return game.getWidth();
	}
	public int getYToPaint() {
		return game.getHeight();
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
			frame.setMinimumSize(minSize);
			frame.setMaximumSize(null); // infinito
		}
	}
	public void setVisible() {
		frame.setVisible(true);
		System.out.println("Ha tardadp " + (System.currentTimeMillis() - start) + "ms en ver la ventana");
	}
	public boolean canFullScreen() {
		return canFullScrenable;
	}
	public void setCanFullScreen(boolean canFullScrenable) {
		this.canFullScrenable = canFullScrenable;
	}
	public boolean isFullScreen() {
		return frame.isUndecorated();
	}
	public void setFullScreen(boolean fs) {
		if (fs) {
			frame.setVisible(false);
			frame.dispose();
			frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			frame.setUndecorated(true);
			frame.setLocation(0, 0);
			frame.setVisible(true);
		} else {
			frame.setVisible(false);
			frame.dispose();
			frame.setSize(new Dimension(x, y));
			frame.setUndecorated(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
		game.requestFocus();
	}
	public void setMinSize(int x, int y) {
		minSize = new Dimension(x, y);
		frame.setMinimumSize(new Dimension(x, y));
	}
	public void setCursor(int cursor) {
		frame.setCursor(cursor);
	}
	public JFrame getFrame() {
		return frame;
	}
}

package me.pepe.GameAPI.Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.pepe.GameAPI.Utils.InteligentPositions.InteligentPosition;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;
import me.pepe.GameAPI.Utils.InteligentDimensions.PixelInteligentDimension;
import me.pepe.GameAPI.Windows.Windows;

public class RenderUtils {
	public static void fillRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color) {
		fillRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, RenderType.PORCENT);
	}
	public static void fillRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderType renderType) {
		fillRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, renderType);
	}
	public static void fillRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits, RenderType renderType) {
		fillRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, renderType);
	}
	public static void fillRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType) {
		fillRect(windows, realX, realY, realHeight, realWight, g, color, resizeType, null, RenderType.PORCENT);
	}
	public static void fillRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits) {
		fillRect(windows, realX, realY, realHeight, realWight, g, color, resizeType, limits, RenderType.PORCENT);
	}
	public static void fillRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits) {
		fillRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, RenderType.PORCENT);
	}
	public static void fillRect(Graphics g, Color color, InteligentPosition pos, InteligentDimension res) {
		fillRect(g, color, pos, res, 0, 0);
	}
	public static void fillRect(Graphics g, Color color, InteligentPosition pos, InteligentDimension res, int resPorcent) {
		fillRect(g, color, pos, res, resPorcent, resPorcent);
	}
	public static void fillRect(Graphics g, Color color, InteligentPosition pos, InteligentDimension res, int resPorcentX, int resPorcentY) {
		g.setColor(color);
		g.fillRect(pos.calculateX(res, resPorcentX), pos.calculateY(res, resPorcentY), res.calcWeidht(), res.calcHeight());
	}
	public static void fillRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits, RenderType renderType) {
		int x;
		int y;
		if (limits != null) {
			x = limits.getSizeX();
			y = limits.getSizeY();
		} else {
			if (windows != null) {
				x = windows.getXToPaint();
				y = windows.getYToPaint();
			} else {
				x = 0;
				y = 0;
			}
		}
		g.setColor(color);
		if (renderType.equals(RenderType.PIXEL)) {
			g.fillRect((int) realX + (limits != null ? limits.getX() : 0), (int) realY + (limits != null ? limits.getY() : 0), (int) realHeight, (int) realWight);
		} else if (renderType.equals(RenderType.PORCENT)) {
	 		if (resizeType.equals(ResizeType.NORMAL)) {
				g.fillRect((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y/ 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_Y)) {
				g.fillRect((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (x) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_X)) {
				g.fillRect((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y) / 100));
			} else if (resizeType.equals(ResizeType.NONE)) {
				g.fillRect((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100));
			}
		}
	}
	public static void fillCenteredRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color) {
		fillCenteredRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, RenderType.PORCENT);
	}
	public static void fillCenteredRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderType renderType) {
		fillCenteredRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, renderType);
	}
	public static void fillCenteredRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits, RenderType renderType) {
		fillCenteredRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, renderType);
	}
	public static void fillCenteredRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType) {
		fillCenteredRect(windows, realX, realY, realHeight, realWight, g, color, resizeType, null, RenderType.PORCENT);
	}
	public static void fillCenteredRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits) {
		fillCenteredRect(windows, realX, realY, realHeight, realWight, g, color, resizeType, limits, RenderType.PORCENT);
	}
	public static void fillCenteredRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits) {
		fillCenteredRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, RenderType.PORCENT);
	}
	public static void fillCenteredRect(Graphics g, Color color, InteligentPosition pos, InteligentDimension res) {
		fillRect(g, color, pos, res, 50, 50);
	}
	public static void fillCenteredRect(Windows windows, double realX, double realY, double realHeight,double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits, RenderType renderType) {
		int x;
		int y;
		if (limits != null) {
			x = limits.getSizeX();
			y = limits.getSizeY();
		} else {
			if (windows != null) {
				x = windows.getXToPaint();
				y = windows.getYToPaint();
			} else {
				x = 0;
				y = 0;
			}
		}
		g.setColor(color);
		if (renderType.equals(RenderType.PIXEL)) {
			g.fillRect((int) (realX + (limits != null ? limits.getX() : 0) - (realHeight / 2)), (int) (realY + (limits != null ? limits.getY() : 0) - (realWight / 2)), (int) realHeight, (int) realWight);
		} else {
			if (resizeType.equals(ResizeType.NORMAL)) {
				g.fillRect((int) ((realX - (realHeight / 2)) * x / 100) + (limits != null ? limits.getX() : 0), (int) ((realY - (realWight / 2)) * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_Y)) {
				g.fillRect((int) ((realX - (realHeight / 2)) * x / 100) + (limits != null ? limits.getX() : 0), (int) ((realY - (realWight / 2)) * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (x) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_X)) {
				g.fillRect((int) ((realX - (realHeight / 2)) * x / 100) + (limits != null ? limits.getX() : 0), (int) ((realY - (realWight / 2)) * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y) / 100));
			} else if (resizeType.equals(ResizeType.NONE)) {
				g.fillRect((int) (((realX) * x / 100) - (realHeight / 2)) + (limits != null ? limits.getX() : 0), (int) (((realY) * y / 100) - (realWight / 2)) + (limits != null ? limits.getY() : 0), (int) (realHeight), (int) (realWight));
			}
		}
	}
	public static void fillOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color) {
		fillOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, RenderType.PORCENT);
	}
	public static void fillOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderType renderType) {
		fillOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, renderType);
	}
	public static void fillOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits, RenderType renderType) {
		fillOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, renderType);
	}
	public static void fillOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType) {
		fillOval(windows, realX, realY, realHeight, realWight, g, color, resizeType, null, RenderType.PORCENT);
	}
	public static void fillOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits) {
		fillOval(windows, realX, realY, realHeight, realWight, g, color, resizeType, limits, RenderType.PORCENT);
	}
	public static void fillOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits) {
		fillOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, RenderType.PORCENT);
	}
	public static void fillOval(Graphics g, Color color, InteligentPosition pos, InteligentDimension res) {
		fillOval(g, color, pos, res, 0, 0);
	}
	public static void fillOval(Graphics g, Color color, InteligentPosition pos, InteligentDimension res, int resPorcent) {
		fillOval(g, color, pos, res, resPorcent, resPorcent);
	}
	public static void fillOval(Graphics g, Color color, InteligentPosition pos, InteligentDimension res, int resPorcentX, int resPorcentY) {
		g.setColor(color);
		g.fillOval(pos.calculateX(res, resPorcentX), pos.calculateY(res, resPorcentY), res.calcWeidht(), res.calcHeight());
	}
	public static void fillOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits, RenderType renderType) {
		int x;
		int y;
		if (limits != null) {
			x = limits.getSizeX();
			y = limits.getSizeY();
		} else {
			if (windows != null) {
				x = windows.getXToPaint();
				y = windows.getYToPaint();
			} else {
				x = 0;
				y = 0;
			}
		}
		g.setColor(color);
		if (renderType.equals(RenderType.PIXEL)) {
			g.fillOval((int) realX + (limits != null ? limits.getX() : 0), (int) realY + (limits != null ? limits.getY() : 0), (int) realHeight, (int) realWight);
		} else if (renderType.equals(RenderType.PORCENT)) {
	 		if (resizeType.equals(ResizeType.NORMAL)) {
				g.fillOval((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y/ 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_Y)) {
				g.fillOval((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (x) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_X)) {
				g.fillOval((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y) / 100));
			} else if (resizeType.equals(ResizeType.NONE)) {
				g.fillOval((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100));
			}
		}
	}
	public static void fillCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color) {
		fillCenteredOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, RenderType.PORCENT);
	}
	public static void fillCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderType renderType) {
		fillCenteredOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, renderType);
	}
	public static void fillCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits, RenderType renderType) {
		fillCenteredOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, renderType);
	}
	public static void fillCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType) {
		fillCenteredOval(windows, realX, realY, realHeight, realWight, g, color, resizeType, null, RenderType.PORCENT);
	}
	public static void fillCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits) {
		fillCenteredOval(windows, realX, realY, realHeight, realWight, g, color, resizeType, limits, RenderType.PORCENT);
	}
	public static void fillCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits) {
		fillCenteredOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, RenderType.PORCENT);
	}
	public static void fillCenteredOval(Graphics g, Color color, InteligentPosition pos, InteligentDimension res) {
		fillOval(g, color, pos, res, 50, 50);
	}
	public static void fillCenteredOval(Windows windows, double realX, double realY, double realHeight,double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits, RenderType renderType) {
		int x;
		int y;
		if (limits != null) {
			x = limits.getSizeX();
			y = limits.getSizeY();
		} else {
			if (windows != null) {
				x = windows.getXToPaint();
				y = windows.getYToPaint();
			} else {
				x = 0;
				y = 0;
			}
		}
		g.setColor(color);
		if (renderType.equals(RenderType.PIXEL)) {
			g.fillOval((int) (realX + (limits != null ? limits.getX() : 0) - (realHeight / 2)), (int) (realY + (limits != null ? limits.getY() : 0) - (realWight / 2)), (int) realHeight, (int) realWight);
		} else {
			if (resizeType.equals(ResizeType.NORMAL)) {
				g.fillOval((int) ((realX - (realHeight / 2)) * x / 100) + (limits != null ? limits.getX() : 0), (int) ((realY - (realWight / 2)) * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_Y)) {
				g.fillOval((int) ((realX - (realHeight / 2)) * x / 100) + (limits != null ? limits.getX() : 0), (int) ((realY - (realWight / 2)) * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (x) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_X)) {
				g.fillOval((int) ((realX - (realHeight / 2)) * x / 100) + (limits != null ? limits.getX() : 0), (int) ((realY - (realWight / 2)) * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y) / 100));
			} else if (resizeType.equals(ResizeType.NONE)) {
				g.fillOval((int) (((realX) * x / 100) - (realHeight / 2)), (int) (((realY) * y / 100) - (realWight / 2)), (int) (realHeight), (int) (realWight));
			}
		}
	}
	public static void drawOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color) {
		drawOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, RenderType.PORCENT);
	}
	public static void drawOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderType renderType) {
		drawOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, renderType);
	}
	public static void drawOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits, RenderType renderType) {
		drawOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, renderType);
	}
	public static void drawOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType) {
		drawOval(windows, realX, realY, realHeight, realWight, g, color, resizeType, null, RenderType.PORCENT);
	}
	public static void drawOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits) {
		drawOval(windows, realX, realY, realHeight, realWight, g, color, resizeType, limits, RenderType.PORCENT);
	}
	public static void drawOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits) {
		drawOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, RenderType.PORCENT);
	}
	public static void drawOval(Graphics g, Color color, InteligentPosition pos, InteligentDimension res) {
		drawOval(g, color, pos, res, 0, 0);
	}
	public static void drawOval(Graphics g, Color color, InteligentPosition pos, InteligentDimension res, int resPorcent) {
		drawOval(g, color, pos, res, resPorcent, resPorcent);
	}
	public static void drawOval(Graphics g, Color color, InteligentPosition pos, InteligentDimension res, int resPorcentX, int resPorcentY) {
		g.setColor(color);
		g.drawOval(pos.calculateX(res, resPorcentX), pos.calculateY(res, resPorcentY), res.calcWeidht(), res.calcHeight());
	}
	public static void drawOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits, RenderType renderType) {
		int x;
		int y;
		if (limits != null) {
			x = limits.getSizeX();
			y = limits.getSizeY();
		} else {
			if (windows != null) {
				x = windows.getXToPaint();
				y = windows.getYToPaint();
			} else {
				x = 0;
				y = 0;
			}
		}
		g.setColor(color);
		if (renderType.equals(RenderType.PIXEL)) {
			g.drawOval((int) realX + (limits != null ? limits.getX() : 0), (int) realY + (limits != null ? limits.getY() : 0), (int) realHeight, (int) realWight);
		} else if (renderType.equals(RenderType.PORCENT)) {
	 		if (resizeType.equals(ResizeType.NORMAL)) {
				g.drawOval((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y/ 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_Y)) {
				g.drawOval((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (x) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_X)) {
				g.drawOval((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y) / 100));
			} else if (resizeType.equals(ResizeType.NONE)) {
				g.drawOval((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100));
			}
		}
	}
	public static void drawCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color) {
		drawCenteredOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, RenderType.PORCENT);
	}
	public static void drawCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderType renderType) {
		drawCenteredOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, renderType);
	}
	public static void drawCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits, RenderType renderType) {
		drawCenteredOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, renderType);
	}
	public static void drawCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType) {
		drawCenteredOval(windows, realX, realY, realHeight, realWight, g, color, resizeType, null, RenderType.PORCENT);
	}
	public static void drawCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits) {
		drawCenteredOval(windows, realX, realY, realHeight, realWight, g, color, resizeType, limits, RenderType.PORCENT);
	}
	public static void drawCenteredOval(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits) {
		drawCenteredOval(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, RenderType.PORCENT);
	}
	public static void drawCenteredOval(Graphics g, Color color, InteligentPosition pos, InteligentDimension res) {
		drawOval(g, color, pos, res, 50, 50);
	}
	public static void drawCenteredOval(Windows windows, double realX, double realY, double realHeight,double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits, RenderType renderType) {
		int x;
		int y;
		if (limits != null) {
			x = limits.getSizeX();
			y = limits.getSizeY();
		} else {
			if (windows != null) {
				x = windows.getXToPaint();
				y = windows.getYToPaint();
			} else {
				x = 0;
				y = 0;
			}
		}
		g.setColor(color);
		if (renderType.equals(RenderType.PIXEL)) {
			g.drawOval((int) (realX + (limits != null ? limits.getX() : 0) - (realHeight / 2)), (int) (realY + (limits != null ? limits.getY() : 0) - (realWight / 2)), (int) realHeight, (int) realWight);
		} else {
			if (resizeType.equals(ResizeType.NORMAL)) {
				g.drawOval((int) ((realX - (realHeight / 2)) * x / 100) + (limits != null ? limits.getX() : 0), (int) ((realY - (realWight / 2)) * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_Y)) {
				g.drawOval((int) ((realX - (realHeight / 2)) * x / 100) + (limits != null ? limits.getX() : 0), (int) ((realY - (realWight / 2)) * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (x) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_X)) {
				g.drawOval((int) ((realX - (realHeight / 2)) * x / 100) + (limits != null ? limits.getX() : 0), (int) ((realY - (realWight / 2)) * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y) / 100));
			} else if (resizeType.equals(ResizeType.NONE)) {
				g.drawOval((int) (((realX) * x / 100) - (realHeight / 2)), (int) (((realY) * y / 100) - (realWight / 2)), (int) (realHeight), (int) (realWight));
			}
		}
	}
	public static void drawRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color) {
		drawRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, RenderType.PORCENT);
	}
	public static void drawRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderType renderType) {
		drawRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, null, renderType);
	}
	public static void drawRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits, RenderType renderType) {
		drawRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, renderType);
	}
	public static void drawRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType) {
		drawRect(windows, realX, realY, realHeight, realWight, g, color, resizeType, null, RenderType.PORCENT);
	}
	public static void drawRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits) {
		drawRect(windows, realX, realY, realHeight, realWight, g, color, resizeType, limits, RenderType.PORCENT);
	}
	public static void drawRect(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, Color color, RenderLimits limits) {
		drawRect(windows, realX, realY, realHeight, realWight, g, color, ResizeType.NORMAL, limits, RenderType.PORCENT);
	}
	public static void drawRect(Graphics g, Color color, InteligentPosition pos, InteligentDimension res) {
		drawRect(g, color, pos, res, 0, 0);
	}
	public static void drawRect(Graphics g, Color color, InteligentPosition pos, InteligentDimension res, int resPorcent) {
		drawRect(g, color, pos, res, resPorcent, resPorcent);
	}
	public static void drawRect(Graphics g, Color color, InteligentPosition pos, InteligentDimension res, int resPorcentX, int resPorcentY) {
		g.setColor(color);
		g.drawRect(pos.calculateX(res, resPorcentX), pos.calculateY(res, resPorcentY), res.calcWeidht(), res.calcHeight());
	}
	public static void drawRect(Windows windows, double realX, double realY, double realHeight,double realWight, Graphics g, Color color, ResizeType resizeType, RenderLimits limits, RenderType renderType) {
		int x;
		int y;
		if (limits != null) {
			x = limits.getSizeX();
			y = limits.getSizeY();
		} else {
			if (windows != null) {
				x = windows.getXToPaint();
				y = windows.getYToPaint();
			} else {
				x = 0;
				y = 0;
			}
		}
		g.setColor(color);
		if (renderType.equals(RenderType.PIXEL)) {
			g.drawRect((int) realX + (limits != null ? limits.getX() : 0), (int) realY + (limits != null ? limits.getY() : 0), (int) realHeight, (int) realWight);
		} else if (renderType.equals(RenderType.PORCENT)) {
	 		if (resizeType.equals(ResizeType.NORMAL)) {
				g.drawRect((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y/ 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_Y)) {
				g.drawRect((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (x) / 100));
			} else if (resizeType.equals(ResizeType.ONLY_X)) {
				g.drawRect((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y) / 100));
			} else if (resizeType.equals(ResizeType.NONE)) {
				g.drawRect((int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100));
			}
		}
	}
	public static void drawString(Windows windows, String s, Font font, double realX, double realY, Graphics g, Color color) {
		drawString(windows, s, font, realX, realY, g, color, null);
	}
	public static void drawString(String s, Graphics g, Color color, InteligentPosition pos, Font font) {
		drawString(s, g, color, pos, font, 0, 0);
	}
	public static void drawString(String s, Graphics g, Color color, InteligentPosition pos, Font font, int resPorcent) {
		drawString(s, g, color, pos, font, resPorcent, resPorcent);
	}
	public static void drawString(String s, Graphics g, Color color, InteligentPosition pos, Font font, int resPorcentX, int resPorcentY) {
	    FontMetrics metrics = g.getFontMetrics(font);
		PixelInteligentDimension res = new PixelInteligentDimension(metrics.stringWidth(s), metrics.getHeight());
		g.setFont(font);
		g.setColor(color);
		g.drawString(s, pos.calculateX(res, resPorcentX), pos.calculateY(res, resPorcentY));
	}
	public static void drawString(Windows windows, String s, Font font, double realX, double realY, Graphics g, Color color, RenderLimits limits) {
		int x;
		int y;
		if (limits != null) {
			x = limits.getSizeX();
			y = limits.getSizeY();
		} else {
			if (windows != null) {
				x = windows.getXToPaint();
				y = windows.getYToPaint();
			} else {
				x = 0;
				y = 0;
			}
		}
		g.setColor(color);
		g.setFont(font);
		g.drawString(s, (int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0));
	}
	public static void drawCenteredString(Windows windows, String s, Font font, double realX, double realY, Graphics g, Color color) {
		drawCenteredString(windows, s, font, realX, realY, g, color, null);
	}
	public static void drawCenteredString(String s, Graphics g, Color color, InteligentPosition pos, Font font) {
		drawString(s, g, color, pos, font, 50, 50);
	}
	public static void drawCenteredString(Windows windows, String s, Font font, double realX, double realY, Graphics g, Color color, RenderLimits limits) {
		int x;
		int y;
		if (limits != null) {
			x = limits.getSizeX();
			y = limits.getSizeY();
		} else {
			if (windows != null) {
				x = windows.getXToPaint();
				y = windows.getYToPaint();
			} else {
				x = 0;
				y = 0;
			}
		}
	    FontMetrics metrics = g.getFontMetrics(font);
	    x = (int) (realX * x / 100) + ((metrics.getHeight() / 6) - metrics.stringWidth(s)) / 2;
	    y = (int) ((realY * y / 100) + (((metrics.getHeight() / 6) - metrics.getHeight()) / 2) + (metrics.getAscent() * 1.1));
		g.setColor(color);
		g.setFont(font);
		g.drawString(s, x + (limits != null ? limits.getX() : 0), y + (limits != null ? limits.getY() : 0));
	}
	public static void drawImage(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, BufferedImage buf) {
		drawImage(windows, realX, realY, realHeight, realWight, g, buf, ResizeType.NORMAL, null, RenderType.PORCENT);
	}
	public static void drawImage(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, BufferedImage buf, RenderType renderType) {
		drawImage(windows, realX, realY, realHeight, realWight, g, buf, ResizeType.NORMAL, null, renderType);
	}
	public static void drawImage(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, BufferedImage buf, RenderLimits limits, RenderType renderType) {
		drawImage(windows, realX, realY, realHeight, realWight, g, buf, ResizeType.NORMAL, limits, renderType);
	}
	public static void drawImage(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, BufferedImage buf, ResizeType resizeType) {
		drawImage(windows, realX, realY, realHeight, realWight, g, buf, resizeType, null, RenderType.PORCENT);
	}
	public static void drawImage(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, BufferedImage buf, ResizeType resizeType, RenderLimits limits) {
		drawImage(windows, realX, realY, realHeight, realWight, g, buf, resizeType, limits, RenderType.PORCENT);
	}
	public static void drawImage(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, BufferedImage buf, RenderLimits limits) {
		drawImage(windows, realX, realY, realHeight, realWight, g, buf, ResizeType.NORMAL, limits, RenderType.PORCENT);
	}
	public static void drawImage(Graphics g, BufferedImage buf, InteligentPosition pos, InteligentDimension res) {
		drawImage(g, buf, pos, res, 0, 0);
	}
	public static void drawImage(Graphics g, BufferedImage buf, InteligentPosition pos, InteligentDimension res, int resPorcent) {
		drawImage(g, buf, pos, res, resPorcent, resPorcent);
	}
	public static void drawImage(Graphics g, BufferedImage buf, InteligentPosition pos, InteligentDimension res, int resPorcentX, int resPorcentY) {
		g.drawImage(buf, pos.calculateX(res, resPorcentX), pos.calculateY(res, resPorcentY), res.calcWeidht(), res.calcHeight(), null);
	}
	public static void drawImage(Windows windows, double realX, double realY, double realHeight, double realWight, Graphics g, BufferedImage buf, ResizeType resizeType, RenderLimits limits, RenderType renderType) {
		int x;
		int y;
		if (limits != null) {
			x = limits.getSizeX();
			y = limits.getSizeY();
		} else {
			if (windows != null) {
				x = windows.getXToPaint();
				y = windows.getYToPaint();
			} else {
				x = 0;
				y = 0;
			}
		}
		if (renderType.equals(RenderType.PIXEL)) {
			g.drawImage(buf, (int) realX + (limits != null ? limits.getX() : 0), (int) realY + (limits != null ? limits.getY() : 0), (int) realHeight, (int) realWight, null);
		} else if (renderType.equals(RenderType.PORCENT)) {
			if (resizeType.equals(ResizeType.NORMAL)) {
				g.drawImage(buf, (int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y/ 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100), null);
			} else if (resizeType.equals(ResizeType.ONLY_Y)) {
				g.drawImage(buf, (int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (x) / 100), null);
			} else if (resizeType.equals(ResizeType.ONLY_X)) {
				g.drawImage(buf, (int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y) / 100), null);
			} else if (resizeType.equals(ResizeType.NONE)) {
				g.drawImage(buf, (int) (realX * x / 100) + (limits != null ? limits.getX() : 0), (int) (realY * y / 100) + (limits != null ? limits.getY() : 0), (int) (realHeight * (y > x ? y : x) / 100), (int) (realWight * (y > x ? x : y) / 100), null);
			}
		}
	}
}

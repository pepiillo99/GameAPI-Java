package me.pepe.GameAPI.Utils.InteligentPositions;

import me.pepe.GameAPI.Game.Objects.ScreenObjects.Menu;
import me.pepe.GameAPI.Utils.Borders.HorizontalBorders;
import me.pepe.GameAPI.Utils.Borders.VerticalBorders;
import me.pepe.GameAPI.Utils.RenderLimits;
import me.pepe.GameAPI.Windows.Windows;

public class ReferenceBordersInteligentPosition extends InteligentPosition {
	private int x;
	private int y;
	private Windows windows;
	private Menu menu;
	private VerticalBorders vertical;
	private HorizontalBorders horizontal;
	public ReferenceBordersInteligentPosition(int x, int y, Windows windows, VerticalBorders vertical, HorizontalBorders horizontal) {
		this(x, y, windows, null, vertical, horizontal);
	}
	public ReferenceBordersInteligentPosition(int x, int y, Windows windows, RenderLimits limits, VerticalBorders vertical, HorizontalBorders horizontal) {
		super(limits);
		this.x = x;
		this.y = y;
		this.windows = windows;
		this.vertical = vertical;
		this.horizontal = horizontal;
	}
	public ReferenceBordersInteligentPosition(int x, int y, Menu menu, VerticalBorders vertical, HorizontalBorders horizontal) {
		super();
		this.x = x;
		this.y = y;
		this.menu = menu;
		this.vertical = vertical;
		this.horizontal = horizontal;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public VerticalBorders getVerticalBorder() {
		return vertical;
	}
	public HorizontalBorders getHorizontalBorder() {
		return horizontal;
	}
	@Override
	protected int calcX() {
		if (hasRenderLimits()) {
			if (vertical.equals(VerticalBorders.RIGHT)) {
				return (getRenderLimits().getX() + getRenderLimits().getSizeX()) - x;
			} else if (vertical.equals(VerticalBorders.LEFT)) {
				return x;
			}		
		} else {
			if (windows != null) {
				if (vertical.equals(VerticalBorders.RIGHT)) {
					return windows.getXToPaint() - x;
				} else if (vertical.equals(VerticalBorders.LEFT)) {
					return x;
				}
			} else if (menu != null ) {
				if (vertical.equals(VerticalBorders.RIGHT)) {
					return (int) (menu.getDimension().getX() - x);
				} else if (vertical.equals(VerticalBorders.LEFT)) {
					return x;
				}
			}
		}
		return -1;
	}
	@Override
	protected int calcY() {
		if (hasRenderLimits()) {
			if (horizontal.equals(HorizontalBorders.DOWN)) {
				return (getRenderLimits().getY() + getRenderLimits().getSizeY()) - y;
			} else if (horizontal.equals(HorizontalBorders.UP)) {
				return y;
			}		
		} else {
			if (windows != null) {
				if (horizontal.equals(HorizontalBorders.DOWN)) {
					return windows.getYToPaint() - y;
				} else if (horizontal.equals(HorizontalBorders.UP)) {
					return y;
				}
			} else if (menu != null ) {
				if (horizontal.equals(HorizontalBorders.DOWN)) {
					return (int) (menu.getDimension().getY() - y);
				} else if (horizontal.equals(HorizontalBorders.UP)) {
					return y;
				}
			}
		}
		return -1;
	}
}

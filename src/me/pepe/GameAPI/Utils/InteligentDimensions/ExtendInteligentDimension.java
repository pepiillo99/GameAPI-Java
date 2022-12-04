package me.pepe.GameAPI.Utils.InteligentDimensions;

import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Menu;
import me.pepe.GameAPI.Screen.Screen;

public class ExtendInteligentDimension extends InteligentDimension {
	private Menu menu;
	private Screen screen;
	private GameObject object;
	private InteligentExtendiblePosibility extendiblePosibility = InteligentExtendiblePosibility.BOTH;
	private InteligentDimension auxDim;
	public ExtendInteligentDimension() { // define menu or screen and object next? (both)
		
	}
	public ExtendInteligentDimension(InteligentExtendiblePosibility extendiblePosibility, InteligentDimension intDim) { // define menu or screen and object next?
		this.extendiblePosibility = extendiblePosibility;
		this.auxDim = intDim;
	}
	/**
	 * For use InteligentExtendiblePosibility.BOTH
	 * You only need use this if you use object.setInteligentPosition(..)
	 */
	public ExtendInteligentDimension(Screen screen) {
		this.screen = screen;
	}
	/**
	 * You only need use this if you use object.setInteligentPosition(..)
	 */
	public ExtendInteligentDimension(Screen screen, InteligentExtendiblePosibility extendiblePosibility, InteligentDimension intDim) {
		this.screen = screen;
		this.extendiblePosibility = extendiblePosibility;
		this.auxDim = intDim;
	}
	public InteligentExtendiblePosibility getInteligentExtengoblePosibility() {
		return extendiblePosibility;
	}
	@Override
	public int calcWeidht() {
		if (extendiblePosibility.equals(InteligentExtendiblePosibility.BOTH) || extendiblePosibility.equals(InteligentExtendiblePosibility.WEIDHT)) {
			return (int) (getMaxWeidht() - object.getX());
		} else if (extendiblePosibility.equals(InteligentExtendiblePosibility.HEIGHT)) {
			if (auxDim != null) {
				return auxDim.calcWeidht();
			}
		}
		return 0;
	}
	@Override
	public int calcHeight() {
		if (extendiblePosibility.equals(InteligentExtendiblePosibility.BOTH) || extendiblePosibility.equals(InteligentExtendiblePosibility.HEIGHT)) {
			return (int) (getMaxHeight() - object.getY());
		} else if (extendiblePosibility.equals(InteligentExtendiblePosibility.WEIDHT)) {
			if (auxDim != null) {
				return auxDim.calcHeight();
			}
		}
		return 0;
	}
	public GameObject getGameObject() {
		return object;
	}
	public void setGameObject(GameObject object) {
		this.object = object;
	}
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	private int getMaxWeidht() {
		if (screen != null) {
			return screen.getWindows().getXToPaint();
		} else if (menu != null) {
			return (int) menu.getDimension().getX();
		}
		return 0;
	}
	private int getMaxHeight() {
		if (screen != null) {
			return screen.getWindows().getYToPaint();
		} else if (menu != null) {
			return (int) menu.getDimension().getY();
		}
		return 0;
	}
	public enum InteligentExtendiblePosibility {
		WEIDHT, // x
		HEIGHT, // y
		BOTH; // ambos
	}	
}
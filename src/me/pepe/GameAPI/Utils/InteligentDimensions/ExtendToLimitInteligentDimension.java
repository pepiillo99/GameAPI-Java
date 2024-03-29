package me.pepe.GameAPI.Utils.InteligentDimensions;

import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Menu;
import me.pepe.GameAPI.Screen.Screen;
import me.pepe.GameAPI.Utils.Borders.HorizontalBorders;
import me.pepe.GameAPI.Utils.Borders.VerticalBorders;
import me.pepe.GameAPI.Utils.InteligentPositions.ReferenceBordersInteligentPosition;

public class ExtendToLimitInteligentDimension extends InteligentDimension {
	// DIMENSION QUE SE EXTIENDE HASTA EL FINAL DE LOS OBJETOS DEL MENU/SCREEN
	private Menu menu;
	private Screen screen;
	private GameObject object;
	private InteligentExtendiblePosibility extendiblePosibility = InteligentExtendiblePosibility.BOTH;
	private int offWeidht = 0;
	private int offHeight = 0;
	private InteligentDimension auxDim;
	public ExtendToLimitInteligentDimension() { // define menu or screen and object next? (both)
		
	}
	public ExtendToLimitInteligentDimension(InteligentExtendiblePosibility extendiblePosibility, InteligentDimension intDim) { // define menu or screen and object next?
		this(extendiblePosibility, intDim, 0, 0);
	}
	public ExtendToLimitInteligentDimension(InteligentExtendiblePosibility extendiblePosibility, InteligentDimension intDim, int offWeidht, int offHeight) { // define menu or screen and object next?
		this.extendiblePosibility = extendiblePosibility;
		this.auxDim = intDim;
		this.offWeidht = offWeidht;
		this.offHeight = offHeight;
	}
	/**
	 * For use InteligentExtendiblePosibility.BOTH
	 * You only need use this if you use object.setInteligentPosition(..)
	 */
	public ExtendToLimitInteligentDimension(Screen screen) {
		this.screen = screen;
	}
	/**
	 * You only need use this if you use object.setInteligentPosition(..)
	 */
	public ExtendToLimitInteligentDimension(Screen screen, InteligentExtendiblePosibility extendiblePosibility, InteligentDimension intDim) {
		this(screen, extendiblePosibility, intDim, 0, 0);
	}
	public ExtendToLimitInteligentDimension(Screen screen, InteligentExtendiblePosibility extendiblePosibility, InteligentDimension intDim, int offWeidht, int offHeight) {
		this.screen = screen;
		this.extendiblePosibility = extendiblePosibility;
		this.auxDim = intDim;
		this.offWeidht = offWeidht;
		this.offHeight = offHeight;
	}
	public InteligentExtendiblePosibility getInteligentExtengoblePosibility() {
		return extendiblePosibility;
	}
	@Override
	public int calcWeidht() {
		if (extendiblePosibility.equals(InteligentExtendiblePosibility.BOTH) || extendiblePosibility.equals(InteligentExtendiblePosibility.WEIDHT)) {
			return (int) ((getMaxWeidht() - (object != null ? object.getX() : 0)) + 1) + offWeidht;
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
			return (int) ((getMaxHeight() - (object != null ? object.getY() : 0)) + 1) + offHeight;
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
	public int getOffWeidht() {
		return offWeidht;
	}
	public void setOffWeidht(int off) {
		this.offWeidht = off;
	}
	public int getOffHeight() {
		return offHeight;
	}
	public void setOffHeight(int off) {
		this.offHeight = off;
	}
	// tiene que tener en cuenta que el objeto no esté pegado al borde de la derecha, porque si no al agrandar el menu, el objeto se pegara mas, entrara en un bucle y el menu se agrandará siempre
	private int getMaxWeidht() {
		int result = 0;
		if (screen != null) {
			for (GameObject object : screen.getGameObjects()) {
				boolean valid = true;
				if (object.hasInteligence() && object.getInteligentPosition() instanceof ReferenceBordersInteligentPosition) {
					ReferenceBordersInteligentPosition inteligentPosition = (ReferenceBordersInteligentPosition) object.getInteligentPosition();
					if (inteligentPosition.getVerticalBorder() == VerticalBorders.RIGHT) {
						valid = false;
					}
				}
				if (valid) {
					int sum = (int) (object.getX() + object.getDimension().getX());
					if (result < sum) {
						result = sum;
					}
				}
			}
		} else if (menu != null) {
			result = menu.getLastObjectWeidht();
		}
		if (result == 0 && auxDim != null) {
			return auxDim.calcHeight();
		}
		return result;
	}
	// tiene que tener en cuenta que el objeto no esté pegado al borde de abajo, porque si no al agrandar el menu, el objeto se pegara mas, entrara en un bucle y el menu se agrandará siempre
	private int getMaxHeight() {
		int result = 0;
		if (screen != null) {
			for (GameObject object : screen.getGameObjects()) {
				boolean valid = true;
				if (object.hasInteligence() && object.getInteligentPosition() instanceof ReferenceBordersInteligentPosition) {
					ReferenceBordersInteligentPosition inteligentPosition = (ReferenceBordersInteligentPosition) object.getInteligentPosition();
					if (inteligentPosition.getHorizontalBorder() == HorizontalBorders.DOWN) {
						valid = false;
					}
				}
				if (valid) {
					int sum = (int) (object.getY() + object.getDimension().getY());
					if (result < sum) {
						result = sum;
					}
				}
			}
			//System.out.println("result is " + result);
		} else if (menu != null) {
			result = menu.getLastObjectHeight();
		}
		if (result == 0 && auxDim != null) {
			return auxDim.calcHeight();
		}
		return result;
	}
}

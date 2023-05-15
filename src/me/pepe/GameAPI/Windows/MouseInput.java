package me.pepe.GameAPI.Windows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Menu;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.SelectBox;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.TextBox;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Button.Button;
import me.pepe.GameAPI.Screen.Screen;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Windows.Buttons.MouseButtons;


public abstract class MouseInput extends MouseAdapter {
	private Screen screen;
	private List<Integer> pressedButtons = new ArrayList<Integer>();
	private int x, y, xOnScreen, yOnScreen;
	public MouseInput(Screen screen) {
		this.screen = screen;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getXOnScreen() {
		return xOnScreen;
	}
	public int getYOnScreen() {
		return yOnScreen;
	}
	public abstract void tick();
	public abstract void onClick(MouseButtons mouseButton);
	public abstract void onWheelMoved(MouseButtons mouseButton);
	public abstract void onButtonPressed(MouseButtons mouseButton);
	public abstract void onButtonReleased(MouseButtons mouseButton);
	public void onMouseMoved() {}
	public void onMouseDragged(int difX, int difY) {}
	private void addPressedButton(int button)  {
		if (!pressedButtons.contains(button)) {
			pressedButtons.add(button);
		}
	}
	private void removePressedButton(int button) {
		pressedButtons.remove((Object) button);
	}
    public void mouseClicked(MouseEvent e) {
    	onClick(MouseButtons.getClickedButton(e.getButton()));
    }
    public void mouseWheelMoved(MouseWheelEvent e) {
    	int id = e.getWheelRotation();
    	if (id == 1) {
    		id = -2;
    	}
		for (GameObject object : screen.getGameObjects()) {
	    	if (object instanceof Menu) {
				Menu menu = (Menu) object;
				if (menu.isOver()) {
					menu = getResultMenu(menu);
					menu.setStartRender(new GameLocation(menu.getStartRender().getX(), menu.getStartRender().getY() + (MouseButtons.getClickedButton(id) == MouseButtons.WHEEL_DOWN ? -10 : 10)));
				}
			}
		}
    	onWheelMoved(MouseButtons.getClickedButton(id));
    }
   public void mousePressed(MouseEvent e) {
	   addPressedButton(e.getButton());
		for (GameObject object : screen.getGameObjects()) {
			if (object instanceof Menu) {
				Menu menu = (Menu) object;
				if (menu.isOver()) {
					getResultMenu(menu).setClicked(true);
				}
			}
		}	
	   onButtonPressed(MouseButtons.getClickedButton(e.getButton()));
   }
   public void mouseReleased(MouseEvent e) {
	   removePressedButton(e.getButton());
	   Menu selectedMenu = null;
		for (GameObject object : screen.getGameObjects()) {
			if (object instanceof Menu) {
				Menu menu = (Menu) object;
				if (menu.isOver()) {
					selectedMenu = menu;
				}
			}
		}
		if (selectedMenu != null) {
			getResultMenu(selectedMenu).setClicked(true);
			getResultMenu(selectedMenu).registerClick((int) (x - selectedMenu.getX()), (int) (y - selectedMenu.getY()));
			execMouseOverOnGameObject(getResultMenu(selectedMenu).getGameObjects());
		} else {
			execMouseOverOnGameObject(screen.getGameObjects());
		}
	   onButtonReleased(MouseButtons.getClickedButton(e.getButton()));
   }
   private void execMouseOverOnGameObject(Collection<GameObject> collection) {
		for (GameObject object : collection) {
			if (object instanceof Button) {
				Button button = (Button) object;
				if (button.isOver()) {
					button.onClick();
				}
			} else if (object instanceof SelectBox) {
				SelectBox select = (SelectBox) object;
				if (select.isOver()) {
					select.select(!select.isSelected());
				}
			} else if (object instanceof TextBox) {
				TextBox text = (TextBox) object;
				if (text.isOver()) {
					text.requestFocus();
				} else {
					text.unFocus();
				}
			}
		}
   }
   private Menu getResultMenu(Menu menu) {
	   for (GameObject go : menu.getGameObjects()) {
		   if (go instanceof Menu) {
			   Menu menuu = (Menu) go;
			   if (menuu.isOver()) {
				   return getResultMenu(menuu);
			   }
		   }
	   }
	   return menu;
   }
   public void mouseMoved(MouseEvent e) {
	   this.x = e.getX();
	   this.xOnScreen = e.getXOnScreen();
	   this.y = e.getY();
	   this.yOnScreen = e.getYOnScreen();
	   onMouseMoved();
   }
   public void mouseDragged(MouseEvent e) { // cuando el raton se est� arrastrando (moverse pulsado no ejecuta 'mouseMoved') ;(
	   int diffX = x - e.getX();
	   int diffY = y - e.getY();
	   this.x = e.getX();
	   this.xOnScreen = e.getXOnScreen();
	   this.y = e.getY();
	   this.yOnScreen = e.getYOnScreen();
		for (GameObject object : screen.getGameObjects()) {
	    	if (object instanceof Menu) {
				Menu menu = (Menu) object;
				if (menu.isClicked()) {
					menu.setStartRender(new GameLocation(menu.getStartRender().getX() - diffX, menu.getStartRender().getY() - diffY));
				}
			}
		}
	   onMouseDragged(diffX, diffY);
   }
   public boolean isPressed(MouseButtons mouseButton) {
	   return pressedButtons.contains(mouseButton.getID());
   }
   public void restart() {
	   x = 0;
	   xOnScreen = 0;
	   y = 0;
	   yOnScreen = 0;
   }
}

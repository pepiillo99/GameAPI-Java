package me.pepe.GameAPI.Windows;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Menu;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.TextBox;
import me.pepe.GameAPI.Screen.Screen;

public abstract class KeyInput extends KeyAdapter {
	private Screen screen;
	private Windows windows;
	private List<Integer> pressedKeys = new ArrayList<Integer>();
	public abstract void tick();
	public KeyInput(Screen screen) {
		this.screen = screen;
		this.windows = screen.getWindows();
	}
	public void onWrite(char c) {
		
	}
	public abstract void onKeyPressed(int key);
	public abstract void onKeyReleased(int key);
	public boolean checkIsPressed(int key) {
		return pressedKeys.contains(key);
	}
	private void addPressedKey(int key)  {
		if (!pressedKeys.contains(key)) {
			pressedKeys.add(key);
		}
	}
	private void removePressedKey(int key) {
		pressedKeys.remove((Object) key);
	}
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		if (windows != null && windows.canFullScreen() && key == 122) {
			windows.setFullScreen(!windows.isFullScreen());
		}
		addPressedKey(key);
		if (event.getID() == KeyEvent.KEY_PRESSED) {
			onWrite(event.getKeyChar());
			for (GameObject go : screen.getGameObjects()) {
				if (go instanceof TextBox) {
					textBoxKeyEvent(event, key, (TextBox) go);
				} else if (go instanceof Menu) {
					menuKeyEvent(event, key, (Menu) go);
				}
			}
		}
		onKeyPressed(key);
	}
	private void menuKeyEvent(KeyEvent event, int key, Menu menu) {
		for (GameObject go : menu.getGameObjects()) {
			if (go instanceof TextBox) {
				textBoxKeyEvent(event, key, (TextBox) go);
			} else if (go instanceof Menu) {
				menuKeyEvent(event, key, (Menu) go);
			}
		}
	}
	private void textBoxKeyEvent(KeyEvent event, int key, TextBox tb) {
		if (tb.isFocused()) {
			tb.onPress(key);
			if (key != 8 && key != 127) {
				char c = event.getKeyChar();
				// queda por hacer las tildes
				//System.out.println(event.getKeyCode() + " - " + event.getKeyChar() + " - " + event.isMetaDown());
				if (Character.isDefined(c)) {
					tb.write(c);
				}	
			}
		}
	}
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();
		removePressedKey(key);
		onKeyReleased(key);
	}
	public List<Integer> getKeyPresseds() {
		return pressedKeys;
	}
}

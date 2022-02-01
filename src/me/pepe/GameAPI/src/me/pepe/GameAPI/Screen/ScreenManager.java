package me.pepe.GameAPI.Screen;

import java.util.HashMap;

import me.pepe.GameAPI.Game.Game;

public class ScreenManager {
	private Game game;
	private HashMap<String, Screen> screens = new HashMap<String, Screen>();
	public ScreenManager(Game game) {
		this.game = game;
	}
	public Screen getScreen(String screen) {
		if (screens.containsKey(screen)) {
			return screens.get(screen);
		} else {
			return null;
		}
	}
	public void registerNewScreen(String screenName, Screen screen) {
		screens.put(screenName, screen);
	}
}

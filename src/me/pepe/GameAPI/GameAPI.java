package me.pepe.GameAPI;

import me.pepe.GameAPI.Cache.CacheManager;
import me.pepe.GameAPI.TextureManager.TextureManager;
import me.pepe.GameAPI.Utils.FontManager;

public class GameAPI {
	private CacheManager cacheManager;
	private TextureManager textureManager;
	private FontManager fontManager;
	private static GameAPI instance;
	public GameAPI() {
		instance = this;
		this.cacheManager = new CacheManager();
		this.textureManager = new TextureManager();
		this.fontManager = new FontManager();
	}
	public static GameAPI getInstance() {
		return instance;
	}
	public CacheManager getCacheManager() {
		return cacheManager;
	}
	public TextureManager getTextureManager() {
		return textureManager;
	}
	public FontManager getFontManager() {
		return fontManager;
	}
}

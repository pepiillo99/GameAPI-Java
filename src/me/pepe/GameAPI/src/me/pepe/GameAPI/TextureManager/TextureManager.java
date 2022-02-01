package me.pepe.GameAPI.TextureManager;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.pepe.GameAPI.TextureManager.Texture.Texture;
import me.pepe.GameAPI.Utils.LoadAction;

public class TextureManager {
	private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	private ExecutorService loadQueue = Executors.newSingleThreadExecutor();

	public Texture getTexture(String textureName) {
		return textures.get(textureName);
	}
	public void registerTexture(Texture texture) {
		textures.put(texture.getName(), texture);
	}
	public boolean hasTexture(String textureName) {
		return textures.containsKey(textureName);
	}
	public void load(Texture texture, LoadAction loadAction) {
		loadQueue.submit(new Runnable() {
			@Override
			public void run() {
				loadAction.execute();
			}			
		});
	}
}

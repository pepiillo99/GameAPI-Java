package me.pepe.GameAPI.TextureManager.Texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import me.pepe.GameAPI.TextureManager.TextureDistance;
import me.pepe.GameAPI.TextureManager.TextureManager;
import me.pepe.GameAPI.Utils.Callback;
import me.pepe.GameAPI.Utils.LoadAction;

public abstract class Texture {
	private String name;
	private TextureDistance actual = null;
	private HashMap<TextureDistance, String> path = new HashMap<TextureDistance, String>();
	private HashMap<TextureDistance, BufferedImage> dist = new HashMap<TextureDistance, BufferedImage>();
	private HashMap<String, TextureChanger> textureChageds = new HashMap<String, TextureChanger>();
	public Texture(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public TextureDistance getActualTextureDistance() {
		return actual;
	}
	public abstract void onChangeTextureDistance();
	public void changeActualTextureDistance(TextureDistance actual) {
		this.actual = actual;
		onChangeTextureDistance();
	}
	public TextureDistance getMaxTextureDistance() {
		for (int i = TextureDistance.HIGHT.getID(); i >= TextureDistance.LOD.getID(); i--) {
			System.out.println(i);
			TextureDistance td = TextureDistance.getByID(i);
			if (hasTextureDistance(td)) {
				return td;
			}
		}
		return null;
	}
	public boolean registerImage(TextureDistance distance, String paths) {
		try {
			long startLoad = System.currentTimeMillis();
			BufferedImage image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(paths));
			long finishLoad = System.currentTimeMillis();
			if (image != null) {
				path.put(distance, paths);
				System.out.println("Imagen de textura " + name + " cargada en " + (finishLoad - startLoad) + "ms");
				return true;
			} else {
				System.out.println("Error al coger " + paths);
			}
		} catch (IllegalArgumentException | IOException e) {
			System.out.println("Error al coger " + paths);
			//e.printStackTrace();
		}	
		return false;
	}
	public boolean hasTextureDistance(TextureDistance distance) {
		return path.containsKey(distance);
	}
	public boolean isTextureDistanceLoaded(TextureDistance distance) {
		return dist.containsKey(distance);
	}
	public BufferedImage getTexture(TextureDistance distance) {
		if (isTextureDistanceLoaded(distance)) {
			return dist.get(distance);
		}
		return null;
	}
	public abstract void onLoad(TextureDistance distance);
	public void loadDistance(TextureManager textureManager, TextureDistance distance, Callback<Boolean> callback) {
		if (hasTextureDistance(distance)) {
			if (!isTextureDistanceLoaded(distance)) {
				textureManager.load(this, new LoadAction() {
					@Override
					public boolean execute() {
						try {
							BufferedImage image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path.get(distance)));
							dist.put(distance, image);
							callback.done(true, null);
							onLoad(distance);
							System.out.println("textura " + name + " cargada");
							return true;
						} catch (IOException e) {
							callback.done(false, e);
						}
						callback.done(false, null);
						return false;
					}				
				});
			} else {
				callback.done(true, null);
			}
		} else {
			System.out.println("Distancia " + distance.name() + " no registrada");
		}
	}
	public void unloadDistance(TextureDistance distance) {
		dist.put(distance, null);
		onUnload(distance);
	}
	public abstract void onUnload(TextureDistance distance);
}

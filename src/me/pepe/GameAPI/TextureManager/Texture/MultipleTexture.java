package me.pepe.GameAPI.TextureManager.Texture;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Set;

import me.pepe.GameAPI.GameAPI;
import me.pepe.GameAPI.TextureManager.TextureDistance;
import me.pepe.GameAPI.Utils.SubTextureCoords;
import pepiillo99.mc.minesound.net.DatabaseAPI.Utils.Callback;

public class MultipleTexture extends Texture {
	private HashMap<SubTextureCoords, Boolean> partOfTextureLoadeds = new HashMap<SubTextureCoords, Boolean>();
	private HashMap<SubTextureCoords, BufferedImage> partOfTexture = new HashMap<SubTextureCoords, BufferedImage>();
	public MultipleTexture(String name) {
		super(name);
	}
	public boolean hasSubTextureCoords(String name) {
		return getSubTextureCoords(name) != null;
	}
	public boolean hasSubTextureCoords(SubTextureCoords stc) {
		return partOfTextureLoadeds.containsKey(stc);
	}
	public SubTextureCoords getSubTextureCoords(String name) {
		for (SubTextureCoords stc : partOfTextureLoadeds.keySet()) {
			if (stc.getName().equals(name)) {
				return stc;
			}
		}
		return null;
	}
	public Set<SubTextureCoords> getSubTextures() {
		return partOfTextureLoadeds.keySet();
	}
	public boolean isSubTextureCoordsLoaded(String name) {
		return isSubTextureCoordsLoaded(getSubTextureCoords(name));
	}
	public boolean isSubTextureCoordsLoaded(SubTextureCoords stc) {
		return stc != null && partOfTextureLoadeds.get(stc) && partOfTexture.get(stc) != null;
	}
	public BufferedImage getPartOfTexture(SubTextureCoords stc) {
		if (isSubTextureCoordsLoaded(stc)) {
			return partOfTexture.get(stc);
		}
		return null;
	}
	public void register(SubTextureCoords stc) {
		partOfTextureLoadeds.put(stc, false);
	}
	public void load(SubTextureCoords stc) {
		System.out.println("chckando");
		TextureDistance td = getMaxTextureDistance();
		System.out.println("test: " + td.name());
		if (isTextureDistanceLoaded(td)) {
			System.out.println("ya esta cargada la textura, recortando...");
			partOfTextureLoadeds.put(stc, true);
			partOfTexture.put(stc, getTexture(td).getSubimage(stc.getX(), stc.getY(), stc.getCutX(), stc.getCutY()));
			System.out.println(stc.getName() + " cargada");
		} else {
			System.out.println("cargando imagen y recortando");
			loadDistance(GameAPI.getInstance().getTextureManager(), td, new Callback<Boolean>() {
				@Override
				public void done(Boolean result, Exception exception) {
					if (result) {
						partOfTextureLoadeds.put(stc, true);
						partOfTexture.put(stc, getTexture(td).getSubimage(stc.getX(), stc.getY(), stc.getCutX(), stc.getCutY()));
						System.out.println(stc.getName() + " cargada desde 0");
					} else {
						partOfTextureLoadeds.put(stc, false);
						partOfTexture.remove(stc);
						exception.printStackTrace();
					}
				}			
			});
		}
	}
	protected void reload(SubTextureCoords stc) {
		load(stc);
	}
	public void unload(SubTextureCoords stc) {
		if (isSubTextureCoordsLoaded(stc)) {
			partOfTextureLoadeds.put(stc, false);
			partOfTexture.remove(stc);
		}
	}
	@Override
	public void onChangeTextureDistance() {}
	@Override
	public void onLoad(TextureDistance distance) {
		System.out.println("multiple texture " + getName() + " loaded");
		for (SubTextureCoords stc : getSubTextures()) {
			System.out.println(stc.getName() + " loading");
			load(stc);
			System.out.println(stc.getName() + " loaded");
		}
	}
	@Override
	public void onUnload(TextureDistance distance) {}
}

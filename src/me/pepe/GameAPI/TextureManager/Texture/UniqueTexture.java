package me.pepe.GameAPI.TextureManager.Texture;

import me.pepe.GameAPI.TextureManager.TextureDistance;

public class UniqueTexture extends Texture {
	
	public UniqueTexture(String name) {
		super(name);
	}
	@Override
	public void onChangeTextureDistance() {}
	@Override
	public void onLoad(TextureDistance distance) {}
	@Override
	public void onUnload(TextureDistance distance) {}
}

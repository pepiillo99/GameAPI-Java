package me.pepe.GameAPI.TextureManager;

public enum TextureDistance {
	LOD(1),
	LOW(2),
	MEDIUM(3),
	HIGHT(4);
	
	private int id;
	
	TextureDistance(int id) {
		this.id = id;
	}
	public int getID() {
		return id;
	}
	public static TextureDistance getByID(int id) {
		for (TextureDistance td : values()) {
			if (td.getID() == id) {
				return td;
			}
		}
		return null;
	}
	public TextureDistance getNext() {
		return getByID(id+1);
	}
	public TextureDistance getPrevious() {
		return getByID(id-1);
	}
}

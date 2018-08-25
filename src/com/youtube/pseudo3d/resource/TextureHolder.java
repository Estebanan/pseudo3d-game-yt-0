package com.youtube.pseudo3d.resource;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.youtube.pseudo3d.util.ImageHandler;

public class TextureHolder {

	public static enum ID{
		TEST_MAP,
		BARREL,
		BLUESTONE,
		COBBLESTONE,
		EMBLEM,
		GREENLIGHT,
		BRICK_0,
		BRICK_1,
		MOSSYSTONE,
		PILLAR,
		PURPLESTONE,
		WOOD
	}

	private HashMap<ID, BufferedImage> textureMap;
	
	public TextureHolder() {
		textureMap = new HashMap<ID, BufferedImage>();
	}
	
	public void load(ID id, String src) {
		BufferedImage image = ImageHandler.readImage(src);
		textureMap.put(id, image);
	}
	
	public BufferedImage get(ID id) {
		return textureMap.get(id);
	}
}

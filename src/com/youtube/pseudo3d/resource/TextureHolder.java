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
		BRICK_0,
		BRICK_1,
		MOSSYSTONE,
		PILLAR,
		PURPLESTONE,
		WOOD,
		SPIDER,
		PLAYER_LATTERN,
		PLAYER_SWORD,
		PLAYER_AXE,
		PLAYER_WAND
	}

	private static HashMap<ID, BufferedImage> textureMap = new HashMap<ID, BufferedImage>();
	
	public static void load(ID id, String src) {
		BufferedImage image = ImageHandler.readImage(src);
		textureMap.put(id, image);
	}
	
	public static BufferedImage get(ID id) {
		return textureMap.get(id);
	}
}

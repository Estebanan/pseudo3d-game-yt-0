package com.youtube.pseudo3d.resource;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.youtube.pseudo3d.util.ImageHandler;

public class TextureHolder {

	public static enum ID{
		TEST_MAP,
		BLUESTONE,
		COBBLESTONE,
		EMBLEM,
		BRICK_0,
		BRICK_1,
		MOSSYSTONE,
		PURPLESTONE,
		WOOD,
		
		SPIDER,
		PILLAR,
		BARREL,
		LATTERN_COLLECT,
		SWORD_COLLECT,
		AXE_COLLECT,
		WAND_COLLECT,
		
		PLAYER_LATTERN,
		PLAYER_SWORD,
		PLAYER_AXE,
		PLAYER_WAND,
		PLAYER_WAND_ATTACK,
		
		GUI_EMPTY_SLOT,
		GUI_SELECTED_SLOT,
		GUI_HEALTH_BAR,
		GUI_LATTERN_ICON,
		GUI_SWORD_ICON,
		GUI_AXE_ICON,
		GUI_WAND_ICON
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

package com.youtube.pseudo3d.resource;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.youtube.pseudo3d.util.ImageHandler;

public class TextureHolder {

	public static enum ID{
		//MAPS
		TEST_MAP,
		
		//WALLS
		BLUESTONE,
		COBBLESTONE,
		EMBLEM,
		BRICK_0,
		BRICK_1,
		MOSSYSTONE,
		PURPLESTONE,
		WOOD,
		
		//STILL SPRITES
		SPIDER,
		PILLAR,
		BARREL,
		
		//MISSLES
		SWORD_MISSLE,
		AXE_MISSLE,
		WAND_MISSLE,
		
		//COLLECT
		LATTERN_COLLECT,
		SWORD_COLLECT,
		AXE_COLLECT,
		WAND_COLLECT,
		
		//ENEMIES
		ENEMY_BAT,
		ENEMY_BAT_DYING,
		ENEMY_BAT_CORPSE,
		
		ENEMY_RAT,
		ENEMY_RAT_MOVING,
		ENEMY_RAT_DYING,
		ENEMY_RAT_CORPSE,

		//PLAYER
		PLAYER_LATTERN,
		PLAYER_SWORD,
		PLAYER_SWORD_ATTACK,
		PLAYER_AXE,
		PLAYER_AXE_ATTACK,
		PLAYER_WAND,
		PLAYER_WAND_ATTACK,
		
		//GUI
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

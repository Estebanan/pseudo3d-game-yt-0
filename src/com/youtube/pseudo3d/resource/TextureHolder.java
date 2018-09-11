package com.youtube.pseudo3d.resource;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.youtube.pseudo3d.util.ImageHandler;

public class TextureHolder {

	public static enum ID{
		//MAPS
		TEST_MAP,
		LEVEL_0,
		LEVEL_1,
		
		//WALLS
		SKY,
		BLUESTONE,
		COBBLESTONE,
		EMBLEM,
		BRICK_0,
		BRICK_1,
		MOSSYSTONE,
		PURPLESTONE,
		WOOD,
		BUSH,
		GRASS,
		ROTTEN_WOOD,
		
		//STILL SPRITES
		SPIDER,
		PILLAR,
		BARREL,
		BLUE_FLOWER,
		GRASS_0,
		GRASS_BUSH,
		BIG_BUSH,
		GRASS_STALKS,
		ROSE,
		YELLOW_FLOWER,
		PORTAL,

		//MISSLES
		PUNCH_MISSLE,
		SWORD_MISSLE,
		AXE_MISSLE,
		WAND_MISSLE,
		
		ENEMY_GREEN_MISSLE,
		
		//COLLECT
		LATTERN_COLLECT,
		SWORD_COLLECT,
		AXE_COLLECT,
		WAND_COLLECT,
		HEALTH_POTION_COLLECT,
		
		//ENEMIES
		ENEMY_BAT,
		ENEMY_BAT_DYING,
		ENEMY_BAT_CORPSE,
		
		ENEMY_RAT,
		ENEMY_RAT_MOVING,
		ENEMY_RAT_DYING,
		ENEMY_RAT_CORPSE,
		
		ENEMY_ZOMBIE,
		ENEMY_ZOMBIE_MOVING,
		ENEMY_ZOMBIE_DYING,
		ENEMY_ZOMBIE_CORPSE,
		
		ENEMY_MAGE,
		ENEMY_MAGE_ATTACK,
		ENEMY_MAGE_DYING,
		ENEMY_MAGE_CORPSE,

		//PLAYER
		PLAYER_HAND_ATTACK,
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

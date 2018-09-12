package com.youtube.pseudo3d.resource;

import com.youtube.pseudo3d.resource.TextureHolder.ID;

public class TextureLoader {

	public TextureLoader() {
		//MAPS
		TextureHolder.load(ID.TEST_MAP, 		"/maps/test_map.png");
		TextureHolder.load(ID.LEVEL_0, 			"/maps/level_0.png");
		TextureHolder.load(ID.LEVEL_1, 			"/maps/level_1.png");
		TextureHolder.load(ID.LEVEL_2, 			"/maps/level_2.png");
		
		//WALLS
		TextureHolder.load(ID.SKY, 				"/tiles/sky.png");
		TextureHolder.load(ID.EMBLEM, 			"/tiles/emblem.png");
		TextureHolder.load(ID.BRICK_0, 			"/tiles/brick_0.png");
		TextureHolder.load(ID.BRICK_1, 			"/tiles/brick_1.png");
		TextureHolder.load(ID.PURPLESTONE, 		"/tiles/purplestone.png");
		TextureHolder.load(ID.BLUESTONE, 		"/tiles/bluestone.png");
		TextureHolder.load(ID.MOSSYSTONE, 		"/tiles/mossystone.png");
		TextureHolder.load(ID.WOOD, 			"/tiles/wood.png");
		TextureHolder.load(ID.COBBLESTONE, 		"/tiles/cobblestone.png");
		TextureHolder.load(ID.BUSH, 			"/tiles/bush.png");
		TextureHolder.load(ID.GRASS, 			"/tiles/grass.png");
		TextureHolder.load(ID.ROTTEN_WOOD, 		"/tiles/rotten-wood.png");
		TextureHolder.load(ID.RED_DOOR, 		"/tiles/red-door.png");
		TextureHolder.load(ID.BLUE_DOOR, 		"/tiles/blue-door.png");
		
		//STILL SPRITES
		TextureHolder.load(ID.BARREL, 			"/sprites/barrel.png");
		TextureHolder.load(ID.PILLAR, 			"/sprites/pillar.png");
		TextureHolder.load(ID.SPIDER, 			"/sprites/spider.png");
		TextureHolder.load(ID.BLUE_FLOWER, 		"/sprites/blue-flower.png");
		TextureHolder.load(ID.GRASS_0, 			"/sprites/grass_0.png");
		TextureHolder.load(ID.GRASS_BUSH, 		"/sprites/grass-bush.png");
		TextureHolder.load(ID.BIG_BUSH, 		"/sprites/big-bush.png");
		TextureHolder.load(ID.GRASS_STALKS, 	"/sprites/grass-stalks.png");
		TextureHolder.load(ID.ROSE, 			"/sprites/rose.png");
		TextureHolder.load(ID.YELLOW_FLOWER, 	"/sprites/yellow-flower.png");
		TextureHolder.load(ID.PORTAL, 			"/sprites/portal.png");
		TextureHolder.load(ID.THANOS_CAR, 		"/sprites/thanos-car.png");

		//LEVERS
		TextureHolder.load(ID.RED_LEVER_OFF, 	"/sprites/red-lever-off.png");
		TextureHolder.load(ID.RED_LEVER_ON, 	"/sprites/red-lever-on.png");
		TextureHolder.load(ID.BLUE_LEVER_OFF, 	"/sprites/blue-lever-off.png");
		TextureHolder.load(ID.BLUE_LEVER_ON, 	"/sprites/blue-lever-on.png");

		//MISSLES
		TextureHolder.load(ID.PUNCH_MISSLE,  	"/sprites/missle/punch-missle.png");
		TextureHolder.load(ID.SWORD_MISSLE,  	"/sprites/missle/sword-missle.png");
		TextureHolder.load(ID.AXE_MISSLE,       "/sprites/missle/axe-missle.png");
		TextureHolder.load(ID.WAND_MISSLE,  	"/sprites/missle/wand-missle.png");
		
		TextureHolder.load(ID.ENEMY_BLUE_MISSLE,"/sprites/missle/enemy-blue-missle.png");
		TextureHolder.load(ID.ENEMY_ORANGE_MISSLE,"/sprites/missle/enemy-orange-missle.png");
		TextureHolder.load(ID.ENEMY_RED_MISSLE,"/sprites/missle/enemy-red-missle.png");
		TextureHolder.load(ID.ENEMY_GREEN_MISSLE,"/sprites/missle/enemy-green-missle.png");
		TextureHolder.load(ID.ENEMY_PURPLE_MISSLE,"/sprites/missle/enemy-purple-missle.png");
		TextureHolder.load(ID.ENEMY_YELLOW_MISSLE,"/sprites/missle/enemy-yellow-missle.png");
		
		//COLLECT
		TextureHolder.load(ID.LATTERN_COLLECT,  "/sprites/collect/lattern_collect.png");
		TextureHolder.load(ID.SWORD_COLLECT,  	"/sprites/collect/sword_collect.png");
		TextureHolder.load(ID.AXE_COLLECT,  	"/sprites/collect/axe_collect.png");
		TextureHolder.load(ID.WAND_COLLECT,  	"/sprites/collect/wand_collect.png");
		TextureHolder.load(ID.HEALTH_POTION_COLLECT,"/sprites/collect/health-potion.png");
		
		//ENEMIES
		TextureHolder.load(ID.ENEMY_BAT,		"/sprites/enemy/bat/bat.png");
		TextureHolder.load(ID.ENEMY_BAT_DYING,	"/sprites/enemy/bat/bat-dying.png");
		TextureHolder.load(ID.ENEMY_BAT_CORPSE,	"/sprites/enemy/bat/bat-corpse.png");
		
		TextureHolder.load(ID.ENEMY_RAT,		"/sprites/enemy/rat/rat.png");
		TextureHolder.load(ID.ENEMY_RAT_MOVING,	"/sprites/enemy/rat/rat-moving.png");
		TextureHolder.load(ID.ENEMY_RAT_DYING,	"/sprites/enemy/rat/rat-dying.png");
		TextureHolder.load(ID.ENEMY_RAT_CORPSE,	"/sprites/enemy/rat/rat-corpse.png");

		TextureHolder.load(ID.ENEMY_ZOMBIE,		"/sprites/enemy/zombie/zombie.png");
		TextureHolder.load(ID.ENEMY_ZOMBIE_MOVING,"/sprites/enemy/zombie/zombie-moving.png");
		TextureHolder.load(ID.ENEMY_ZOMBIE_DYING,"/sprites/enemy/zombie/zombie-dying.png");
		TextureHolder.load(ID.ENEMY_ZOMBIE_CORPSE,"/sprites/enemy/zombie/zombie-corpse.png");
		
		TextureHolder.load(ID.ENEMY_MAGE,		"/sprites/enemy/mage/mage.png");
		TextureHolder.load(ID.ENEMY_MAGE_ATTACK,"/sprites/enemy/mage/mage-attack.png");
		TextureHolder.load(ID.ENEMY_MAGE_DYING,	"/sprites/enemy/mage/mage-death.png");
		TextureHolder.load(ID.ENEMY_MAGE_CORPSE,"/sprites/enemy/mage/mage-corpse.png");
		
		TextureHolder.load(ID.ENEMY_THANOS,		"/sprites/enemy/thanos/thanos.png");
		TextureHolder.load(ID.ENEMY_THANOS_MOVING,"/sprites/enemy/thanos/thanos-moving.png");
		TextureHolder.load(ID.ENEMY_THANOS_ATTACK,"/sprites/enemy/thanos/thanos-shooting.png");
		TextureHolder.load(ID.ENEMY_THANOS_DYING,"/sprites/enemy/thanos/thanos-dying.png");

		
		//PLAYER
		TextureHolder.load(ID.PLAYER_HAND_ATTACK,"/player/hand/hand-fight.png");
		TextureHolder.load(ID.PLAYER_LATTERN,	"/player/lattern/lattern_hand.png");
		TextureHolder.load(ID.PLAYER_SWORD,		"/player/sword/sword_hand.png");
		TextureHolder.load(ID.PLAYER_SWORD_ATTACK,"/player/sword/sword_attack.png");
		TextureHolder.load(ID.PLAYER_AXE,		"/player/axe/axe_hand.png");
		TextureHolder.load(ID.PLAYER_AXE_ATTACK,"/player/axe/axe_attack.png");
		TextureHolder.load(ID.PLAYER_WAND,		"/player/wand/wand_hand.png");
		TextureHolder.load(ID.PLAYER_WAND_ATTACK,"/player/wand/wand_attack.png");
		
		//GUI
		TextureHolder.load(ID.GUI_EMPTY_SLOT,	"/gui/empty-slot.png");
		TextureHolder.load(ID.GUI_SELECTED_SLOT,"/gui/selected-slot.png");
		TextureHolder.load(ID.GUI_HEALTH_BAR,   "/gui/health-bar.png");
		TextureHolder.load(ID.GUI_LATTERN_ICON, "/gui/lattern-icon.png");
		TextureHolder.load(ID.GUI_SWORD_ICON, 	"/gui/sword-icon.png");
		TextureHolder.load(ID.GUI_AXE_ICON, 	"/gui/axe-icon.png");
		TextureHolder.load(ID.GUI_WAND_ICON, 	"/gui/wand-icon.png");
	}
}

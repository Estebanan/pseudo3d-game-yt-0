package com.youtube.pseudo3d.engine.objects.enemy;

import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class MageCorpse extends GameObject{

	public MageCorpse(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		texture = TextureHolder.get(ID.ENEMY_MAGE_CORPSE);
	}

	@Override
	public void update(double elapsed) {}
}

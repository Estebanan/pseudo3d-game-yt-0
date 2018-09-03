package com.youtube.pseudo3d.engine.objects.enemy;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class BatCorpse extends GameObject{

	public BatCorpse(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		texture = TextureHolder.get(ID.ENEMY_BAT_CORPSE);
	}

	@Override
	public void update(double elapsed) {		
	}

}

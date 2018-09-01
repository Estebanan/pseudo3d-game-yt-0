package com.youtube.pseudo3d.engine.objects;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class LatternCollect extends GameObject{

	public LatternCollect(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		texture = ID.LATTERN_COLLECT;
	}
}

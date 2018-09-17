package com.youtube.pseudo3d.engine.objects.collect;

import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class LatternCollect extends GameObject{

	public LatternCollect(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		texture =  TextureHolder.get(ID.LATTERN_COLLECT);
	}

	@Override
	public void update(double elapsed) {
		
	}
}

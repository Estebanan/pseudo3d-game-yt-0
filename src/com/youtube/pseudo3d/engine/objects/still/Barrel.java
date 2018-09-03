package com.youtube.pseudo3d.engine.objects.still;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class Barrel extends GameObject{

	public Barrel(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		texture =  TextureHolder.get(ID.BARREL);
	}

	@Override
	public void update(double elapsed) {
		
	}

}

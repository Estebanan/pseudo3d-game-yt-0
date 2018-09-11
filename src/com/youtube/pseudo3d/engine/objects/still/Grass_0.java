package com.youtube.pseudo3d.engine.objects.still;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class Grass_0 extends GameObject{

	public Grass_0(GameLogic raycaster, Vector2d position) {

		super(raycaster, position);
		
		texture =  TextureHolder.get(ID.GRASS_0);
	}

	@Override
	public void update(double elapsed) {
		
	}

}

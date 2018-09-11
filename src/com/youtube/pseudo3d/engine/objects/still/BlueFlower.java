package com.youtube.pseudo3d.engine.objects.still;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class BlueFlower extends GameObject{

	public BlueFlower(GameLogic raycaster, Vector2d position) {

		super(raycaster, position);
		
		texture =  TextureHolder.get(ID.BLUE_FLOWER);
	}

	@Override
	public void update(double elapsed) {
		
	}

}

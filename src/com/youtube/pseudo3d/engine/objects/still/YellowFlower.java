package com.youtube.pseudo3d.engine.objects.still;

import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class YellowFlower extends GameObject{

	public YellowFlower(GameLogic raycaster, Vector2d position) {

		super(raycaster, position);
		
		texture =  TextureHolder.get(ID.YELLOW_FLOWER);
	}

	@Override
	public void update(double elapsed) {
		
	}

}
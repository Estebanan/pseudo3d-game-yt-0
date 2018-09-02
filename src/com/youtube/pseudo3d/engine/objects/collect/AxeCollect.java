package com.youtube.pseudo3d.engine.objects.collect;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class AxeCollect extends GameObject{

	public AxeCollect(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);

		texture = ID.AXE_COLLECT;
	}

	@Override
	public void update(double elapsed) {
		
	}

}

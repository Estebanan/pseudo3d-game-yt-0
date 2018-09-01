package com.youtube.pseudo3d.engine.objects;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class AxeCollect extends GameObject{

	public AxeCollect(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);

		texture = ID.AXE_COLLECT;
	}

}

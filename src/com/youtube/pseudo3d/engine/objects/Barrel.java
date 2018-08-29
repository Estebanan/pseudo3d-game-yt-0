package com.youtube.pseudo3d.engine.objects;

import com.youtube.pseudo3d.engine.Raycaster;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class Barrel extends GameObject{

	public Barrel(Raycaster raycaster, Vector2d position) {
		super(raycaster, position);
		
		texture = ID.BARREL;
	}

}

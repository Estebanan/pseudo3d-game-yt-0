package com.youtube.pseudo3d.engine.objects.missle;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.MovingObject;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class AxeMissle extends MovingObject implements Missle{

	public static int DAMAGE = 40;
	
	public AxeMissle(GameLogic raycaster, Vector2d position, Vector2d direction, double actualMovementSpeed) {
		super(raycaster, position, direction, actualMovementSpeed);
		texture =  TextureHolder.get(ID.AXE_MISSLE);
	}
	
	@Override
	public void update(double elapsed) {
		super.update(elapsed);
	}

}

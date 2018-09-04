package com.youtube.pseudo3d.engine.objects.missle;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.ConstantlyMovingObject;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class SwordMissle extends ConstantlyMovingObject implements Missle{

	public static int DAMAGE = 50;
	
	public SwordMissle(GameLogic raycaster, Vector2d position, Vector2d direction, double actualMovementSpeed) {
		super(raycaster, position, direction, actualMovementSpeed);
		texture =  TextureHolder.get(ID.SWORD_MISSLE);
	}
	
	@Override
	public void update(double elapsed) {
		super.update(elapsed);
	}

}

package com.youtube.pseudo3d.engine.objects.missle;

import com.youtube.pseudo3d.engine.objects.ConstantlyMovingObject;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class ThanosCar extends ConstantlyMovingObject implements EnemyMissle{

	public static int DAMAGE = 5;
	
	public ThanosCar(GameLogic raycaster, Vector2d position, Vector2d direction, double actualMovementSpeed) {
		super(raycaster, position, direction, actualMovementSpeed);
		
		texture = TextureHolder.get(ID.THANOS_CAR);
	}

	@Override
	public void update(double elapsed) {
		super.update(elapsed);
	}

}

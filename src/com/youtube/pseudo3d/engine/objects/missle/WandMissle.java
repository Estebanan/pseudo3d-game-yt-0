package com.youtube.pseudo3d.engine.objects.missle;

import com.youtube.pseudo3d.engine.objects.ConstantlyMovingObject;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.AudioPaths;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.AudioHandler;
import com.youtube.pseudo3d.util.Vector2d;

public class WandMissle extends ConstantlyMovingObject implements Missle{
	
	public static int DAMAGE = 40;

	public WandMissle(GameLogic raycaster, Vector2d position, Vector2d direction, double actualMovementSpeed) {
		super(raycaster, position, direction, actualMovementSpeed);
		texture =  TextureHolder.get(ID.WAND_MISSLE);
		AudioHandler.playAudio(AudioPaths.WAND_ZIP).start();

	}
	
	@Override
	public void update(double elapsed) {
		super.update(elapsed);
	}

}

package com.youtube.pseudo3d.engine.objects.missle;

import com.youtube.pseudo3d.engine.objects.ConstantlyMovingObject;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.AudioPaths;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.AudioHandler;
import com.youtube.pseudo3d.util.Vector2d;

public class BlueEnemyMissle extends ConstantlyMovingObject implements EnemyMissle{

	public static int DAMAGE = 3;
	
	private double mx, my;
	
	public BlueEnemyMissle(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		double moveSpeed = 5D * 1e-2;
		
		mx = (raycaster.getPlayer().getPosition().x - position.x) * moveSpeed;
		my = (raycaster.getPlayer().getPosition().y - position.y) * moveSpeed;

		texture = TextureHolder.get(ID.ENEMY_BLUE_MISSLE);
		AudioHandler.playAudio(AudioPaths.WAND_ZIP).start();

	}
	
	@Override
	public void update(double elapsed) {
		moveX(mx);
		moveY(my);
	}

}
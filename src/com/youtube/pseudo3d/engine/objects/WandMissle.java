package com.youtube.pseudo3d.engine.objects;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class WandMissle extends GameObject{

	private Vector2d direction;
	
	private double actualMovementSpeed = 100.0;
	
	public WandMissle(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		texture = ID.WAND_MISSLE;
	}
	
	public WandMissle(GameLogic raycaster, Vector2d position, Vector2d direction) {
		super(raycaster, position);
		this.direction = direction;
		
		texture = ID.WAND_MISSLE;
	}
	
	@Override
	public void update(double elapsed) {
		double moveSpeed = actualMovementSpeed * elapsed;

		move(moveSpeed);
	}
	
	private void move(double delta) {
		if (TextureHolder.get(ID.TEST_MAP).getRGB((int) (position.x + direction.y * delta),
				(int) (position.y)) == 0xff000000)
			position.x += direction.y * delta;
		if (TextureHolder.get(ID.TEST_MAP).getRGB((int) (position.x),
				(int) (position.y + direction.x * delta)) == 0xff000000)
			position.y += direction.x * delta;
	}
}

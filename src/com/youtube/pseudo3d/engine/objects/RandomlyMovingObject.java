package com.youtube.pseudo3d.engine.objects;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;

public abstract class RandomlyMovingObject extends GameObject{

	protected Vector2d direction;
	protected double moveSpeed = .0;	
	
	public RandomlyMovingObject(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		direction = new Vector2d(.0, .0);
	}
	
	@Override
	public void update(double elapsed) {
		if(raycaster.time % 10 == 0) {
			direction.x = MathUtil.randomWithRange(-2D, 2D);
			direction.y = MathUtil.randomWithRange(-2D, 2D);
			moveSpeed = MathUtil.randomWithRange(-10D, 10D) * elapsed;
			
			moving = true;
		}
		
		move(moveSpeed);
	}

	public void move(double delta) {
		// ONLY MOVE IF THE CURRENT TILE IS 0XFF000000 - BLACK
		if (raycaster.getCurrentLevel().getMap().getRGB((int) (position.x + direction.y * delta),
				(int) (position.y)) == 0xff000000)
			position.x += direction.y * delta;
		if (raycaster.getCurrentLevel().getMap().getRGB((int) (position.x),
				(int) (position.y + direction.x * delta)) == 0xff000000)
			position.y += direction.x * delta;
	}

}

package com.youtube.pseudo3d.engine.objects;

import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;

public abstract class FollowingObject extends GameObject{

	protected double moveSpeed = .0;
	protected double reactDistance;
	
	public FollowingObject(GameLogic raycaster, Vector2d position, double reactDistance) {
		super(raycaster, position);	
		this.reactDistance = reactDistance;
	}
	
	@Override
	public void update(double elapsed) {
		if(MathUtil.pythagoreanDistance(raycaster.getPlayer().getPosition(), position) < reactDistance
				&& MathUtil.pythagoreanDistance(raycaster.getPlayer().getPosition(), position) > 1) {
			moving = true;
			
			moveSpeed = 5D * elapsed;
			
			moveX((raycaster.getPlayer().getPosition().x - position.x) * moveSpeed);
			moveY((raycaster.getPlayer().getPosition().y - position.y) * moveSpeed);

		} else
			moving = false;		
	}
	
	public void moveX(double delta) {
		// ONLY MOVE IF THE CURRENT TILE IS 0XFF000000 - BLACK
		if (raycaster.getCurrentLevel().getMap().getRGB((int) (position.x + delta),
				(int) (position.y)) == 0xff000000)
			position.x += delta;
	}
	
	public void moveY(double delta) {
		// ONLY MOVE IF THE CURRENT TILE IS 0XFF000000 - BLACK
		if (raycaster.getCurrentLevel().getMap().getRGB((int) (position.x),
				(int) (position.y + delta)) == 0xff000000)
			position.y += delta;
	}
}

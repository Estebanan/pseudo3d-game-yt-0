package com.youtube.pseudo3d.engine.objects;

import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.util.Vector2d;

public abstract class ConstantlyMovingObject extends GameObject{

	protected Vector2d direction;
	
	protected double actualMovementSpeed;
	
	public ConstantlyMovingObject(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
	}
	
	public ConstantlyMovingObject(GameLogic raycaster, Vector2d position, Vector2d direction) {
		super(raycaster, position);
		this.direction = direction;
	}
	
	public ConstantlyMovingObject(GameLogic raycaster, Vector2d position, Vector2d direction, double actualMovementSpeed) {
		super(raycaster, position);
		this.direction = direction;
		this.actualMovementSpeed = actualMovementSpeed;
	}

	@Override
	public void update(double elapsed) {
		double moveSpeed = actualMovementSpeed * elapsed;
		move(moveSpeed);
	}
	
	protected void move(double delta) {
		position.x += direction.y * delta;
		position.y += direction.x * delta;
	}
	
	protected void moveX(double delta) {
		position.x += delta;
	}
	
	protected void moveY(double delta) {
		position.y += delta;
	}

}

package com.youtube.pseudo3d.engine.objects;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.util.Vector2d;

public abstract class MovableObject extends GameObject{

	protected Vector2d direction;
	
	protected double actualMovementSpeed;
	
	public MovableObject(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
	}
	
	public MovableObject(GameLogic raycaster, Vector2d position, Vector2d direction) {
		super(raycaster, position);
		this.direction = direction;
	}
	
	public MovableObject(GameLogic raycaster, Vector2d position, Vector2d direction, double actualMovementSpeed) {
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

}

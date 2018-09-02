package com.youtube.pseudo3d.engine.objects;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public abstract class GameObject {

	protected Vector2d position;
	protected GameLogic raycaster;
	
	protected ID texture;
	
	public GameObject(GameLogic raycaster, Vector2d position) {
		this.raycaster = raycaster;
		this.position = position;
	}
	
	public abstract void update(double elapsed);
	
	public Vector2d getPosition() {
		return position;
	}
	
	public ID getTexture() {
		return texture;
	}
}

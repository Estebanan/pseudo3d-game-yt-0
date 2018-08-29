package com.youtube.pseudo3d.engine.objects;

import com.youtube.pseudo3d.engine.Raycaster;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public abstract class GameObject {

	protected Vector2d position;
	protected Raycaster raycaster;
	
	protected ID texture;
	
	public GameObject(Raycaster raycaster, Vector2d position) {
		this.raycaster = raycaster;
		this.position = position;
	}
	
	public Vector2d getPosition() {
		return position;
	}
	
	public ID getTexture() {
		return texture;
	}
}

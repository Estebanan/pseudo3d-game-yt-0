package com.youtube.pseudo3d.engine;

import com.youtube.pseudo3d.util.Vector2d;

public class Camera {

	private Vector2d plane;
	
	public Camera() {
		plane = new Vector2d(0, 0.8);

	}

	public Vector2d getPlane() {
		return plane;
	}

	public void setPlane(Vector2d plane) {
		this.plane = plane;
	}
	
	
}

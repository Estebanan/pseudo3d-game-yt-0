package com.youtube.pseudo3d.util;

public class Vector2d {

	public double x, y;
	
	public Vector2d() {}
	
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object object) {
		if(!(object instanceof Vector2d))
			return false;

		Vector2d vector = (Vector2d) object;
		if(vector.x == this.x
				&& vector.y == this.y)
			return true;
		
		return false;
	}
}

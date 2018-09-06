package com.youtube.pseudo3d.util;

public class Vector2i {

	public int x, y;
	
	public Vector2i() {}
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object object) {
		if(!(object instanceof Vector2i))
			return false;

		Vector2i vector = (Vector2i) object;
		if(vector.x == this.x
				&& vector.y == this.y)
			return true;
		
		return false;
	}
}

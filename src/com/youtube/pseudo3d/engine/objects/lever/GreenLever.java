package com.youtube.pseudo3d.engine.objects.lever;

import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class GreenLever extends GameObject{

	public boolean on = false;
	
	public GreenLever(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		texture = TextureHolder.get(ID.GREEN_LEVER_OFF);
	}

	@Override
	public void update(double elapsed) {
		if(on)
			texture = TextureHolder.get(ID.GREEN_LEVER_ON);
		else
			texture = TextureHolder.get(ID.GREEN_LEVER_OFF);

	}
}

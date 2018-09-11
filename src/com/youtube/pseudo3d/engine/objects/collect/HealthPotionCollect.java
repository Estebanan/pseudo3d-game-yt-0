package com.youtube.pseudo3d.engine.objects.collect;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class HealthPotionCollect extends GameObject{

	public static int BONUS = 20;
	
	public HealthPotionCollect(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		texture =  TextureHolder.get(ID.HEALTH_POTION_COLLECT);
	}

	@Override
	public void update(double elapsed) {}
}

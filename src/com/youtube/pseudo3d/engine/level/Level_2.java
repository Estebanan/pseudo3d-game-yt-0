package com.youtube.pseudo3d.engine.level;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.collect.HealthPotionCollect;
import com.youtube.pseudo3d.engine.objects.enemy.Thanos;
import com.youtube.pseudo3d.engine.objects.still.Barrel;
import com.youtube.pseudo3d.engine.objects.still.Pillar;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class Level_2 extends Level{

	public Level_2(GameLogic gameLogic) {
		super(gameLogic);
		
		setMap(ID.LEVEL_2);
		setFloor(ID.BRICK_0);
		setCeiling(ID.COBBLESTONE);
		
		setSpawn(new Vector2d(10.5, 11.5));
		
		gameLogic.getPlayer().rotate(Math.PI/2);
		
		initGameObjects();
	}
	
	private void initGameObjects() {
		gameObjects.clear();
		
		gameObjects.add(new Pillar(gameLogic, new Vector2d(14.5, 11.7)));
		gameObjects.add(new Pillar(gameLogic, new Vector2d(14.5, 11.3)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(10, 8.5)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(11, 8.5)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(10, 14.5)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(11, 14.5)));
		
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(11.5, 8.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(9.5, 8.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(9.5, 14.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(11.5, 14.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(6.5, 11.5)));
	
	
		gameObjects.add(new Thanos(gameLogic, new Vector2d(12.5, 4.0)));
	}

}

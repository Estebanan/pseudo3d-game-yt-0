package com.youtube.pseudo3d.engine.level;

import com.youtube.pseudo3d.engine.objects.collect.AxeCollect;
import com.youtube.pseudo3d.engine.objects.collect.LatternCollect;
import com.youtube.pseudo3d.engine.objects.collect.SwordCollect;
import com.youtube.pseudo3d.engine.objects.collect.WandCollect;
import com.youtube.pseudo3d.engine.objects.enemy.Bat;
import com.youtube.pseudo3d.engine.objects.enemy.Mage;
import com.youtube.pseudo3d.engine.objects.enemy.Rat;
import com.youtube.pseudo3d.engine.objects.enemy.Zombie;
import com.youtube.pseudo3d.engine.objects.still.Barrel;
import com.youtube.pseudo3d.engine.objects.still.Pillar;
import com.youtube.pseudo3d.engine.objects.still.Spider;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class TestLevel extends Level{

	public TestLevel(GameLogic gameLogic) {
		super(gameLogic);
		
		setMap(ID.TEST_MAP);
		setFloor(ID.COBBLESTONE);
		setCeiling(ID.WOOD);
		
		setSpawn(new Vector2d(22, 22));
		
		initGameObjects();
	}
	
	private void initGameObjects() {	
		gameObjects.clear();
		
		gameObjects.add(new Barrel(gameLogic, new Vector2d(16.5, 22.5)));
		gameObjects.add(new Spider(gameLogic, new Vector2d(17.5, 21.5)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(17.5, 18.5)));

		gameObjects.add(new Pillar(gameLogic, new Vector2d(22.5, 13.5)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(22.5, 7.5)));
		gameObjects.add(new Pillar(gameLogic, new Vector2d(22.5, 6.5)));

		gameObjects.add(new Spider(gameLogic, new Vector2d(22.5, 2.5)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(22.5, 1.5)));
		gameObjects.add(new Spider(gameLogic, new Vector2d(21.5, 1.5)));
		
		gameObjects.add(new Spider(gameLogic, new Vector2d(15.5, 13.5)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(16.5, 13.5)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(17.5, 15.5)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(19.5, 14.5)));
		
		gameObjects.add(new Barrel(gameLogic, new Vector2d(8.5, 14.5)));
		gameObjects.add(new Spider(gameLogic, new Vector2d(7.5, 15.5)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(2.5, 14.5)));
		gameObjects.add(new Spider(gameLogic, new Vector2d(2.5, 13.5)));
		gameObjects.add(new Pillar(gameLogic, new Vector2d(1.5, 12.5)));
		gameObjects.add(new Spider(gameLogic, new Vector2d(5.5, 22.5)));
		gameObjects.add(new Pillar(gameLogic, new Vector2d(9.5, 22.5)));
		gameObjects.add(new Pillar(gameLogic, new Vector2d(10.5,22.5)));
		gameObjects.add(new Spider(gameLogic, new Vector2d(6.5, 21.5)));
		gameObjects.add(new Barrel(gameLogic, new Vector2d(6.5, 20.5)));
		
		gameObjects.add(new LatternCollect(gameLogic, new Vector2d(18.5, 18.5)));
		gameObjects.add(new SwordCollect(gameLogic, new Vector2d(17.5, 17.5)));
		gameObjects.add(new AxeCollect(gameLogic, new Vector2d(16.5, 16.5)));
		gameObjects.add(new WandCollect(gameLogic, new Vector2d(15.5, 15.5)));
		
		for(int i=0; i<5; i++)
			gameObjects.add(new Bat(gameLogic, new Vector2d(7.0, 12.0)));
			
		gameObjects.add(new Zombie(gameLogic, new Vector2d(7.5, 12.5), 8));
		gameObjects.add(new Zombie(gameLogic, new Vector2d(14.5, 2.5), 10));
		
		gameObjects.add(new Rat(gameLogic, new Vector2d(6.5, 16.5), 5));
		gameObjects.add(new Rat(gameLogic, new Vector2d(20.5, 10.5), 5));
		gameObjects.add(new Rat(gameLogic, new Vector2d(14.5, 19.5), 5));
		
		gameObjects.add(new Mage(gameLogic, new Vector2d(7.5, 16.5)));
		gameObjects.add(new Mage(gameLogic, new Vector2d(17.5, 2.5)));
		gameObjects.add(new Mage(gameLogic, new Vector2d(2.5, 16.5)));

	}
	
}

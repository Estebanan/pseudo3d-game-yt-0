package com.youtube.pseudo3d.engine.level;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.collect.AxeCollect;
import com.youtube.pseudo3d.engine.objects.collect.GoldCollect;
import com.youtube.pseudo3d.engine.objects.collect.HealthPotionCollect;
import com.youtube.pseudo3d.engine.objects.enemy.Bat;
import com.youtube.pseudo3d.engine.objects.enemy.Mage;
import com.youtube.pseudo3d.engine.objects.enemy.Rat;
import com.youtube.pseudo3d.engine.objects.enemy.Zombie;
import com.youtube.pseudo3d.engine.objects.still.BlueFlower;
import com.youtube.pseudo3d.engine.objects.still.GrassBush;
import com.youtube.pseudo3d.engine.objects.still.GrassStalks;
import com.youtube.pseudo3d.engine.objects.still.Grass_0;
import com.youtube.pseudo3d.engine.objects.still.Portal;
import com.youtube.pseudo3d.engine.objects.still.Rose;
import com.youtube.pseudo3d.engine.objects.still.YellowFlower;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;

public class Level_1 extends Level{

	public Level_1(GameLogic gameLogic) {
		super(gameLogic);
		
		setMap(ID.LEVEL_1);
		setFloor(ID.GRASS);
		setCeiling(ID.WOOD);
		
		setSpawn(new Vector2d(16.5, 10.5));
		
		gameLogic.getPlayer().rotate(Math.PI);
		
		initGameObjects();
	}
	
	private void initGameObjects() {		
		gameObjects.add(new Portal(gameLogic, new Vector2d(26.5, 23.5)));
		
		gameObjects.add(new AxeCollect(gameLogic, new Vector2d(17.5, 21.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(11.5, 21.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(21.5, 1.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(18.5, 1.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(15.5, 2.5)));
		
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(21.5, 3.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(20.5, 5.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(30.5, 15.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(26.5, 9.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(26.5, 7.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(30.5, 1.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(24.5, 3.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(23.5, 2.5)));
		
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(7.5, 7.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(1.5, 7.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(1.5, 1.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(7.5, 9.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(7.5, 10.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(1.5, 9.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(15.5, 27.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(9.5, 19.5)));
		
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(16.5, 18.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(10.5, 27.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(29.5, 29.5)));
		gameObjects.add(new GoldCollect(gameLogic, new Vector2d(29.5, 18.5)));


		
		for(int i=0; i<50; i++) {
			gameObjects.add(new Grass_0(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
			gameObjects.add(new GrassBush(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
			gameObjects.add(new GrassStalks(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
		}
		for(int i=0; i<20; i++) {
			gameObjects.add(new BlueFlower(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
			gameObjects.add(new Rose(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
			gameObjects.add(new YellowFlower(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
		}
		
		gameObjects.add(new Rat(gameLogic, new Vector2d(29.5, 14.5), 7));
		gameObjects.add(new Rat(gameLogic, new Vector2d(29.5, 10.5), 7));
		
		gameObjects.add(new Mage(gameLogic, new Vector2d(29.5, 2.5)));
		gameObjects.add(new Mage(gameLogic, new Vector2d(27.5, 2.5)));

		for(int i=0; i<7; i++)
			gameObjects.add(new Bat(gameLogic, new Vector2d(28.5, 5.5)));
		
		gameObjects.add(new Zombie(gameLogic, new Vector2d(23.5, 29.5), 8));
	}

}

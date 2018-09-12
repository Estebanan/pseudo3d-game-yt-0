package com.youtube.pseudo3d.engine.level;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.collect.HealthPotionCollect;
import com.youtube.pseudo3d.engine.objects.collect.LatternCollect;
import com.youtube.pseudo3d.engine.objects.collect.SwordCollect;
import com.youtube.pseudo3d.engine.objects.enemy.Bat;
import com.youtube.pseudo3d.engine.objects.enemy.Mage;
import com.youtube.pseudo3d.engine.objects.enemy.Rat;
import com.youtube.pseudo3d.engine.objects.lever.BlueLever;
import com.youtube.pseudo3d.engine.objects.lever.RedLever;
import com.youtube.pseudo3d.engine.objects.still.BigBush;
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

public class Level_0 extends Level{

	public Level_0(GameLogic gameLogic) {
		super(gameLogic);
		
		setMap(ID.LEVEL_0);
		setFloor(ID.GRASS);
		setCeiling(ID.WOOD);
		
		setSpawn(new Vector2d(49, 97));
		
		initGameObjects();
	}
	
	private void initGameObjects() {
		gameObjects.clear();
		
		gameObjects.add(new Portal(gameLogic, new Vector2d(48.5, 49.5)));
		gameObjects.add(new RedLever(gameLogic, new Vector2d(39.5, 88.5)));
		gameObjects.add(new BlueLever(gameLogic, new Vector2d(37.5, 38.5)));
		
		gameObjects.add(new LatternCollect(gameLogic, new Vector2d(65.5, 82.5)));
		gameObjects.add(new SwordCollect(gameLogic, new Vector2d(66.5, 96.5)));
		
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(66.5, 85.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(88.5, 96.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(77.5, 98.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(97.5, 70.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(97.5, 72.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(86.5, 70.5)));
		gameObjects.add(new HealthPotionCollect(gameLogic, new Vector2d(86.5, 72.5)));
		
		gameObjects.add(new BigBush(gameLogic, new Vector2d(45.5, 93.0)));
		gameObjects.add(new BigBush(gameLogic, new Vector2d(39.5, 89.0)));
		gameObjects.add(new BigBush(gameLogic, new Vector2d(45.5, 82.5)));
		gameObjects.add(new BigBush(gameLogic, new Vector2d(45.5, 83.5)));
		gameObjects.add(new BigBush(gameLogic, new Vector2d(51.5, 83.5)));
		gameObjects.add(new BigBush(gameLogic, new Vector2d(51.5, 82.5)));
		
		for(int i=0; i<120; i++) {
			gameObjects.add(new Grass_0(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
			gameObjects.add(new GrassBush(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
			gameObjects.add(new GrassStalks(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
		}
		for(int i=0; i<60; i++) {
			gameObjects.add(new BlueFlower(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
			gameObjects.add(new Rose(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
			gameObjects.add(new YellowFlower(gameLogic, new Vector2d(MathUtil.randomWithRange(1, map.getWidth() - 1), MathUtil.randomWithRange(1, map.getWidth() - 1))));
		}
		
		for(int i=0; i<5; i++) {
			gameObjects.add(new Bat(gameLogic, new Vector2d(46.5, 91.5)));
			gameObjects.add(new Bat(gameLogic, new Vector2d(42.5, 83.5)));
			gameObjects.add(new Bat(gameLogic, new Vector2d(65.5, 83.5)));
		}
				
		gameObjects.add(new Rat(gameLogic, new Vector2d(48.5, 83.5), 4));
		gameObjects.add(new Rat(gameLogic, new Vector2d(55.5, 82.5), 4));
		gameObjects.add(new Rat(gameLogic, new Vector2d(79.5, 84.5), 8));
		gameObjects.add(new Rat(gameLogic, new Vector2d(86.5, 64.5), 3.6));
		gameObjects.add(new Rat(gameLogic, new Vector2d(97.5, 78.5), 3.6));
		gameObjects.add(new Rat(gameLogic, new Vector2d(62.5, 77.5), 7));
		gameObjects.add(new Rat(gameLogic, new Vector2d(48.5, 58.5), 7));
		
		gameObjects.add(new Mage(gameLogic, new Vector2d(92.0, 70.0)));
		gameObjects.add(new Mage(gameLogic, new Vector2d(97.5, 64.5)));
		gameObjects.add(new Mage(gameLogic, new Vector2d(86.5, 79.5)));
		gameObjects.add(new Mage(gameLogic, new Vector2d(39.5, 53.5)));
		gameObjects.add(new Mage(gameLogic, new Vector2d(56.5, 50.5)));
		gameObjects.add(new Mage(gameLogic, new Vector2d(44.5, 40.5)));
		gameObjects.add(new Mage(gameLogic, new Vector2d(64.5, 94.5)));
		gameObjects.add(new Mage(gameLogic, new Vector2d(75.5, 54.5)));
	}

}

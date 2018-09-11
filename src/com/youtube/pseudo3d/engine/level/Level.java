package com.youtube.pseudo3d.engine.level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public abstract class Level {

	protected GameLogic gameLogic;
	protected BufferedImage map;
	protected BufferedImage floor;
	protected BufferedImage ceiling;
	
	protected ArrayList<GameObject> gameObjects;
	
	public Level(GameLogic gameLogic) { 
		this.gameLogic = gameLogic;
		
		gameObjects = new ArrayList<GameObject>();
	}
	
	public void setSpawn(Vector2d spawn) {
		gameLogic.getPlayer().setPosition(spawn);
	}
	
	public void setMap(ID id) {
		map = TextureHolder.get(id);
	}
	
	public void setFloor(ID id) {
		floor = TextureHolder.get(id);
	}
	
	public void setCeiling(ID id) {
		ceiling = TextureHolder.get(id);
	}
	
	public BufferedImage getMap() {
		return map;
	}
	
	public BufferedImage getFloor() {
		return floor;
	}
	
	public BufferedImage getCeiling() {
		return ceiling;
	}
	
	public ArrayList<GameObject> getGameObjects(){
		return gameObjects;
	}
}

package com.youtube.pseudo3d.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.youtube.pseudo3d.engine.objects.Barrel;
import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.engine.objects.Pillar;
import com.youtube.pseudo3d.engine.objects.Spider;
import com.youtube.pseudo3d.main.Main;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Constants;
import com.youtube.pseudo3d.util.Vector2d;

public class Raycaster {

	public static final int TEST_MAP_TEXTURE_WIDTH = 64;
	public static final int TEST_MAP_TEXTURE_HEIGHT = 64;
	
	private Camera camera;
	private Player player;
	private Rayprojector rayprojector;
	
	private BufferedImage screen;
	
	private Main main;

	private ArrayList<GameObject> gameObjects;
	
	public Raycaster(Main main) {
		this.main = main;
		initTextures();
		initGameObjects();
		initScreen();
		initRaycastingFields();
	}
	
	private void initTextures() {
		generateTextures();
	}
	
	private void generateTextures() {
		TextureHolder.load(ID.TEST_MAP, 	"/maps/test_map.png");
		TextureHolder.load(ID.EMBLEM, 		"/tiles/emblem.png");
		TextureHolder.load(ID.BRICK_0, 		"/tiles/brick_0.png");
		TextureHolder.load(ID.BRICK_1, 		"/tiles/brick_1.png");
		TextureHolder.load(ID.PURPLESTONE, 	"/tiles/purplestone.png");
		TextureHolder.load(ID.BLUESTONE, 	"/tiles/bluestone.png");
		TextureHolder.load(ID.MOSSYSTONE, 	"/tiles/mossystone.png");
		TextureHolder.load(ID.WOOD, 		"/tiles/wood.png");
		TextureHolder.load(ID.COBBLESTONE, 	"/tiles/cobblestone.png");
		TextureHolder.load(ID.BARREL, 		"/sprites/barrel.png");
		TextureHolder.load(ID.PILLAR, 		"/sprites/pillar.png");
		TextureHolder.load(ID.SPIDER, 		"/sprites/spider.png");
	}
	
	private void initGameObjects() {
		gameObjects = new ArrayList<GameObject>();

		for(int i=0; i<20; i++)
			
		gameObjects.add(new Barrel(this, new Vector2d(16.5, 22.5)));
		gameObjects.add(new Spider(this, new Vector2d(17.5, 21.5)));
		gameObjects.add(new Barrel(this, new Vector2d(17.5, 18.5)));

		gameObjects.add(new Pillar(this, new Vector2d(22.5, 13.5)));
		gameObjects.add(new Barrel(this, new Vector2d(22.5, 7.5)));
		gameObjects.add(new Pillar(this, new Vector2d(22.5, 6.5)));

		gameObjects.add(new Spider(this, new Vector2d(22.5, 2.5)));
		gameObjects.add(new Barrel(this, new Vector2d(22.5, 1.5)));
		gameObjects.add(new Spider(this, new Vector2d(21.5, 1.5)));
		
		gameObjects.add(new Spider(this, new Vector2d(15.5, 13.5)));
		gameObjects.add(new Barrel(this, new Vector2d(16.5, 13.5)));
		gameObjects.add(new Barrel(this, new Vector2d(17.5, 15.5)));
		gameObjects.add(new Barrel(this, new Vector2d(19.5, 14.5)));
	}
	
	private void initScreen() {
		screen = new BufferedImage(Constants.RESOLUTION_WIDTH, Constants.RESOLUTION_HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	private void initRaycastingFields() {
		camera = new Camera();
		player = new Player(this);
		rayprojector = new Rayprojector(this);
	}
	
	public void handleInput(double elapsed) {
		player.handleInput(elapsed);
	}
	
	public void update(double elapsed) {
		// UPDATE SCREEN SIZE DEPENDING ON WINDOW SIZE
		screen = new BufferedImage(Constants.RESOLUTION_WIDTH, Constants.RESOLUTION_HEIGHT, BufferedImage.TYPE_INT_RGB);
		rayprojector.projectRays();
	}
	
	public void render(Graphics g) {
		g.drawImage(screen, 0, 0, main.getWidth(), main.getHeight(), null);
		
		//DEBUG INFO
		g.setColor(Color.GRAY);
		g.drawString(
				"x = " + Math.floor(player.getPosition().x) + 
				", y = " + Math.floor(player.getPosition().y) +
				", direction = (" + (player.getDirection().x) + ", " + (player.getDirection().y) + ")"
				, 100, 20);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public BufferedImage getScreen() {
		return screen;
	}

	public void setScreen(BufferedImage screen) {
		this.screen = screen;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(ArrayList<GameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}	
	
	
}

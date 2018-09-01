package com.youtube.pseudo3d.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.youtube.pseudo3d.engine.objects.Barrel;
import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.engine.objects.Pillar;
import com.youtube.pseudo3d.engine.objects.Spider;
import com.youtube.pseudo3d.gui.Gui;
import com.youtube.pseudo3d.main.Main;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Constants;
import com.youtube.pseudo3d.util.Vector2d;

public class GameLogic {

	public static final int TEST_MAP_TEXTURE_WIDTH = 64;
	public static final int TEST_MAP_TEXTURE_HEIGHT = 64;
	
	private Camera camera;
	private Player player;
	private Rayprojector rayprojector;
	
	private BufferedImage screen;
	
	private Main main;

	private ArrayList<GameObject> gameObjects;
	
	private Gui gui;
	
	public GameLogic(Main main) {
		this.main = main;
		initTextures();
		initGameObjects();
		initScreen();
		initRaycastingFields();
		initGui();
	}
	
	private void initTextures() {
		generateTextures();
	}
	
	private void generateTextures() {
		TextureHolder.load(ID.TEST_MAP, 		"/maps/test_map.png");
		TextureHolder.load(ID.EMBLEM, 			"/tiles/emblem.png");
		TextureHolder.load(ID.BRICK_0, 			"/tiles/brick_0.png");
		TextureHolder.load(ID.BRICK_1, 			"/tiles/brick_1.png");
		TextureHolder.load(ID.PURPLESTONE, 		"/tiles/purplestone.png");
		TextureHolder.load(ID.BLUESTONE, 		"/tiles/bluestone.png");
		TextureHolder.load(ID.MOSSYSTONE, 		"/tiles/mossystone.png");
		TextureHolder.load(ID.WOOD, 			"/tiles/wood.png");
		TextureHolder.load(ID.COBBLESTONE, 		"/tiles/cobblestone.png");
		
		TextureHolder.load(ID.BARREL, 			"/sprites/barrel.png");
		TextureHolder.load(ID.PILLAR, 			"/sprites/pillar.png");
		TextureHolder.load(ID.SPIDER, 			"/sprites/spider.png");
		
		TextureHolder.load(ID.PLAYER_LATTERN,	"/sprites/player/lattern_hand.png");
		TextureHolder.load(ID.PLAYER_SWORD,		"/sprites/player/sword_hand.png");
		TextureHolder.load(ID.PLAYER_AXE,		"/sprites/player/axe_hand.png");
		TextureHolder.load(ID.PLAYER_WAND,		"/sprites/player/wand_hand.png");
		
		TextureHolder.load(ID.GUI_EMPTY_SLOT,	"/gui/empty-slot.png");
		TextureHolder.load(ID.GUI_SELECTED_SLOT,"/gui/selected-slot.png");
		TextureHolder.load(ID.GUI_HEALTH_BAR,   "/gui/health-bar.png");
		TextureHolder.load(ID.GUI_LATTERN_ICON, "/gui/lattern-icon.png");
		TextureHolder.load(ID.GUI_SWORD_ICON, 	"/gui/sword-icon.png");
		TextureHolder.load(ID.GUI_AXE_ICON, 	"/gui/axe-icon.png");
		TextureHolder.load(ID.GUI_WAND_ICON, 	"/gui/wand-icon.png");
	}
	
	private void initGameObjects() {
		gameObjects = new ArrayList<GameObject>();
			
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
		
		gameObjects.add(new Barrel(this, new Vector2d(8.5, 14.5)));
		gameObjects.add(new Spider(this, new Vector2d(7.5, 15.5)));
		gameObjects.add(new Barrel(this, new Vector2d(2.5, 14.5)));
		gameObjects.add(new Spider(this, new Vector2d(2.5, 13.5)));
		gameObjects.add(new Pillar(this, new Vector2d(1.5, 12.5)));
		gameObjects.add(new Spider(this, new Vector2d(5.5, 22.5)));
		gameObjects.add(new Pillar(this, new Vector2d(9.5, 22.5)));
		gameObjects.add(new Pillar(this, new Vector2d(10.5,22.5)));
		gameObjects.add(new Spider(this, new Vector2d(6.5, 21.5)));
		gameObjects.add(new Barrel(this, new Vector2d(6.5, 20.5)));
	}
	
	private void initScreen() {
		screen = new BufferedImage(Constants.RESOLUTION_WIDTH, Constants.RESOLUTION_HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	private void initRaycastingFields() {
		camera = new Camera();
		player = new Player(this);
		rayprojector = new Rayprojector(this);
	}
	
	private void initGui() {
		gui = new Gui(this);
	}
	
	public void handleInput(double elapsed) {
		player.handleInput(elapsed);
	}
	
	public void update(double elapsed) {
		// UPDATE SCREEN SIZE DEPENDING ON WINDOW SIZE
		screen = new BufferedImage(Constants.RESOLUTION_WIDTH, Constants.RESOLUTION_HEIGHT, BufferedImage.TYPE_INT_RGB);
		rayprojector.projectRays();
		player.update(elapsed);
		gui.update(elapsed);
	}
	
	public void render(Graphics g) {
		g.drawImage(screen, 0, 0, main.getWidth(), main.getHeight(), null);
		player.render(g);
		gui.render(g);
		
		//DEBUG INFO
		g.setColor(Color.GRAY);
		g.drawString(
				"x = " + Math.floor(player.getPosition().x) + 
				", y = " + Math.floor(player.getPosition().y) +
				", direction = (" + (player.getDirection().x) + ", " + (player.getDirection().y) + ")"
				, 100, 20);
	}

	public Main getMain() {
		return main;
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

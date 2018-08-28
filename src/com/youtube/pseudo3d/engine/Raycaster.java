package com.youtube.pseudo3d.engine;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.youtube.pseudo3d.main.Main;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Constants;

public class Raycaster {

	public static final int TEST_MAP_TEXTURE_WIDTH = 64;
	public static final int TEST_MAP_TEXTURE_HEIGHT = 64;
	
	private Camera camera;
	private Player player;
	private Rayprojector rayprojector;
	
	private BufferedImage screen;
	
	private TextureHolder textureHolder;
	private Main main;

	
	public Raycaster(Main main) {
		this.main = main;
		initTextures();
		initScreen();
		initRaycastingFields();
	}
	
	private void initTextures() {
		textureHolder = new TextureHolder();
		generateTextures();
	}
	
	private void generateTextures() {
		textureHolder.load(ID.TEST_MAP, 	"/maps/test_map.png");
		textureHolder.load(ID.EMBLEM, 		"/tiles/emblem.png");
		textureHolder.load(ID.BRICK_0, 		"/tiles/brick_0.png");
		textureHolder.load(ID.BRICK_1, 		"/tiles/brick_1.png");
		textureHolder.load(ID.PURPLESTONE, 	"/tiles/purplestone.png");
		textureHolder.load(ID.BLUESTONE, 	"/tiles/bluestone.png");
		textureHolder.load(ID.MOSSYSTONE, 	"/tiles/mossystone.png");
		textureHolder.load(ID.WOOD, 		"/tiles/wood.png");
		textureHolder.load(ID.COBBLESTONE, 	"/tiles/cobblestone.png");
		textureHolder.load(ID.BARREL, 		"/sprites/barrel.png");
		textureHolder.load(ID.PILLAR, 		"/sprites/pillar.png");
		textureHolder.load(ID.GREENLIGHT, 	"/sprites/greenlight.png");
	}
	
	private void initScreen() {
		screen = new BufferedImage(Constants.RESOLUTION_WIDTH, Constants.RESOLUTION_HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	private void initRaycastingFields() {
		camera = new Camera();
		player = new Player(camera, textureHolder);
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

	public TextureHolder getTextureHolder() {
		return textureHolder;
	}

	public void setTextureHolder(TextureHolder textureHolder) {
		this.textureHolder = textureHolder;
	}
	
}

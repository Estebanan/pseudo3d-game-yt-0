package com.youtube.pseudo3d.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.youtube.pseudo3d.main.Main;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Constants;
import com.youtube.pseudo3d.util.Vector2d;
import com.youtube.pseudo3d.util.Vector2i;

public class Raycaster {

	public static final int TEST_MAP_TEXTURE_WIDTH = 64;
	public static final int TEST_MAP_TEXTURE_HEIGHT = 64;
	
	private Camera camera;
	private Player player;
	
	private BufferedImage screen;
	
	private TextureHolder textureHolder;
	private Main main;
	
	private Vector2d rayDirection;
	private Vector2i playerPositionOnMap;
	private Vector2d deltaDistance;
	
	private Vector2i step;
	private Vector2d sideDistance;
	
	private Vector2i rayPositionOnTexture;
	private Vector2d wallOnScreen;
	
	private double rayLength;
	private int projectedLineHeight;
	
	private int drawStart;
	private int drawEnd;
	
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
	}
	
	private void initScreen() {
		screen = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	private void initRaycastingFields() {
		camera = new Camera();
		player = new Player(camera, textureHolder);
		
		rayDirection = new Vector2d();
		playerPositionOnMap = new Vector2i();
		deltaDistance = new Vector2d();
		
		rayPositionOnTexture = new Vector2i();
		wallOnScreen = new Vector2d();
	}
	
	public void handleInput(double elapsed) {
		player.handleInput(elapsed);
	}
	
	public void update(double elapsed) {
		// UPDATE SCREEN SIZE DEPENDING ON WINDOW SIZE
		screen = new BufferedImage(main.getWidth(), main.getHeight(), BufferedImage.TYPE_INT_RGB);
		projectRays();
	}
	
	private void projectRays() {
		for (int x = 0; x < screen.getWidth(); x++) {
			double cameraX = 2 * x / (double) screen.getWidth() - 1;

			rayDirection.x = (player.getDirection().x / player.getActualFov() + camera.getPlane().x * cameraX);
			rayDirection.y = (player.getDirection().y / player.getActualFov() + camera.getPlane().y * cameraX);

			playerPositionOnMap.x = (int) player.getPosition().x; 
			playerPositionOnMap.y = (int) player.getPosition().y;

			deltaDistance.x = Math.abs(1 / rayDirection.x); 
			deltaDistance.y = Math.abs(1 / rayDirection.y);
			
			boolean hit = false;
			boolean side = false;

			step = nextStep();
			sideDistance = nextSideDistance();

			// PERFORM DDA
			while (!hit) {
				side = performedDDASide();
				hit = performedDDAHit();
			}

			rayLength = calculatedRayLength(side);	
			projectedLineHeight = (int) (screen.getHeight() / rayLength);

			// CALCULATE LOWEST AND HIGHEST PIXEL TO FILL IN CURRENT STRIPE
			drawStart = -projectedLineHeight / 2 + screen.getHeight() / 2;
			drawEnd = projectedLineHeight / 2 + screen.getHeight() / 2;
			handleDrawingOutOfBounds();
			
			int tileColor = textureHolder.get(ID.TEST_MAP).getRGB(playerPositionOnMap.x, playerPositionOnMap.y);
			
			calculateWallPositionOnScreen(side);
			calculateRayPositionOnTexture(side);
			
			for(int y=drawStart; y<drawEnd; y++) {
				int d = y * 256 - screen.getHeight() * 128 + projectedLineHeight * 128;
				rayPositionOnTexture.y = ((d * TEST_MAP_TEXTURE_HEIGHT) / projectedLineHeight) / 256;
				
				if(rayPositionOnTexture.x <= TEST_MAP_TEXTURE_WIDTH && rayPositionOnTexture.y <= TEST_MAP_TEXTURE_HEIGHT)
					screen.setRGB(x, y, properWallColor(tileColor, rayPositionOnTexture.x, rayPositionOnTexture.y, side));
				else
					screen.setRGB(x, y, 0);
			}
		}
	}
	
	private Vector2i nextStep() {
		Vector2i step = new Vector2i();
		
		step.x = (rayDirection.x < 0) ? -1 : 1;
		step.y = (rayDirection.y < 0) ? -1 : 1;
		
		return step;
	}
	
	private Vector2d nextSideDistance() {
		Vector2d sideDistance = new Vector2d();

		sideDistance.x = 
				(rayDirection.x < 0) ? (player.getPosition().x - playerPositionOnMap.x) * deltaDistance.x
									 : (playerPositionOnMap.x + 1.0 - player.getPosition().x) * deltaDistance.x;
		sideDistance.y =
				(rayDirection.y < 0) ? (player.getPosition().y - playerPositionOnMap.y) * deltaDistance.y
									 : (playerPositionOnMap.y + 1.0 - player.getPosition().y) * deltaDistance.y;
				
		return sideDistance;
	}
	
	private boolean performedDDASide() {
		boolean side = false;
		
		if (sideDistance.x < sideDistance.y) {
			sideDistance.x += deltaDistance.x;
			playerPositionOnMap.x += step.x;
			side = false;
		} else {
			sideDistance.y += deltaDistance.y;
			playerPositionOnMap.y += step.y;
			side = true;
		}
		
		return side;
	}
	
	private boolean performedDDAHit() {
		return (textureHolder.get(ID.TEST_MAP).getRGB(playerPositionOnMap.x, playerPositionOnMap.y) 
				!= 0xff000000);
	}
	
	private double calculatedRayLength(boolean side) {
		return
		(side) ? (playerPositionOnMap.y - player.getPosition().y + (1 - step.y) / 2)/ rayDirection.y
				   : (playerPositionOnMap.x - player.getPosition().x + (1 - step.x) / 2) / rayDirection.x;
	}
	
	private void handleDrawingOutOfBounds() {
		if (drawStart < 0)
			drawStart = 0;
		if (drawEnd >= screen.getHeight())
			drawEnd = screen.getHeight() - 1;
	}
	
	private void calculateWallPositionOnScreen(boolean side) {
		if(!side)
			wallOnScreen.x = player.getPosition().y + rayLength * rayDirection.y;
		else
			wallOnScreen.x = player.getPosition().x + rayLength * rayDirection.x;
		wallOnScreen.x -= Math.floor(wallOnScreen.x);
		
	}
	
	private void calculateRayPositionOnTexture(boolean side) {
		rayPositionOnTexture.x = (int)(wallOnScreen.x * (double)(TEST_MAP_TEXTURE_WIDTH));
		if(!side && rayDirection.x > 0)
			rayPositionOnTexture.x = TEST_MAP_TEXTURE_WIDTH - rayPositionOnTexture.x - 1;
		if(side && rayDirection.y < 0)
			rayPositionOnTexture.x = TEST_MAP_TEXTURE_WIDTH - rayPositionOnTexture.x - 1;
	}
	
	private int properWallColor(int tileColor, int texX, int texY, boolean side) {
		int color = 0;
		
		switch(tileColor) {
		case 0xffff0000:
			color = textureHolder.get(ID.BRICK_0).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff00ff00:
			color = textureHolder.get(ID.BRICK_1).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff0000ff:
			color = textureHolder.get(ID.BLUESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xffff00ff:
			color = textureHolder.get(ID.COBBLESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xffffff00:
			color = textureHolder.get(ID.PURPLESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff00ffff:
			color = textureHolder.get(ID.WOOD).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xffabcdef:
			color = textureHolder.get(ID.EMBLEM).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		default:
			color = textureHolder.get(ID.MOSSYSTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		}
		
		if(side)
			color = (color & 0xfefefe) >> 1;
				
				
		return color;
	}
	
	public void render(Graphics g) {
		g.drawImage(screen, 0, 0, screen.getWidth(), screen.getHeight(), null);
		
		//DEBUG INFO
		g.setColor(Color.GRAY);
		g.drawString(
				"x = " + Math.floor(player.getPosition().x) + 
				", y = " + Math.floor(player.getPosition().y) +
				", direction = (" + (player.getDirection().x) + ", " + (player.getDirection().y) + ")"
				, 100, 20);
	}
}

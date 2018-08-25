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
		// CAST WALLS
		for (int x = 0; x < screen.getWidth(); x++) {
			double cameraX = 2 * x / (double) screen.getWidth() - 1;

			Vector2d rayDirection = new Vector2d(
					(player.getDirection().x / player.getActualFov() + camera.getPlane().x * cameraX),
					(player.getDirection().y / player.getActualFov() + camera.getPlane().y * cameraX)
					);

			Vector2i mapPosition = new Vector2i(
					(int) player.getPosition().x, 
					(int) player.getPosition().y
					);


			Vector2d deltaDistance = new Vector2d(
					Math.abs(1 / rayDirection.x), 
					Math.abs(1 / rayDirection.y)
					);
			
			double rayLength;

			boolean hit = false;
			boolean side = false;

			Vector2i step = nextStep(rayDirection);
			Vector2d sideDistance = nextSideDistance(rayDirection, mapPosition, deltaDistance);

			// PERFORM DDA
			while (!hit) {
				// jump to next map square
				if (sideDistance.x < sideDistance.y) { // jump to the x square
					sideDistance.x += deltaDistance.x;
					mapPosition.x += step.x;
					side = false;
				} else { // jump to the y square
					sideDistance.y += deltaDistance.y;
					mapPosition.y += step.y;
					side = true;
				}
				// Check if ray has hit a wall
				if (textureHolder.get(ID.TEST_MAP).getRGB(mapPosition.x, mapPosition.y) != 0xff000000)
					hit = true;
			}

			// Calculate distance projected on camera direction
			if (!side)
				rayLength = (mapPosition.x - player.getPosition().x + (1 - step.x) / 2) / rayDirection.x;
			else
				rayLength = (mapPosition.y - player.getPosition().y + (1 - step.y) / 2) / rayDirection.y;

			// Calculate height of line to draw on screen
			int lineHeight = (int) (screen.getHeight() / rayLength);

			// Calculate lowest and highest pixel to fill in current stripe
			int drawStart = -lineHeight / 2 + screen.getHeight() / 2;
			if (drawStart < 0)
				drawStart = 0;
			int drawEnd = lineHeight / 2 + screen.getHeight() / 2;
			if (drawEnd >= screen.getHeight())
				drawEnd = screen.getHeight() - 1;
			
			int tileColor = textureHolder.get(ID.TEST_MAP).getRGB(mapPosition.x, mapPosition.y);
			
			double wallX;
			if(!side)
				wallX = player.getPosition().y + rayLength * rayDirection.y;
			else
				wallX = player.getPosition().x + rayLength * rayDirection.x;
			wallX -= Math.floor(wallX);
			
			int texX = (int)(wallX * (double)(TEST_MAP_TEXTURE_WIDTH));
			if(!side && rayDirection.x > 0)
				texX = TEST_MAP_TEXTURE_WIDTH - texX - 1;
			if(side && rayDirection.y < 0)
				texX = TEST_MAP_TEXTURE_WIDTH - texX - 1;

			for(int y=drawStart; y<drawEnd; y++) {
				int d = y * 256 - screen.getHeight() * 128 + lineHeight * 128;
				int texY = ((d * TEST_MAP_TEXTURE_HEIGHT) / lineHeight) / 256;
				
				if(texX <= TEST_MAP_TEXTURE_WIDTH && texY <= TEST_MAP_TEXTURE_HEIGHT)
					screen.setRGB(x, y, properWallColor(tileColor, texX, texY, side));
				else
					screen.setRGB(x, y, 0);
			}
		}
	}
	
	private Vector2i nextStep(Vector2d rayDirection) {
		Vector2i step = new Vector2i();
		
		step.x = (rayDirection.x < 0) ? -1 : 1;
		step.y = (rayDirection.y < 0) ? -1 : 1;
		
		return step;
	}
	
	private Vector2d nextSideDistance(Vector2d rayDirection, Vector2i mapPosition, Vector2d deltaDistance) {
		Vector2d sideDistance = new Vector2d();

		sideDistance.x = 
				(rayDirection.x < 0) ? (player.getPosition().x - mapPosition.x) * deltaDistance.x
									 : (mapPosition.x + 1.0 - player.getPosition().x) * deltaDistance.x;
		sideDistance.y =
				(rayDirection.y < 0) ? (player.getPosition().y - mapPosition.y) * deltaDistance.y
									 : (mapPosition.y + 1.0 - player.getPosition().y) * deltaDistance.y;
				
		return sideDistance;
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

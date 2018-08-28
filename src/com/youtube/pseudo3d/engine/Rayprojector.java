package com.youtube.pseudo3d.engine;

import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;
import com.youtube.pseudo3d.util.Vector2i;

public class Rayprojector {

	private Raycaster raycaster;
	
	private Vector2d rayDirection;
	private Vector2i playerPositionOnMap;
	private Vector2d deltaDistance;
	
	private Vector2i step;
	private Vector2d sideDistance;
	
	private Vector2i rayPositionOnTexture;
	private Vector2d wallOnScreen;
	
	private Vector2d floorWall;
	
	private double wallDistance, playerDistance, currentDistance;
	
	private double rayLength;
	private int projectedLineHeight;
	
	private int drawStart;
	private int drawEnd;
		
	public Rayprojector(Raycaster raycaster) {
		this.raycaster = raycaster;
		
		rayDirection = new Vector2d();
		playerPositionOnMap = new Vector2i();
		deltaDistance = new Vector2d();
		
		rayPositionOnTexture = new Vector2i();
		wallOnScreen = new Vector2d();
		
		floorWall = new Vector2d();
	}
	
	public void projectRays() {
		for (int x = 0; x < raycaster.getScreen().getWidth(); x++) {
			boolean hit = false;
			boolean side = false;
			
			double cameraX = 2 * x / (double) raycaster.getScreen().getWidth() - 1;

			rayDirection.y = (raycaster.getPlayer().getDirection().x / raycaster.getPlayer().getActualFov() + raycaster.getCamera().getPlane().x * cameraX);
			rayDirection.x = (raycaster.getPlayer().getDirection().y / raycaster.getPlayer().getActualFov() + raycaster.getCamera().getPlane().y * cameraX);

			playerPositionOnMap.x = (int) raycaster.getPlayer().getPosition().x; 
			playerPositionOnMap.y = (int) raycaster.getPlayer().getPosition().y;

			deltaDistance.x = Math.abs(1 / rayDirection.x); 
			deltaDistance.y = Math.abs(1 / rayDirection.y);

			step = nextStep();
			sideDistance = nextSideDistance();

			// PERFORM DDA
			while (!hit) {
				side = performedDDASide();
				hit = performedDDAHit();
			}

			rayLength = calculatedRayLength(side);	
			projectedLineHeight = (int) (raycaster.getScreen().getHeight() / rayLength);

			// CALCULATE LOWEST AND HIGHEST PIXEL TO FILL IN CURRENT STRIPE
			drawStart = (-projectedLineHeight / 2 + raycaster.getScreen().getHeight() / 2);
			drawEnd = (projectedLineHeight / 2 + raycaster.getScreen().getHeight() / 2);
			handleDrawingOutOfBounds();
			
			int tileColor = raycaster.getTextureHolder().get(ID.TEST_MAP).getRGB(playerPositionOnMap.x, playerPositionOnMap.y);
						
			calculateWallPositionOnScreen(side);
			calculateRayPositionOnTexture(side);
			
			for(int y=drawStart; y<drawEnd; y++) {
				int d = y * 256 - raycaster.getScreen().getHeight() * 128 + projectedLineHeight * 128;
				rayPositionOnTexture.y = ((d * Raycaster.TEST_MAP_TEXTURE_HEIGHT) / projectedLineHeight) / 256;
				
				if(rayPositionOnTexture.x <= Raycaster.TEST_MAP_TEXTURE_WIDTH && rayPositionOnTexture.y <= Raycaster.TEST_MAP_TEXTURE_HEIGHT)
					raycaster.getScreen().setRGB(x, y, properWallColor(tileColor, rayPositionOnTexture.x, rayPositionOnTexture.y, side));
				else
					raycaster.getScreen().setRGB(x, y, 0);
			}
							
			projectFloor(side, x);
		}

	}
	
	
	public int properWallColor(int tileColor, int texX, int texY, boolean side) {
		int color = 0;
		
		switch(tileColor) {
		case 0xffff0000:
			color = raycaster.getTextureHolder().get(ID.BRICK_0).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff00ff00:
			color = raycaster.getTextureHolder().get(ID.BRICK_1).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff0000ff:
			color = raycaster.getTextureHolder().get(ID.BLUESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xffff00ff:
			color = raycaster.getTextureHolder().get(ID.COBBLESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xffffff00:
			color = raycaster.getTextureHolder().get(ID.PURPLESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff00ffff:
			color = raycaster.getTextureHolder().get(ID.WOOD).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xffabcdef:
			color = raycaster.getTextureHolder().get(ID.EMBLEM).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		default:
			color = raycaster.getTextureHolder().get(ID.MOSSYSTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		}

		if(side)
			color = (color & 0xfefefe) >> 1;

		//SHADE COLORS
		if(rayLength > 1)
			color = MathUtil.shadeColor(color, rayLength * 1.1);
			
		return color;
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
				(rayDirection.x < 0) ? (raycaster.getPlayer().getPosition().x - playerPositionOnMap.x) * deltaDistance.x
									 : (playerPositionOnMap.x + 1.0 - raycaster.getPlayer().getPosition().x) * deltaDistance.x;
		sideDistance.y =
				(rayDirection.y < 0) ? (raycaster.getPlayer().getPosition().y - playerPositionOnMap.y) * deltaDistance.y
									 : (playerPositionOnMap.y + 1.0 - raycaster.getPlayer().getPosition().y) * deltaDistance.y;
				
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
		return (raycaster.getTextureHolder().get(ID.TEST_MAP).getRGB(playerPositionOnMap.x, playerPositionOnMap.y) 
				!= 0xff000000);
	}
	
	private double calculatedRayLength(boolean side) {
		return
		(side) ? (playerPositionOnMap.y - raycaster.getPlayer().getPosition().y + (1 - step.y) / 2)/ rayDirection.y
				   : (playerPositionOnMap.x - raycaster.getPlayer().getPosition().x + (1 - step.x) / 2) / rayDirection.x;
	}
	
	private void handleDrawingOutOfBounds() {
		if (drawStart < 0)
			drawStart = 0;
		if (drawEnd >= raycaster.getScreen().getHeight())
			drawEnd = raycaster.getScreen().getHeight() - 1;
	}
	
	private void calculateWallPositionOnScreen(boolean side) {
		if(!side)
			wallOnScreen.x = raycaster.getPlayer().getPosition().y + rayLength * rayDirection.y;
		else
			wallOnScreen.x = raycaster.getPlayer().getPosition().x + rayLength * rayDirection.x;
		wallOnScreen.x -= Math.floor(wallOnScreen.x);
		
	}
	
	private void calculateRayPositionOnTexture(boolean side) {
		rayPositionOnTexture.x = (int)(wallOnScreen.x * (double)(Raycaster.TEST_MAP_TEXTURE_WIDTH));
		if(!side && rayDirection.x > 0)
			rayPositionOnTexture.x = Raycaster.TEST_MAP_TEXTURE_WIDTH - rayPositionOnTexture.x - 1;
		if(side && rayDirection.y < 0)
			rayPositionOnTexture.x = Raycaster.TEST_MAP_TEXTURE_WIDTH - rayPositionOnTexture.x - 1;
	}
	
	private void projectFloor(boolean side, int x) {
		if(!side && rayDirection.x > 0) {
			floorWall.x = playerPositionOnMap.x;
			floorWall.y = playerPositionOnMap.y + wallOnScreen.x;
		}else if(!side && rayDirection.x < 0) {
			floorWall.x = playerPositionOnMap.x + 1.0;
			floorWall.y = playerPositionOnMap.y + wallOnScreen.x;
		}else if(side && rayDirection.y > 0) {
			floorWall.x = playerPositionOnMap.x + wallOnScreen.x;
			floorWall.y = playerPositionOnMap.y;
		}else {
			floorWall.x = playerPositionOnMap.x + wallOnScreen.x;
			floorWall.y = playerPositionOnMap.y + 1.0;
		}
		
		wallDistance = rayLength;
		playerDistance = .0;
		
		if(drawEnd < 0)
			drawEnd = raycaster.getScreen().getHeight();
		
		
		for(int y=drawEnd+1; y<raycaster.getScreen().getHeight();  y++) {
			currentDistance = raycaster.getScreen().getHeight() / (2.0 * y - raycaster.getScreen().getHeight());
			
			double weight = (currentDistance - playerDistance) / (wallDistance - playerDistance);
			
			Vector2d currentFloor = new Vector2d(
					weight * floorWall.x + (1.0 - weight) * raycaster.getPlayer().getPosition().x,
					weight * floorWall.y + (1.0 - weight) * raycaster.getPlayer().getPosition().y
					);
			
			Vector2i floorTexture = new Vector2i(
					(int)(currentFloor.x * Raycaster.TEST_MAP_TEXTURE_WIDTH) % Raycaster.TEST_MAP_TEXTURE_WIDTH,
					(int)(currentFloor.y * Raycaster.TEST_MAP_TEXTURE_HEIGHT) % Raycaster.TEST_MAP_TEXTURE_HEIGHT
					);
			
			int floorColor = (raycaster.getTextureHolder().get(ID.COBBLESTONE).getRGB(floorTexture.x, floorTexture.y) & 0xfefefe) >> 1;
			int ceilingColor = (raycaster.getTextureHolder().get(ID.WOOD).getRGB(floorTexture.x, floorTexture.y) & 0xfefefe) >> 1;
			
			//SHADE COLORS
			floorColor = MathUtil.shadeColor(floorColor, currentDistance);
			ceilingColor = MathUtil.shadeColor(ceilingColor, currentDistance);
				
			raycaster.getScreen().setRGB(x, y, floorColor);
			raycaster.getScreen().setRGB(x, raycaster.getScreen().getHeight() - y, ceilingColor);
		}
	}

	public double getRayLength() {
		return rayLength;
	}

	public void setRayLength(double rayLength) {
		this.rayLength = rayLength;
	}
	
	
}

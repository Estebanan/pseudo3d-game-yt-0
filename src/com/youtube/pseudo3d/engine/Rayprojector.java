package com.youtube.pseudo3d.engine;

import java.util.ArrayList;

import com.youtube.pseudo3d.engine.objects.Barrel;
import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.engine.objects.Greenlight;
import com.youtube.pseudo3d.engine.objects.Pillar;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;
import com.youtube.pseudo3d.util.Vector2i;

public class Rayprojector {

	private Raycaster raycaster;
	
	private Vector2d rayDirection;
	private Vector2i positionOnMap;
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
	
	private ArrayList<GameObject> gameObjects;

	private double ZBuffer[];
	
	private int spriteOrder[];
	private double spriteDistance[];
	
	public Rayprojector(Raycaster raycaster) {
		this.raycaster = raycaster;

		initGameObjects();
		initRayprojectorFields();
	}
	
	private void initGameObjects() {
		gameObjects = new ArrayList<GameObject>();
		
		gameObjects.add(new Barrel(raycaster, new Vector2d(16.5, 22.5)));
		gameObjects.add(new Barrel(raycaster, new Vector2d(17.5, 21.5)));
		gameObjects.add(new Barrel(raycaster, new Vector2d(17.5, 18.5)));
				
		gameObjects.add(new Pillar(raycaster, new Vector2d(22.5, 13.5)));
		gameObjects.add(new Pillar(raycaster, new Vector2d(22.5, 7.5)));
		gameObjects.add(new Pillar(raycaster, new Vector2d(22.5, 6.5)));
		
		gameObjects.add(new Greenlight(raycaster, new Vector2d(22.5, 2.5)));
		gameObjects.add(new Greenlight(raycaster, new Vector2d(22.5, 1.5)));
		gameObjects.add(new Greenlight(raycaster, new Vector2d(21.5, 1.5)));
	}
		
	private void initRayprojectorFields() {
		rayDirection = new Vector2d();
		positionOnMap = new Vector2i();
		deltaDistance = new Vector2d();
		
		rayPositionOnTexture = new Vector2i();
		wallOnScreen = new Vector2d();
		
		floorWall = new Vector2d();
		
		ZBuffer = new double[raycaster.getScreen().getWidth()];
		spriteOrder = new int[gameObjects.size()];
		spriteDistance = new double[gameObjects.size()];
	}

	public void projectRays() {		
		for (int x = 0; x < raycaster.getScreen().getWidth(); x++) {
			boolean hit = false;
			boolean side = false;
			
			double cameraX = 2 * x / (double) raycaster.getScreen().getWidth() - 1;

			rayDirection.y = (raycaster.getPlayer().getDirection().x / raycaster.getPlayer().getActualFov() + raycaster.getCamera().getPlane().x * cameraX);
			rayDirection.x = (raycaster.getPlayer().getDirection().y / raycaster.getPlayer().getActualFov() + raycaster.getCamera().getPlane().y * cameraX);

			positionOnMap.x = (int) raycaster.getPlayer().getPosition().x; 
			positionOnMap.y = (int) raycaster.getPlayer().getPosition().y;

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
			
			int tileColor = TextureHolder.get(ID.TEST_MAP).getRGB(positionOnMap.x, positionOnMap.y);
						
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
					
			ZBuffer[x] = rayLength;
			
			projectFloor(side, x);
		}
		
		   //SPRITE CASTING
	    //sort sprites from far to close
	    for(int i = 0; i < gameObjects.size(); i++)
	    {
	      spriteOrder[i] = i;
	      spriteDistance[i] = ((raycaster.getPlayer().getPosition().x - gameObjects.get(i).getPosition().x) * (raycaster.getPlayer().getPosition().x - gameObjects.get(i).getPosition().x) 
	    		  + 
	      (raycaster.getPlayer().getPosition().y - gameObjects.get(i).getPosition().y) * (raycaster.getPlayer().getPosition().y - gameObjects.get(i).getPosition().y)); //sqrt not taken, unneeded
	    }
	    
	    combSort(spriteOrder, spriteDistance, gameObjects.size());

	    for(int i = 0; i < gameObjects.size(); i++)
	    {
		  double spriteY = gameObjects.get(i).getPosition().x - raycaster.getPlayer().getPosition().x;
	      double spriteX = gameObjects.get(i).getPosition().y - raycaster.getPlayer().getPosition().y;

	      double invDet = 1.0 / (raycaster.getCamera().getPlane().x / raycaster.getPlayer().getActualFov() * raycaster.getPlayer().getDirection().y
	    		  - 
	    		 raycaster.getPlayer().getDirection().x / raycaster.getPlayer().getActualFov() * raycaster.getCamera().getPlane().y);

	      double transformX = invDet * (raycaster.getPlayer().getDirection().y / raycaster.getPlayer().getActualFov() * spriteX - raycaster.getPlayer().getDirection().x / raycaster.getPlayer().getActualFov() * spriteY);
	      double transformY = invDet * (-raycaster.getCamera().getPlane().y * spriteX + raycaster.getCamera().getPlane().x * spriteY); 

	      int spriteScreenX = (int)((raycaster.getScreen().getWidth() / 2) * (1 + transformX / transformY));

	      int spriteHeight = Math.abs((int)(raycaster.getScreen().getHeight() / (transformY)));

	      int drawStartY = -spriteHeight / 2 + raycaster.getScreen().getHeight() / 2;
	      if(drawStartY < 0) drawStartY = 0;
	      int drawEndY = spriteHeight / 2 + raycaster.getScreen().getHeight() / 2;
	      if(drawEndY >= raycaster.getScreen().getHeight()) drawEndY = raycaster.getScreen().getHeight() - 1;

	      int spriteWidth = Math.abs( (int) (raycaster.getScreen().getHeight() / (transformY)));
	      int drawStartX = -spriteWidth / 2 + spriteScreenX;
	      if(drawStartX < 0) drawStartX = 0;
	      int drawEndX = spriteWidth / 2 + spriteScreenX;
	      if(drawEndX >= raycaster.getScreen().getWidth()) drawEndX = raycaster.getScreen().getWidth() - 1;

	      for(int stripe = drawStartX; stripe < drawEndX; stripe++)
	      {
	        int texX = (int)(256 * (stripe - (-spriteWidth / 2 + spriteScreenX)) * Raycaster.TEST_MAP_TEXTURE_WIDTH / spriteWidth) / 256;
	        
	        if(transformY > 0 && stripe > 0 && stripe < raycaster.getScreen().getWidth() && transformY < ZBuffer[stripe])
	        	
	        for(int y = drawStartY; y < drawEndY; y++) //for every pixel of the current stripe
	        {
	          int d = (y) * 256 - raycaster.getScreen().getHeight() * 128 + spriteHeight * 128; //256 and 128 factors to avoid floats
	          int texY = ((d * Raycaster.TEST_MAP_TEXTURE_HEIGHT) / spriteHeight) / 256;
	          
	          int color = TextureHolder.get(gameObjects.get(i).getTexture()).getRGB(texX, texY);
	          if(color != 0xff000000)
	        	  raycaster.getScreen().setRGB(stripe, y, color);
	        }
	      }
	    }
	}
	
	
	public int properWallColor(int tileColor, int texX, int texY, boolean side) {
		int color = 0;
		
		switch(tileColor) {
		case 0xffff0000:
			color = TextureHolder.get(ID.BRICK_0).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff00ff00:
			color = TextureHolder.get(ID.BRICK_1).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff0000ff:
			color = TextureHolder.get(ID.BLUESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xffff00ff:
			color = TextureHolder.get(ID.COBBLESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xffffff00:
			color = TextureHolder.get(ID.PURPLESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff00ffff:
			color = TextureHolder.get(ID.WOOD).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xffabcdef:
			color = TextureHolder.get(ID.EMBLEM).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		default:
			color = TextureHolder.get(ID.MOSSYSTONE).getRGB(Math.abs(texX), Math.abs(texY));
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
				(rayDirection.x < 0) ? (raycaster.getPlayer().getPosition().x - positionOnMap.x) * deltaDistance.x
									 : (positionOnMap.x + 1.0 - raycaster.getPlayer().getPosition().x) * deltaDistance.x;
		sideDistance.y =
				(rayDirection.y < 0) ? (raycaster.getPlayer().getPosition().y - positionOnMap.y) * deltaDistance.y
									 : (positionOnMap.y + 1.0 - raycaster.getPlayer().getPosition().y) * deltaDistance.y;
				
		return sideDistance;
	}
	
	private boolean performedDDASide() {
		boolean side = false;
		
		if (sideDistance.x < sideDistance.y) {
			sideDistance.x += deltaDistance.x;
			positionOnMap.x += step.x;
			side = false;
		} else {
			sideDistance.y += deltaDistance.y;
			positionOnMap.y += step.y;
			side = true;
		}
		
		return side;
	}
	
	private boolean performedDDAHit() {
		return (TextureHolder.get(ID.TEST_MAP).getRGB(positionOnMap.x, positionOnMap.y) 
				!= 0xff000000);
	}
	
	private double calculatedRayLength(boolean side) {
		return
		(side) ? (positionOnMap.y - raycaster.getPlayer().getPosition().y + (1 - step.y) / 2)/ rayDirection.y
				   : (positionOnMap.x - raycaster.getPlayer().getPosition().x + (1 - step.x) / 2) / rayDirection.x;
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
			floorWall.x = positionOnMap.x;
			floorWall.y = positionOnMap.y + wallOnScreen.x;
		}else if(!side && rayDirection.x < 0) {
			floorWall.x = positionOnMap.x + 1.0;
			floorWall.y = positionOnMap.y + wallOnScreen.x;
		}else if(side && rayDirection.y > 0) {
			floorWall.x = positionOnMap.x + wallOnScreen.x;
			floorWall.y = positionOnMap.y;
		}else {
			floorWall.x = positionOnMap.x + wallOnScreen.x;
			floorWall.y = positionOnMap.y + 1.0;
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
			
			int floorColor = (TextureHolder.get(ID.COBBLESTONE).getRGB(floorTexture.x, floorTexture.y) & 0xfefefe) >> 1;
			int ceilingColor = (TextureHolder.get(ID.WOOD).getRGB(floorTexture.x, floorTexture.y) & 0xfefefe) >> 1;
			
			//SHADE COLORS
			floorColor = MathUtil.shadeColor(floorColor, currentDistance);
			ceilingColor = MathUtil.shadeColor(ceilingColor, currentDistance);
				
			raycaster.getScreen().setRGB(x, y, floorColor);
			raycaster.getScreen().setRGB(x, raycaster.getScreen().getHeight() - y, ceilingColor);
		}
	}

 
	private void combSort(int order[], double dist[], int amount)
	{
	  int gap = amount;
	  boolean swapped = false;
	  while(gap > 1 || swapped)
	  {
	    //shrink factor 1.3
	    gap = (gap * 10) / 13;
	    if(gap == 9 || gap == 10) gap = 11;
	    if (gap < 1) gap = 1;
	    swapped = false;
	    for(int i = 0; i < amount - gap; i++)
	    {
	      int j = i + gap;
	      if(dist[i] < dist[j])
	      {
	        swap(dist, i, j);
	        swap(order, i, j);
	        swapped = true;
	      }
	    }
	  }
	}
	
	private void swap(int array[], final int i, final int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	private void swap(double array[], final int i, final int j) {
		double temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	
	
	public double getRayLength() {
		return rayLength;
	}

	public void setRayLength(double rayLength) {
		this.rayLength = rayLength;
	}
	
	
}

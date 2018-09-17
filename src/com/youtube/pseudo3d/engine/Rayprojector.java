package com.youtube.pseudo3d.engine;

import com.youtube.pseudo3d.engine.level.Level_0;
import com.youtube.pseudo3d.engine.level.Level_1;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;
import com.youtube.pseudo3d.util.Vector2i;

public class Rayprojector {

	private GameLogic raycaster;

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

	private Vector2d spritePosition;
	private Vector2d transform;
	
	private double zBuffer[];
	private int objectsOrder[];
	private double objectsDistance[];

	public Rayprojector(GameLogic raycaster) {
		this.raycaster = raycaster;

		initRayprojectorFields();
	}

	private void initRayprojectorFields() {
		rayDirection = new Vector2d();
		positionOnMap = new Vector2i();
		deltaDistance = new Vector2d();

		rayPositionOnTexture = new Vector2i();
		wallOnScreen = new Vector2d();

		floorWall = new Vector2d();

		spritePosition = new Vector2d();
	}

	public void projectRays() {
		
		updateInitialGameObjectValues();

		for (int x = 0; x < raycaster.getScreen().getWidth(); x++) {
			boolean hit = false;
			boolean side = false;

			double cameraX = 2 * x / (double) raycaster.getScreen().getWidth() - 1;

			rayDirection.y = (raycaster.getPlayer().getDirection().x / raycaster.getPlayer().getActualFov()
					+ raycaster.getCamera().getPlane().x * cameraX);
			rayDirection.x = (raycaster.getPlayer().getDirection().y / raycaster.getPlayer().getActualFov()
					+ raycaster.getCamera().getPlane().y * cameraX);

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
			drawStart = -projectedLineHeight / 2 + raycaster.getScreen().getHeight() / 2;
			drawEnd = (projectedLineHeight / 2 + raycaster.getScreen().getHeight() / 2);
			handleDrawingOutOfBounds();

			int tileColor = raycaster.getCurrentLevel().getMap().getRGB(positionOnMap.x, positionOnMap.y);

			calculateWallPositionOnScreen(side);
			calculateRayPositionOnTexture(side);

			for (int y = drawStart; y < drawEnd; y++) {
				int d = y * 256 - raycaster.getScreen().getHeight() * 128 + projectedLineHeight * 128;
				rayPositionOnTexture.y = ((d * GameLogic.TEXTURE_HEIGHT) / projectedLineHeight) / 256;
				
				if (rayPositionOnTexture.x <= GameLogic.TEXTURE_WIDTH
						&& rayPositionOnTexture.y <= GameLogic.TEXTURE_HEIGHT)
					raycaster.getScreen().setRGB(x, y,
							properWallColor(tileColor, rayPositionOnTexture.x, rayPositionOnTexture.y, side));
				else
					raycaster.getScreen().setRGB(x, y, 0);
			}

			zBuffer[x] = rayLength;

			projectFloor(side, x);
		}

		projectSprites();
	}

	private void updateInitialGameObjectValues() {
		zBuffer = new double[raycaster.getScreen().getWidth()];
		objectsOrder = new int[raycaster.getCurrentLevel().getGameObjects().size()];
		objectsDistance = new double[raycaster.getCurrentLevel().getGameObjects().size()];
	}

	public int properWallColor(int tileColor, int texX, int texY, boolean side) {
		int color = 0;

		switch (tileColor) {
		case 0xff0000ff:
			color = TextureHolder.get(ID.BLUESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff2c2c2c:
			color = TextureHolder.get(ID.BRICK_0).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xffff0000:
			color = TextureHolder.get(ID.BRICK_1).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff878079:
			color = TextureHolder.get(ID.COBBLESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xffd45b1d:
			color = TextureHolder.get(ID.EMBLEM).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff00ff00:
			color = TextureHolder.get(ID.BUSH).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff254f18:
			color = TextureHolder.get(ID.GRASS).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff657b39:
			color = TextureHolder.get(ID.MOSSYSTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff533242:
			color = TextureHolder.get(ID.PURPLESTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff6b5945:
			color = TextureHolder.get(ID.ROTTEN_WOOD).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff645343:
			color = TextureHolder.get(ID.WOOD).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff3b1c26:
			color = TextureHolder.get(ID.RED_DOOR).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		case 0xff004889:
			color = TextureHolder.get(ID.BLUE_DOOR).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		default:
			color = TextureHolder.get(ID.MOSSYSTONE).getRGB(Math.abs(texX), Math.abs(texY));
			break;
		}

		if (side)
			color = (color & 0xfefefe) >> 1;

		// SHADE COLORS
		if (rayLength > raycaster.getPlayer().getEmittedLight())
			color = MathUtil.shadeColor(color, rayLength / raycaster.getPlayer().getEmittedLight());

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

		sideDistance.x = (rayDirection.x < 0)
				? (raycaster.getPlayer().getPosition().x - positionOnMap.x) * deltaDistance.x
				: (positionOnMap.x + 1.0 - raycaster.getPlayer().getPosition().x) * deltaDistance.x;
		sideDistance.y = (rayDirection.y < 0)
				? (raycaster.getPlayer().getPosition().y - positionOnMap.y) * deltaDistance.y
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
		return (raycaster.getCurrentLevel().getMap().getRGB(positionOnMap.x, positionOnMap.y) != 0xff000000);
	}

	private double calculatedRayLength(boolean side) {
		return (side) ? (positionOnMap.y - raycaster.getPlayer().getPosition().y + (1 - step.y) / 2) / rayDirection.y
				: (positionOnMap.x - raycaster.getPlayer().getPosition().x + (1 - step.x) / 2) / rayDirection.x;
	}

	private void handleDrawingOutOfBounds() {
		if (drawStart < 0)
			drawStart = 0;
		if (drawEnd >= raycaster.getScreen().getHeight())
			drawEnd = raycaster.getScreen().getHeight() - 1;
	}

	private void calculateWallPositionOnScreen(boolean side) {
		if (!side)
			wallOnScreen.x = raycaster.getPlayer().getPosition().y + rayLength * rayDirection.y;
		else
			wallOnScreen.x = raycaster.getPlayer().getPosition().x + rayLength * rayDirection.x;
		wallOnScreen.x -= Math.floor(wallOnScreen.x);
	}

	private void calculateRayPositionOnTexture(boolean side) {
		rayPositionOnTexture.x = (int) (wallOnScreen.x * (double) (GameLogic.TEXTURE_WIDTH));
		if (!side && rayDirection.x > 0)
			rayPositionOnTexture.x = GameLogic.TEXTURE_WIDTH - rayPositionOnTexture.x - 1;
		if (side && rayDirection.y < 0)
			rayPositionOnTexture.x = GameLogic.TEXTURE_WIDTH - rayPositionOnTexture.x - 1;		
	}

	private void projectFloor(boolean side, int x) {
		if (!side && rayDirection.x > 0) {
			floorWall.x = positionOnMap.x;
			floorWall.y = positionOnMap.y + wallOnScreen.x;
		} else if (!side && rayDirection.x < 0) {
			floorWall.x = positionOnMap.x + 1.0;
			floorWall.y = positionOnMap.y + wallOnScreen.x;
		} else if (side && rayDirection.y > 0) {
			floorWall.x = positionOnMap.x + wallOnScreen.x;
			floorWall.y = positionOnMap.y;
		} else {
			floorWall.x = positionOnMap.x + wallOnScreen.x;
			floorWall.y = positionOnMap.y + 1.0;
		}
		
		wallDistance = rayLength;
		playerDistance = .0;

		if (drawEnd < 0)
			drawEnd = raycaster.getScreen().getHeight();

		for (int y = drawEnd + 1; y < raycaster.getScreen().getHeight(); y++) {
			currentDistance = raycaster.getScreen().getHeight() / (2.0 * y - raycaster.getScreen().getHeight());

			double weight = (currentDistance - playerDistance) / (wallDistance - playerDistance);

			Vector2d currentFloor = new Vector2d(
					weight * floorWall.x + (1.0 - weight) * raycaster.getPlayer().getPosition().x,
					weight * floorWall.y + (1.0 - weight) * raycaster.getPlayer().getPosition().y);

			Vector2i floorTexture = new Vector2i(
					(int) (currentFloor.x * GameLogic.TEXTURE_WIDTH) % GameLogic.TEXTURE_WIDTH,
					(int) (currentFloor.y * GameLogic.TEXTURE_HEIGHT) % GameLogic.TEXTURE_HEIGHT);

			int floorColor = (raycaster.getCurrentLevel().getFloor().getRGB(floorTexture.x, floorTexture.y) & 0xfefefe) >> 1;
			int ceilingColor = (raycaster.getCurrentLevel().getCeiling().getRGB(floorTexture.x, floorTexture.y) & 0xfefefe) >> 1;
		
			// SHADE COLORS
			if(currentDistance > raycaster.getPlayer().getEmittedLight()) {
				floorColor = MathUtil.shadeColor(floorColor, currentDistance / raycaster.getPlayer().getEmittedLight());
				ceilingColor = MathUtil.shadeColor(ceilingColor, currentDistance / raycaster.getPlayer().getEmittedLight());
			}
			
			raycaster.getScreen().setRGB(x, y, floorColor);
			
			if(!(raycaster.getCurrentLevel() instanceof Level_0 || raycaster.getCurrentLevel() instanceof Level_1))
				raycaster.getScreen().setRGB(x, raycaster.getScreen().getHeight() - y, ceilingColor);
			else
				raycaster.getScreen().setRGB(x, raycaster.getScreen().getHeight() - y, 0xff020203);

		}
	}

	private void projectSprites() {
		for (int i = 0; i < raycaster.getCurrentLevel().getGameObjects().size(); i++) {
			objectsOrder[i] = i;
			objectsDistance[i] = ((raycaster.getPlayer().getPosition().x - raycaster.getCurrentLevel().getGameObjects().get(i).getPosition().x)
					* (raycaster.getPlayer().getPosition().x - raycaster.getCurrentLevel().getGameObjects().get(i).getPosition().x)
					+ (raycaster.getPlayer().getPosition().y - raycaster.getCurrentLevel().getGameObjects().get(i).getPosition().y)
							* (raycaster.getPlayer().getPosition().y - raycaster.getCurrentLevel().getGameObjects().get(i).getPosition().y));
		}

		MathUtil.combSort(objectsOrder, objectsDistance, raycaster.getCurrentLevel().getGameObjects().size());

		for (int i = 0; i < raycaster.getCurrentLevel().getGameObjects().size(); i++) {
			spritePosition = new Vector2d(raycaster.getCurrentLevel().getGameObjects().get(objectsOrder[i]).getPosition().y - raycaster.getPlayer().getPosition().y,
					raycaster.getCurrentLevel().getGameObjects().get(objectsOrder[i]).getPosition().x - raycaster.getPlayer().getPosition().x);

			double invDet = 1.0 / (raycaster.getCamera().getPlane().x / raycaster.getPlayer().getActualFov()
					* raycaster.getPlayer().getDirection().y
					- raycaster.getPlayer().getDirection().x / raycaster.getPlayer().getActualFov()
							* raycaster.getCamera().getPlane().y);

			transform = new Vector2d(invDet * (raycaster.getPlayer().getDirection().y
					/ raycaster.getPlayer().getActualFov() * spritePosition.x
					- raycaster.getPlayer().getDirection().x / raycaster.getPlayer().getActualFov() * spritePosition.y),
					invDet * (-raycaster.getCamera().getPlane().y * spritePosition.x
							+ raycaster.getCamera().getPlane().x * spritePosition.y));

			int spriteScreenX = (int) ((raycaster.getScreen().getWidth() / 2) * (1 + transform.x / transform.y));

			int spriteHeight = Math.abs((int) (raycaster.getScreen().getHeight() / (transform.y)));

			int drawStartY = -spriteHeight / 2 + raycaster.getScreen().getHeight() / 2;
			if (drawStartY < 0)
				drawStartY = 0;
			int drawEndY = spriteHeight / 2 + raycaster.getScreen().getHeight() / 2;
			if (drawEndY >= raycaster.getScreen().getHeight())
				drawEndY = raycaster.getScreen().getHeight() - 1;

			int spriteWidth = Math.abs((int) (raycaster.getScreen().getHeight() / (transform.y)));
			int drawStartX = -spriteWidth / 2 + spriteScreenX;
			if (drawStartX < 0)
				drawStartX = 0;
			int drawEndX = spriteWidth / 2 + spriteScreenX;
			if (drawEndX >= raycaster.getScreen().getWidth())
				drawEndX = raycaster.getScreen().getWidth() - 1;

			for (int stripe = drawStartX; stripe < drawEndX; stripe++) {
				int texX = (int) (256 * (stripe - (-spriteWidth / 2 + spriteScreenX)) * GameLogic.TEXTURE_WIDTH
						/ spriteWidth) / 256;

				if (transform.y > 0 && stripe > 0 && stripe < raycaster.getScreen().getWidth()
						&& transform.y < zBuffer[stripe])

					for (int y = drawStartY; y < drawEndY; y++)
					{
						int d = (y) * 256 - raycaster.getScreen().getHeight() * 128 + spriteHeight * 128;
						
						int texY = ((d * GameLogic.TEXTURE_HEIGHT) / spriteHeight) / 256;

						int color = raycaster.getCurrentLevel().getGameObjects().get(objectsOrder[i]).getTexture().getRGB(Math.abs(texX), Math.abs(texY));

						if (spriteHeight < 300 / raycaster.getPlayer().getEmittedLight())
							color = MathUtil.shadeColor(color, 400 / (spriteHeight * raycaster.getPlayer().getEmittedLight()));

						if (color != 0xff000000)
							raycaster.getScreen().setRGB(stripe, y, color);
					}
			}
		}
	}

	public double getRayLength() {
		return rayLength;
	}

	public void setRayLength(double rayLength) {
		this.rayLength = rayLength;
	}

}

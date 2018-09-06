package com.youtube.pseudo3d.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.engine.objects.collect.AxeCollect;
import com.youtube.pseudo3d.engine.objects.collect.LatternCollect;
import com.youtube.pseudo3d.engine.objects.collect.SwordCollect;
import com.youtube.pseudo3d.engine.objects.collect.WandCollect;
import com.youtube.pseudo3d.engine.objects.enemy.Bat;
import com.youtube.pseudo3d.engine.objects.enemy.Enemy;
import com.youtube.pseudo3d.engine.objects.enemy.Mage;
import com.youtube.pseudo3d.engine.objects.enemy.Rat;
import com.youtube.pseudo3d.engine.objects.enemy.Zombie;
import com.youtube.pseudo3d.engine.objects.missle.AxeMissle;
import com.youtube.pseudo3d.engine.objects.missle.EnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.GreenEnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.Missle;
import com.youtube.pseudo3d.engine.objects.missle.SwordMissle;
import com.youtube.pseudo3d.engine.objects.missle.WandMissle;
import com.youtube.pseudo3d.engine.objects.still.Barrel;
import com.youtube.pseudo3d.engine.objects.still.Pillar;
import com.youtube.pseudo3d.engine.objects.still.Spider;
import com.youtube.pseudo3d.gui.Gui;
import com.youtube.pseudo3d.main.Main;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.resource.TextureLoader;
import com.youtube.pseudo3d.util.Constants;
import com.youtube.pseudo3d.util.MathUtil;
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
	
	public int time = 0;
	
	public GameLogic(Main main) {
		this.main = main;
		initTextures();
		initGameObjects();
		initScreen();
		initRaycastingFields();
		initGui();
	}
	
	private void initTextures() {
		new TextureLoader();
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
		
		gameObjects.add(new LatternCollect(this, new Vector2d(18.5, 18.5)));
		gameObjects.add(new SwordCollect(this, new Vector2d(17.5, 17.5)));
		gameObjects.add(new AxeCollect(this, new Vector2d(16.5, 16.5)));
		gameObjects.add(new WandCollect(this, new Vector2d(15.5, 15.5)));
		
		for(int i=0; i<5; i++)
			gameObjects.add(new Bat(this, new Vector2d(7.0, 12.0)));
			
		gameObjects.add(new Zombie(this, new Vector2d(7.0, 12.0), 8));
		
		gameObjects.add(new Rat(this, new Vector2d(6.5, 16.5), 5));
		gameObjects.add(new Rat(this, new Vector2d(20.5, 10.5), 5));
		gameObjects.add(new Rat(this, new Vector2d(14.5, 2.5), 4));
		gameObjects.add(new Rat(this, new Vector2d(14.5, 19.5), 5));
		
		gameObjects.add(new Mage(this, new Vector2d(7.5, 16.5)));
		gameObjects.add(new Mage(this, new Vector2d(17.5, 2.5)));
		gameObjects.add(new Mage(this, new Vector2d(2.5, 16.5)));

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
		time += (elapsed * 1e3);		
		// UPDATE SCREEN SIZE DEPENDING ON WINDOW SIZE
		screen = new BufferedImage(Constants.RESOLUTION_WIDTH, Constants.RESOLUTION_HEIGHT, BufferedImage.TYPE_INT_RGB);
		rayprojector.projectRays();
		player.update(elapsed);
		gui.update(elapsed);
		
		updateGameObjects(elapsed);
		updateWallCollisions();
		
		updateDamagingPlayerByMissles();
		
		updateDamagingEnemies();
		updateEnemiesDeath();
		
		updateCloseDistanceMisslesDisapear();
		
		updatePickupLattern();
		updatePickupSword();
		updatePickupAxe();
		updatePickupWand();
	}
	
	private void updateGameObjects(double elapsed) {
		for(int i=0; i<gameObjects.size(); i++)
			gameObjects.get(i).update(elapsed);
	}
	
	private void updateWallCollisions() {
		for(int i=0; i<gameObjects.size(); i++)
			if((gameObjects.get(i) instanceof Missle || gameObjects.get(i) instanceof EnemyMissle)
					&& TextureHolder.get(ID.TEST_MAP).getRGB((int) (gameObjects.get(i).getPosition().x),
							(int) (gameObjects.get(i).getPosition().y)) != 0xff000000) {
				
				gameObjects.remove(i);
				i--;
			}
	}
	
	private void updateDamagingPlayerByMissles() {
		for(int i=0; i<gameObjects.size(); i++)
			if(gameObjects.get(i) instanceof EnemyMissle
					&& Math.floor(gameObjects.get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(gameObjects.get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
				
				if(gameObjects.get(i) instanceof GreenEnemyMissle)
					Player.HEALTH -= GreenEnemyMissle.DAMAGE;
				
				gameObjects.remove(i);
				i--;
			}
	}
	
	private void updateDamagingEnemies() {
		for(int i=0; i<gameObjects.size(); i++)
			for(int j=0; j<gameObjects.size(); j++)
				if(gameObjects.get(i) instanceof Enemy
						&& gameObjects.get(j) instanceof Missle
						&& Math.floor(gameObjects.get(i).getPosition().x) == Math.floor(gameObjects.get(j).getPosition().x)
						&& Math.floor(gameObjects.get(i).getPosition().y) == Math.floor(gameObjects.get(j).getPosition().y)) {
					
					if(gameObjects.get(j) instanceof SwordMissle)
						gameObjects.get(i).health -= SwordMissle.DAMAGE;
					
					else if(gameObjects.get(j) instanceof AxeMissle)
						gameObjects.get(i).health -= AxeMissle.DAMAGE;
					
					else if(gameObjects.get(j) instanceof WandMissle)
						gameObjects.get(i).health -= WandMissle.DAMAGE;
					
					gameObjects.remove(j);
					j--;
				}
	}
	
	private void updateEnemiesDeath() {
		for(int i=0; i<gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof Enemy
					&& gameObjects.get(i).dead) {
				gameObjects.remove(i);
				i--;
			}
		}
	}
	
	private void updateCloseDistanceMisslesDisapear() {
		for(int i=0; i<gameObjects.size(); i++)
			if((gameObjects.get(i) instanceof SwordMissle || gameObjects.get(i) instanceof AxeMissle)
					&& MathUtil.pythagoreanDistance(player.getPosition(), gameObjects.get(i).getPosition()) > 1.5) {						
				gameObjects.remove(i);
				i--;
			}
	}
	
	private void updatePickupLattern() {
		for(int i=0; i<gameObjects.size(); i++)
			if(gameObjects.get(i) instanceof LatternCollect
					&& Math.floor(gameObjects.get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(gameObjects.get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
				Items.unlocked.put(Items.Holding.LATTERN, true);
				gameObjects.remove(i);
				i--;
			}
	}
	
	private void updatePickupSword() {
		for(int i=0; i<gameObjects.size(); i++)
			if(gameObjects.get(i) instanceof SwordCollect
					&& Math.floor(gameObjects.get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(gameObjects.get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
				Items.unlocked.put(Items.Holding.SWORD, true);
				gameObjects.remove(i);
				i--;
			}
	}
	
	private void updatePickupAxe() {
		for(int i=0; i<gameObjects.size(); i++)
			if(gameObjects.get(i) instanceof AxeCollect
					&& Math.floor(gameObjects.get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(gameObjects.get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
				Items.unlocked.put(Items.Holding.AXE, true);
				gameObjects.remove(i);
				i--;
			}
	}
	
	private void updatePickupWand() {
		for(int i=0; i<gameObjects.size(); i++)
			if(gameObjects.get(i) instanceof WandCollect
					&& Math.floor(gameObjects.get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(gameObjects.get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
				Items.unlocked.put(Items.Holding.WAND, true);
				gameObjects.remove(i);
				i--;
			}
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

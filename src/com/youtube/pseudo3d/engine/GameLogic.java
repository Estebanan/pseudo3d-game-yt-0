package com.youtube.pseudo3d.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.youtube.pseudo3d.engine.level.Level;
import com.youtube.pseudo3d.engine.level.Level_0;
import com.youtube.pseudo3d.engine.level.Level_1;
import com.youtube.pseudo3d.engine.level.Level_2;
import com.youtube.pseudo3d.engine.objects.collect.AxeCollect;
import com.youtube.pseudo3d.engine.objects.collect.HealthPotionCollect;
import com.youtube.pseudo3d.engine.objects.collect.LatternCollect;
import com.youtube.pseudo3d.engine.objects.collect.SwordCollect;
import com.youtube.pseudo3d.engine.objects.collect.WandCollect;
import com.youtube.pseudo3d.engine.objects.enemy.Enemy;
import com.youtube.pseudo3d.engine.objects.enemy.Thanos;
import com.youtube.pseudo3d.engine.objects.enemy.Thanos.State;
import com.youtube.pseudo3d.engine.objects.lever.BlueLever;
import com.youtube.pseudo3d.engine.objects.lever.RedLever;
import com.youtube.pseudo3d.engine.objects.missle.AxeMissle;
import com.youtube.pseudo3d.engine.objects.missle.BlueEnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.EnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.GreenEnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.Missle;
import com.youtube.pseudo3d.engine.objects.missle.OrangeEnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.PunchMissle;
import com.youtube.pseudo3d.engine.objects.missle.PurpleEnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.RedEnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.SwordMissle;
import com.youtube.pseudo3d.engine.objects.missle.ThanosCar;
import com.youtube.pseudo3d.engine.objects.missle.WandMissle;
import com.youtube.pseudo3d.engine.objects.missle.YellowEnemyMissle;
import com.youtube.pseudo3d.engine.objects.still.Portal;
import com.youtube.pseudo3d.gui.Gui;
import com.youtube.pseudo3d.main.Main;
import com.youtube.pseudo3d.resource.TextureLoader;
import com.youtube.pseudo3d.util.Constants;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;

public class GameLogic {

	public static final int TEXTURE_WIDTH = 64;
	public static final int TEXTURE_HEIGHT = 64;
	
	private Camera camera;
	private Player player;
	private Rayprojector rayprojector;
	
	private BufferedImage screen;
	
	private Main main;
	
	private Gui gui;
	
	public int time = 0;
	
	private Level currentLevel;
	private int levelNumber = 0;
		
	public GameLogic(Main main) {
		this.main = main;
		initTextures();
		initScreen();
		initRaycastingFields();
		initLevels();
		initGui();
	}
	
	private void initTextures() {
		new TextureLoader();
	}
	

	private void initScreen() {
		screen = new BufferedImage(Constants.RESOLUTION_WIDTH, Constants.RESOLUTION_HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	private void initRaycastingFields() {
		camera = new Camera();
		player = new Player(this);
		rayprojector = new Rayprojector(this);
	}
	
	private void initLevels() {
		levelNumber = 0;
		currentLevel = new Level_0(this);
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
		updatePickupHealth();
		
		updateSwitchingLever();
		updateEnteringPortal();
		
		updateThanosFight();
	}
	
	private void updateGameObjects(double elapsed) {		
		for(int i=0; i<currentLevel.getGameObjects().size(); i++)
			currentLevel.getGameObjects().get(i).update(elapsed);
	}
	
	private void updateWallCollisions() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++)
			if((currentLevel.getGameObjects().get(i) instanceof Missle || currentLevel.getGameObjects().get(i) instanceof EnemyMissle)
					&& currentLevel.getMap().getRGB((int) (currentLevel.getGameObjects().get(i).getPosition().x),
							(int) (currentLevel.getGameObjects().get(i).getPosition().y)) != 0xff000000) {
				
				currentLevel.getGameObjects().remove(i);
				i--;
			}
	}
	
	private void updateDamagingPlayerByMissles() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++)
			if(currentLevel.getGameObjects().get(i) instanceof EnemyMissle
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
				
				
				if(currentLevel.getGameObjects().get(i) instanceof BlueEnemyMissle)
					Player.HEALTH -= BlueEnemyMissle.DAMAGE;
				if(currentLevel.getGameObjects().get(i) instanceof OrangeEnemyMissle)
					Player.HEALTH -= OrangeEnemyMissle.DAMAGE;
				if(currentLevel.getGameObjects().get(i) instanceof RedEnemyMissle)
					Player.HEALTH -= RedEnemyMissle.DAMAGE;
				if(currentLevel.getGameObjects().get(i) instanceof GreenEnemyMissle)
					Player.HEALTH -= GreenEnemyMissle.DAMAGE;
				if(currentLevel.getGameObjects().get(i) instanceof PurpleEnemyMissle)
					Player.HEALTH -= PurpleEnemyMissle.DAMAGE;
				if(currentLevel.getGameObjects().get(i) instanceof YellowEnemyMissle)
					Player.HEALTH -= YellowEnemyMissle.DAMAGE;
				if(currentLevel.getGameObjects().get(i) instanceof ThanosCar)
					Player.HEALTH -= ThanosCar.DAMAGE;
				
				currentLevel.getGameObjects().remove(i);
				i--;
			}
	}
	
	private void updateDamagingEnemies() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++)
			for(int j=0; j<currentLevel.getGameObjects().size(); j++)
				if(currentLevel.getGameObjects().get(i) instanceof Enemy
						&& currentLevel.getGameObjects().get(j) instanceof Missle
						&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(currentLevel.getGameObjects().get(j).getPosition().x)
						&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(currentLevel.getGameObjects().get(j).getPosition().y)) {
				
					if(currentLevel.getGameObjects().get(j) instanceof PunchMissle)
						currentLevel.getGameObjects().get(i).health -= PunchMissle.DAMAGE;
					
					else if(currentLevel.getGameObjects().get(j) instanceof SwordMissle)
						currentLevel.getGameObjects().get(i).health -= SwordMissle.DAMAGE;
					
					else if(currentLevel.getGameObjects().get(j) instanceof AxeMissle)
						currentLevel.getGameObjects().get(i).health -= AxeMissle.DAMAGE;
					
					else if(currentLevel.getGameObjects().get(j) instanceof WandMissle)
						currentLevel.getGameObjects().get(i).health -= WandMissle.DAMAGE;
					
					currentLevel.getGameObjects().remove(j);
					j--;
				}
	}
	
	private void updateEnemiesDeath() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++) {
			if(currentLevel.getGameObjects().get(i) instanceof Enemy
					&& currentLevel.getGameObjects().get(i).dead) {
				currentLevel.getGameObjects().remove(i);
				i--;
			}
		}
	}
	
	private void updateCloseDistanceMisslesDisapear() {		
		for(int i=0; i<currentLevel.getGameObjects().size(); i++) {
			if((currentLevel.getGameObjects().get(i) instanceof PunchMissle)
					&& MathUtil.pythagoreanDistance(player.getPosition(), currentLevel.getGameObjects().get(i).getPosition()) > 1.0) {						
				currentLevel.getGameObjects().remove(i);
				i--;
			}
			if((currentLevel.getGameObjects().get(i) instanceof SwordMissle)
					&& MathUtil.pythagoreanDistance(player.getPosition(), currentLevel.getGameObjects().get(i).getPosition()) > 2.0) {						
				currentLevel.getGameObjects().remove(i);
				i--;
			}
			if((currentLevel.getGameObjects().get(i) instanceof AxeMissle)
					&& MathUtil.pythagoreanDistance(player.getPosition(), currentLevel.getGameObjects().get(i).getPosition()) > 1.5) {						
				currentLevel.getGameObjects().remove(i);
				i--;
			}
		}
	}
	
	private void updatePickupLattern() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++)
			if(currentLevel.getGameObjects().get(i) instanceof LatternCollect
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
				Items.unlocked.put(Items.Holding.LATTERN, true);
				currentLevel.getGameObjects().remove(i);
				i--;
			}
	}
	
	private void updatePickupSword() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++)
			if(currentLevel.getGameObjects().get(i) instanceof SwordCollect
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
				Items.unlocked.put(Items.Holding.SWORD, true);
				currentLevel.getGameObjects().remove(i);
				i--;
			}
	}
	
	private void updatePickupAxe() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++)
			if(currentLevel.getGameObjects().get(i) instanceof AxeCollect
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
				Items.unlocked.put(Items.Holding.AXE, true);
				currentLevel.getGameObjects().remove(i);
				i--;
			}
	}
	
	private void updatePickupWand() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++)
			if(currentLevel.getGameObjects().get(i) instanceof WandCollect
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
				Items.unlocked.put(Items.Holding.WAND, true);
				currentLevel.getGameObjects().remove(i);
				i--;
			}
	}
	
	private void updatePickupHealth() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++)
			if(currentLevel.getGameObjects().get(i) instanceof HealthPotionCollect
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)
					&& Player.HEALTH < 100) {
				
				if(Player.HEALTH < 100 - HealthPotionCollect.BONUS)
					Player.HEALTH += HealthPotionCollect.BONUS;
				else if(Player.HEALTH >= 100 - HealthPotionCollect.BONUS)
					Player.HEALTH = 100;
				
				currentLevel.getGameObjects().remove(i);
				i--;
			}
	}
	
	private void updateSwitchingLever() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++) {
			if(currentLevel.getGameObjects().get(i) instanceof RedLever
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
			
				((RedLever) currentLevel.getGameObjects().get(i)).on = true;
				
				for(int x=0; x<currentLevel.getMap().getWidth(); x++)
					for(int y=0; y<currentLevel.getMap().getHeight(); y++)
						if(currentLevel.getMap().getRGB(x, y) == 0xff3b1c26)
							currentLevel.getMap().setRGB(x, y, 0xff000000);
			}
			
			
			if(currentLevel.getGameObjects().get(i) instanceof BlueLever
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
		
				((BlueLever) currentLevel.getGameObjects().get(i)).on = true;
				
				for(int x=0; x<currentLevel.getMap().getWidth(); x++)
					for(int y=0; y<currentLevel.getMap().getHeight(); y++)
						if(currentLevel.getMap().getRGB(x, y) == 0xff004889)
							currentLevel.getMap().setRGB(x, y, 0xff000000);
			}
		}
	}
	
	private void updateEnteringPortal() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++)
			if(currentLevel.getGameObjects().get(i) instanceof Portal
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
			
					increaseLevel();
			}
	}
	
	private void increaseLevel() {
		levelNumber++;
		
		switch(levelNumber) {
		default:
		case 0:
			break;
		case 1:
			currentLevel = new Level_1(this);
			break;
		case 2:
			currentLevel = new Level_2(this);
			break;
		}
	}
	
	private void updateThanosFight() {
		if(currentLevel instanceof Level_2 
				&& Math.floor(player.getPosition().x) == 3
				&& Math.floor(player.getPosition().y) == 5) {
			currentLevel.getMap().setRGB(3, 6, 0xff3b1c26);
			
			for(int i=0; i<currentLevel.getGameObjects().size(); i++) {
				if(currentLevel.getGameObjects().get(i) instanceof Thanos && !Thanos.trigerred)
					((Thanos)currentLevel.getGameObjects().get(i)).state = State.FIGHT;
			}
			
			Thanos.trigerred = true;
		}
		
		for(int i=0; i<currentLevel.getGameObjects().size(); i++) {
			if(currentLevel.getGameObjects().get(i) instanceof Thanos
					&& ((Thanos)currentLevel.getGameObjects().get(i)).state == State.FIGHT
					&& time % 20 == 0) {
				
				currentLevel.getGameObjects().add(new ThanosCar(this, new Vector2d(13.5, 2.5),  new Vector2d(.0, -1.0), 50));
				currentLevel.getGameObjects().add(new ThanosCar(this, new Vector2d(13.5, 5.5),  new Vector2d(.0, -1.0), 50));
			}
				
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
	
	public Level getCurrentLevel() {
		return currentLevel;
	}
}

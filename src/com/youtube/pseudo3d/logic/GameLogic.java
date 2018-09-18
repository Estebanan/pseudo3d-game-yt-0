package com.youtube.pseudo3d.logic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.youtube.pseudo3d.engine.Camera;
import com.youtube.pseudo3d.engine.Items;
import com.youtube.pseudo3d.engine.Items.Holding;
import com.youtube.pseudo3d.engine.Player;
import com.youtube.pseudo3d.engine.Rayprojector;
import com.youtube.pseudo3d.engine.level.Level;
import com.youtube.pseudo3d.engine.level.Level_0;
import com.youtube.pseudo3d.engine.level.Level_1;
import com.youtube.pseudo3d.engine.level.Level_2;
import com.youtube.pseudo3d.engine.objects.collect.AxeCollect;
import com.youtube.pseudo3d.engine.objects.collect.GoldCollect;
import com.youtube.pseudo3d.engine.objects.collect.HealthPotionCollect;
import com.youtube.pseudo3d.engine.objects.collect.LatternCollect;
import com.youtube.pseudo3d.engine.objects.collect.SwordCollect;
import com.youtube.pseudo3d.engine.objects.collect.WandCollect;
import com.youtube.pseudo3d.engine.objects.enemy.Enemy;
import com.youtube.pseudo3d.engine.objects.enemy.Thanos;
import com.youtube.pseudo3d.engine.objects.enemy.Thanos.State;
import com.youtube.pseudo3d.engine.objects.lever.BlueLever;
import com.youtube.pseudo3d.engine.objects.lever.GreenLever;
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
import com.youtube.pseudo3d.gui.QuickText;
import com.youtube.pseudo3d.main.Main;
import com.youtube.pseudo3d.resource.AudioPaths;
import com.youtube.pseudo3d.util.AudioHandler;
import com.youtube.pseudo3d.util.Constants;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;

public class GameLogic extends Logic{

	public static final int TEXTURE_WIDTH = 64;
	public static final int TEXTURE_HEIGHT = 64;
	
	private Camera camera;
	private Player player;
	private Rayprojector rayprojector;
	
	private BufferedImage screen;
		
	private Gui gui;
	
	public int time = 0;
	
	private Level currentLevel;
	private int levelNumber = 0;
		
	public GameLogic(Main main) {
		super(main);
		initScreen();
		initRaycastingFields();
		initLevels();
		initGui();
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
	
	@Override
	public void update(double elapsed) {				
		time += (elapsed * 1e3);		
		// UPDATE SCREEN SIZE DEPENDING ON WINDOW SIZE
		screen = new BufferedImage(Constants.RESOLUTION_WIDTH, Constants.RESOLUTION_HEIGHT, BufferedImage.TYPE_INT_RGB);
		rayprojector.projectRays();
		player.update(elapsed);
		gui.update(elapsed);
		
		updateDying();
		
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
		updatePickupGold();
		
		updateSwitchingLever();
		updateEnteringPortal();
		
		updateThanosFight();
	}
	
	private void updateDying() {
		if(Player.HEALTH <= 0) {	
			AudioHandler.playAudio(AudioPaths.DEATH).start();
			Player.HEALTH = 100;
			
			QuickText.resetTimers();
			
			if(currentLevel instanceof Level_0) {
				currentLevel = new Level_0(this);
				Items.unlocked.put(Items.Holding.LATTERN, false);
				Items.unlocked.put(Items.Holding.SWORD, false);
			}
			else if(currentLevel instanceof Level_1) {
				currentLevel = new Level_1(this);
				Items.unlocked.put(Items.Holding.AXE, false);
			}
			else if(currentLevel instanceof Level_2)
				currentLevel = new Level_2(this);
		}
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
				AudioHandler.playAudio(AudioPaths.BLOOD_SPLASH_0).start();
				
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
				AudioHandler.playAudio(AudioPaths.TREASURE_0).start();
				
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
				AudioHandler.playAudio(AudioPaths.TREASURE_0).start();

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
				AudioHandler.playAudio(AudioPaths.TREASURE_0).start();

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
				AudioHandler.playAudio(AudioPaths.TREASURE_0).start();

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
				
				if(Player.HEALTH < 100 - HealthPotionCollect.BONUS) {
					Player.HEALTH += HealthPotionCollect.BONUS;
				}
				else if(Player.HEALTH >= 100 - HealthPotionCollect.BONUS)
					Player.HEALTH = 100;

				AudioHandler.playAudio(AudioPaths.POTION_DRINK).start();
				
				currentLevel.getGameObjects().remove(i);
				i--;
			}
	}
	
	private void updatePickupGold() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++)
			if(currentLevel.getGameObjects().get(i) instanceof GoldCollect
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {

				Player.COINS += 10;
				AudioHandler.playAudio(AudioPaths.GOLD_PICKUP).start();
				
				currentLevel.getGameObjects().remove(i);
				i--;
			}
	}
	
	private void updateSwitchingLever() {
		for(int i=0; i<currentLevel.getGameObjects().size(); i++) {
			if(currentLevel.getGameObjects().get(i) instanceof RedLever
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
			
				if(((RedLever) currentLevel.getGameObjects().get(i)).on == false) {
					AudioHandler.playAudio(AudioPaths.METAL_SQUEAK).start();
				}
				
				((RedLever) currentLevel.getGameObjects().get(i)).on = true;
				
				for(int x=0; x<currentLevel.getMap().getWidth(); x++)
					for(int y=0; y<currentLevel.getMap().getHeight(); y++)
						if(currentLevel.getMap().getRGB(x, y) == 0xff3b1c26)
							currentLevel.getMap().setRGB(x, y, 0xff000000);
			}
			
			
			if(currentLevel.getGameObjects().get(i) instanceof BlueLever
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
		
				if(((BlueLever) currentLevel.getGameObjects().get(i)).on == false) {
					AudioHandler.playAudio(AudioPaths.METAL_SQUEAK).start();
				}
				
				((BlueLever) currentLevel.getGameObjects().get(i)).on = true;

				for(int x=0; x<currentLevel.getMap().getWidth(); x++)
					for(int y=0; y<currentLevel.getMap().getHeight(); y++)
						if(currentLevel.getMap().getRGB(x, y) == 0xff004889)
							currentLevel.getMap().setRGB(x, y, 0xff000000);
			}
			
			
			if(currentLevel.getGameObjects().get(i) instanceof GreenLever
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().x) == Math.floor(player.getPosition().x)
					&& Math.floor(currentLevel.getGameObjects().get(i).getPosition().y) == Math.floor(player.getPosition().y)) {
		
				if(((GreenLever) currentLevel.getGameObjects().get(i)).on == false) {
					AudioHandler.playAudio(AudioPaths.METAL_SQUEAK).start();
				}
				
				((GreenLever) currentLevel.getGameObjects().get(i)).on = true;

				for(int x=0; x<currentLevel.getMap().getWidth(); x++)
					for(int y=0; y<currentLevel.getMap().getHeight(); y++)
						if(currentLevel.getMap().getRGB(x, y) == 0xff294d2a)
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
				if(currentLevel.getGameObjects().get(i) instanceof Thanos 
						&& ((Thanos)currentLevel.getGameObjects().get(i)).state == State.SLEEP)
					AudioHandler.playAudio(AudioPaths.AVENGERS_THEME).start();
				
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
	
	@Override
	public void render(Graphics g) {
		g.drawImage(screen, 
				(int)(Math.cos(player.getPosition().x * 2) * Math.cos(player.getPosition().y * 2 * 10) - 100), 
				(int)(Math.sin(player.getPosition().x * 2.5) * Math.sin(player.getPosition().y * 2.5) * 20 - 100), 
				(int)(main.getWidth() + 200), 
				(int)(main.getHeight() + 200), 
				null);
		
		player.render(g);
		gui.render(g);

		renderQuickText(g);
	}
	
	private void renderQuickText(Graphics g) {
		if(currentLevel instanceof Level_0) {
			if((player.getPosition().x != 49.0 || player.getPosition().y != 97.0)) {
				QuickText.displayChapter1Text(this, g);
				QuickText.displayChapter1NameText(this, g);
			}
			if(QuickText.timers[0] >= QuickText.DELAY)
				QuickText.displayFindLatternText(this, g);
			
			if(QuickText.timers[2] >= QuickText.DELAY)
				QuickText.displayFindSwordText(this, g);

			if(Items.unlocked.get(Holding.LATTERN))
				QuickText.displayFoundLatternText(this, g);
			
			if(Items.unlocked.get(Holding.SWORD))
				QuickText.displayFoundSwordText(this, g);
			
			
			//RED SECRET
			if(Math.floor(player.getPosition().x) == 39
					&& Math.floor(player.getPosition().y) == 88)
				QuickText.displayRedDoorsOpen(this, g);
			
			if((Math.floor(player.getPosition().x) == 53 
					|| Math.floor(player.getPosition().x) == 52
					|| Math.floor(player.getPosition().x) == 54)
					&& Math.floor(player.getPosition().y) == 90)
				QuickText.displayRedSecretFound(this, g);
			
			//BLUE SECRET
			if(Math.floor(player.getPosition().x) == 37
					&& Math.floor(player.getPosition().y) == 38)
				QuickText.displayBlueDoorsOpen(this, g);
			
			if((Math.floor(player.getPosition().y) == 35 
					|| Math.floor(player.getPosition().y) == 36
					|| Math.floor(player.getPosition().y) == 37
					|| Math.floor(player.getPosition().y) == 38)
					&& Math.floor(player.getPosition().x) == 55)
				QuickText.displayBlueSecretFound(this, g);
			
		}
		
		if(currentLevel instanceof Level_1) {
			if((player.getPosition().x != 16.0 || player.getPosition().y != 10.0)) {
				QuickText.displayChapter2Text(this, g);
				QuickText.displayChapter2NameText(this, g);
			}
			
			if(QuickText.timers[6] >= QuickText.DELAY && !Items.unlocked.get(Items.Holding.AXE))
				QuickText.displayFindAxeText(this, g);
			
			if(Items.unlocked.get(Holding.AXE) && QuickText.timers[6] >= QuickText.DELAY)
				QuickText.displayFoundAxeText(this, g);
			
			if((Math.floor(player.getPosition().x) == 17
					|| Math.floor(player.getPosition().x) == 18
					|| Math.floor(player.getPosition().x) == 19)
					&& Math.floor(player.getPosition().y) == 15)
				QuickText.displayThisWay(this, g);
			
			//GREEN SECRET
			if(Math.floor(player.getPosition().x) == 27
					&& Math.floor(player.getPosition().y) == 29)
				QuickText.displayGreenDoorsOpen(this, g);
			
			if((Math.floor(player.getPosition().x) == 10 
					|| Math.floor(player.getPosition().x) == 11
					|| Math.floor(player.getPosition().x) == 12)
					&& Math.floor(player.getPosition().y) == 30)
				QuickText.displayGreenSecretFound(this, g);
			
		}
		
		if(currentLevel instanceof Level_2) {
			if((player.getPosition().x != 10.0 || player.getPosition().y != 11.0)) {
				QuickText.displayChapter3Text(this, g);
				QuickText.displayChapter3NameText(this, g);
			}
		}
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

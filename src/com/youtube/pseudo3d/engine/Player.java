package com.youtube.pseudo3d.engine;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.youtube.pseudo3d.engine.objects.missle.AxeMissle;
import com.youtube.pseudo3d.engine.objects.missle.PunchMissle;
import com.youtube.pseudo3d.engine.objects.missle.SwordMissle;
import com.youtube.pseudo3d.engine.objects.missle.WandMissle;
import com.youtube.pseudo3d.input.InputHandler;
import com.youtube.pseudo3d.resource.Animator;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class Player {

	public static int HEALTH = 100;
	
	private BufferedImage currentTexture;
	
	private Vector2d position;
	private Vector2d direction;

	private GameLogic raycaster;
	
	private double initialFov;
	private double actualFov;
	private double fovAcceleration;

	private double initialMovementSpeed;
	private double initialRotationSpeed;

	private double actualMovementSpeed;
	private double actualRotationSpeed;

	private double movementAcceleration;

	private double emittedLight;
	
	private Vector2d spriteScale;
		
	private Animator handAnimator;	
	private Animator swordAnimator;	
	private Animator axeAnimator;
	private Animator wandAnimator;		
	private boolean attack = false;
	private double attackDelay = 0;
	
	
	public Player(GameLogic raycaster) {
		this.raycaster = raycaster;

		initInitialFields();
		initAnimators();
	}

	private void initInitialFields() {

		initialMovementSpeed = 25.0;
		initialRotationSpeed = 15.0;

		actualMovementSpeed = initialMovementSpeed;
		actualRotationSpeed = initialRotationSpeed;

		movementAcceleration = .99;

		initialFov = 1;
		actualFov = initialFov;
		fovAcceleration = .01;

		position = new Vector2d(0, 0);
		direction = new Vector2d(-1, 0);

		spriteScale = new Vector2d(.44, .73);
	
	}
	
	private void initAnimators() {
		handAnimator = new Animator(TextureHolder.get(ID.PLAYER_HAND_ATTACK), 128, 128, 6);
		wandAnimator = new Animator(TextureHolder.get(ID.PLAYER_WAND_ATTACK), 64, 128, 6);
		swordAnimator = new Animator(TextureHolder.get(ID.PLAYER_SWORD_ATTACK), 64, 64, 6);
		axeAnimator = new Animator(TextureHolder.get(ID.PLAYER_AXE_ATTACK), 128, 128, 6);
	}

	public void handleInput(double elapsed) {
		handleInputHoldingItem();
		handleInputAttack();
		handleInputMovement(elapsed);
		handleInputSprinting(elapsed);
	}
	
	private void handleInputHoldingItem() {
		if(InputHandler.isKeyPressed(KeyEvent.VK_1))
			Items.holding = Items.Holding.HAND;
		if(InputHandler.isKeyPressed(KeyEvent.VK_2) && Items.unlocked.get(Items.Holding.LATTERN))
			Items.holding = Items.Holding.LATTERN;
		if(InputHandler.isKeyPressed(KeyEvent.VK_3) && Items.unlocked.get(Items.Holding.SWORD))
			Items.holding = Items.Holding.SWORD;
		if(InputHandler.isKeyPressed(KeyEvent.VK_4) && Items.unlocked.get(Items.Holding.AXE))
			Items.holding = Items.Holding.AXE;
		if(InputHandler.isKeyPressed(KeyEvent.VK_5) && Items.unlocked.get(Items.Holding.WAND))
			Items.holding = Items.Holding.WAND;
	}
	
	private void handleInputAttack() {
		if(InputHandler.isKeyPressed(KeyEvent.VK_SPACE))
			attack = true;
		else
			attack = false;
	}
	
	private void handleInputMovement(double elapsed) {
		double moveSpeed = actualMovementSpeed * elapsed;
		double rotationSpeed = actualRotationSpeed * elapsed;

		if (InputHandler.isKeyPressed(KeyEvent.VK_W))
			move(moveSpeed);

		if (InputHandler.isKeyPressed(KeyEvent.VK_S))
			move(-moveSpeed);

		if (InputHandler.isKeyPressed(KeyEvent.VK_D))
			rotate(-rotationSpeed);

		if (InputHandler.isKeyPressed(KeyEvent.VK_A))
			rotate(rotationSpeed);
	}
	
	private void handleInputSprinting(double elapsed) {
		if (InputHandler.isKeyPressed(KeyEvent.VK_SHIFT)
				&& (InputHandler.isKeyPressed(KeyEvent.VK_W) && actualMovementSpeed < 3.4 * initialMovementSpeed)
				&& !InputHandler.isKeyPressed(KeyEvent.VK_S)) {
			actualMovementSpeed += movementAcceleration;
		} else if (actualMovementSpeed > initialMovementSpeed) {
			actualMovementSpeed -= movementAcceleration;
		}

		if (InputHandler.isKeyPressed(KeyEvent.VK_SHIFT)
				&& (InputHandler.isKeyPressed(KeyEvent.VK_W) && actualFov < 2.0 * initialFov)
				&& !InputHandler.isKeyPressed(KeyEvent.VK_S)) {
			actualFov += fovAcceleration;
		} else if (actualFov > initialFov) {
			actualFov -= 8 * fovAcceleration;
		}
	}

	public void move(double delta) {
		// ONLY MOVE IF THE CURRENT TILE IS 0XFF000000 - BLACK
		if (raycaster.getCurrentLevel().getMap().getRGB((int) (position.x + direction.y * delta),
				(int) (position.y)) == 0xff000000)
			position.x += direction.y * delta;
		if (raycaster.getCurrentLevel().getMap().getRGB((int) (position.x),
				(int) (position.y + direction.x * delta)) == 0xff000000)
			position.y += direction.x * delta;
	}
	
	public void rotate(double angle) {
		double oldDirX = direction.x;
		direction.x = direction.x * Math.cos(angle) - direction.y * Math.sin(angle);
		direction.y = oldDirX * Math.sin(angle) + direction.y * Math.cos(angle);
		double oldPlaneX = raycaster.getCamera().getPlane().x;
		raycaster.getCamera().getPlane().x = raycaster.getCamera().getPlane().x * Math.cos(angle) - raycaster.getCamera().getPlane().y * Math.sin(angle);
		raycaster.getCamera().getPlane().y = oldPlaneX * Math.sin(angle) + raycaster.getCamera().getPlane().y * Math.cos(angle);
	}
	
	public void update(double elapsed) {
		updateCurrentTexture();
		updateEmittedLight();
		updateAttackAction();
	}
	
	private void updateCurrentTexture() {
		switch(Items.holding) {
		default:
		case HAND:{
			currentTexture = null;
			spriteScale.x = 1.3;
			spriteScale.y = 1.6;
		}
		break;
		case LATTERN:{
			currentTexture = TextureHolder.get(ID.PLAYER_LATTERN);
			spriteScale.x = .48;
			spriteScale.y = 0.89;
			
		}
			break;
		case SWORD:{
			currentTexture = TextureHolder.get(ID.PLAYER_SWORD);
			spriteScale.x = .55;
			spriteScale.y = 0.96;
		}
			break;
		case AXE:{
			currentTexture = TextureHolder.get(ID.PLAYER_AXE);
			spriteScale.x = .5;
			spriteScale.y = 1.3;
		}
			break;
		case WAND:{
			currentTexture = TextureHolder.get(ID.PLAYER_WAND);
			spriteScale.x = .5;
			spriteScale.y = 1.3;
		}
			break;
		}
	}
	
	private void updateEmittedLight() {
		switch(Items.holding) {
		default:
		case HAND:
		case AXE:
		case SWORD:
			emittedLight = 0.5;
			break;
		case WAND:
			emittedLight = 1.0;
			break;
		case LATTERN:
			emittedLight = 2.0;
			break;
		}
	}
	
	private void updateAttackAction() {
		if(attack) {
			attackDelay++;
			switch(Items.holding) {
			default:
			case HAND:
				updateAttackHand();
				break;
			case LATTERN:
				break;
			case SWORD:
				updateAttackSword();
				break;
			case AXE:
				updateAttackAxe();
				break;
			case WAND:
				updateAttackWand();
				break;
			}
		}
	}
	
	private void updateAttackHand() {
		int duration = 15;
		updateAnimation(handAnimator, duration);
			
		if(((raycaster.time / duration) % (handAnimator.getCurrentFrame().length) == 3 //3 IS THE MIDDLE ANIMATION FRAME
				|| (raycaster.time / duration) % (handAnimator.getCurrentFrame().length) == 5) //5 IS THE END ANIMATION FRAME
				&& attackDelay > 5) {
			attackDelay = 0;
			raycaster.getCurrentLevel().getGameObjects().add(new PunchMissle(raycaster, new Vector2d(position.x, position.y), new Vector2d(direction.x, direction.y), 50.0));
		}

	}
	
	private void updateAttackSword() {
		int duration = 15;
		updateAnimation(swordAnimator, duration);
			
		if((raycaster.time / duration) % (swordAnimator.getCurrentFrame().length) == 3 && attackDelay > 10) { //3 IS THE MIDDLE ANIMATION FRAME
			attackDelay = 0;
			raycaster.getCurrentLevel().getGameObjects().add(new SwordMissle(raycaster, new Vector2d(position.x, position.y), new Vector2d(direction.x, direction.y), 70.0));
		}

	}
	
	private void updateAttackAxe() {
		int duration = 10;
		updateAnimation(axeAnimator, duration);
			
		if((raycaster.time / duration) % (axeAnimator.getCurrentFrame().length) == 3 && attackDelay > 10) { //3 IS THE MIDDLE ANIMATION FRAME
			attackDelay = 0;
			raycaster.getCurrentLevel().getGameObjects().add(new AxeMissle(raycaster, new Vector2d(position.x, position.y), new Vector2d(direction.x, direction.y), 100.0));
		}

	}
	
	private void updateAttackWand() {
		int duration = 10;
		updateAnimation(wandAnimator, duration);
			
		if((raycaster.time / duration) % (wandAnimator.getCurrentFrame().length) == 3 && attackDelay > 10) { //3 IS THE MIDDLE ANIMATION FRAME
			raycaster.getCurrentLevel().getGameObjects().add(new WandMissle(raycaster, new Vector2d(position.x, position.y), new Vector2d(direction.x, direction.y), 100.0));
			attackDelay = 0;
		}

	}
	
	private void updateAnimation(Animator animator, int duration) {
		currentTexture = animator.getCurrentFrame()[(raycaster.time / duration) % animator.getCurrentFrame().length];
	}
	
	public void render(Graphics g) {		
		g.drawImage(currentTexture,
				(int)(raycaster.getMain().getWidth() - 
						spriteScale.x * raycaster.getMain().getWidth() + 50 + Math.cos(position.x * 2) * Math.cos(position.y * 2) * 10),
				(int)(raycaster.getMain().getHeight() - 
						spriteScale.y * raycaster.getMain().getHeight() + Math.sin(position.x * 2.5) * Math.sin(position.y * 2.5) * 50) + 100,
				(int)(spriteScale.x * raycaster.getMain().getWidth()),
				(int)(spriteScale.y * raycaster.getMain().getHeight()),
				null);
	}

	public Vector2d getPosition() {
		return position;
	}

	public void setPosition(Vector2d position) {
		this.position = position;
	}

	public Vector2d getDirection() {
		return direction;
	}

	public void setDirection(Vector2d direction) {
		this.direction = direction;
	}

	public double getInitialFov() {
		return initialFov;
	}

	public void setInitialFov(double initialFov) {
		this.initialFov = initialFov;
	}

	public double getActualFov() {
		return actualFov;
	}

	public void setActualFov(double actualFov) {
		this.actualFov = actualFov;
	}

	public double getFovAcceleration() {
		return fovAcceleration;
	}

	public void setFovAcceleration(double fovAcceleration) {
		this.fovAcceleration = fovAcceleration;
	}

	public double getInitialMovementSpeed() {
		return initialMovementSpeed;
	}

	public void setInitialMovementSpeed(double initialMovementSpeed) {
		this.initialMovementSpeed = initialMovementSpeed;
	}

	public double getInitialRotationSpeed() {
		return initialRotationSpeed;
	}

	public void setInitialRotationSpeed(double initialRotationSpeed) {
		this.initialRotationSpeed = initialRotationSpeed;
	}

	public double getActualMovementSpeed() {
		return actualMovementSpeed;
	}

	public void setActualMovementSpeed(double actualMovementSpeed) {
		this.actualMovementSpeed = actualMovementSpeed;
	}

	public double getActualRotationSpeed() {
		return actualRotationSpeed;
	}

	public void setActualRotationSpeed(double actualRotationSpeed) {
		this.actualRotationSpeed = actualRotationSpeed;
	}

	public double getMovementAcceleration() {
		return movementAcceleration;
	}

	public void setMovementAcceleration(double movementAcceleration) {
		this.movementAcceleration = movementAcceleration;
	}

	public double getEmittedLight() {
		return emittedLight;
	}

	public void setEmittedLight(double emittedLight) {
		this.emittedLight = emittedLight;
	}
	
}

package com.youtube.pseudo3d.engine.objects.enemy;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.Player;
import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.engine.objects.missle.BlueEnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.GreenEnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.OrangeEnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.PurpleEnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.RedEnemyMissle;
import com.youtube.pseudo3d.engine.objects.missle.YellowEnemyMissle;
import com.youtube.pseudo3d.engine.objects.still.Portal;
import com.youtube.pseudo3d.resource.Animator;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;

public class Thanos extends GameObject implements Enemy{

	public static final int DAMAGE = 1;
	
	public static boolean trigerred = false;
	
	public enum State{
		SLEEP,
		IDLE,
		FIGHT,
		SHOOT
	}
	
	public State state = State.SLEEP;
	
	protected double moveSpeed = .0;
	
	private int attackDelay = 0;
	private int trigerTimer = 0;
	private int deathTimer = 0;
	
	private Animator moveAnimator;
	private Animator attackAnimator;
	private Animator deathAnimator;
	
	public Thanos(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		texture = TextureHolder.get(ID.ENEMY_THANOS);
		
		moveAnimator = new Animator(TextureHolder.get(ID.ENEMY_THANOS_MOVING), 64, 64, 6);
		attackAnimator = new Animator(TextureHolder.get(ID.ENEMY_THANOS_ATTACK), 64, 64, 6);
		deathAnimator = new Animator(TextureHolder.get(ID.ENEMY_THANOS_DYING), 64, 64, 6);
		
		health = 2000;
	}

	@Override
	public void update(double elapsed) {
		if(health <= 0)
			dying = true;
		
		
		int duration = 10;
		int attackDuration = 20;
		int deathDuration = 6;
		
		if(dying) {
			deathTimer++;
			texture = deathAnimator.getCurrentFrame()[(deathTimer / deathDuration) % deathAnimator.getCurrentFrame().length];
			if((deathTimer / deathDuration) % (deathAnimator.getCurrentFrame().length) == 5) {
				dead = true;	
				raycaster.getCurrentLevel().getGameObjects().add(new Portal(raycaster, position));
			}
		} else {
			//TRIGERRED LOGIC
			if(state == State.FIGHT) {			
				trigerTimer++;
				
				moving = true;
				
				moveSpeed = 5D * elapsed;
				
				texture = moveAnimator.getCurrentFrame()[(trigerTimer / duration) % attackAnimator.getCurrentFrame().length];
				
				if(MathUtil.pythagoreanDistance(position, raycaster.getPlayer().getPosition()) <= 1
						&& (trigerTimer / attackDuration) % (attackAnimator.getCurrentFrame().length) == 3)
					Player.HEALTH -= DAMAGE;
				
				moveX((raycaster.getPlayer().getPosition().x - position.x) * moveSpeed);
				moveY((raycaster.getPlayer().getPosition().y - position.y) * moveSpeed);
	
				if(trigerTimer >= 400) {
					state = State.SHOOT;
					trigerTimer = 0;
				}
			} else if(state == State.SHOOT){
				attackDelay++;
				trigerTimer++;
				
				raycaster.getPlayer().setEmittedLight(raycaster.getPlayer().getEmittedLight()*10);
							
				position.x = 12.5;
				position.y = 4.0;
				
				texture = attackAnimator.getCurrentFrame()[(trigerTimer / attackDuration) % attackAnimator.getCurrentFrame().length];
	
				if((trigerTimer / attackDuration) % (attackAnimator.getCurrentFrame().length) == 0 && attackDelay > 3) {
						raycaster.getCurrentLevel().getGameObjects().add(new BlueEnemyMissle(raycaster, new Vector2d(position.x, position.y)));
						attackDelay = 0;
				}
				if((trigerTimer / attackDuration) % (attackAnimator.getCurrentFrame().length) == 1 && attackDelay > 3) {
					raycaster.getCurrentLevel().getGameObjects().add(new OrangeEnemyMissle(raycaster, new Vector2d(position.x, position.y)));
					attackDelay = 0;
				}
				if((trigerTimer / attackDuration) % (attackAnimator.getCurrentFrame().length) == 2 && attackDelay > 3) {
					raycaster.getCurrentLevel().getGameObjects().add(new RedEnemyMissle(raycaster, new Vector2d(position.x, position.y)));
					attackDelay = 0;
				}
				if((trigerTimer / attackDuration) % (attackAnimator.getCurrentFrame().length) == 3 && attackDelay > 3) {
					raycaster.getCurrentLevel().getGameObjects().add(new GreenEnemyMissle(raycaster, new Vector2d(position.x, position.y)));
					attackDelay = 0;
				}
				if((trigerTimer / attackDuration) % (attackAnimator.getCurrentFrame().length) == 4 && attackDelay > 3) {
					raycaster.getCurrentLevel().getGameObjects().add(new PurpleEnemyMissle(raycaster, new Vector2d(position.x, position.y)));
					attackDelay = 0;
				}
				if((trigerTimer / attackDuration) % (attackAnimator.getCurrentFrame().length) == 5 && attackDelay > 3) {
					raycaster.getCurrentLevel().getGameObjects().add(new YellowEnemyMissle(raycaster, new Vector2d(position.x, position.y)));
					attackDelay = 0;
				}
				
				if(trigerTimer >= 100) {
					state = State.IDLE;
					trigerTimer = 0;
				}
				
			} else if(state == State.IDLE){
				trigerTimer++;
				
				moving = false;	
				
				texture = TextureHolder.get(ID.ENEMY_THANOS);
	
				if(trigerTimer >= 300) {
					state = State.FIGHT;
					trigerTimer = 0;
				}
			}
			
			else {
				moving = false;
				texture = TextureHolder.get(ID.ENEMY_THANOS);
			}
		}
	}
	
	public void moveX(double delta) {
		// ONLY MOVE IF THE CURRENT TILE IS 0XFF000000 - BLACK
		if (raycaster.getCurrentLevel().getMap().getRGB((int) (position.x + delta),
				(int) (position.y)) == 0xff000000)
			position.x += delta;
	}
	
	public void moveY(double delta) {
		// ONLY MOVE IF THE CURRENT TILE IS 0XFF000000 - BLACK
		if (raycaster.getCurrentLevel().getMap().getRGB((int) (position.x),
				(int) (position.y + delta)) == 0xff000000)
			position.y += delta;
	}

}

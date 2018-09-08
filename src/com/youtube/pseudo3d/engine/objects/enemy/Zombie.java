package com.youtube.pseudo3d.engine.objects.enemy;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.Player;
import com.youtube.pseudo3d.engine.objects.PathfindingObject;
import com.youtube.pseudo3d.resource.Animator;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;

public class Zombie extends PathfindingObject implements Enemy{

	public static int DAMAGE = 20;
	
	private Animator moveAnimator;
	private Animator deathAnimator;
	
	private int deathTimer = 0;
	
	public Zombie(GameLogic raycaster, Vector2d position, double reactDistance) {
		super(raycaster, position, reactDistance);
		
		moveAnimator = new Animator(TextureHolder.get(ID.ENEMY_ZOMBIE_MOVING), 64, 64, 6);
		deathAnimator = new Animator(TextureHolder.get(ID.ENEMY_ZOMBIE_DYING), 64, 64, 6);		
	
		health = 300;
	}
	
	@Override
	public void update(double elapsed) {
		super.update(elapsed);
		
		if(health <= 0)
			dying = true;
		
		int duration = 10;
		int deathDuration = 6;
		
		if(dying) {
			deathTimer++;
			texture = deathAnimator.getCurrentFrame()[(deathTimer / deathDuration) % deathAnimator.getCurrentFrame().length];
			if((deathTimer / deathDuration) % (deathAnimator.getCurrentFrame().length) == 5) {
				dead = true;	
				raycaster.getGameObjects().add(new ZombieCorpse(raycaster, position));
			}
		}
		else {	
			if(moving) 
				texture = moveAnimator.getCurrentFrame()[(raycaster.time / duration) % moveAnimator.getCurrentFrame().length];
			else
				texture = TextureHolder.get(ID.ENEMY_ZOMBIE);
		
		
			if(MathUtil.pythagoreanDistance(raycaster.getPlayer().getPosition(), position) <= 1.6
					&& raycaster.time % 60 == 0)
				Player.HEALTH -= DAMAGE;
		}
	}

}

package com.youtube.pseudo3d.engine.objects.enemy;

import com.youtube.pseudo3d.engine.Player;
import com.youtube.pseudo3d.engine.objects.FollowingObject;
import com.youtube.pseudo3d.engine.objects.collect.GoldCollect;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.Animator;
import com.youtube.pseudo3d.resource.AudioPaths;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.AudioHandler;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;

public class Rat extends FollowingObject implements Enemy{

	public static int DAMAGE = 10;
	
	private Animator animator;
	private Animator moveAnimator;
	private Animator deathAnimator;
		
	private int deathTimer = 0;
	
	public Rat(GameLogic raycaster, Vector2d position, double reactDistance) {
		super(raycaster, position, reactDistance);
		
		animator = new Animator(TextureHolder.get(ID.ENEMY_RAT), 64, 64, 6);
		moveAnimator = new Animator(TextureHolder.get(ID.ENEMY_RAT_MOVING), 64, 64, 6);
		deathAnimator = new Animator(TextureHolder.get(ID.ENEMY_RAT_DYING), 64, 64, 6);
		
		health = 100;
	}
	
	@Override
	public void update(double elapsed) {
		super.update(elapsed);
		
		if(health <= 0)
			dying = true;
		
		int duration = 10;
		int deathDuration = 3;
		
		if(dying) {
			deathTimer++;
			texture = deathAnimator.getCurrentFrame()[(deathTimer / deathDuration) % deathAnimator.getCurrentFrame().length];
			if((deathTimer / deathDuration) % (deathAnimator.getCurrentFrame().length) == 5) {
				dead = true;	
				raycaster.getCurrentLevel().getGameObjects().add(new RatCorpse(raycaster, position));
				
				for(int i=0; i<2; i++)
					raycaster.getCurrentLevel().getGameObjects().add(new GoldCollect(raycaster, position));

			}
		}
		else {	
			if(moving) 
				texture = moveAnimator.getCurrentFrame()[(raycaster.time / duration) % moveAnimator.getCurrentFrame().length];
			else
				texture = animator.getCurrentFrame()[(raycaster.time / duration) % animator.getCurrentFrame().length];
		
		
			if(MathUtil.pythagoreanDistance(raycaster.getPlayer().getPosition(), position) <= 1.6
					&& raycaster.time % 30 == 0) {
				Player.HEALTH -= DAMAGE;
				AudioHandler.playAudio(AudioPaths.RAT_SQUEAK).start();
			}
		}
	}
	
}

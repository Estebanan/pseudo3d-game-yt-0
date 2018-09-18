package com.youtube.pseudo3d.engine.objects.enemy;

import com.youtube.pseudo3d.engine.objects.RandomlyMovingObject;
import com.youtube.pseudo3d.engine.objects.collect.GoldCollect;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.Animator;
import com.youtube.pseudo3d.resource.AudioPaths;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.AudioHandler;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;

public class Bat extends RandomlyMovingObject implements Enemy{
	
	private Animator animator;
	private Animator deathAnimator;
		
	private int deathTimer = 0;

	public Bat(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		animator = new Animator(TextureHolder.get(ID.ENEMY_BAT), 64, 64, 6);
		deathAnimator = new Animator(TextureHolder.get(ID.ENEMY_BAT_DYING), 64, 64, 6);
		
		health = 20;
	}
	
	@Override
	public void update(double elapsed) {
		super.update(elapsed);
				
		if(raycaster.time % 40 == 0 && MathUtil.pythagoreanDistance(position, raycaster.getPlayer().getPosition()) <= 2)
			AudioHandler.playAudio(AudioPaths.BAT_SQUEAK).start();
		
		if(health <= 0)
			dying = true;
		
		int duration = 10;
		int deathDuration = 3;
		if(dying) {
			deathTimer++;
			texture = deathAnimator.getCurrentFrame()[(deathTimer / deathDuration) % deathAnimator.getCurrentFrame().length];
			if((deathTimer / deathDuration) % (deathAnimator.getCurrentFrame().length) == 5) {
				dead = true;			
				raycaster.getCurrentLevel().getGameObjects().add(new BatCorpse(raycaster, position));
				raycaster.getCurrentLevel().getGameObjects().add(new GoldCollect(raycaster, position));
			}
		}
		else
			texture = animator.getCurrentFrame()[(raycaster.time / duration) % animator.getCurrentFrame().length];
	
	}
}

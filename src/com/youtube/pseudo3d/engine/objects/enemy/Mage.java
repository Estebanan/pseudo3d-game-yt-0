package com.youtube.pseudo3d.engine.objects.enemy;

import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.engine.objects.collect.GoldCollect;
import com.youtube.pseudo3d.engine.objects.missle.GreenEnemyMissle;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.Animator;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;

public class Mage extends GameObject implements Enemy{

	private Animator fightAnimator;
	private Animator deathAnimator;
	
	private int attackDelay = 0;
	private int deathTimer = 0;
	
	public Mage(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		fightAnimator = new Animator(TextureHolder.get(ID.ENEMY_MAGE_ATTACK), 64, 64, 6);
		deathAnimator = new Animator(TextureHolder.get(ID.ENEMY_MAGE_DYING), 64, 64, 6);
		
		texture = TextureHolder.get(ID.ENEMY_MAGE);
		
		health = 200;
	}

	@Override
	public void update(double elapsed) {
		attackDelay++;
		
		if(health <= 0)
			dying = true;
		
		int duration = 20;
		int deathDuration = 5;
		
		if(dying) {
			deathTimer++;
			texture = deathAnimator.getCurrentFrame()[(deathTimer / deathDuration) % deathAnimator.getCurrentFrame().length];
			if((deathTimer / deathDuration) % (deathAnimator.getCurrentFrame().length) == 5) {
				dead = true;
				
				raycaster.getCurrentLevel().getGameObjects().add(new MageCorpse(raycaster, position));
				
				for(int i=0; i<3; i++)
					raycaster.getCurrentLevel().getGameObjects().add(new GoldCollect(raycaster, position));
			}
		}else {
			if(MathUtil.pythagoreanDistance(raycaster.getPlayer().getPosition(), position) <= 7) {
				texture = fightAnimator.getCurrentFrame()[(raycaster.time / duration) % fightAnimator.getCurrentFrame().length];

				if((raycaster.time / duration) % (fightAnimator.getCurrentFrame().length) == 3 && attackDelay > 10) {
						raycaster.getCurrentLevel().getGameObjects().add(new GreenEnemyMissle(raycaster, new Vector2d(position.x, position.y)));
						attackDelay = 0;
				}
			}else {
				texture = TextureHolder.get(ID.ENEMY_MAGE);
			}	
		}	
	}

}

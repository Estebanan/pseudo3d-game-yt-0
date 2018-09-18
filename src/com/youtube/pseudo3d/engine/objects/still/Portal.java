package com.youtube.pseudo3d.engine.objects.still;

import com.youtube.pseudo3d.engine.objects.GameObject;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.Animator;
import com.youtube.pseudo3d.resource.AudioPaths;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.AudioHandler;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Vector2d;

public class Portal extends GameObject{

	private Animator animator;
	
	public Portal(GameLogic raycaster, Vector2d position) {
		super(raycaster, position);
		
		animator = new Animator(TextureHolder.get(ID.PORTAL), 64, 64, 12);
	}

	@Override
	public void update(double elapsed) {
		int duration = 10;
		
		texture = animator.getCurrentFrame()[(raycaster.time / duration) % animator.getCurrentFrame().length];
	
		if(MathUtil.pythagoreanDistance(position, raycaster.getPlayer().getPosition()) <= 3
				&& raycaster.time % 40 == 0)
			AudioHandler.playAudio(AudioPaths.PORTAL).start();
	}

}

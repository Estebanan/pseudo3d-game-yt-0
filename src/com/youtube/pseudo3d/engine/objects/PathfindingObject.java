package com.youtube.pseudo3d.engine.objects;

import java.util.List;

import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.util.MathUtil;
import com.youtube.pseudo3d.util.Node;
import com.youtube.pseudo3d.util.Vector2d;
import com.youtube.pseudo3d.util.Vector2i;

public abstract class PathfindingObject extends GameObject{

	protected double moveSpeed = .0;
	protected double reactDistance;
		
	private List<Node> path = null;
	
	public PathfindingObject(GameLogic raycaster, Vector2d position, double reactDistance) {
		super(raycaster, position);	
		this.reactDistance = reactDistance;
	}
	
	@Override
	public void update(double elapsed) {
		if(MathUtil.pythagoreanDistance(raycaster.getPlayer().getPosition(), position) < reactDistance
				&& MathUtil.pythagoreanDistance(raycaster.getPlayer().getPosition(), position) > 1) {
			moving = true;			
			moveSpeed = 10D * elapsed;

			Vector2i start = new Vector2i((int)position.x, (int)position.y);
			Vector2i goal = new Vector2i((int)raycaster.getPlayer().getPosition().x, (int)raycaster.getPlayer().getPosition().y);
			
			if(raycaster.time % 5 == 0)
				path = MathUtil.findPath(raycaster.getCurrentLevel(), start, goal);
			
			if(path != null && path.size() > 0) {
					Vector2i move = path.get(path.size() - 1).position;
					
					if(position.x > move.x + .5)
						moveX(-moveSpeed);
					if(position.x < move.x + .5)
						moveX(moveSpeed);
					
					if(position.y > move.y + .5)
						moveY(-moveSpeed);
					if(position.y < move.y + .5)
						moveY(moveSpeed);
			}
			
		} else
			moving = false;		
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

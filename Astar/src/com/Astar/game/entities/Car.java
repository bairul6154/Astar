package com.Astar.game.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.Astar.game.Game;
import com.Astar.game.dataStructures.Grid;
import com.Astar.game.dataStructures.Point;

public class Car extends Entity {
	
	private int currentIndex, slowDownIndex, stoppingDist = 50;
	private boolean startFollowPath = false;
	private Point[] path;
	private float speed;

	public Car(Game game, Grid grid, Point point, int size, float speed) {
		super(game, grid, point, size);
		this.speed = speed;
	}
	
	public void updatePath(Point[] path) {
		this.path = path;
		currentIndex = 0;
		startFollowPath = true;
		
		if (path == null)
			return;
		
		float distToEnd = 0;
		for (int i=path.length-1; i>0; i--) {
			distToEnd += path[i].getMagnitudeFrom(path[i-1]);
			if (distToEnd > stoppingDist) {
				slowDownIndex = i;
				break;
			}
		}
		if (distToEnd < stoppingDist)
			slowDownIndex = 0;
		for (int i=path.length-1; i>0; i--) {
			if (!grid.blockedByWall(point, path[i])) {
				currentIndex = i;
				break;
			}
		}
	}
	
	private void followPath() {
		if (startFollowPath && path != null && currentIndex < path.length) {
			float speedPercent = 1;
			if (currentIndex >= slowDownIndex) {
				speedPercent = point.getMagnitudeFrom(path[path.length-1])/stoppingDist;
				speedPercent = Math.max(0, Math.min(speedPercent, 1));
				
				if (speedPercent < 0.05)
					startFollowPath = false;
			}
			
			point.moveTowards(path[currentIndex], speed*speedPercent);
			
			if (point.approximates(path[currentIndex], speed)) {
				if (!grid.blockedByWall(point, path[path.length-1]))
					currentIndex = path.length-1;
				else {
					currentIndex++;
					for (int i=currentIndex+1; i<path.length; i++) {
						if (!grid.blockedByWall(point, path[i])) {
							currentIndex = i;
							break;
						}
					}
				}
			}
		}
		
	}

	@Override
	public void tick() {
		followPath();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillOval(point.intX()-size/2, point.intY()-size/2, size, size);
		g.setColor(Color.black);
		if (path != null) {
			for (Point p: path) {
				g.fillOval(p.intX()-3, p.intY()-3, 6,6);
			}
		}
	}
	
}

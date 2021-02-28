package com.Astar.game.entities;

import java.awt.Graphics;

import com.Astar.game.Game;
import com.Astar.game.dataStructures.Grid;
import com.Astar.game.dataStructures.Point;

public abstract class Entity {
	
	protected Point point;
	protected Game game;
	protected int size;
	protected Grid grid;
	
	public Entity(Game game, Grid grid, Point point, int size) {
		this.game = game;
		this.point = new Point(point.getX()+size/2,point.getY()+size/2);
		this.grid = grid;
		this.size = size;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
}

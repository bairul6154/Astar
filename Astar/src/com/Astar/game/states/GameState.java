package com.Astar.game.states;

import java.awt.Color;
import java.awt.Graphics;

import com.Astar.game.Game;
import com.Astar.game.dataStructures.Grid;
import com.Astar.game.dataStructures.PathFinding;
import com.Astar.game.dataStructures.Point;
import com.Astar.game.entities.Car;
import com.Astar.game.entities.Wall;

public class GameState extends State {
	
	private int size = 12;
	private Wall[] walls;
	private Grid grid;
	private PathFinding pathFinding;
	
	private Car car;
	private Point endP = new Point(200,200);

	public GameState(Game game) {
		super(game);
		walls = new Wall[3];
		wallCreate();
		
		grid = new Grid(game.width(),game.height(),size,walls);
		pathFinding = new PathFinding(grid);
		car = new Car(game, grid, new Point(6*size,4*size), size, 3);
	}

	@Override
	public void tick() {
		if (game.mouseManager().isLeftClicked()) {
			endP.setPoint(game.mouseX(), game.mouseY());
			car.updatePath(pathFinding.PathFind(car.getPoint(), endP));
			
			game.mouseManager().setLeftClicked(false);
		}
		car.tick();
	}

	@Override
	public void render(Graphics g) {
		grid.render(g);
		for (Wall w: walls) {
			w.render(g);
		}
		
		car.render(g);
		g.setColor(Color.cyan);
		g.drawOval(endP.intX()-size/2, endP.intY()-size/2, size,size);
		
	}
	
	private void wallCreate() {
		Point[] vertices = new Point[6];
		vertices[0] = new Point(73,125);
		vertices[1] = new Point(213,66);
		vertices[2] = new Point(213,202);
		vertices[3] = new Point(132,231);
		vertices[4] = new Point(160,124);
		vertices[5] = vertices[0];
		Point[] vertices2 = new Point[6];
		vertices2[0] = new Point(102,42);
		vertices2[1] = new Point(317,41);
		vertices2[2] = new Point(203,132);
		vertices2[3] = new Point(155,252);
		vertices2[4] = new Point(160,124);
		vertices2[5] = vertices2[0];
		Point[] vertices3 = new Point[6];
		vertices3[0] = new Point(102,42);
		vertices3[1] = new Point(317,41);
		vertices3[2] = new Point(203,132);
		vertices3[3] = new Point(155,252);
		vertices3[4] = new Point(160,124);
		vertices3[5] = vertices3[0];
		walls[0] = new Wall(vertices);
		walls[1] = new Wall(vertices2);
		walls[2] = new Wall(vertices3);
		walls[0].translate(50, 0);
		walls[1].translate(100, 220);
		walls[2].rotate(1);
		walls[2].translate(380, 70);
	}

}

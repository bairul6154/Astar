package com.Astar.game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import com.Astar.game.dataStructures.Point;

public class Wall {
	
	private Polygon shape;
	private int[] xPoints;
	private int[] yPoints;
	private Point[] vertices;
	private int length;
	
	public Wall(Point[] vertices) {
		this.vertices = vertices;
		length = vertices.length;
		shape = new Polygon();
		xPoints = new int[length];
		yPoints = new int[length];
		
		for (int i=0; i<length; i++) {
			xPoints[i] = vertices[i].intX();
			yPoints[i] = vertices[i].intY();
		}
		
		shape.xpoints = xPoints;
		shape.ypoints = yPoints;
		shape.npoints = length;
	}
	
	public boolean contains(Point p, int size) {
		return shape.contains(p.intX()+size/2, p.intY()+size/2);
	}
	
	public void translate(int transX, int transY) {
		for (int i=0; i<length; i++) {
			xPoints[i] += transX;
			yPoints[i] += transY;
			vertices[i].setPoint(xPoints[i], yPoints[i]);
		}
		
		shape.xpoints = xPoints;
		shape.ypoints = yPoints;
	}
	
	public void rotate(int numRotate) {
		int xToOrigin = xPoints[0];
		int yToOrigin = yPoints[0];
		for (int r=0; r< numRotate%4; r++) {
			for (int i=1; i<length; i++) {
				int tempX = xPoints[i]-xToOrigin;
				int tempY = yPoints[i]-yToOrigin;
				
				xPoints[i] = -tempY+xToOrigin;
				yPoints[i] = tempX+yToOrigin;
				vertices[i].setPoint(xPoints[i], yPoints[i]);
			}
		}
		shape.xpoints = xPoints;
		shape.ypoints = yPoints;
	}
	
	public Point[] getVertices() {
		return vertices;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.drawPolygon(shape);
	}
}

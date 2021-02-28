package com.Astar.game.dataStructures;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.Astar.game.entities.Wall;

public class Grid {
	private Node[][] grid;
	private int nodeDiameter,gridSizeX,gridSizeY;
	private Wall[] walls;
	public ArrayList<Node> path;
	private Point intersection;
	
	public Grid(int width, int height, int nodeDiameter, Wall[] walls) {
		this.nodeDiameter = nodeDiameter;
		this.walls = walls;
		
		gridSizeX = Math.round(width/nodeDiameter);
		gridSizeY = Math.round(height/nodeDiameter);
		
		createGrid();
	}
	
	private void createGrid() {
		grid = new Node[gridSizeY][gridSizeX];
		
		for (int y=0; y<gridSizeY; y++) {
			for (int x=0; x<gridSizeX; x++) {
				Point worldPoint = new Point(x*nodeDiameter,y*nodeDiameter);
				boolean walkable = true;
				
				for (Wall w: walls) {
					if (w.contains(worldPoint, nodeDiameter))
						walkable = false;
				}
				
				grid[y][x] = new Node(worldPoint,walkable);
			}
		}
	}
	
	public Node getNodeFromPoint(Point point) {
		float percentX = point.getX()/nodeDiameter;
		float percentY = point.getY()/nodeDiameter;
		
		percentX = Math.max(0, Math.min(percentX, gridSizeX-1));
		percentY = Math.max(0, Math.min(percentY, gridSizeY-1));
		
		int x = (int) Math.floor(percentX);
		int y= (int) Math.floor(percentY);
		
		return grid[y][x];
	}
	
	public ArrayList<Node> getNeighbors(Node n) {
		ArrayList<Node> neighbors = new ArrayList<Node>();
		
		for (int y=-1; y<=1; y++) {
			for (int x=-1; x<=1; x++) {
				if (y == 0 && x == 0)
					continue;
				
				int checkX = n.getPoint().intX()/nodeDiameter+x;
				int checkY = n.getPoint().intY()/nodeDiameter+y;
				
				if (checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeY) {
					if (grid[checkY][checkX].isWalkable())
						neighbors.add(grid[checkY][checkX]);
				}
			}
		}
		
		return neighbors;
	}
	
	public boolean blockedByWall(Point startP, Point endP) {
		float m1 = startP.getSlopeFrom(endP);
		float b1 = startP.getYInterFrom(endP);
		
		for (Wall w: walls) {
			Point[] vertices = w.getVertices();
			for (int i=0; i<vertices.length-1; i++) {
				float m2 = vertices[i].getSlopeFrom(vertices[i+1]);
				float b2 = vertices[i].getYInterFrom(vertices[i+1]);
				//System.out.println("M1: "+m1+", B1: "+b1+", M2: "+m2+", B2: "+b2);
				
				if (m1 == m2)
					continue;
					
				float x = (b2-b1) / (m1-m2);
				float y = m1*x+b1;
				
				if (m1 == Float.POSITIVE_INFINITY) {
					x = startP.getX();
					y = m2*x+b2;
				} else if (m2 == Float.POSITIVE_INFINITY) {
					x = vertices[i].getX();
					y = m1*x+b1;
				}
					
				Point inter = new Point(x,y);
				float dirToEnd = startP.getDirectionFrom(endP);
				float dirToInter = startP.getDirectionFrom(inter);
				
				intersection = inter;
				if (Math.round(dirToEnd*100) == Math.round(dirToInter*100)) {
					float magToEnd = startP.getMagnitudeFrom(endP);
					float magToInter = startP.getMagnitudeFrom(inter);
					
					if (magToEnd > magToInter) {
						float magVerts = vertices[i].getMagnitudeFrom(vertices[i+1]);
						if (inter.getMagnitudeFrom(vertices[i]) < magVerts && inter.getMagnitudeFrom(vertices[i+1]) < magVerts)
							return true;
					}
				}
			}
		}
		intersection = null;
		return false;
	}
	
	public void render(Graphics g) {
		for (Node[] i: grid) {
			for (Node n: i) {
				if (path != null && path.contains(n))
					g.setColor(Color.black);
				else if (n.isWalkable())
					g.setColor(Color.gray);
				else
					g.setColor(Color.red);
				
				g.fillRect(n.getPoint().intX(), n.getPoint().intY(),nodeDiameter-1,nodeDiameter-1);
			}
		}
//		g.setColor(Color.black);
//		if (intersection != null)
//			g.fillOval(intersection.intX()-5, intersection.intY()-5, 10,10);
	}
	
	public int nodeDiameter() {
		return nodeDiameter;
	}
}

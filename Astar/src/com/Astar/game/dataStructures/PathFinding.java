package com.Astar.game.dataStructures;

import java.util.ArrayList;
import java.util.HashSet;

public class PathFinding {
	private Grid grid;
	
	public PathFinding(Grid grid) {
		this.grid = grid;
	}
	
	public Point[] PathFind(Point startP, Point endP) {
		Node startNode = grid.getNodeFromPoint(startP);
		Node endNode = grid.getNodeFromPoint(endP);
		
		if (endNode.isWalkable()) {
			Heap<Node> openSet = new Heap<Node>();
			HashSet<Node> closedSet = new HashSet<Node>();
			
			if (startNode == endNode)
				return null;
			
			openSet.add(startNode);
			
			while (openSet.size()>0) {
				Node currentNode = openSet.removeFirst();
				
//				for (int i=1; i< openSet.size(); i++) {
//					if (openSet.get(i).fCost() <= currentNode.fCost() && openSet.get(i).hCost < currentNode.hCost)
//						currentNode = openSet.get(i);
//				}//each iteration search through each node in openset for the lowest fcost
//				
//				openSet.remove(currentNode);
				closedSet.add(currentNode);
				
				if (currentNode == endNode) {
					return retracePath(startNode,endNode);
				}
				
				for (Node neighbor: grid.getNeighbors(currentNode)) {
					if (closedSet.contains(neighbor))
						continue;
					
					int newMvtCostToNeighbor = currentNode.gCost + getDistance(currentNode,neighbor);
					if (newMvtCostToNeighbor < neighbor.gCost || !openSet.contains(neighbor)) {
						neighbor.gCost = newMvtCostToNeighbor;
						neighbor.hCost = getDistance(neighbor,endNode);
						neighbor.setParent(currentNode);
						
						if (!openSet.contains(neighbor))
							openSet.add(neighbor);
						else
							openSet.updateItem(neighbor);
					}
				}
			}
		}
		return null;
	}
	
	private Point[] retracePath(Node startNode, Node endNode) {
		Node currentNode = endNode;
		ArrayList<Node> path = new ArrayList<Node>();
		
		while (currentNode != startNode) {
			path.add(currentNode);
			currentNode = currentNode.getParent();
		}
		
		for (int i=0; i<path.size()/2; i++) {
			Node temp = path.get(i);
			
			int index = path.size()-1-i;
			path.set(i, path.get(index));
			path.set(index, temp);
		}
		grid.path = path;
		
		return simplifyPath(path);
	}
	
	private Point[] simplifyPath(ArrayList<Node> path) {
		ArrayList<Point> wayPoints = new ArrayList<Point>();
		float directionOld = 0;
		Point wayPoint;
		
		for (int i=0; i<path.size(); i++) {
			Node currentNode = path.get(i);
			wayPoint = new Point (currentNode.getPoint().getX(), currentNode.getPoint().getY());
			if (i < path.size()-1) {
				Node nextNode = path.get(i+1);
				float directionNew = currentNode.getPoint().getDirectionFrom(nextNode.getPoint());
				
				if (directionOld != directionNew) {
					wayPoint.chgPoint(grid.nodeDiameter()/2, grid.nodeDiameter()/2);
					wayPoints.add(wayPoint);
				}
				directionOld = directionNew;
			} else {
				wayPoint.chgPoint(grid.nodeDiameter()/2, grid.nodeDiameter()/2);
				wayPoints.add(wayPoint);
			}		
		}
		return wayPoints.toArray(new Point[0]);
	}
	private int getDistance(Node a, Node b) {
		float distX = Math.abs(b.getPoint().getX() - a.getPoint().getX());
		float distY = Math.abs(b.getPoint().getY() - a.getPoint().getY());
		
		if (distX > distY)
			return (int) (14*distY+10*(distX-distY));
		return (int) (14*distX+10*(distY-distX));
	}
}

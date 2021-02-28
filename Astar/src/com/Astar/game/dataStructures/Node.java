package com.Astar.game.dataStructures;

public class Node extends IHeapItem<Node>{
	
	private Point point;
	private boolean walkable;
	private Node parent;
	
	public int hCost,gCost;
	private int heapIndex;
	
	public Node(Point point, boolean walkable) {
		this.point = point;
		this.walkable = walkable;
	}
	
	public int fCost() {
		return hCost+gCost;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	@Override
	public int compareTo(Node other) {
		Integer FCost = fCost();
		
		int compare = FCost.compareTo(other.fCost());
		if (fCost() == other.fCost()) {
			Integer HCost = hCost;
			compare = HCost.compareTo(other.hCost);
		}
		
		return -compare;
	}
	
}

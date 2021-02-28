package com.Astar.game.dataStructures;

public class Point {
	
	private float x,y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getMagnitudeFrom(Point other) {
		float dx = other.getX() - x;
		float dy = other.getY() - y;
		
		return (float) Math.sqrt(dx*dx + dy*dy);
	}
	
	public float getDirectionFrom(Point other) {
		float dx = other.getX() - x;
		float dy = other.getY() - y;
		
		return (float) Math.atan2(dy, dx);
	}
	
	public void moveTowards(Point destination, float speed) {
		if (this.approximates(destination, speed))
			return;
		
		float direction = getDirectionFrom(destination);
		float vx = (float) Math.cos(direction);
		float vy = (float) Math.sin(direction);
		
		x+=vx*speed;
		y+=vy*speed;
	}
	
	public boolean equals(Point other) {
		return x == other.getX() && y == other.getY();
	}
	
	public boolean approximates(Point other, float speed) {
		if (this.getMagnitudeFrom(other) < speed/2) {
			x = other.getX();
			y = other.getY();
			return true;
		}
		return false;
	}
	
	public float getSlopeFrom(Point other) {
		if (other.getX() == x)
			return Float.POSITIVE_INFINITY;
		return (other.getY() - y) / (other.getX() - x);
	}
	
	public float getYInterFrom(Point other) {
		return y - getSlopeFrom(other)*x;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public int intX() {
		return Math.round(x);
	}
	
	public int intY() {
		return Math.round(y);
	}
	
	public void setPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void chgPoint(float dx, float dy) {
		x += dx;
		y += dy;
	}
	
	public String toString() {
		return "("+x+", "+y+")";
	}
}

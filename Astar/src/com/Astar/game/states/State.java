package com.Astar.game.states;

import java.awt.Graphics;

import com.Astar.game.Game;

public abstract class State {
	
	protected Game game;
	private static State currentState = null;
	
	public State(Game game) {
		this.game = game;
	}
	
	public static void setState(State newState) {
		currentState = newState;
	}
	
	public static State getState() {
		return currentState;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
}

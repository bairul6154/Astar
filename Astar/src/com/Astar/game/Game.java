package com.Astar.game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.Astar.game.display.Display;
import com.Astar.game.inputs.MouseManager;
import com.Astar.game.states.GameState;
import com.Astar.game.states.State;

public class Game implements Runnable{
	
	private boolean running;
	private Thread thread;
	
	//Display & render
	private Display display;
	private BufferStrategy bs;
	private Graphics g; 
	
	//States
	private State gameState;
	
	//inputs
	private MouseManager mouseManager;

	@Override
	public void run() {
		init();
		
		int fps = 60;
		double nsPerFrame = 1000000000/fps;
		double delta = 0;
		long now;
		long past = System.nanoTime();
		
		while (running) {//game loop
			now = System.nanoTime();
			delta += (now-past)/nsPerFrame;
			past = now;
			
			if (delta >= 1) {
				tick();
				delta--;
			}
			
			try {
				thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			render();
		}
		stop();
	}
	
	private void init() {
		display = new Display("Astar",128,5);
		gameState = new GameState(this);
		gameState.setState(gameState);
		mouseManager = new MouseManager();
		
		display.frame().addMouseListener(mouseManager);
		display.frame().addMouseMotionListener(mouseManager);
		display.canvas().addMouseListener(mouseManager);
		display.canvas().addMouseMotionListener(mouseManager);
	}

	private void tick() {
		if (State.getState() != null)
			State.getState().tick();
	}
	
	private void render() {
		bs = display.canvas().getBufferStrategy();
		
		if (bs == null) {
			display.canvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, display.width(), display.height());
		//start draw stuff
		if (State.getState() != null)
			State.getState().render(g);
		//end draw stuff
		bs.show();
		g.dispose();
	}


	public synchronized void start() {
		if (running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if (!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public MouseManager mouseManager() {
		return mouseManager;
	}
	
	public int mouseX() {
		return mouseManager.mouseX();
	}
	
	public int mouseY() {
		return mouseManager.mouseY();
	}
	
	public int width() {
		return display.width();
	}
	
	public int height() {
		return display.height();
	}
}

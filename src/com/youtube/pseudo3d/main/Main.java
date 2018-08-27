package com.youtube.pseudo3d.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.youtube.pseudo3d.engine.Raycaster;
import com.youtube.pseudo3d.input.InputHandler;
import com.youtube.pseudo3d.util.Constants;

public class Main extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private Thread thread;
	
	private Window window;
	private Raycaster raycaster;
	
	private double fps;
	
	public Main() {
		initCanvas();
		initWindow();
		initInput();
		initRaycaster();
	}
	
	private void initCanvas() {
		setMinimumSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
		setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
		setMaximumSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
	}
	
	private void initWindow() {
		window = new Window();
		window.add(this);
		window.pack();
	}
	
	private void initInput() {
		addKeyListener(new InputHandler());
	}
	
	private void initRaycaster() {
		raycaster = new Raycaster(this);
	}
	
	@Override
	public void run() {
		double previous = System.nanoTime();
		double lag = 0.0;
		
		while(running) {
			double current = System.nanoTime();
			double elapsed = (current - previous) / 1e10;
			previous = current;
			lag += elapsed;
			
			handleInput(elapsed);
			update(elapsed);
			
			while(lag >= Constants.MS_PER_UPDATE)
				lag -= Constants.MS_PER_UPDATE;
			
			fps = (1/elapsed - lag)/Constants.MS_PER_UPDATE*10;
			render();
		}
	}
	
	public void handleInput(double elapsed) {
		raycaster.handleInput(elapsed);
	}
	
	public void update(double elapsed) {
		raycaster.update(elapsed);
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, getWidth(), getHeight());
		
		raycaster.render(g);
		
		// DEBUG INFO
		g.drawString("FPS: " + Math.floor(fps), 10, 20);
		
		g.dispose();
		bs.show();
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
}

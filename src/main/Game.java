package main;

import inputs.Input;

import java.awt.Graphics;

public class Game implements Runnable
{
	// Singleton
	private static Game gameInstance = null;

	private Thread gameThread;
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Input input;

	// Game's properties
	private final int FPS_CAP = 120;
	private final int UPS_CAP = 200;
	private boolean isRunning = false;
	public static String GAME_TITLE = "Retro Blade | FPS: 0 | UPS: 0";
	public static final float SCALE = 2f;
	public static final int TILE_WIDTH_DEFAULT = 32;
	public static final int TILE_HEIGHT_DEFAULT = 32;
	public static final int TILES_IN_WIDTH = 26;
	public static final int TILES_IN_HEIGHT = 14;
	public static final int TILE_WIDTH = (int)(TILE_WIDTH_DEFAULT * SCALE);
	public static final int TILE_HEIGHT = (int)(TILE_HEIGHT_DEFAULT * SCALE);
	public static final int GAME_WIDTH = TILES_IN_WIDTH * TILE_WIDTH;
	public static final int GAME_HEIGHT = TILES_IN_HEIGHT  * TILE_HEIGHT;

	private Game()
	{
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();

		startGameLoop();
	}

	private void initClasses()
	{
		input = new Input();
	}

	private void startGameLoop()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update()
	{

		input.update();
	}

	public void render(Graphics g)
	{

	}

	@Override
	public void run()
	{
		isRunning = true;

		double timePerFrame = 1_000_000_000.0 / FPS_CAP;
		double timePerUpdate = 1_000_000_000.0 / UPS_CAP;

		long previousTime = System.nanoTime();
		long currentTime = 0;
		long lastCheck = System.nanoTime();

		int frames = 0;
		int updates = 0;

		double deltaF = 0.0;
		double deltaU = 0.0;

		while (isRunning)
		{
			currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1.0)
			{
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1.0)
			{
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.nanoTime() - lastCheck >= 1_000_000_000)
			{
				lastCheck = System.nanoTime();
				GAME_TITLE = String.format("Retro Blade | FPS: %d | UPS: %d", frames, updates);
				gameWindow.getJFrame().setTitle(GAME_TITLE);
				frames = 0;
				updates = 0;
			}
		}
	}

	public static Game getGameInstance()
	{
		if (Game.gameInstance == null)
			Game.gameInstance = new Game();
		return Game.gameInstance;
	}

	public Input getInput()
	{
		return input;
	}
}

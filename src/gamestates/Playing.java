/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: Control the playing state of the game.
 */

package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.EnemyManager;
import entities.Player;
import inputs.Input;
import level.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utils.LoadSave;
import utils.SpriteConsts;

public class Playing implements StateMethods {
	private Input input;
	private Player player;
	private LevelManager lvlManager;
	private EnemyManager enemyManager;
	private BufferedImage seaBackground, cloudBackground, skyBackground, farGroundBackground, backwall;
	private boolean paused = false;
	private PauseOverlay pauseOverlay;

	private int xLvlOffset;
	private int leftBorder = (int)(0.3 * Game.GAME_WIDTH);
	private int rightBorder = (int)(0.7 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetLevelData(LoadSave.WORLD_DATA)[0].length;
	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * Game.TILE_WIDTH;

	private boolean gameOver;
	private GameOverOverlay gameOverOverlay;

	public Playing(Input input)
	{
		gameOver = false;
		this.input = input;
		initClasses();
	}

	private void initClasses()
	{
		lvlManager = new LevelManager();

		int startPosX = (int)(100 * Game.SCALE);
		int startPosY = (int)(100 * Game.SCALE);
		player = new Player(startPosX, startPosY,
				(int)(SpriteConsts.PLAYER.getSpriteWidth() * Game.SCALE),
				(int)(SpriteConsts.PLAYER.getSpriteHeight() * Game.SCALE), input, this);
		player.setCurrentLevel(lvlManager.getCurrentLevel());
		enemyManager = new EnemyManager(this);
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this, input);

		seaBackground = LoadSave.GetSpriteSheet(LoadSave.SEA_BACKGROUND);
		cloudBackground = LoadSave.GetSpriteSheet(LoadSave.CLOUD_BACKGROUND);
		skyBackground = LoadSave.GetSpriteSheet(LoadSave.SKY_BACKGROUND);
		farGroundBackground = LoadSave.GetSpriteSheet(LoadSave.FAR_GROUND_BACKGROUND);
		backwall = LoadSave.GetSpriteSheet(LoadSave.BACKWALL_BACKGROUND);
	}

	@Override
	public void update()
	{
		CheckEscapeButton();
		if (!paused && !gameOver)
		{
			lvlManager.update();
			player.update(gameOver);
			enemyManager.update();
			checkCloseBorder();
		}
		else
		{
			pauseOverlay.checkMouseMoved(paused);
			pauseOverlay.checkMousePressed(paused);
			pauseOverlay.checkMouseDragged(paused);
			pauseOverlay.checkMouseReleased(paused);
			pauseOverlay.update();
		}
	}

	private void checkCloseBorder() {
		int playerX = (int)player.getRect().x;
		int diff = playerX - xLvlOffset;

		if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xLvlOffset += diff - leftBorder;

		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;
	}

	@Override
	public void render(Graphics g) {
		renderBackground(g);

		lvlManager.render(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemyManager.render(g, xLvlOffset);

		if (paused)
		{
			pauseOverlay.render(g);
		}
		else if (gameOver)
		{
			gameOverOverlay.render(g);
		}
	}

	private void CheckEscapeButton()
	{
		if (input.isKeyDown(KeyEvent.VK_ESCAPE))
		{
			if (gameOver)
				gameOverOverlay.checkKeyPressed();
			else
			{
				paused = !paused;
			}
		}
	}

	private void renderBackground(Graphics g)
	{
		// Sky background
		for (int i = 0; i < 16; i++)
		{
			g.drawImage(skyBackground,
					i * (int)(SpriteConsts.SKY.getSpriteWidth() * Game.SCALE) - (int)(xLvlOffset * 0.2),
					0,
					(int)(SpriteConsts.SKY.getSpriteWidth() * Game.SCALE),
					(int)(SpriteConsts.SKY.getSpriteHeight() * Game.SCALE), null);
		}

		// Cloud background
		for (int i = 0; i < 4; i++)
		{
			g.drawImage(cloudBackground,
					i * (int)(SpriteConsts.CLOUD.getSpriteWidth() * Game.SCALE) - (int)(xLvlOffset * 0.3),
					(int)(SpriteConsts.CLOUD.getSpriteHeight() * 0.5 * Game.SCALE),
					(int)(SpriteConsts.CLOUD.getSpriteWidth() * Game.SCALE),
					(int)(SpriteConsts.CLOUD.getSpriteHeight() * Game.SCALE),
					null);
		}

		// Sea background
		for (int i = 0; i < 16; i++)
		{
			g.drawImage(seaBackground,
					i * (int)(SpriteConsts.SEA.getSpriteWidth() * Game.SCALE) - (int)(xLvlOffset * 0.5),
					Game.GAME_HEIGHT - (int)(SpriteConsts.SEA.getSpriteHeight() * Game.SCALE),
					(int)(SpriteConsts.SEA.getSpriteWidth() * Game.SCALE),
					(int)(SpriteConsts.SEA.getSpriteHeight() * Game.SCALE),
					null);
		}

		// Far Ground background
		for (int i = 0; i < 2; i++)
		{
			g.drawImage(farGroundBackground,
					(int)((60 + 1200 * i) * Game.SCALE) - (int)(xLvlOffset * 0.6),
					Game.GAME_HEIGHT - (int)(SpriteConsts.FAR_GROUND.getSpriteHeight() * Game.SCALE),
					(int)(SpriteConsts.FAR_GROUND.getSpriteWidth() * Game.SCALE),
					(int)(SpriteConsts.FAR_GROUND.getSpriteHeight() * Game.SCALE),
					null);
		}

		// Back wall background
		g.drawImage(backwall,
				(int)(500 * Game.SCALE) - (int)(xLvlOffset),
				(int)(-30 * Game.SCALE),
				(int)(SpriteConsts.BACKWALL.getSpriteWidth() * Game.SCALE),
				Game.GAME_HEIGHT,
				null);

		g.drawImage(backwall,
				(int)(1200 * Game.SCALE) - (int)(xLvlOffset),
				(int)(-50 * Game.SCALE),
				(int)(SpriteConsts.BACKWALL.getSpriteWidth() * Game.SCALE),
				Game.GAME_HEIGHT,
				null);

	}

	public void checkEnemyHit(Rectangle2D.Float attackBox)
	{
		enemyManager.checkEnemyHit(attackBox);
	}

	public void setGameOver(boolean gameOver)
	{
		this.gameOver = gameOver;
	}

	public void unpauseGame()
	{
		paused = false;
	}

	public void windowLostFocus()
	{
		if (!paused)
			paused = true;
	}

	public void windowGainFocus()
	{
		if (paused)
			paused = false;
	}

	public Input getInput()
	{
		return input;
	}

	public LevelManager getLevelManager()
	{
		return lvlManager;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void resetAll()
	{
		// TODO: reset playing, enemy, etc,...
		gameOver = false;
		paused = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
	}
}

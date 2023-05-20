/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: Display a GameOver message to the player from the game.
 */

package ui;

import gamestates.GameState;
import gamestates.Playing;
import inputs.Input;
import main.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class GameOverOverlay
{
	private Playing playing;
	private Input input;

	public GameOverOverlay(Playing playing, Input input)
	{
		this.playing = playing;
		this.input = input;
	}

	public void render(Graphics g)
	{
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

		g.setColor(Color.white);
		g.drawString("Game Over", Game.GAME_WIDTH / 2, 150);
		g.drawString("Press esc to enter Main Menu!", Game.GAME_WIDTH / 2 - 50, 300);
	}

	public void checkKeyPressed()
	{
		if (input.isKeyDown(KeyEvent.VK_ESCAPE))
		{
			playing.resetAll();
			GameState.state = GameState.MENU;
		}
	}
}

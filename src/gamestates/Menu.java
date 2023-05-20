/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: Control the menu state of the game.
 */

package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import inputs.Input;
import main.Game;
import ui.MenuButton;
import utils.LoadSave;

public class Menu extends State implements StateMethods
{
	private Input input;

	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg, menuBackgroundImg;
	private int menuX, menuY, menuWidth, menuHeight;

	public Menu(Input input)
	{
		this.input = input;
		loadButtons();
		loadBackground();
		menuBackgroundImg = LoadSave.GetSpriteSheet(LoadSave.BACKGROUND_MENU);
	}

	private void loadBackground()
	{
		backgroundImg = LoadSave.GetSpriteSheet(LoadSave.MENU_BACKGROUND);
		menuWidth = (int)(backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int)(backgroundImg.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int)(45 * Game.SCALE);
	}

	private void loadButtons()
	{
		buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, GameState.PLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, GameState.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, GameState.QUIT);
	}

	@Override
	public void update()
	{
		checkMenuEnter();
		if (input.isMouseMoved())
			checkMouseMoved();
		if (input.isButtonDown(MouseEvent.BUTTON1))
			checkMousePressed();
		else if (input.isButtonUp(MouseEvent.BUTTON1))
			checkMouseReleased();


		for (MenuButton mb : buttons)
			mb.update();
	}

	@Override
	public void render(Graphics g)
	{
		g.drawImage(menuBackgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

		for (MenuButton mb : buttons)
			mb.render(g);
	}

	private void checkMouseMoved()
	{
		for (MenuButton mb : buttons)
		{
			mb.setMouseOver(false);
		}

		for (MenuButton mb : buttons)
		{
			if (mb.getBounds().contains(input.getMouseX(), input.getMouseY()))
			{
				mb.setMouseOver(true);
				break;
			}
		}
	}

	private void checkMousePressed()
	{
		for (MenuButton mb : buttons)
			if (mb.getBounds().contains(input.getMouseX(), input.getMouseY()))
				mb.setMousePressed(true);
	}

	private void checkMouseReleased()
	{

		for (MenuButton mb : buttons)
			if (mb.getBounds().contains(input.getMouseX(), input.getMouseY()))
			{
				if (mb.isMousePressed())
					mb.applyGamestate();
				mb.setMousePressed(false);
				break;
			}

		resetButtons();
	}

	private void resetButtons()
	{
		for (MenuButton mb : buttons)
			mb.resetMouse();
	}

	private void checkMenuEnter()
	{
		if (input.isKeyDown(KeyEvent.VK_ENTER))
			GameState.state = GameState.PLAYING;
	}
}
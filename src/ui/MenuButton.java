package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import main.Game;
import utils.LoadSave;
import utils.SpriteConsts;

public class MenuButton
{
	private int xPos, yPos, rowIndex, index;
	private int xOffsetCenter = (int)(SpriteConsts.MENU_BUTTON.getSpriteWidth() * Game.SCALE) / 2;
	private GameState state;
	private BufferedImage[] imgs;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;

	public MenuButton(int xPos, int yPos, int rowIndex, GameState state)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;
		loadImages();
		initBounds();
	}

	private void initBounds()
	{
		bounds = new Rectangle(xPos - xOffsetCenter, yPos,
				(int)(SpriteConsts.MENU_BUTTON.getSpriteWidth() * Game.SCALE),
				(int)(SpriteConsts.MENU_BUTTON.getSpriteHeight() * Game.SCALE));
	}

	private void loadImages()
	{
		imgs = new BufferedImage[3];
		BufferedImage temp = LoadSave.GetSpriteSheet(LoadSave.MENU_BUTTONS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * SpriteConsts.MENU_BUTTON.getSpriteWidth(), rowIndex * SpriteConsts.MENU_BUTTON.getSpriteHeight(),
					SpriteConsts.MENU_BUTTON.getSpriteWidth(), SpriteConsts.MENU_BUTTON.getSpriteHeight());
	}

	public void render(Graphics g)
	{
//		g.setColor(Color.GREEN);
//		g.drawRect(xPos - xOffsetCenter, yPos,
//				(int)bounds.getWidth(),
//				(int)bounds.getHeight());
//		g.setColor(Color.BLACK);
		g.drawImage(imgs[index], xPos - xOffsetCenter, yPos,
				(int)(SpriteConsts.MENU_BUTTON.getSpriteWidth() * Game.SCALE),
				(int)(SpriteConsts.MENU_BUTTON.getSpriteHeight() * Game.SCALE), null);
	}

	public void update()
	{
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;
//		System.out.println(index);
	}

	public boolean isMouseOver()
	{
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver)
	{
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed()
	{
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed)
	{
		this.mousePressed = mousePressed;
	}

	public Rectangle getBounds()
	{
		return bounds;
	}

	public void applyGamestate()
	{
		GameState.state = state;
	}

	public void resetMouse()
	{
		mouseOver = false;
		mousePressed = false;
	}
}

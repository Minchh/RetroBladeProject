/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: Configuration of the volume button in game's pause menu.
 */

package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;
import utils.SpriteConsts;

public class VolumeButton extends PauseButton
{
	private BufferedImage[] imgs;
	private BufferedImage slider;
	private int index = 0;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;

	public VolumeButton(int x, int y, int width, int height)
	{
		super(x + width / 2, y, (int)(SpriteConsts.VOLUME_BUTTON.getSpriteWidth() * Game.SCALE), height);
		bounds.x -= (int)(SpriteConsts.VOLUME_BUTTON.getSpriteWidth() * Game.SCALE) / 2;
		buttonX = x + width / 2;
		this.x = x;
		this.width = width;
		minX = x + (int)(SpriteConsts.VOLUME_BUTTON.getSpriteWidth() * Game.SCALE) / 2;
		maxX = x + width - (int)(SpriteConsts.VOLUME_BUTTON.getSpriteWidth() * Game.SCALE) / 2;
		loadImgs();
	}

	private void loadImgs()
	{
		BufferedImage temp = LoadSave.GetSpriteSheet(LoadSave.VOLUME_BUTTONS);
		imgs = new BufferedImage[3];
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(
					i * SpriteConsts.VOLUME_BUTTON.getSpriteWidth(),
					0,
					SpriteConsts.VOLUME_BUTTON.getSpriteWidth(),
					SpriteConsts.VOLUME_BUTTON.getSpriteHeight());

		slider = temp.getSubimage(
				3 * SpriteConsts.VOLUME_BUTTON.getSpriteWidth(),
				0,
				SpriteConsts.VOLUME_SLIDER.getSpriteWidth(),
				SpriteConsts.VOLUME_BUTTON.getSpriteHeight());
	}

	public void update()
	{
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;
	}

	public void render(Graphics g)
	{

		g.drawImage(slider, x, y, width, height, null);
		g.drawImage(imgs[index],
				buttonX - (int)(SpriteConsts.VOLUME_BUTTON.getSpriteWidth() * Game.SCALE) / 2,
				y,
				(int)(SpriteConsts.VOLUME_BUTTON.getSpriteWidth() * Game.SCALE),
				height,
				null);
	}

	public void changeX(int x)
	{
		if (x < minX)
			buttonX = minX;
		else if (x > maxX)
			buttonX = maxX;
		else
			buttonX = x;

		bounds.x = buttonX - (int)(SpriteConsts.VOLUME_BUTTON.getSpriteWidth() * Game.SCALE) / 2;

	}

	public void resetMouse()
	{
		mouseOver = false;
		mousePressed = false;
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
}

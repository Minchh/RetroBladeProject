package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;
import utils.SpriteConsts;

public class UrmButton extends PauseButton
{
	private BufferedImage[] imgs;
	private int rowIndex, index;
	private boolean mouseOver, mousePressed;


	public UrmButton(int x, int y, int width, int height, int rowIndex)
	{
		super(x, y, width, height);
		this.rowIndex = rowIndex;
		loadImgs();
	}

	private void loadImgs()
	{
		BufferedImage temp = LoadSave.GetSpriteSheet(LoadSave.URM_BUTTONS);
		imgs = new BufferedImage[3];
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(
					i * SpriteConsts.URM_BUTTON.getSpriteWidth(),
					rowIndex * SpriteConsts.URM_BUTTON.getSpriteHeight(),
					SpriteConsts.URM_BUTTON.getSpriteWidth(),
					SpriteConsts.URM_BUTTON.getSpriteHeight());
	}

	public void update()
	{
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;
	}

	public void render(Graphics g) {
		g.drawImage(imgs[index],
				x,
				y,
				(int)(SpriteConsts.URM_BUTTON.getSpriteWidth() * Game.SCALE),
				(int)(SpriteConsts.URM_BUTTON.getSpriteHeight() * Game.SCALE),
				null);
	}

	public void resetMouse() {
		mouseOver = false;
		mousePressed = false;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

}

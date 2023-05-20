/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: Control the sound buttons' configuration of the game.
 */

package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;
import utils.SpriteConsts;

public class SoundButton extends PauseButton
{
	private BufferedImage[][] soundImgs;
	private boolean mouseOver, mousePressed;
	private boolean muted;
	private int rowIndex, colIndex;

	public SoundButton(int x, int y, int width, int height)
	{
		super(x, y, width, height);

		loadSoundImgs();
	}

	private void loadSoundImgs()
	{
		BufferedImage temp = LoadSave.GetSpriteSheet(LoadSave.SOUND_BUTTONS);
		soundImgs = new BufferedImage[2][3];
		for (int y= 0; y < soundImgs.length; y++)
		{
			for (int x = 0; x < soundImgs[y].length; x++)
				soundImgs[y][x] = temp.getSubimage(
						x * SpriteConsts.SOUND_BUTTON.getSpriteWidth(),
						y * SpriteConsts.SOUND_BUTTON.getSpriteHeight(),
						SpriteConsts.SOUND_BUTTON.getSpriteWidth(),
						SpriteConsts.SOUND_BUTTON.getSpriteHeight());
		}
	}

	public void update()
	{
		if (muted)
			rowIndex = 1;
		else
			rowIndex = 0;

		colIndex = 0;
		if (mouseOver)
			colIndex = 1;
		if (mousePressed)
			colIndex = 2;
	}

	public void resetMouse()
	{
		mouseOver = false;
		mousePressed = false;
	}

	public void render(Graphics g)
	{
		g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
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

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}
}

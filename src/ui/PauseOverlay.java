package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;
import utils.SpriteConsts;

public class PauseOverlay
{
	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;
	private SoundButton musicButton, sfxButton;
	private UrmButton menuB, replayB, unpauseB;
	private VolumeButton volumeButton;

	public PauseOverlay(Playing playing)
	{
		this.playing = playing;
		loadBackground();
		createSoundButtons();
		createUrmButtons();
		createVolumeButtons();
	}

	private void createVolumeButtons()
	{
		int vX = (int)(309 * Game.SCALE);
		int vY = (int)(278 * Game.SCALE);
		volumeButton = new VolumeButton(vX, vY,
				(int)(SpriteConsts.VOLUME_SLIDER.getSpriteWidth() * Game.SCALE),
				(int)(SpriteConsts.VOLUME_BUTTON.getSpriteHeight() * Game.SCALE));
	}

	private void createUrmButtons()
	{
		int menuX = (int)(313 * Game.SCALE);
		int replayX = (int)(387 * Game.SCALE);
		int unpauseX = (int)(462 * Game.SCALE);
		int bY = (int)(325 * Game.SCALE);

		menuB = new UrmButton(menuX, bY,
				(int)(SpriteConsts.URM_BUTTON.getSpriteWidth() * Game.SCALE),
				(int)(SpriteConsts.URM_BUTTON.getSpriteHeight() * Game.SCALE), 2);

		replayB = new UrmButton(replayX, bY,
				(int)(SpriteConsts.URM_BUTTON.getSpriteWidth() * Game.SCALE),
				(int)(SpriteConsts.URM_BUTTON.getSpriteHeight() * Game.SCALE), 1);

		unpauseB = new UrmButton(unpauseX, bY,
				(int)(SpriteConsts.URM_BUTTON.getSpriteWidth() * Game.SCALE),
				(int)(SpriteConsts.URM_BUTTON.getSpriteHeight() * Game.SCALE), 0);
	}

	private void createSoundButtons()
	{
		int soundX = (int) (450 * Game.SCALE);
		int musicY = (int) (140 * Game.SCALE);
		int sfxY = (int) (186 * Game.SCALE);
		musicButton = new SoundButton(soundX, musicY,
				(int)(SpriteConsts.SOUND_BUTTON.getSpriteWidth() * Game.SCALE),
				(int)(SpriteConsts.SOUND_BUTTON.getSpriteHeight() * Game.SCALE));
		sfxButton = new SoundButton(soundX, sfxY,
				(int)(SpriteConsts.SOUND_BUTTON.getSpriteWidth() * Game.SCALE),
				(int)(SpriteConsts.SOUND_BUTTON.getSpriteHeight() * Game.SCALE));
	}

	private void loadBackground()
	{
		backgroundImg = LoadSave.GetSpriteSheet(LoadSave.PAUSE_BACKGROUND);
		bgW = (int)(backgroundImg.getWidth() * Game.SCALE);
		bgH = (int)(backgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int)(25 * Game.SCALE);
	}

	public void update()
	{
		musicButton.update();
		sfxButton.update();

		menuB.update();
		replayB.update();
		unpauseB.update();

		volumeButton.update();
	}

	public void render(Graphics g)
	{
		// Background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

		// Sound buttons;
		musicButton.render(g);
		sfxButton.render(g);

		// Urm Buttons;
		menuB.render(g);
		replayB.render(g);
		unpauseB.render(g);

		// Volume Button
		volumeButton.render(g);
	}

	public void checkMouseDragged(boolean isPaused)
	{
		if (isPaused)
		{
			if (playing.getInput().isButton(MouseEvent.BUTTON1) && playing.getInput().isMouseMoved())
			{
				if (volumeButton.isMousePressed())
					volumeButton.changeX(playing.getInput().getMouseX());
			}
		}
	}

	public void checkMousePressed(boolean isPaused)
	{
		if (isPaused)
		{
			if (playing.getInput().isButtonDown(MouseEvent.BUTTON1))
			{
				if (isIn(musicButton))
					musicButton.setMousePressed(true);
				else if (isIn(sfxButton))
					sfxButton.setMousePressed(true);
				else if (isIn(menuB))
					menuB.setMousePressed(true);
				else if (isIn(replayB))
					replayB.setMousePressed(true);
				else if (isIn(unpauseB))
					unpauseB.setMousePressed(true);
				else if (isIn(volumeButton))
					volumeButton.setMousePressed(true);
			}
		}
	}

	public void checkMouseReleased(boolean isPaused)
	{
		if (isPaused)
		{
			if (playing.getInput().isButtonUp(MouseEvent.BUTTON1))
			{
				if (isIn(musicButton))
				{
					if (musicButton.isMousePressed())
						musicButton.setMuted(!musicButton.isMuted());
				}
				else if (isIn(sfxButton))
				{
					if (sfxButton.isMousePressed())
						sfxButton.setMuted(!sfxButton.isMuted());
				}
				else if (isIn(menuB))
				{
					if (menuB.isMousePressed())
					{
						GameState.state = GameState.MENU;
						playing.unpauseGame();
					}
				}
				else if (isIn(replayB))
				{
					if (replayB.isMousePressed())
					{
						playing.resetAll();
						playing.unpauseGame();
					}
				}
				else if (isIn(unpauseB))
				{
					if (unpauseB.isMousePressed())
						playing.unpauseGame();
				}

				musicButton.resetMouse();
				sfxButton.resetMouse();
				menuB.resetMouse();
				replayB.resetMouse();
				unpauseB.resetMouse();
				volumeButton.resetMouse();
			}
		}

	}

	public void checkMouseMoved(boolean isPaused)
	{
		if (isPaused)
		{
			if (playing.getInput().isMouseMoved())
			{
				musicButton.setMouseOver(false);
				sfxButton.setMouseOver(false);
				menuB.setMouseOver(false);
				replayB.setMouseOver(false);
				unpauseB.setMouseOver(false);
				volumeButton.setMouseOver(false);

				if (isIn(musicButton))
					musicButton.setMouseOver(true);
				else if (isIn(sfxButton))
					sfxButton.setMouseOver(true);
				else if (isIn(menuB))
					menuB.setMouseOver(true);
				else if (isIn(replayB))
					replayB.setMouseOver(true);
				else if (isIn(unpauseB))
					unpauseB.setMouseOver(true);
				else if (isIn(volumeButton))
					volumeButton.setMouseOver(true);
			}
		}
	}

	private boolean isIn(PauseButton b)
	{
		return b.getBounds().contains(playing.getInput().getMouseX(), playing.getInput().getMouseY());
	}
}

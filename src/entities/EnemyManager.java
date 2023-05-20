/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: This class manages all the enemies in the game.
 */

package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;
import utils.SpriteConsts;

public class EnemyManager
{
	private Playing playing;
	private BufferedImage[][] slimeArr;
	private ArrayList<Slime> slimes = new ArrayList<>();

	private int count = 0;

	public EnemyManager(Playing playing)
	{
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();

		for (Slime s : slimes)
			if (s.isActived())
			{
				s.setCurrentPlayer(playing.getPlayer());
			}
	}

	private void addEnemies()
	{
		slimes = LoadSave.getSlimes();
		for (Slime s : slimes)
		{
			s.setCurrentLevel(playing.getLevelManager().getCurrentLevel());
		}
		System.out.println("Size of slimes: " + slimes.size());
	}

	public void update()
	{
		for (Slime s : slimes)
			if (s.isActived())
			{
				s.update();
			}
		if (isAllSlimesInactived())
			playing.setGameOver(true);
	}

	private boolean isAllSlimesInactived()
	{
		int count = 0;
		for (Slime s : slimes)
		{
			if (!s.isActived())
			{
				count++;
			}
		}
		if (count == slimes.size())
			return true;
		return false;
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox)
	{
		for (Slime s : slimes)
			if (s.isActived())
			{
				if (attackBox.intersects(s.getRect()))
				{
					s.hurt(10);
					return;
				}
			}
	}

	public void render(Graphics g, int xLevelOffset)
	{
		renderSlimes(g, xLevelOffset);
	}

	private void renderSlimes(Graphics g, int xLevelOffset)
	{
		for (Slime s : slimes)
			if (s.isActived())
			{
				g.drawImage(slimeArr[s.getEnemyState().ordinal()][s.getAniIndex()],
						(int)s.getRect().x - (int)(6 * Game.SCALE * 1.5) + s.flipX - xLevelOffset,
						(int)s.getRect().y - (int)(10 * Game.SCALE * 1.5),
						(int)(SpriteConsts.SLIME.getSpriteWidth() * Game.SCALE * 1.5) * s.flipW, (int)(SpriteConsts.SLIME.getSpriteHeight() * Game.SCALE * 1.5), null);
//				s.renderRect(g, xLevelOffset);
			}
	}

	private void loadEnemyImgs()
	{
		slimeArr = new BufferedImage[3][8];
		BufferedImage temp = LoadSave.GetSpriteSheet(LoadSave.SLIME_SPRITE);
		for (int y = 0; y < slimeArr.length; y++)
			for (int x = 0; x < slimeArr[y].length; x++)
				slimeArr[y][x] = temp.getSubimage(x * SpriteConsts.SLIME.getSpriteWidth(),
						y * SpriteConsts.SLIME.getSpriteHeight(),
						SpriteConsts.SLIME.getSpriteWidth(),
						SpriteConsts.SLIME.getSpriteHeight());
	}

	public void resetAllEnemies()
	{
		for (Slime s : slimes)
		{
			s.resetEnemy();
		}
	}
}

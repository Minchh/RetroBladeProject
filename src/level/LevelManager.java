package level;

import main.Game;
import utils.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class LevelManager
{
	private BufferedImage[] lvlSprite;
	private Level level;

	public LevelManager()
	{
		importOutsideSprites();
		level = new Level();
		level.setFirstLayer(LoadSave.GetLevelData(LoadSave.WORLD_DATA));
		level.setSecondLayer(LoadSave.GetLevelData(LoadSave.WORLD_GRASS_DATA));
		level.init();

	}

	private void importOutsideSprites()
	{
		BufferedImage img = LoadSave.GetSpriteSheet(LoadSave.WORLD_SPRITE_SHEET);
		lvlSprite = new BufferedImage[80];
		for (int y = 0; y < 6; y++)
			for (int x = 0; x < 13; x++)
			{
				int index = y * 13 + x + 1;
				lvlSprite[index] = img.getSubimage(x * 32, y * 32, 32, 32);
			}
	}

	public void render(Graphics g, int lvlOffset)
	{


		for (int y = 0; y < Game.TILES_IN_HEIGHT; y++)
			for (int x = 0; x < level.getFirstLayer()[0].length; x++)
			{
				int index = level.getFirstLayerIndex(x, y);
				g.drawImage(lvlSprite[index], Game.TILE_WIDTH * x - lvlOffset, Game.TILE_HEIGHT * y,
						Game.TILE_WIDTH, Game.TILE_HEIGHT, null);
				index = level.getSecondLayerIndex(x, y);
				g.drawImage(lvlSprite[index], Game.TILE_WIDTH * x - lvlOffset, Game.TILE_HEIGHT * y,
						Game.TILE_WIDTH, Game.TILE_HEIGHT, null);
			}

		// For debugging only
//		level.renderTilesHitbox(g, lvlOffset);
	}

	public void update()
	{

	}

	public Level getCurrentLevel()
	{
		return level;
	}
}

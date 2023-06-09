/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: This class loads the assets and data from files in res folder.
 */

package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Slime;
import main.Game;

public class LoadSave
{
	public static final String PLAYER_SPRITE_SHEET = "magic_cliff_player.png";
	public static final String WORLD_SPRITE_SHEET = "magic_cliff_tiles.png";
	public static final String WORLD_DATA = "world_data.png";
	public static final String WORLD_GRASS_DATA = "world_data_grass.png";
	public static final String SEA_BACKGROUND = "sea.png";
	public static final String CLOUD_BACKGROUND = "clouds.png";
	public static final String SKY_BACKGROUND = "sky.png";
	public static final String FAR_GROUND_BACKGROUND = "far_grounds.png";
	public static final String BACKWALL_BACKGROUND = "backwall.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_button.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String BACKGROUND_MENU = "background_menu.png";
	public static final String SLIME_SPRITE = "slime_sheet.png";
	public static final String STATUS_BAR = "health_power_bar.png";

	public static BufferedImage GetSpriteSheet(String fileName)
	{
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try
		{
			img = ImageIO.read(is);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return img;
	}

	public static ArrayList<Slime> getSlimes()
	{
		BufferedImage img = GetSpriteSheet(LoadSave.WORLD_DATA);
		ArrayList<Slime> list = new ArrayList<>();
		for (int y = 0; y < img.getHeight(); y++)
		{
			for (int x = 0; x < img.getWidth(); x++)
			{
				Color color = new Color(img.getRGB(x, y));
				int value = color.getGreen();
				if (value == EnemyType.SLIME.ordinal())
				{
					list.add(new Slime(x * Game.TILE_WIDTH, y * Game.TILE_HEIGHT));
				}
			}
		}
		return list;
	}

	public static int[][] GetLevelData(String data)
	{
		BufferedImage img = GetSpriteSheet(data);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++)
			{
				Color color = new Color(img.getRGB(x, y));
				int value = color.getRed();
				if (value >= 79)
					value = 0;
				lvlData[y][x] = value;
			}

		return lvlData;
	}
}

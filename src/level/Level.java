/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: Level class for each level.
 */

package level;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Level
{
	private ArrayList<Tile> solidTiles;
	private int[][] firstLayer, secondLayer;

	public Level()
	{
		solidTiles = new ArrayList<>();
	}

	public void init()
	{
		int sheetHeight = firstLayer.length;
		int sheetWidth = firstLayer[0].length;

		for (int y = 0; y < sheetHeight; y++)
			for (int x = 0; x < sheetWidth; x++)
			{
				int spriteInex = firstLayer[y][x];
				switch (spriteInex)
				{
					case 14:
						solidTiles.add(new Tile(spriteInex, x, y, 11, 0, 32, 32 * 4));
						break;
					case 21:
						solidTiles.add(new Tile(spriteInex, x,  y, 0, 0, 32, 32 * 4));
						break;
					case 15, 16, 63, 64, 66, 67, 69:
						solidTiles.add(new Tile(spriteInex, x, y, 0, 0, 32, 32));
						break;
					case 62:
						solidTiles.add(new Tile(spriteInex, x, y, 22, 0, 10, 29));
						break;
					case 65:
						solidTiles.add(new Tile(spriteInex, x, y, 0, 0, 11, 14));
						break;
					case 76:
						solidTiles.add(new Tile(spriteInex, x, y, 10, 0, 22, 24));
						break;
					case 77:
						solidTiles.add(new Tile(spriteInex, x, y, 0, 0, 27, 10));
						break;
					case 12:
						solidTiles.add(new Tile(spriteInex, x, y, 8, 24, 24, 8));
						break;
					case 13:
						solidTiles.add(new Tile(spriteInex, x, y, 0, 24, 24, 8));
						break;
					case 25:
						solidTiles.add(new Tile(spriteInex, x, y, 8, 0, 24, 17));
						break;
					case 26:
						solidTiles.add(new Tile(spriteInex, x, y, 0, 0, 24, 17));
						break;
					default:
						break;
				}
				spriteInex = secondLayer[y][x];
				switch (spriteInex)
				{
					case 11, 23, 36, 68, 70, 71, 72, 73, 74:
						solidTiles.add(new Tile(spriteInex, x, y, 0, 0, 32, 32));
						break;
				}
			}
	}

	// For debugging only
	public void renderTilesHitbox(Graphics g, int lvlOffset)
	{
		g.setColor(Color.GREEN);
		for (Tile t : solidTiles)
			g.drawRect(t.getX() - lvlOffset, t.getY(), t.getWidth(), t.getHeight());

		g.setColor(Color.BLACK);
	}

	public int getFirstLayerIndex(int x, int y)
	{
		return firstLayer[y][x];
	}

	public int getSecondLayerIndex(int x, int y)
	{
		return secondLayer[y][x];
	}

	public ArrayList<Tile> getTiles()
	{
		return solidTiles;
	}

	public int[][] getFirstLayer()
	{
		return firstLayer;
	}

	public int[][] getSecondLayer()
	{
		return secondLayer;
	}

	public void setFirstLayer(int[][] firstLayer)
	{
		this.firstLayer = firstLayer;
	}

	public void setSecondLayer(int[][] secondLayer)
	{
		this.secondLayer = secondLayer;
	}
}

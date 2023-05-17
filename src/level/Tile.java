package level;

import java.awt.Rectangle;

import main.Game;

public class Tile
{
	private int spriteID;
	private int x, y;
	private int width, height;
	private Rectangle rect;

	public Tile(int spriteID, int x, int y, int offX, int offY, int width, int height)
	{
		this.spriteID = spriteID;
		this.x = (int)(x * Game.TILE_WIDTH + offX * Game.SCALE) ;
		this.y = (int)(y * Game.TILE_HEIGHT + offY * Game.SCALE);
		this.width = (int)(width * Game.SCALE);
		this.height = (int)(height * Game.SCALE);
		rect = new Rectangle(this.x, this.y, this.width, this.height);
	}

	public int getSpriteID() {
		return spriteID;
	}

	public void setSpriteID(int spriteID) {
		this.spriteID = spriteID;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public Rectangle getRect()
	{
		return rect;
	}

	public void setRect(Rectangle rect)
	{
		this.rect = rect;
	}
}

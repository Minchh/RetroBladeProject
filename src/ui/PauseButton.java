/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: All properties that a basic pause button should have.
 */

package ui;

import java.awt.Rectangle;

public abstract class PauseButton
{
	protected int x, y, width, height;
	protected Rectangle bounds;

	public PauseButton(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		createBounds();
	}

	private void createBounds()
	{
		bounds = new Rectangle(x, y, width, height);
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

	public Rectangle getBounds()
	{
		return bounds;
	}

	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
	}
}

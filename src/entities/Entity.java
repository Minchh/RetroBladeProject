/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: Every player or enemy extends this class.
 */

package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Entity
{
	protected float x, y;
	private int width, height;
	private Rectangle2D.Float rect;

	public Entity(float x, float y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	// For debugging the hitbox;
	protected void renderRect(Graphics g, int xLvlOffset)
	{
		g.setColor(Color.RED);
		g.drawRect((int)rect.x - xLvlOffset, (int)rect.y, (int)rect.width, (int)rect.height);
		g.setColor(Color.BLACK);
	}

	protected void initRect(float x, float y, float width, float height) {
		rect = new Rectangle2D.Float(x, y, width, height);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle2D.Float getRect() {
		return rect;
	}

	public void setRect(Rectangle2D.Float rect) {
		this.rect = rect;
	}
}

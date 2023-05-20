/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: Slime - enemy.
 */

package entities;

import main.Game;
import utils.EnemyType;
import utils.SpriteConsts;

public class Slime extends Enemy
{


	public Slime(float x, float y)
	{
		super(x, y, (int)(SpriteConsts.SLIME.getSpriteWidth() * Game.SCALE * 1.5), (int)(SpriteConsts.SLIME.getSpriteHeight() * Game.SCALE * 1.5), EnemyType.SLIME);
		initRect(x, y, (int)(22 * Game.SCALE * 1.5), (int)(14 * Game.SCALE * 1.5));
	}
}

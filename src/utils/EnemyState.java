/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: All the enemy's states.
 */

package utils;

public enum EnemyState
{
	IDLE(8, 30),
	ATTACK(8, 30),
	DEATH(8, 30);

	private int spriteAmount;
	private int spriteSpeed;

	private EnemyState(int spriteAmount, int spriteSpeed)
	{
		this.spriteAmount = spriteAmount;
		this.spriteSpeed = spriteSpeed;
	}

	public int getSpriteAmount()
	{
		return spriteAmount;
	}

	public int getSpriteSpeed()
	{
		return spriteSpeed;
	}
}

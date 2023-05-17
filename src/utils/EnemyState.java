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

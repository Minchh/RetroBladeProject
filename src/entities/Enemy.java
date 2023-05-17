package entities;

import level.Level;
import level.Tile;
import main.Game;
import utils.EnemyState;
import utils.EnemyType;
import utils.Vector2;

public abstract class Enemy extends Entity
{
	private int aniIndex, aniTick;
	private EnemyState enemyState = EnemyState.IDLE;
	private EnemyType enemyType;
//	private boolean firstUpdate = true;

	private boolean isAttacking, isOutOfBoundY, isOnGround;
	Vector2 direction;
	private float gravity;
	private Level currentLevel;
	private float enemySpeed = 0.7f;
	public int flipX, flipW;
	private Player currentPlayer;

	public Enemy(float x, float y, int width, int height, EnemyType enemyType)
	{
		super(x, y, width, height);
		this.enemyType = enemyType;

		isOnGround = false;
		isOutOfBoundY = false;

		// Movement
		direction = new Vector2(1, 1);
		gravity = 0.04f * Game.SCALE;

		flipX = getWidth();
		flipW = -1;

		initRect(x, y, width, height);
	}

	private void updateAnimationTick()
	{
		aniTick++;
		if (aniTick >= enemyState.getSpriteSpeed())
		{
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= enemyState.getSpriteAmount())
			{
				aniIndex = 0;
				if (isAttacking == true)
				{
					currentPlayer.changeHealth(-5);
				}
				isAttacking = false;
			}
		}
	}

	public void update()
	{
		readyForAttack();
		updateState();
		verticalMovementCollision();
		updateAnimationTick();
	}

	public void setCurrentLevel(Level level)
	{
		this.currentLevel = level;
	}

	private void applyGravity()
	{
		direction.setY(direction.getY() + gravity);
	}

	private void updatePositionVertical()
	{
		isOutOfBoundY = false;

		applyGravity();
		float changeY;
		changeY = direction.getY();
		if (getRect().y + changeY < 0 || getRect().y >= Game.GAME_HEIGHT)
			isOutOfBoundY = true;
		else
			getRect().y += direction.getY();

	}

	private void verticalMovementCollision()
	{
		updatePositionVertical();

		if (isOutOfBoundY)
		{
			return;
		}

		for (Tile t : currentLevel.getTiles())
		{
			if (t.getRect().intersects(getRect()))
			{
				if (direction.getY() > 0)
				{
					getRect().y = t.getRect().y - getRect().height;
					direction.setY(0);
					isOnGround = true;
				}
				else if (direction.getY() < 0)
				{
					getRect().y = t.getRect().y + t.getRect().height;
					direction.setY(0);
				}
			}
		}


		if (isOnGround && direction.getY() < 0 || direction.getY() > 0)
			isOnGround = false;
	}

	private void updatePositionHorizontal()
	{
		for (Tile t : currentLevel.getTiles())
		{
			if (t.getSpriteID() == 21)
			{
				if (!t.getRect().contains(getRect().x + getRect().width, getRect().y + getRect().height)
						&& t.getRect().contains(getRect().x, getRect().y + getRect().height))
				{
//					System.out.println("Slime not touched");
					direction.setX(direction.getX() * -1);
				}
			}
			else if (t.getSpriteID() == 14)
			{
				if (!t.getRect().contains(getRect().x, getRect().y + getRect().height)
						&& t.getRect().contains(getRect().x + getRect().width, getRect().y + getRect().height))
				{
					direction.setX(direction.getX() * -1);
				}

			}
		}
		if (direction.getX() < 0)
		{
			flipX = 0;
			flipW = 1;
		}
		else
		{
			flipX = getWidth();
			flipW = -1;
		}
		getRect().x += direction.getX() * enemySpeed;
	}

	private void readyForAttack()
	{
		if (getRect().intersects(currentPlayer.getRect()))
		{
			enemyState = EnemyState.ATTACK;
			isAttacking = true;
		}
	}

	public void setCurrentPlayer(Player currentPlayer)
	{
		this.currentPlayer = currentPlayer;
	}

	private void resetAniTick()
	{
		aniTick = 0;
		aniIndex = 0;
	}

	private void updateState()
	{
		if (isOnGround)
		{
			switch (enemyState)
			{
				case IDLE:
					updatePositionHorizontal();
					break;
				case ATTACK:
					if (!isAttacking)
					{
						enemyState = EnemyState.IDLE;
						resetAniTick();
					}
					break;
				case DEATH:
					break;
			}
		}
	}

	public int getAniIndex()
	{
		return aniIndex;
	}

	public EnemyState getEnemyState()
	{
		return enemyState;
	}


}

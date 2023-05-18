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

	private boolean isAttacking, isOutOfBoundY, isOnGround, isActived;
	Vector2 direction;
	private float gravity;
	private Level currentLevel;
	private float enemySpeed = 0.7f;
	public int flipX, flipW;
	private Player currentPlayer;
	protected int maxHealth;
	protected int currentHealth;

	public Enemy(float x, float y, int width, int height, EnemyType enemyType)
	{
		super(x, y, width, height);
		this.enemyType = enemyType;

		isOnGround = false;
		isOutOfBoundY = false;
		isActived = true;

		// Movement
		direction = new Vector2(1, 1);
		gravity = 0.04f * Game.SCALE;

		flipX = getWidth();
		flipW = -1;

		initRect(x, y, width, height);
		maxHealth = 10;
		currentHealth = maxHealth;
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
				if (enemyState == EnemyState.ATTACK)
				{
					currentPlayer.changeHealth(-20);
					newState(EnemyState.IDLE);
					isAttacking = false;
				}
				else if (enemyState == EnemyState.DEATH)
				{
					isActived = false;
				}

			}
		}
	}

	public void update()
	{
		if (!isAttacking)
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
			newState(EnemyState.ATTACK);
			isAttacking = true;
		}
	}

	private void newState(EnemyState enemyState)
	{
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}

	public void hurt(int amount)
	{
		currentHealth -= amount;
		if (currentHealth <= 0)
		{
			newState(EnemyState.DEATH);
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

	public boolean isActived()
	{
		return isActived;
	}

	public void setActived(boolean isActived)
	{
		this.isActived = isActived;
	}

	public void resetEnemy()
	{
		getRect().x = x;
		getRect().y = y;
		currentHealth = maxHealth;
		newState(EnemyState.IDLE);
		isActived = true;

	}
}
